package org.sclyt.store;

public class Session {

	String username;
	String full_name;
	String avatar;
	
	
	//-------------MUTATORS------------------------//
	public void setUsername(String _username)
	{
		username = _username;
	}
	
	public void setFullName(String _full_name)
	{
		full_name = _full_name;
	}
	
	public void setAvatar(String _avatar)
	{
		avatar = _avatar;
	}
	
	
	//-------------ACCESSORS-----------------------//
	public String getUsername()
	{
		return username;
	}
	
	public String getFullName()
	{
		return full_name;
	}
	
	public String getAvatar()
	{
		return avatar;
	}
}
