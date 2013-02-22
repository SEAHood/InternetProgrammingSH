package org.sclyt.model;

import java.util.LinkedList;

import org.sclyt.store.ProfileStore;

public class Subscriptions {
	
	String username;
	
	public Subscriptions(String _username)
	{
		username = _username;
	}
	
	public LinkedList<ProfileStore> getSubscriptions()
	{
		DBConnection DBConn = new DBConnection();
		DBConn.connect();
		LinkedList<ProfileStore> subscription_profiles = DBConn.getSubscriptions(username);
		return subscription_profiles;
	}
	
	public boolean removeSubscription(String _subscription_username)
	{
		DBConnection DBConn = new DBConnection();
		DBConn.connect();
		return DBConn.deleteSubscription(username, _subscription_username);
	}
}
