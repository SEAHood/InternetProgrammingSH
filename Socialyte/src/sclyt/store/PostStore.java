package sclyt.store;

public class PostStore {

	String post_full_name;
	String post_tags;
	String post_body;
	
	
	String rowcontents;
	
	
	public void setFullName(String _full_name)
	{
		post_full_name = _full_name;
	}
	
	public void setTags(String _tags)
	{
		post_tags = _tags;
	}
	
	public void setBody(String _body)
	{
		post_body = _body;
	}
	
	public String getFullName()
	{
		return post_full_name;
	}
	
	public String getTags()
	{
		return post_tags;
	}
	
	public String getBody()
	{
		return post_body;
	}
	
	
	public void setRowContents(String newContents)
	{
		rowcontents = newContents;
		
	}
	
	public String getRowContents()
	{
		return rowcontents;
		
	}

}
