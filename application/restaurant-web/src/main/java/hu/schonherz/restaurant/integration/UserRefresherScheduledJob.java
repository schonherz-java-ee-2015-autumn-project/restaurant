package hu.schonherz.restaurant.integration;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.lang3.Validate;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class UserRefresherScheduledJob implements Job {

	private final int MAX_TRY = 5;
	private final int WAIT_TIME_MILLISEC = 10000;

	Refresher refresher;

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
		refresher.refresh();
		System.out.println("userrefresherjob");

//		JobDataMap dataMap = context.getJobDetail().getJobDataMap();
//		int count = dataMap.getIntValue("count");
//
//		if (count >= MAX_TRY) {
//			JobExecutionException e = new JobExecutionException("The fault retry limit has been exceeded");
//
//			e.setUnscheduleAllTriggers(true);
//			throw e;
//		}
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
//				e1.printStackTrace();
//			}
//			e2.setRefireImmediately(true);
//			throw e2;
//		}
	}

}
