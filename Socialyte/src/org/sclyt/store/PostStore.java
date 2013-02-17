package org.sclyt.store;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PostStore {

	String post_full_name;
	String post_tags;
	String post_body;
	Date post_date;
	
	
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
	
	public void setDate(Date _date)
	{
		post_date = _date;
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
	
	public Date getDate()
	{
		return post_date;
	}
	
	public String getDateAsString()
	{
		//String str_date;
		
		//str_date = post_date.toString();
		
		SimpleDateFormat s = new SimpleDateFormat("HH:mm dd/MM/yy");
		String str_date = s.format(post_date);
		
		return str_date;
	}
	
	public long getDateAsLong()
	{
		return post_date.getTime();
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
