package org.sclyt.model;

import org.sclyt.store.Session;

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
	DBConnection DBConn;
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
		DBConn = new DBConnection();
		boolean success = DBConn.connect();
		return success;
	}
	
	public boolean execute()
	{
		if (DBConn.attemptLogin(login_username, login_password))
			return true;
		else
			return false;		
	}
	
	public Session createSession()
	{
		Session thisSession = new Session();

		String avatar = DBConn.fetchAvatar(login_username);
		String full_name = DBConn.fetchFullName(login_username);
		
		thisSession.setUsername(login_username);
		thisSession.setAvatar(avatar);
		thisSession.setFullName(full_name);		
		
		return thisSession;
	}

}
