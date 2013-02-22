package org.sclyt.model;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.UUID;

import org.sclyt.store.PostStore;
import org.sclyt.store.ProfileStore;

import me.prettyprint.cassandra.serializers.LongSerializer;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.serializers.UUIDSerializer;
import me.prettyprint.cassandra.service.template.ColumnFamilyResult;
import me.prettyprint.cassandra.service.template.ColumnFamilyTemplate;
import me.prettyprint.cassandra.service.template.ColumnFamilyUpdater;
import me.prettyprint.cassandra.service.template.ThriftColumnFamilyTemplate;
import me.prettyprint.cassandra.utils.TimeUUIDUtils;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.beans.OrderedRows;
import me.prettyprint.hector.api.beans.Row;
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
	
	public DBConnection()
	{
		
	}
	
	public boolean connect()
	{		
		boolean connected = false;
		boolean err_found = false;
	
	
		while (!connected)
		{
			try 
			{
				//INITIALISE CONNECTION
				
				
				
				cassCluster = HFactory.getOrCreateCluster("Test Cluster","77.99.214.115:9160");
				
				cassKeyspace = HFactory.createKeyspace("TESTSPACE", cassCluster);
						
			
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
		                                                               "REVERSE_TEST",
		                                                               StringSerializer.get(),
		                                                               LongSerializer.get());	
			
				//DEFINE TEMPLATE FOR COLUMNFAMILY
				/*UUIDTemplate =  new ThriftColumnFamilyTemplate<UUID, String>(cassKeyspace,
		                                                               columnFamily,
		                                                               UUIDSerializer.get(),
		                                                               StringSerializer.get());	*/
				
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
	
	public boolean checkUsernameExists(String username)
	{
		ColumnFamilyResult<String, String> res = null;
		boolean connected = false;
		boolean err_found = false;
		
		
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
	
	
	public String fetchAvatar(String username)
	{
		String avatar;
		
		ColumnFamilyResult<String, String> res = null;
		boolean connected = false;
		boolean err_found = false;
		
		
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
	
	
	public String fetchFullName(String username)
	{
		String first_name;
		String surname;
		
		ColumnFamilyResult<String, String> res = null;
		boolean connected = false;
		boolean err_found = false;
		
		
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
		
		return user_profile;
	}
	
	
	public boolean attemptLogin(String login_username, String login_password)
	{
		ColumnFamilyResult<String, String> res = null;
		boolean connected = false;
		boolean err_found = false;
		
		
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
	
	
	public LinkedList<ColumnFamilyResult<Long, String>> querySubscriptionPosts(String _username) //LinkedList<Row<Long, String, String>>
	{		
		LinkedList<ColumnFamilyResult<Long, String>> all_sub_posts = new LinkedList<ColumnFamilyResult<Long, String>>();
		
		//Find user's subscriptions
		ColumnFamilyResult<String, String> res = null;
		boolean connected = false;
		boolean err_found = false;
		
		
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
	
	
	public LinkedList<ProfileStore> getSubscriptions(String _username)
	{
		LinkedList<ProfileStore> subscription_profiles = new LinkedList<ProfileStore>();
		ColumnFamilyResult<String, String> res = null;
		boolean connected = false;
		boolean err_found = false;
		
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
	
	
	public LinkedList<ProfileStore> getSubscribers(String _username)
	{
		LinkedList<ProfileStore> subscriber_profiles = new LinkedList<ProfileStore>();
		ColumnFamilyResult<String, String> res = null;
		boolean connected = false;
		boolean err_found = false;
		
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
	
	
	public boolean createPost(String _username, String _full_name, String _body, String _tags)
	{
		UUID timeUUID = generateTimeUUID();
		long timestamp = System.currentTimeMillis();
		// UPDATE / CREATE CODE
		// <String, String> correspond to key and Column name.
		//ColumnFamilyUpdater<UUID, String> nameUpdater = UUIDTemplate.createUpdater(timeUUID);
		ColumnFamilyUpdater<Long, String> nameUpdater = PostTemplate.createUpdater(timestamp);
		nameUpdater.setString("full_name", _full_name);
		nameUpdater.setLong("time", System.currentTimeMillis());
		
		//ColumnFamilyUpdater<UUID, String> bodyUpdater = UUIDTemplate.createUpdater(timeUUID);
		ColumnFamilyUpdater<Long, String> bodyUpdater = PostTemplate.createUpdater(timestamp);
		bodyUpdater.setString("body", _body);
		bodyUpdater.setLong("time", System.currentTimeMillis());
		
		//ColumnFamilyUpdater<UUID, String> tagsUpdater = UUIDTemplate.createUpdater(timeUUID);
		ColumnFamilyUpdater<Long, String> tagsUpdater = PostTemplate.createUpdater(timestamp);
		tagsUpdater.setString("tags", _tags);
		tagsUpdater.setLong("time", System.currentTimeMillis());
		
		
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
	
	
	public boolean createAccount(String _first_name, String _surname, String _username, String _password, String _email, String _avatar)
	{
		long timestamp = System.currentTimeMillis();
		// UPDATE / CREATE CODE
		// <String, String> correspond to key and Column name.
		//ColumnFamilyUpdater<UUID, String> nameUpdater = UUIDTemplate.createUpdater(timeUUID);
		ColumnFamilyUpdater<String, String> first_name = UserTemplate.createUpdater(_username);
		first_name.setString("first_name", _first_name);
		first_name.setLong("time", System.currentTimeMillis());
		
		//ColumnFamilyUpdater<UUID, String> bodyUpdater = UUIDTemplate.createUpdater(timeUUID);
		ColumnFamilyUpdater<String, String> surname = UserTemplate.createUpdater(_username);
		surname.setString("surname", _surname);
		surname.setLong("time", System.currentTimeMillis());
		
		//ColumnFamilyUpdater<UUID, String> tagsUpdater = UUIDTemplate.createUpdater(timeUUID);
		ColumnFamilyUpdater<String, String> email = UserTemplate.createUpdater(_username);
		email.setString("email", _email);
		email.setLong("time", System.currentTimeMillis());
		
		//ColumnFamilyUpdater<UUID, String> tagsUpdater = UUIDTemplate.createUpdater(timeUUID);
		ColumnFamilyUpdater<String, String> avatar = UserTemplate.createUpdater(_username);
		avatar.setString("avatar", _avatar);
		avatar.setLong("time", System.currentTimeMillis());
		
		
		
		//ColumnFamilyUpdater<UUID, String> tagsUpdater = UUIDTemplate.createUpdater(timeUUID);
		ColumnFamilyUpdater<String, String> password = LoginTemplate.createUpdater(_username);
		password.setString("password", _password);
		password.setLong("time", System.currentTimeMillis());

		boolean connected = false;
		boolean err_found = false;
		
		
		
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
	
	
	public boolean deleteSubscription(String _username, String _subscription_username)
	{
		try {
		    SubscribesToTemplate.deleteColumn(_username, _subscription_username);
			return true;
		} catch (HectorException e) {
		    return false;
		}
	}
	
		
	private UUID generateTimeUUID()
	{
		UUID timeUUID = TimeUUIDUtils.getUniqueTimeUUIDinMillis();
		return timeUUID;
	}
		
	//CONNECTION SETUP
	
	
	/*
	// READ CODE
    ColumnFamilyResult<String, String> res = template.queryColumns("shood");
    String value = res.getString("full_name");
    //out.println("<p>The full name of user 'shood' is: " + value + "</p>"); //DISPLAY
	    
	
	// UPDATE / CREATE CODE
	// <String, String> correspond to key and Column name.
	ColumnFamilyUpdater<String, String> updater = template.createUpdater("jbloggs");
	updater.setString("full_name", "Joe Bloggs");
	updater.setLong("time", System.currentTimeMillis());

	try {
	    template.update(updater);
	} catch (HectorException e) {
	    // do something ...
	}*/

}
