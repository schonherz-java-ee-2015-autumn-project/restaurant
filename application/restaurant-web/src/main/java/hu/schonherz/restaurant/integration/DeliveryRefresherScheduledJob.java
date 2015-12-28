package hu.schonherz.restaurant.integration;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class DeliveryRefresherScheduledJob implements Job {

	Refresher refresher;

	public DeliveryRefresherScheduledJob() {
		super();
		init();
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
		refresher.refresh();
	}

}
