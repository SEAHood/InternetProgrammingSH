package org.sclyt.model;

import java.util.LinkedList;

import org.sclyt.store.ProfileStore;

public class Subscribers {
String username;
	
	public Subscribers(String _username)
	{
		username = _username;
	}
	
	//Returns a list of subscribers for <username>
	public LinkedList<ProfileStore> getSubscribers()
	{
		DBConnection DBConn = new DBConnection();
		DBConn.connect();
		LinkedList<ProfileStore> subscribers_profiles = DBConn.getSubscribers(username);
		return subscribers_profiles;
	}
	
	//Removes subscriber
	public boolean deleteSubscriber(String _sub_username)
	{
		DBConnection DBConn = new DBConnection();
		DBConn.connect();
		return DBConn.deleteSubscriber(username, _sub_username);
	}
}
