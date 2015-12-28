package hu.schonherz.restaurant.integration;

import java.util.Date;

public interface Refresher {

	public void refresh();
	
	public void refreshSince(Date date);
}
