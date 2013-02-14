<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Socialyte</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="shortcut icon" href="img/favicon.ico" />
<link rel="stylesheet" type="text/css" href="css/main_style.css">
<link rel="stylesheet" type="text/css" href="css/login_style.css">
<script type="text/JavaScript" src="js/loading.js"></script> 
</head>

<body>
	<div id="wrapper">

		<div id="top_bar">
			
			<a href="/"><img src="img/logo.png" id="logo" /></a>
			<form id="search_box" action="" method="GET" >
				<input class="blue_textbox" type="text" name="search" size="35" placeholder="Search for anything..">
				<input class="blue_button" id="submit_search" type="submit" value="Go" >
			</form>

		</div>
			
		<div id="content">
		
			<div id="loading_popup">
				<img src="img/loader_white.gif" /><br/>
				<img src="img/logging_in_black.png" />
			</div>
		
		<!-- LOGIN MARKUP -->
			<div id="login_pane">
			<img src="img/login.png" id="login_img" />
				<form id="login" action="./Login" method="POST" >
					<input class="blue_textbox" id="username_textbox" type="text" name="username" size="35" placeholder="Username"><br/>
					<input class="blue_textbox" id="password_textbox" type="password" name="password" size="35" placeholder="Password"><br/>
					<input class="blue_button" id="submit_login" onclick="showLoading()" type="submit" value="Login" >
					
				</form>
				<br/><br/>
				<span id="signup_text">Don't have an account?<br/></span>
				<!-- <button class="blue_button" type="submit" value="Register" >Sign up</button> -->
				<a href="#">Sign up</a>
			</div>
		 
		</div>
			
		<div id="footer">
			<!--<img src="http://i.imgur.com/Op9dO.gif"/>
			<p>Web sense, nigga!</p>
			<p>Copyright &copy; Socialyte 2013</p>  -->
		</div>
	</div>
</body>
</html>