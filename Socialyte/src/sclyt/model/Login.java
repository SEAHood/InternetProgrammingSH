package sclyt.model;

import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.service.template.ColumnFamilyResult;
import me.prettyprint.cassandra.service.template.ColumnFamilyTemplate;
import me.prettyprint.cassandra.service.template.ColumnFamilyUpdater;
import me.prettyprint.cassandra.service.template.ThriftColumnFamilyTemplate;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.exceptions.HectorException;
import me.prettyprint.hector.api.factory.HFactory;

public class Login {
	
	ColumnFamilyTemplate<String, String> template;
	
	String login_username;
	String login_password;
	
	public Login(String _username, String _password)
	{
		//Initialise username/password variables
		login_username = _username;
		login_password = _password;
	}
	

	
	public boolean setup()
	{
		try {
			//INITIALISE CONNECTION
			Cluster cassCluster;
			Keyspace cassKeyspace;
			
			
			cassCluster = HFactory.getOrCreateCluster("Test Cluster","77.99.214.115:9160");
			
			cassKeyspace = HFactory.createKeyspace("TESTSPACE", cassCluster);
					
			
			//DEFINE TEMPLATE FOR COLUMNFAMILY
			template =  new ThriftColumnFamilyTemplate<String, String>(cassKeyspace,
	                                                               "LOGIN",
	                                                               StringSerializer.get(),
	                                                               StringSerializer.get());		
			return true;
		}
		catch (HectorException e)
		{
			return false;
		}
	}
	
	public boolean execute()
	{
		ColumnFamilyResult<String, String> res = null;
		boolean connected = false;
		boolean err_found = false;
		
		while (!connected)
		{
			err_found = false;
			
			try
			{
				res = template.queryColumns(login_username);
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
