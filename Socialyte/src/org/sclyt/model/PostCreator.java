package org.sclyt.model;

public class PostCreator {
	
	String full_name;
	String body;
	String tags;
	
	public PostCreator(String _full_name, String _body, String _tags)
	{
		full_name = _full_name;
		body = _body;
		tags = _tags;
	}
	
	public boolean create()
	{
		DBConnection DBConn = new DBConnection();
		
		if (DBConn.connect())
		{
			if (!DBConn.createPost(full_name, body, tags))
				return false;
		}
		else
			return false;
		
		return true;		
	}

}
