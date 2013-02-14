package sclyt.model;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.UUID;

import me.prettyprint.hector.api.beans.Row;

import sclyt.store.PostStore;

public class Posts {
	
	public Posts()
	{
		
	}
	
	public LinkedList<PostStore> getPosts()
	{
		LinkedList<PostStore> postList = new LinkedList<PostStore>();
		
		DBConnection DBConn = new DBConnection();
		DBConn.connect("POSTS");
		
		LinkedList<Row<UUID, String, String>> llist = DBConn.queryPosts();
		
		Iterator<Row<UUID, String, String>> iterator;
		
		iterator = llist.iterator();
		
		while (iterator.hasNext())
		{
			Row<UUID, String, String> row = (Row<UUID, String, String>)iterator.next();
			
			PostStore singlePost = new PostStore();
			
			String post_full_name = row.getColumnSlice().getColumnByName("full_name").getValue();
			String post_body = row.getColumnSlice().getColumnByName("body").getValue();
			String post_tags = row.getColumnSlice().getColumnByName("tags").getValue();

			singlePost.setFullName(post_full_name);
			singlePost.setTags(post_tags);
			singlePost.setBody(post_body);
			
			postList.add(singlePost);
			
		}
		
		return postList;
	}

}
