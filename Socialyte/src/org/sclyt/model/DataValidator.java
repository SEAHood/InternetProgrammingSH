package org.sclyt.model;

import java.util.regex.Pattern;

import me.prettyprint.hector.api.exceptions.HectorException;

public class DataValidator {
	
	Signup account;
	
	public DataValidator(Signup _account)
	{
		account = _account;
	}

	//Validate account details
	public String validate()
	{
		String validation_errors = "";
		
		//Create error codes
		if (!validateFirstName(account.first_name))
			validation_errors += "INV_FNAME,";
		
		if (!validateSurname(account.surname))
			validation_errors += "INV_SNAME,";
		
		if (!validateUsername(account.username))
		{
			validation_errors += "INV_USR,";
			
		}
		else
		{
			if (!checkUsernameNotTaken(account.username))
				validation_errors += "INV_USR_EXISTS,";
		}
		
		if (!validateEmail(account.email))
			validation_errors += "INV_EMAIL,";
		
		if (!validatePassword(account.password))
			validation_errors += "INV_PASS,";
		
		if (!matchPasswords(account.password, account.password_c))
			validation_errors += "INV_PASS_MATCH,";
		
		
		return validation_errors;
	}
	
	
	//Validate first name
	//Min 2 chars
	//Max 15 chars
	private boolean validateFirstName(String first_name)
	{
		if (first_name.length() < 2 || first_name.length() > 15)
			return false;
		
		return true;
	}
	
	//Validate surname
	//Min 2 chars
	//Max 25 chars
	private boolean validateSurname(String surname)
	{
		if (surname.length() < 2 || surname.length() > 25)
			return false;
		
		return true;
	}
	
	
	//Validate username
	//Min 5 chars
	//Max 15 chars
	private boolean validateUsername(String username)
	{
		if (username.length() < 5 || username.length() > 15)
			return false;
		
		return true;
	}
	
	
	//Check username is not taken
	private boolean checkUsernameNotTaken(String username)
	{
		DBConnection DBConn;
		boolean connected = false;
		boolean err_found = false;
		boolean user_exists = false;
		while (!connected)
		{
			try 
			{
				DBConn = new DBConnection();
				DBConn.connect();
				
				user_exists = DBConn.checkUsernameExists(username);
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
		
		if (user_exists)
			return false;
		else
			return true;
	}
	
	
	//Validate password
	//Min 6 chars
	//Max 15 chars
	private boolean validatePassword(String password)
	{
		if (password.length() < 2 || password.length() > 15)
			return false;
		
		return true;
	}
	
	
	//Ensure passwords match
	private boolean matchPasswords(String password, String password_c)
	{
		if (password.equals(password_c))
			return true;
		else
			return false;
	}
	
	
	//Validate email
	//Min 5 chars
	//Max 50 chars
	//Must be email format
	private boolean validateEmail(String email)
	{
		if (email.length() < 5 || email.length() > 50)
			return false;
		
		Pattern emailFormat = Pattern.compile("^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$");

		if (!emailFormat.matcher(email).matches())
		    return false;
		
		return true;
	}
}
