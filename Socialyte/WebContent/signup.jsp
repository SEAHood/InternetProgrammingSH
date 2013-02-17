<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="css/main_style.css">
<title>Sign up to Socialyte!</title>
</head>
<body>
	<form id="login" action="./Login" method="POST" >
		<label class="input_label" id="first_name_label">First name:</label>
		<input class="blue_textbox" id="password_textbox" type="password" name="password" size="35"><br/>
		<input class="blue_button" id="submit_login" onclick="showLoading()" type="submit" value="Login" >
					
	</form>
</body>
</html>