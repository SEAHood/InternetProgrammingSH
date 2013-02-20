package org.sclyt.model;

public class PostCreator {
	
	String username;
	String full_name;
	String body;
	String tags;
	
	public PostCreator(String _username, String _full_name, String _body, String _tags)
	{
		username = _username;
		full_name = _full_name;
		body = _body;
		tags = _tags;
	}
	
	public boolean create()
	{
		DBConnection DBConn = new DBConnection();
		
		if (DBConn.connect())
		{
			if (!DBConn.createPost(username, full_name, body, tags))
				return false;
		}
		else
			return false;
		
		return true;		
	}

}
