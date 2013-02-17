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
			
			<div id="logo_div">
				<a href="/Socialyte/"><img src="img/logo.png" id="logo" /></a>
			</div>
			
			<div id="menu_items">
				
			</div>
			
			<div id="search_div">
				<form id="search_box" action="" method="GET" >
					<input class="blue_textbox" type="text" name="search" size="35" placeholder="Search for anything..">
					<input class="blue_button" id="submit_search" type="submit" value="Go" >
				</form>
			</div>
		</div>
			
		<div id="content">
		
			<div id="loading_popup">
				<img src="img/loader_white.gif" /><br/>
				<img src="img/logging_in_black.png" />
			</div>
		
			<div id="login_signup_pane">
			
				<div id="signup_pane">
					<img src="img/sign_up.png" id="sign_up_img" />
					<form id="login" action="./Signup" method="POST" >
					<label>First name</label><br/>
						<input class="form_textbox" id="first_name_textbox" type="text" name="first_name" size="20"><br/>
					<label>Surname</label><br/>						
						<input class="form_textbox" id="surname_textbox" type="text" name="surname"  size="20"><br/>
					<label>Username (to sign in)</label><br/>
						<input class="form_textbox" id="new_username_textbox" type="text" name="new_username" size="20"><br/>
					<label>Password</label><br/>
						<input class="form_textbox" id="new_password_textbox" type="password" name="new_password" size="20"><br/>
					<label>Password (confirm)</label><br/>
						<input class="form_textbox" id="new_password_confirm_textbox" type="password" name="new_password_c" size="20"><br/>
					<label>Email address</label><br/>
						<input class="form_textbox" id="email_textbox" type="text" name="email" size="35"><br/>
						<input class="blue_button" id="submit_signup"  type="submit" value="Sign up!" >
						
					</form>
				</div>
				<div id="or_partition">
					<img src="img/or.png">
				</div>
				
				<div id="login_pane">
					<img src="img/login_here.png" id="login_img" /><br/>
					<img src="img/dots.png" id="dots_img" />
					<form id="login" action="./Login" method="POST" >
						<input class="form_textbox" id="username_textbox" type="text" name="username" size="20" placeholder="Username"><br/>
						<input class="form_textbox" id="password_textbox" type="password" name="password" size="20" placeholder="Password"><br/>
						<input class="blue_button" id="submit_login" onclick="showLoading()" type="submit" value="Login" >
						
					</form>
					<br/><br/>
					<span id="signup_text">Don't have an account?<br/></span>
					<!-- <button class="blue_button" type="submit" value="Register" >Sign up</button> -->
					<img src="img/sign_up_direction.png" id="look_there"/>
				</div>
		<!-- LOGIN MARKUP -->
				
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