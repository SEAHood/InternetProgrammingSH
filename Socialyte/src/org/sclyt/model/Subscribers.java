package org.sclyt.model;

import java.util.LinkedList;

import org.sclyt.store.ProfileStore;

public class Subscribers {
String username;
	
	public Subscribers(String _username)
	{
		username = _username;
	}
	
	public LinkedList<ProfileStore> getSubscribers()
	{
		DBConnection DBConn = new DBConnection();
		DBConn.connect();
		LinkedList<ProfileStore> subscribers_profiles = DBConn.getSubscribers(username);
		return subscribers_profiles;
	}
}
