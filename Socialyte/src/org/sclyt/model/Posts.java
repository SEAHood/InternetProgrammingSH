package org.sclyt.model;

import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;

import org.sclyt.store.PostStore;

import me.prettyprint.cassandra.service.template.ColumnFamilyResult;
import me.prettyprint.hector.api.beans.Row;
import me.prettyprint.hector.api.exceptions.HectorException;


public class Posts {
	
	public Posts()
	{
	}
	
	//Returns a linked list of all posts
	public LinkedList<PostStore> getPosts()
	{		
		LinkedList<PostStore> post_list = new LinkedList<PostStore>();
		LinkedList<Row<Long, String, String>> post_list_unformatted = null;
		boolean connected = false;
		boolean err_found = false;
		
		
		while (!connected)
		{
			try 
			{
				DBConnection DBConn = new DBConnection();
				DBConn.connect();
				
				post_list_unformatted = DBConn.queryPosts();
			}
			catch (HectorException e)
			{
				err_found = true;		
			}
			
			if (!err_found)
			{
				connected = true;
			}
		}
		
		Iterator<Row<Long, String, String>> iterator;
		
		iterator = post_list_unformatted.iterator();
		
		while (iterator.hasNext())
		{
			//Compile list
			Row<Long, String, String> row = (Row<Long, String, String>)iterator.next();
			
			PostStore singlePost = new PostStore();
			
			String post_full_name = row.getColumnSlice().getColumnByName("full_name").getValue();
			String post_body = row.getColumnSlice().getColumnByName("body").getValue();
			String post_tags = row.getColumnSlice().getColumnByName("tags").getValue();
			Date post_date = new Date(row.getKey());

			singlePost.setFullName(post_full_name);
			singlePost.setTags(post_tags);
			singlePost.setBody(post_body);
			singlePost.setDate(post_date);
			
			post_list.add(singlePost);
		}
		
		//Sort list
		Collections.sort(post_list);
		
		return post_list;
	}
	
	
	//Returns a list of subscriptions posts
	public LinkedList<PostStore> getSubscriptionPosts(String _username)
	{		
		LinkedList<PostStore> post_list = new LinkedList<PostStore>();
		LinkedList<ColumnFamilyResult<Long, String>> post_list_unformatted = null;
		boolean connected = false;
		boolean err_found = false;
				
		while (!connected)
		{
			try 
			{
				DBConnection DBConn = new DBConnection();
				DBConn.connect();
				
				post_list_unformatted = DBConn.querySubscriptionPosts(_username);
			}
			catch (HectorException e)
			{
				err_found = true;		
			}
			
			if (!err_found)
			{
				connected = true;
			}
		}
				
		Iterator<ColumnFamilyResult<Long, String>> iterator;
		
		iterator = post_list_unformatted.iterator();
		
		while (iterator.hasNext())
		{			
			//Compile list
			ColumnFamilyResult<Long, String> sub_post = iterator.next();
			
			String full_name = sub_post.getString("full_name");
			String body = sub_post.getString("body");
			String tags = sub_post.getString("tags");
			Date date = new Date(sub_post.getKey());


	        PostStore post = new PostStore();
	        post.setFullName(full_name);
	        post.setBody(body);
	        post.setTags(tags);
	        post.setDate(date);

	    	post_list.add(post);
		}
		
		//Sort list
		Collections.sort(post_list);
		
		return post_list;
	}
}
