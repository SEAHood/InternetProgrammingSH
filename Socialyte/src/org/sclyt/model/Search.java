package org.sclyt.model;

import java.util.LinkedList;

import org.sclyt.store.ProfileStore;

public class Search {
	
	String srch_first_name;
	String srch_surname;
	String srch_email;
	String srch_city;
	DBConnection DBConn;
	
	
	//Sets up the search
	public Search (String _first_name, String _surname, String _email, String _city)
	{
		srch_first_name = _first_name.toLowerCase();
		srch_surname = _surname.toLowerCase();
		srch_email = _email.toLowerCase();
		srch_city = _city.toLowerCase();
		DBConn = new DBConnection();
	}

	
	//Starts the search
	public LinkedList<ProfileStore> go()
	{
		DBConn.connect();
		LinkedList<ProfileStore> results = new LinkedList<ProfileStore>();
		results = DBConn.searchAll(srch_first_name, srch_surname, srch_email, srch_city);
		return results;
	}
}
