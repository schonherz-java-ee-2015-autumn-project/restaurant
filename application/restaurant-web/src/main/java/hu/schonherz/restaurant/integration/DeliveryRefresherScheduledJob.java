package hu.schonherz.restaurant.integration;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.lang3.Validate;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

@SuppressWarnings("deprecation")
public class DeliveryRefresherScheduledJob implements StatefulJob {

	private final int MAX_TRY = 5;
	private final int WAIT_TIME_MILLISEC = 10000;
	
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
//		JobDataMap dataMap = context.getJobDetail().getJobDataMap();
//		int count = dataMap.getIntValue("count");
//
//		if (count >= MAX_TRY) {
//			JobExecutionException e = new JobExecutionException("The fault retry limit has been exceeded");
//			// make sure it doesn't run again
//			e.setUnscheduleAllTriggers(true);
//			throw e;
//		}

//		refresher.refresh();
//
//		try {
//			dataMap.putAsString("count", 0);
//		} catch (Exception e) {
//			count++;
//			dataMap.putAsString("count", count);
//			JobExecutionException e2 = new JobExecutionException(e);
//
//			try {
//				Thread.sleep(WAIT_TIME_MILLISEC);
//			} catch (InterruptedException e1) {
//				
//			}
//			e2.setRefireImmediately(true);
//			throw e2;
//		}
	}

}
