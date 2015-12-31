package hu.schonherz.restaurant.integration;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.lang3.Validate;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class DeliveryRefresherScheduledJob implements Job {

	private final int MAX_TRY = 5;
	private final int WAIT_TIME_MILLISEC = 10000;
	private final String UNSUCCESFUL_TRIES = "user_unsuccessful_asks";

	Refresher refresher;

	public DeliveryRefresherScheduledJob() {
		super();
		init();
		Validate.notNull(refresher);
	}

	private void init() {
		Context context = null;
		try {
			context = new InitialContext();
			refresher = (RefresherRemote) context
					.lookup("deliveryRefresher#hu.schonherz.restaurant.integration.RefresherRemote");
		} catch (NamingException e) {

		} finally {
			try {
				context.close();
			} catch (NamingException e) {
			}
		}
	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobDataMap dataMap = context.getJobDetail().getJobDataMap();
		int count = 0;
		try {
			count = dataMap.getIntValue(UNSUCCESFUL_TRIES);
		} catch (Exception ex) {
			dataMap.putAsString(UNSUCCESFUL_TRIES, 0);
		}

		if (count >= MAX_TRY) {
			JobExecutionException e1 = new JobExecutionException("The fault retry limit has been exceeded");

			e1.setUnscheduleAllTriggers(true);
			throw e1;
		}

		try {
			refresher.refresh();
			dataMap.putAsString(UNSUCCESFUL_TRIES, 0);
		} catch (Exception e1) {
			count++;
			dataMap.putAsString(UNSUCCESFUL_TRIES, count);
			JobExecutionException e3 = new JobExecutionException(e1);

			try {
				Thread.sleep(WAIT_TIME_MILLISEC);
			} catch (InterruptedException e2) {
				e2.printStackTrace();
			}
			e3.setRefireImmediately(true);
			throw e3;
		}
	}
}
