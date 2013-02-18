package org.sclyt.model;

import java.text.Collator;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.UUID;

import org.sclyt.store.PostStore;

import me.prettyprint.cassandra.utils.TimeUUIDUtils;
import me.prettyprint.hector.api.beans.Row;
import me.prettyprint.hector.api.exceptions.HectorException;


public class Posts {
	
	public Posts()
	{
		
	}
	
	public LinkedList<PostStore> getPosts()
	{
		int post_count = 0;
		
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
			post_count++;
		}

		//BIG SHITTY SORT////////////////////////////////////////////////////////////////////////////////////////////
		System.out.println("Post sort started..");
		long[] sorted_dates = new long[post_count];
		int countT = 0;
		int count1 = 0;
		int count2 = 0;
		int count3 = 0;
		
		Iterator<Row<Long, String, String>> post_iterator;
		
		post_iterator = post_list_unformatted.iterator();
		
		while (post_iterator.hasNext())
		{
			Row<Long, String, String> row = (Row<Long, String, String>)post_iterator.next();
			sorted_dates[countT] = row.getKey();
			countT++;
			count1++;
		}
				
		//Sort longs
		long temp;
		
		while (!isSorted(sorted_dates))
		{
			for (int j = 0; j < sorted_dates.length - 1; j++)
			{
				temp = sorted_dates[j];
				
				if (sorted_dates[j+1] > temp)
				{
					sorted_dates[j] = sorted_dates[j+1];
					sorted_dates[j+1] = temp;
					countT++;
					count2++;
					continue;
				}
			}
		}
		
				
		LinkedList<PostStore> sorted_list = new LinkedList<PostStore>();
				
		for (int i = 0; i < sorted_dates.length; i++)
		{
			Iterator<PostStore> sort_iterator = post_list.iterator();
			while (sort_iterator.hasNext())
			{
				PostStore post = (PostStore)sort_iterator.next();
				
				//System.out.println("sorted_dates[i]: " + sorted_dates[i]);
				if (sorted_dates[i] == post.getDateAsLong())
				{
					sorted_list.add(post);
					countT++;
					count3++;
					break;
				}
				countT++;
				count3++;
			}
		}
		
		System.out.println("Sorting posts completed: " + countT + " cycles done in total.");
		System.out.println("While 1: " + count1 + " - While 2: " + count2 + " - While 3: " + count3);
		System.out.println("Post sort ended..\n\n");
		//END OF SHITTY SORT///////////////////////////////////////////////////////////////////////////////////////
		
		return sorted_list;
	}
	
	
	private boolean isSorted(long[] array)
	{
		for (int i = 0; i < array.length - 1; i++)
		{
			if (array[i+1] > array[i])
				return false;
		}
		return true;
	}

}
