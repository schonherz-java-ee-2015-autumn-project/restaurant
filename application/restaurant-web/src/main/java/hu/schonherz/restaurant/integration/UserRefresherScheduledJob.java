package hu.schonherz.restaurant.integration;

import java.util.Date;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.lang3.Validate;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

import hu.schonherz.restaurant.integration.exception.RefresherException;

@SuppressWarnings("deprecation")
public class UserRefresherScheduledJob implements StatefulJob {

	private final int MAX_TRY = 5;
	private final int WAIT_TIME_MILLISEC = 10000;
	private final String SUCCESS_ASK = "user_success_ask";
	private final String UNSUCCESFUL_TRIES = "user_unsuccessful_asks";

	RefresherRemote refresher;

	public UserRefresherScheduledJob() {
		super();
		init();
		Validate.notNull(refresher);
	}

	private void init() {
		Context context = null;
		try {
			context = new InitialContext();
			refresher = (RefresherRemote) context
					.lookup("userRefresher#hu.schonherz.restaurant.integration.RefresherRemote");
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
		Date date = (Date) dataMap.get(SUCCESS_ASK);
		try {
			if (date == null) {
				refresher.refresh();
			} else {
				refresher.refreshSince(date);
			}
			dataMap.put(SUCCESS_ASK, new Date());
		} catch (RefresherException e) {
			int count = dataMap.getIntValue(UNSUCCESFUL_TRIES);

			if (count >= MAX_TRY) {
				JobExecutionException e1 = new JobExecutionException("The fault retry limit has been exceeded");

				e1.setUnscheduleAllTriggers(true);
				throw e1;
			}

			try {
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

}
