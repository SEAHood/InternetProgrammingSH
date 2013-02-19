package org.sclyt.model;

import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.service.template.ColumnFamilyTemplate;
import me.prettyprint.cassandra.service.template.ThriftColumnFamilyTemplate;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.exceptions.HectorException;
import me.prettyprint.hector.api.factory.HFactory;

public class Signup {
	
	ColumnFamilyTemplate<String, String> usersTemplate;
	ColumnFamilyTemplate<String, String> loginTemplate;
	
	String first_name;
	String surname;
	String username;
	String password;
	String password_c;
	String email;
	String avatar;
	
	public Signup(String _first_name, String _surname, String _username, String _password, String _password_c, String _email, String _avatar)
	{
		first_name = _first_name;
		surname = _surname;
		username = _username;
		password = _password;
		password_c = _password_c;
		email = _email;
		avatar = _avatar;
		
	}
	
	public boolean execute()
	{
		DBConnection DBConn = new DBConnection();

		if (DBConn.connect())
		{
			if (DBConn.createAccount(first_name, surname, username, password, email, avatar))
				return true;
		}
		else
		{
			System.out.println("Signup.execute() failed.");
			return false;
		}
		return false;
	}

}
