package hu.schonherz.restaurant.integration;

import java.util.Date;

import hu.schonherz.restaurant.integration.exception.RefresherException;

public interface RefresherRemote  {
	
	public void refresh() throws RefresherException;

	public void refreshSince(Date date) throws RefresherException;
}
