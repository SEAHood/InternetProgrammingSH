package org.sclyt.store;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ProfileStore {
	
	String profile_username;
	
	String first_name;
	String surname;
	Date dob;
	String email;
	String city;
	String country;
	String avatar;
	
	
	public ProfileStore()
	{
		
	}
	
	public void normaliseNulls()
	{
		if (first_name == null)
			first_name = "";
			
		if (surname == null)
			surname = "";
					
		if (email == null)
			email = "";
		
		if (city == null)
			city = "";
			
		if (country == null)
			country = "";
			
		if (avatar == null)
			avatar = "";
	}
	
	
	//-------MUTATORS-------------------------//
	public void setUsername(String _username)
	{
		profile_username = _username;
	}
	
	public void setFirstName(String _first_name)
	{
		first_name = _first_name;
	}
	
	public void setSurname(String _surname)
	{
		surname = _surname;
	}
	
	public void setDOB(Date _dob)
	{
		dob = _dob;
	}
	
	public void setEmail(String _email)
	{
		email = _email;
	}
	
	public void setCity(String _city)
	{
		city = _city;
	}
	
	public void setCountry(String _country)
	{
		country = _country;
	}
	
	public void setAvatar(String _avatar)
	{
		avatar = _avatar;
	}
	
	
	//-------ACCESSORS-------------------------//
	public String getUsername()
	{
		return profile_username;
	}
	
	public String getFirstName()
	{
		return first_name;
	}
	
	public String getSurname()
	{
		return surname;
	}
	
	public Date getDOB()
	{
		return dob;
	}
	
	public String getDOBAsString()
	{
		String str_date;
		
		if (dob != null)
		{
			SimpleDateFormat s = new SimpleDateFormat("dd/MM/yy");
			str_date = s.format(dob);
		}
		else
			str_date = "";
		
		return str_date;
	}
	
	public String getEmail()
	{
		return email;
	}
	
	public String getCity()
	{
		return city;
	}
	
	public String getCountry()
	{
		return country;
	}
	
	public String getAvatar()
	{
		return avatar;
	}
}
