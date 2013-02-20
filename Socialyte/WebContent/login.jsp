<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Socialyte</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="shortcut icon" href="/Socialyte/img/favicon.ico" />
<link rel="stylesheet" type="text/css" href="/Socialyte/css/main_style.css">
<link rel="stylesheet" type="text/css" href="/Socialyte/css/login_style.css">

<script type="text/JavaScript" src="js/scripts.js"></script> 
<script src="http://code.jquery.com/jquery-latest.js"></script>
</head>

<body>
	<div id="wrapper">

		<div id="top_bar">
			
			<div id="logo_div">
				<a href="/Socialyte/"><img src="/Socialyte/img/logo.png" id="logo" /></a>
			</div>
			
			<div id="menu_items">
				
			</div>
			
			<div id="search_div">
				<form id="search_box" action="" method="GET" >
					<input class="blue_textbox" type="text" name="search" size="25" placeholder="Search for anything..">
					<input class="blue_button" id="submit_search" type="submit" value="Go" >
				</form>
			</div>
		</div>
			
		<div id="content">
			<% 
			String test = "";
			if (request.getAttribute("account_created") != null)
			{ %>
				<div class="message_popup">
					<span class="green_text">Account created successfully!</span><br/>
					<span class="small_text">You may now sign in below.</span>
				</div>
			<% } 
			else if (request.getAttribute("invalid_login") != null)
			{ %>				
				<div class="message_popup">
					<span class="red_text">Invalid login details!</span><br/>
					<span class="small_text">Please check and try again</span>
				</div>
			<% } 
			else if (request.getAttribute("validation_error") != null)
			{ %>				
				<div class="message_popup">
					<span id="red_text">Account Creation Failed!</span><br/><span class="small_text">
				<%
					String validation_result = (String)request.getAttribute("validation_error");
					System.out.println("JSP:" + validation_result);
					String error_text;
					
					if (validation_result.contains("INV_FNAME,"))
						%>Invalid first name<br/>
					<%					
					if (validation_result.contains("INV_SNAME,"))
						%>Invalid surname<br/>
						
					<%	
					if (validation_result.contains("INV_USR,"))
						%>Invalid username<br/>
					
					<%	
					if (validation_result.contains("INV_USR_EXISTS,"))
						%>Username taken!<br/>
						
					<%	
					if (validation_result.contains("INV_EMAIL,"))
						%>Invalid email name<br/>
						
					<%	
					if (validation_result.contains("INV_PASS,"))
						%>Invalid password<br/>
						
					</span>	
				
				
				</div>
			<% } %>
			<script>
				$('body').click(function () {
	 				$('.message_popup').fadeOut(1500);
	  			});
			</script>
			<div id="loading_popup">
					<img src="/Socialyte/img/loader_white.gif" /><br/>
					<img src="/Socialyte/img/logging_in_black.png" />
			</div>
			
			<div id="login_signup_pane">
			
				<div id="signup_pane">
					<img src="/Socialyte/img/sign_up.png" id="sign_up_img" />
					<form name="signup" id="signupForm" action="/Socialyte/Signup" method="POST" > <!-- ./Signup -->
					<label for="first_name_tb">First name</label><br/>
						<input class="form_textbox" id="first_name_tb" type="text" name="first_name" size="25"><br/>
					<label for="surname_tb">Surname</label><br/>						
						<input class="form_textbox" id="surname_tb" type="text" name="surname"  size="25"><br/>
					<label for="new_username_tb">Username (to sign in)</label><br/>
						<input class="form_textbox" id="new_username_tb" type="text" name="new_username" size="25"><br/>
					<label for="new_password_tb">Password</label><br/>
						<input class="form_textbox" id="new_password_tb" type="password" name="new_password" size="25"><br/>
					<label for="new_password_confirm_tb">Password (confirm)</label><br/>
						<input class="form_textbox" id="new_password_confirm_tb" type="password" name="new_password_c" size="25"><br/>
					<label for="email_tb">Email address</label><br/>
						<input class="form_textbox" id="email_tb" type="text" name="email" size="25"><br/>
						<input class="blue_button" id="submit_signup"  type="submit" value="Sign up!" >
						
					</form>
				</div>
				<div id="or_partition">
					<img src="/Socialyte/img/or.png">
				</div>
				
				<div id="login_pane">
					<img src="/Socialyte/img/login_here.png" id="login_img" /><br/>
					<img src="/Socialyte/img/dots.png" id="dots_img" />
					<form id="login" action="/Socialyte/Login" method="POST" >
						<input class="form_textbox" id="username_textbox" type="text" name="username" size="25" placeholder="Username"><br/>
						<input class="form_textbox" id="password_textbox" type="password" name="password" size="25" placeholder="Password"><br/>
						<input class="blue_button" id="submit_login" onclick="showLoading()" type="submit" value="Login" >
						
					</form>
					<br/><br/>
					<span id="signup_text">Don't have an account?<br/></span>
					<!-- <button class="blue_button" type="submit" value="Register" >Sign up</button> -->
					<img src="/Socialyte/img/signup_pointer.png" id="look_there"/>
				</div>
		<!-- LOGIN MARKUP -->
				
			</div>
		 
		</div>
			
		<div id="footer">
			<p>Copyright &copy; Socialyte 2013</p>
		</div>
	</div>
</body>
</html>