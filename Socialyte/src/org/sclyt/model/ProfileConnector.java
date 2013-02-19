package org.sclyt.model;

import org.sclyt.store.ProfileStore;

public class ProfileConnector {
	
	String username;
	DBConnection DBConn;
	
	public ProfileConnector(String _username)
	{
		username = _username;
	}
	
	public boolean setup()
	{
		DBConn = new DBConnection();
		boolean success = DBConn.connect();
		return success;
	}
	
	public ProfileStore execute()
	{
		ProfileStore profile_details = DBConn.fetchProfile(username);	
		
		return profile_details;
	}

}
