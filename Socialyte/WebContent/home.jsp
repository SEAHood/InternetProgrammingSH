<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Socialyte</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="shortcut icon" href="img/favicon.ico" />
<link rel="stylesheet" type="text/css" href="css/main.css">
</head>

<body>
	<div id="wrapper">

		<div id="top_bar">
			
			<a href="./"><img src="img/logo.png" id="logo" /></a>
			<form id="search_box" action="" method="GET" >
				<input class="blue_textbox" type="text" name="search" size="35" placeholder="Search for anything..">
				<input class="blue_button" id="submit_search" type="submit" value="Go" >
			</form>

		</div>
			
		<div id="content">
		
				<div id="user_pane">
					<div id="user_avatar">
						<img src="img/avatar.png" />
					</div>
					<div id="user_info">
						{username}<br/>
						{post count}<br/>
						{info}
					</div>
					<div id="user_controls">
						<form name="logout_form" method="POST" action="./Login">
						<input type="hidden" id="logout" name="logout">
		        		<input class="blue_button" id="logout_button" type="submit" value="Logout" >
	    			</form>
					</div>
					
				</div>
		
				<div id="post_pane">
					
					<div class="post">
						<div class="post_left">
							<div class="post_avatar">
								<img src="img/avatar.png" />
							</div>
						</div>
						
						<div class="post_right">
							<div class="post_username">
							{username} says..
							</div>
							
							<div class="post_body">
							{post body}
							</div>
							
							<div class="post_tags">
							{tag} {tag} {tag}
							</div>
							
							<div class="post_date">
							{date}
							</div>
						</div>
					</div>
									
				</div>
				
				<br/>
				<br/>
				<br/>
				<br/>
				<br/>
				<br/>
				<br/>
				<br/>
				<br/>
				<br/>
				<br/>
				<br/>
				<br/>
				Test Stuff<br/>
				<br/>
				<br/>
				<br/>
				<br/>
				<br/>
				<br/>
				<br/>
				<br/>
				<br/>
				<br/>
				<br/>
				<br/>Test Stuff<br/>
				<br/>
				<br/>
				<br/>
				<br/>
				<br/>
				<br/>
				<br/>
				<br/>
				<br/>
				<br/>
				<br/>
				<br/>Test Stuff<br/>
				<br/>
				<br/>
				<br/>
				<br/>
				<br/>
				<br/>
				<br/>
				<br/>
				<br/>
				<br/>
				<br/>
				<br/>Test Stuff<br/>
				<br/>
				<br/>
				<br/>
				<br/>
				<br/>
				<br/>
				<br/>
				<br/>
				<br/>
				<br/>
				<br/>
				<br/>Test Stuff<br/>
				<br/>
				<br/>
				<br/>
				<br/>
				<br/>
				<br/>
				<br/>
				<br/>
				<br/>
				<br/>
				<br/>
				<br/>Test Stuff<br/>
				<br/>
				<br/>
				<br/>
				<br/>
				<br/>
				<br/>
				<br/>
				<br/>
				<br/>
				<br/>
				<br/>
				<br/>
		 
		 
		</div>
			
		<div id="footer">
			<!--<img src="http://i.imgur.com/Op9dO.gif"/>
			<p>Web sense, nigga!</p>  -->
			<p>Copyright &copy; Socialyte 2013</p>
		</div>
	</div>
</body>
</html>