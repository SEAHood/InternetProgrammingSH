package org.sclyt.model;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.UUID;

import org.sclyt.store.ProfileStore;

import me.prettyprint.cassandra.serializers.LongSerializer;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.service.template.ColumnFamilyResult;
import me.prettyprint.cassandra.service.template.ColumnFamilyTemplate;
import me.prettyprint.cassandra.service.template.ColumnFamilyUpdater;
import me.prettyprint.cassandra.service.template.ThriftColumnFamilyTemplate;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.beans.OrderedRows;
import me.prettyprint.hector.api.beans.Row;
import me.prettyprint.hector.api.ddl.KeyspaceDefinition;
import me.prettyprint.hector.api.exceptions.HectorException;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.query.QueryResult;
import me.prettyprint.hector.api.query.RangeSlicesQuery;

public class DBConnection {
	
	ColumnFamilyTemplate<String, String> UserTemplate;
	ColumnFamilyTemplate<String, String> LoginTemplate;
	ColumnFamilyTemplate<String, String> SubscribedToByTemplate;
	ColumnFamilyTemplate<String, String> SubscribesToTemplate;
	ColumnFamilyTemplate<UUID, String> UUIDTemplate;
	ColumnFamilyTemplate<String, Long> UserPostTemplate;
	ColumnFamilyTemplate<Long, String> PostTemplate;
	Cluster cassCluster;
	Keyspace cassKeyspace;
	String _CF;
	String ksp = "SOCIALYTE";
	
	public DBConnection()
	{
	}
	
	//Initial connection for DBConnection
	public boolean connect()
	{		
		//Messy error avoidance - All host pools marked down - didn't find solution
		boolean connected = false;
		boolean err_found = false;
	
		while (!connected)
		{
			try 
			{
				//INITIALISE CONNECTION
				
				cassCluster = HFactory.getOrCreateCluster("Test Cluster","77.99.214.115:9160");
				
				//Attempt to describe keyspace
				KeyspaceDefinition keyspaceDef = cassCluster.describeKeyspace(ksp);
				
				// If keyspace doesn't exist, create it and set CFs up
				if (keyspaceDef == null) {
					cassKeyspace = HFactory.createKeyspace(ksp, cassCluster);
					CassandraDefinition cassDef = new CassandraDefinition(ksp);
					cassDef.setup();
				}
				else
				{
					//For whatever reason it will only work if I use this
					cassKeyspace = HFactory.createKeyspace(ksp, cassCluster);
				}
				
				
				//DEFINE TEMPLATE FOR COLUMNFAMILY
				UserTemplate =  new ThriftColumnFamilyTemplate<String, String>(cassKeyspace,
		                                                               "USER",
		                                                               StringSerializer.get(),
		                                                               StringSerializer.get());	
				
				//DEFINE TEMPLATE FOR COLUMNFAMILY
				LoginTemplate =  new ThriftColumnFamilyTemplate<String, String>(cassKeyspace,
		                                                               "LOGIN",
		                                                               StringSerializer.get(),
		                                                               StringSerializer.get());	
				
				//DEFINE TEMPLATE FOR COLUMNFAMILY
				SubscribedToByTemplate =  new ThriftColumnFamilyTemplate<String, String>(cassKeyspace,
		                                                               "SUBSCRIBED_TO_BY",
		                                                               StringSerializer.get(),
		                                                               StringSerializer.get());	
				
				//DEFINE TEMPLATE FOR COLUMNFAMILY
				SubscribesToTemplate =  new ThriftColumnFamilyTemplate<String, String>(cassKeyspace,
		                                                               "SUBSCRIBES_TO",
		                                                               StringSerializer.get(),
		                                                               StringSerializer.get());	
				
				//DEFINE TEMPLATE FOR COLUMNFAMILY
				UserPostTemplate =  new ThriftColumnFamilyTemplate<String, Long>(cassKeyspace,
		                                                               "USER_POST",
		                                                               StringSerializer.get(),
		                                                               LongSerializer.get());	
				
				//DEFINE TEMPLATE FOR COLUMNFAMILY
				PostTemplate =  new ThriftColumnFamilyTemplate<Long, String>(cassKeyspace,
		                                                               "ALL_POSTS",
		                                                               LongSerializer.get(),
		                                                               StringSerializer.get());	
			
				return true;
			}
			catch (HectorException e)
			{
				System.out.println("HectorException @ DBConnection.connect(): " + e.getMessage());
				err_found = true;		
			}
			
			if (!err_found)
			{
				connected = true;
			}
		}
		return false;
	}
	
	
	//Return true if username exists (signup validation)
	public boolean checkUsernameExists(String username)
	{
		ColumnFamilyResult<String, String> res = null;
		boolean connected = false;
		boolean err_found = false;
		
		//Error avoidance
		while (!connected)
		{
			err_found = false;
			
			try
			{
				res = LoginTemplate.queryColumns(username);
			}
			catch (HectorException e)
			{
				System.out.println(e.getMessage());
				err_found = true;		
			}
			
			if (!err_found)
			{
				connected = true;
			}
		}
		
		System.out.println(res.getString("password"));
		
		if (res.getString("password") != null)
			return true;
		else
			return false;
	}
	
	
	//Fetches an avatar from <username>
	public String fetchAvatar(String username)
	{
		String avatar;
		
		ColumnFamilyResult<String, String> res = null;
		boolean connected = false;
		boolean err_found = false;
		
		//Error avoidance
		while (!connected)
		{
			err_found = false;
			
			try
			{
				res = UserTemplate.queryColumns(username);
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
		
		
		avatar = res.getString("avatar");
		
		if (avatar == null)
			avatar = "/Socialyte/img/profiles/default.png";
		
		return avatar;
	}
	
	
	//Fetches a full name from <username>
	public String fetchFullName(String username)
	{
		String first_name;
		String surname;
		
		ColumnFamilyResult<String, String> res = null;
		boolean connected = false;
		boolean err_found = false;
		
		//Error avoidance
		while (!connected)
		{
			err_found = false;
			
			try
			{
				res = UserTemplate.queryColumns(username);
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
		
		
		first_name = res.getString("first_name");
		surname = res.getString("surname");
		
		String full_name = first_name + " " + surname;
		
		if (first_name == null || surname == null)
			full_name = username;
		
		return full_name;
	}
		
	
	//Fetches a single profile
	public ProfileStore fetchProfile(String username)
	{
		String first_name;
		String surname;
		Date dob;
		String email;
		String city;
		String country;
		String avatar;
		
		ProfileStore user_profile = new ProfileStore();
		
		ColumnFamilyResult<String, String> res = null;
		boolean connected = false;
		boolean err_found = false;
				
		//Error avoidance
		while (!connected)
		{
			err_found = false;
			
			try
			{
				
				res = UserTemplate.queryColumns(username);
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
				
		first_name = res.getString("first_name");
		surname = res.getString("surname");
		dob = res.getDate("dob");
		email = res.getString("email");
		city = res.getString("city");
		country = res.getString("country");
		avatar = res.getString("avatar");
		
		
		user_profile.setUsername(username);
		user_profile.setFirstName(first_name);
		user_profile.setSurname(surname);
		user_profile.setDOB(dob);
		user_profile.setEmail(email);
		user_profile.setCity(city);
		user_profile.setCountry(country);
		user_profile.setAvatar(avatar);
		user_profile.normaliseNulls();
		
		return user_profile;
	}
	
	
	//Attempts to log the user into the system
	public boolean attemptLogin(String login_username, String login_password)
	{
		ColumnFamilyResult<String, String> res = null;
		boolean connected = false;
		boolean err_found = false;
		
		//Error avoidance
		while (!connected)
		{
			err_found = false;
			
			try
			{
				res = LoginTemplate.queryColumns(login_username);
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
		
		
		String value = res.getString("password");
	    
	    if (value != null)
	    {
		    if (value.equals(login_password))
		    	return true;
		    else
		    	return false;
	    }
	    else
	    	return false;
	}
	
		
	public LinkedList<Row<Long, String, String>> queryPosts()
	{		
		LinkedList<Row<Long, String, String>> llist = new LinkedList<Row<Long, String, String>>();
		
		int row_count = 100;

        RangeSlicesQuery<Long, String, String> rangeSlicesQuery = HFactory
            .createRangeSlicesQuery(cassKeyspace, LongSerializer.get(), StringSerializer.get(), StringSerializer.get())
            .setColumnFamily("ALL_POSTS")
            .setRange("", "", false, 100)
            .setRowCount(row_count);

        rangeSlicesQuery.setKeys(null, null);
        QueryResult<OrderedRows<Long, String, String>> result = rangeSlicesQuery.execute();
        OrderedRows<Long, String, String> rows = result.get();
        Iterator<Row<Long, String, String>> rowsIterator = rows.iterator();
 

        while (rowsIterator.hasNext()) 
        {
        	Row<Long, String, String> row = rowsIterator.next();

            if (row.getColumnSlice().getColumns().isEmpty()) 
                continue;

            llist.add(row);
        }	    
	    return llist;
	}
	
	
	//Returns a list of posts by <username>'s subscriptions
	public LinkedList<ColumnFamilyResult<Long, String>> querySubscriptionPosts(String _username) //LinkedList<Row<Long, String, String>>
	{		
		LinkedList<ColumnFamilyResult<Long, String>> all_sub_posts = new LinkedList<ColumnFamilyResult<Long, String>>();
		
		//Find user's subscriptions
		ColumnFamilyResult<String, String> res = null;
		boolean connected = false;
		boolean err_found = false;
		
		//Error avoidance
		while (!connected)
		{
			err_found = false;
			
			try
			{
				res = SubscribesToTemplate.queryColumns(_username);
			}
			catch (HectorException e)
			{
				System.out.println(e.getMessage());
				err_found = true;		
			}
			
			if (!err_found)
			{
				connected = true;
			}
		}
		
		Collection<String> subscriptions = res.getColumnNames();
		for (Iterator<String> iterator = subscriptions.iterator(); iterator.hasNext();)
		{
			String sub_username = (String)iterator.next();
			
			ColumnFamilyResult<String, Long> sub_post_list = null;
			
			boolean conn = false;
			boolean err = false;
					
			//Error avoidance
			while (!conn)
			{
				err = false;
				
				try
				{
					sub_post_list = UserPostTemplate.queryColumns(sub_username);
				}
				catch (HectorException e)
				{
					System.out.println(e.getMessage());
					err = true;		
				}
				
				if (!err)
				{
					conn = true;
				}
			}
			
			Collection<Long> sub_post_ids = sub_post_list.getColumnNames();
			
			for (Iterator<Long> post_iterator = sub_post_ids.iterator(); post_iterator.hasNext();)
			{
				
				Long post_id = post_iterator.next();
				ColumnFamilyResult<Long, String> sub_post = null;
				sub_post = PostTemplate.queryColumns(post_id);
				
				
	            System.out.println(sub_post.toString());
	        	all_sub_posts.add(sub_post);
			}
		}
	    return all_sub_posts;
	}
	
	
	//Returns a list of <username>'s subscriptions
	public LinkedList<ProfileStore> getSubscriptions(String _username)
	{
		LinkedList<ProfileStore> subscription_profiles = new LinkedList<ProfileStore>();
		ColumnFamilyResult<String, String> res = null;
		boolean connected = false;
		boolean err_found = false;
		
		//Error avoidance
		while (!connected)
		{
			err_found = false;
			
			try
			{
				res = SubscribesToTemplate.queryColumns(_username);
			}
			catch (HectorException e)
			{
				System.out.println(e.getMessage());
				err_found = true;		
			}
			
			if (!err_found)
			{
				connected = true;
			}
		}
		
		Collection<String> subscriptions = res.getColumnNames();
		for (Iterator<String> iterator = subscriptions.iterator(); iterator.hasNext();)
		{
			String subscription_username = iterator.next();
			System.out.println(subscription_username);
			ProfileStore profile = fetchProfile(subscription_username);
			subscription_profiles.add(profile);
			System.out.println(profile.getFirstName());
		}
		
		return subscription_profiles;
	}
	
	
	//Returns a list of <username>'s subscribers
	public LinkedList<ProfileStore> getSubscribers(String _username)
	{
		LinkedList<ProfileStore> subscriber_profiles = new LinkedList<ProfileStore>();
		ColumnFamilyResult<String, String> res = null;
		boolean connected = false;
		boolean err_found = false;
		
		//Error avoidance
		while (!connected)
		{
			err_found = false;
			
			try
			{
				res = SubscribedToByTemplate.queryColumns(_username);
			}
			catch (HectorException e)
			{
				System.out.println(e.getMessage());
				err_found = true;		
			}
			
			if (!err_found)
			{
				connected = true;
			}
		}
		
		Collection<String> subscribers = res.getColumnNames();
		for (Iterator<String> iterator = subscribers.iterator(); iterator.hasNext();)
		{
			String subscriber_username = iterator.next();
			System.out.println(subscriber_username);
			ProfileStore profile = fetchProfile(subscriber_username);
			subscriber_profiles.add(profile);
			System.out.println(profile.getFirstName());
		}
		
		return subscriber_profiles;
	}
	
	
	//Creates a post
	public boolean createPost(String _username, String _full_name, String _body, String _tags)
	{
		long timestamp = System.currentTimeMillis();
		// UPDATE / CREATE CODE
		// <String, String> correspond to key and Column name.
		ColumnFamilyUpdater<Long, String> nameUpdater = PostTemplate.createUpdater(timestamp);
		nameUpdater.setString("full_name", _full_name);
		
		ColumnFamilyUpdater<Long, String> bodyUpdater = PostTemplate.createUpdater(timestamp);
		bodyUpdater.setString("body", _body);
		
		ColumnFamilyUpdater<Long, String> tagsUpdater = PostTemplate.createUpdater(timestamp);
		tagsUpdater.setString("tags", _tags);
		
		
		ColumnFamilyUpdater<String, Long> userPostUpdater = UserPostTemplate.createUpdater(_username);
		userPostUpdater.setString(timestamp, "test");

		
		try {
			PostTemplate.update(nameUpdater);
			PostTemplate.update(bodyUpdater);
			PostTemplate.update(tagsUpdater);	
			UserPostTemplate.update(userPostUpdater);
		} catch (HectorException e) {
			System.out.println(e.getMessage());
		    return false;
		}
		
		return true;
	}
	
	
	//Creates an account
	public boolean createAccount(String _first_name, String _surname, String _username, String _password, String _email, String _avatar)
	{
		// UPDATE / CREATE CODE
		// <String, String> correspond to key and Column name.
		ColumnFamilyUpdater<String, String> first_name = UserTemplate.createUpdater(_username);
		first_name.setString("first_name", _first_name);
		
		ColumnFamilyUpdater<String, String> surname = UserTemplate.createUpdater(_username);
		surname.setString("surname", _surname);
		
		ColumnFamilyUpdater<String, String> email = UserTemplate.createUpdater(_username);
		email.setString("email", _email);
		
		ColumnFamilyUpdater<String, String> avatar = UserTemplate.createUpdater(_username);
		avatar.setString("avatar", _avatar);
		
		
		ColumnFamilyUpdater<String, String> password = LoginTemplate.createUpdater(_username);
		password.setString("password", _password);

		boolean connected = false;
		boolean err_found = false;
		
		
		//Error avoidance
		while (!connected)
		{
			err_found = false;
			
			try
			{
				UserTemplate.update(first_name);
				UserTemplate.update(surname);
				UserTemplate.update(email);	
				UserTemplate.update(avatar);	
				
				LoginTemplate.update(password);
			}
			catch (HectorException e)
			{
				System.out.println(e.getMessage());
				err_found = true;		
			}
			
			if (!err_found)
			{
				connected = true;
			}
		}
		return true;
	}
	
	
	//Searches profiles and returns any matches
	public LinkedList<ProfileStore> searchAll(String srch_first_name, String srch_surname, String srch_email, String srch_city)
	{
		LinkedList<ProfileStore> allProfiles = getAllUserProfiles();
		LinkedList<ProfileStore> matchingProfiles = new LinkedList<ProfileStore>();
		
		Iterator<ProfileStore> iterator = allProfiles.iterator();
		
		while (iterator.hasNext())
		{
			ProfileStore single_profile = iterator.next();
			
			if (srch_first_name != "")
			{
				if (single_profile.getFirstName().toLowerCase().equals(srch_first_name))
				{
					matchingProfiles.add(single_profile);
					continue;
				}
			}
			
			if (srch_surname != "")
			{
				if (single_profile.getSurname().toLowerCase().equals(srch_surname))
				{
					matchingProfiles.add(single_profile);
					continue;
				}
			}
			
			if (srch_email != "")
			{
				if (single_profile.getEmail().toLowerCase().equals(srch_email))
				{
					matchingProfiles.add(single_profile);
					continue;
				}
			}
			
			if (srch_city != "")
			{
				if (single_profile.getCity().toLowerCase().equals(srch_city))
				{
					matchingProfiles.add(single_profile);
					continue;
				}
			}
			
		}
		
		return matchingProfiles;
	}
	
	
	//Returns all user profiles
	public LinkedList<ProfileStore> getAllUserProfiles()
	{
		LinkedList<ProfileStore> result_profiles = new LinkedList<ProfileStore>();
		boolean connected = false;
		boolean err_found = false;
		
		//Error avoidance
		while (!connected)
		{
			err_found = false;
			
			try
			{				
				int row_count = 100;

		        RangeSlicesQuery<String, String, String> rangeSlicesQuery = HFactory
		            .createRangeSlicesQuery(cassKeyspace, StringSerializer.get(), StringSerializer.get(), StringSerializer.get())
		            .setColumnFamily("USER")
		            .setRange("", "", false, 100)
		            .setRowCount(row_count);

		        rangeSlicesQuery.setKeys(null, null);
		        QueryResult<OrderedRows<String, String, String>> result = rangeSlicesQuery.execute();
		        OrderedRows<String, String, String> rows = result.get();
		        Iterator<Row<String, String, String>> rowsIterator = rows.iterator();
		 

		        while (rowsIterator.hasNext()) 
		        {
		        	Row<String, String, String> row = rowsIterator.next();

		            if (row.getColumnSlice().getColumns().isEmpty()) 
		                continue;

		            ProfileStore this_profile = fetchProfile(row.getKey());
		            result_profiles.add(this_profile);
		        }
			}
			catch (HectorException e)
			{
				System.out.println(e.getMessage());
				err_found = true;		
			}
			
			if (!err_found)
			{
				connected = true;
			}
		}
		
		return result_profiles;
	}
	
	
	//Adds a subscription for <username>
	public boolean addSubscription(String _username, String _subscription_username)
	{
		System.out.println("1: " + _username + "     2: " + _subscription_username);
		
		ColumnFamilyUpdater<String, String> new_subscription = SubscribesToTemplate.createUpdater(_username);
		new_subscription.setString(_subscription_username, "");
		
		ColumnFamilyUpdater<String, String> new_subscriber = SubscribedToByTemplate.createUpdater(_subscription_username);
		new_subscriber.setString(_username, "");
		
		try
		{
			SubscribesToTemplate.update(new_subscription);
			SubscribedToByTemplate.update(new_subscriber);
		}
		catch (HectorException e)
		{
			System.out.println(e.getMessage());
			return false;
		}
		
		return true;
	}
	
	
	//Removes subscription of <username>
	public boolean deleteSubscription(String _username, String _subscription_username)
	{
		try {
		    SubscribesToTemplate.deleteColumn(_username, _subscription_username);
		    SubscribedToByTemplate.deleteColumn(_subscription_username, _username);
			return true;
		} catch (HectorException e) {
		    return false;
		}
	}
	
	
	//Removes subscriber of <username>
	public boolean deleteSubscriber(String _username, String _subscriber_username)
	{
		try {
		    SubscribedToByTemplate.deleteColumn(_username, _subscriber_username);
		    SubscribesToTemplate.deleteColumn(_subscriber_username, _username);
			return true;
		} catch (HectorException e) {
		    return false;
		}
	}

}
