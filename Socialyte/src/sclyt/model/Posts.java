package sclyt.model;

import java.text.Collator;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.UUID;

import me.prettyprint.cassandra.utils.TimeUUIDUtils;
import me.prettyprint.hector.api.beans.Row;

import sclyt.store.PostStore;

public class Posts {
	
	public Posts()
	{
		
	}
	
	public LinkedList<PostStore> getPosts()
	{
		int post_count = 0;
		
		LinkedList<PostStore> postList = new LinkedList<PostStore>();
		
		DBConnection DBConn = new DBConnection();
		DBConn.connect("ALL_POSTS");
		
		LinkedList<Row<Long, String, String>> llist = DBConn.queryPosts();
		
		Iterator<Row<Long, String, String>> iterator;
		
		iterator = llist.iterator();
		
		while (iterator.hasNext())
		{
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
			
			postList.add(singlePost);
			post_count++;
		}

		//BIG SHITTY SORT
		/*long[] sorted_dates = new long[post_count];
		int count = 0;
		
		LinkedList<Row<Long, String, String>> sorted_list;
		
		Iterator<Row<Long, String, String>> sort_iterator;
		
		sort_iterator = llist.iterator();
		
		while (sort_iterator.hasNext())
		{
			Row<Long, String, String> row = (Row<Long, String, String>)sort_iterator.next();
			sorted_dates[count] = row.getKey();
			count++;
		}
		
		sort_iterator = llist.iterator();
		
		while (sort_iterator.hasNext())
		{
			for (int i = 0; i < post_count; i++)
			{
				
			
			}
		}*/
		
		return postList;
	}

}
