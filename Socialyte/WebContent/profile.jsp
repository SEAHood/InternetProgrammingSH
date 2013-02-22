<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ page import="java.util.*" %>
<%@ page import="org.sclyt.store.*" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Socialyte</title>
<link rel="shortcut icon" href="/Socialyte/img/favicon.ico" />
<link rel="stylesheet" type="text/css" href="/Socialyte/css/main_style.css">
<link rel="stylesheet" type="text/css" href="/Socialyte/css/profile_style.css">

<link rel="stylesheet" type="text/css" href="/Socialyte/css/tooltipster.css" />
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script type="text/javascript" src="/Socialyte/js/jquery.tooltipster.min.js"></script>
<script>
	$(document).ready(function() {
	    $('.tooltip').tooltipster();
	});
</script>
</head>
<body>
<div id="wrapper">
		<div id="top_bar">
			<div id="logo_div">
				<a href="/Socialyte/"><img src="/Socialyte/img/logo.png" id="logo" /></a>
			</div>
			
			<%
				Session thisSession = (Session)request.getAttribute("session");
				String username = thisSession.getUsername();
			%>
			
			<div id="menu_items">
				<a href="/Socialyte/Home" class="tooltip" title="Home"><img src="/Socialyte/img/home_US.png" id="home" onmouseover="this.src='/Socialyte/img/home_S.png'" onmouseout="this.src='/Socialyte/img/home_US.png'"/></a>
				<a href="/Socialyte/Profile/<%=username %>" class="tooltip" title="My Profile"><img src="/Socialyte/img/profile_US.png" id="profile" onmouseover="this.src='/Socialyte/img/profile_S.png'" onmouseout="this.src='/Socialyte/img/profile_US.png'"/></a>
				<a href="/Socialyte/Post/new" class="tooltip" title="New Post"><img src="/Socialyte/img/newpost_US.png" id="new_post" onmouseover="this.src='/Socialyte/img/newpost_S.png'" onmouseout="this.src='/Socialyte/img/newpost_US.png'"/></a>
				<a href="/Socialyte/Subscribers" class="tooltip" title="Subscribers"><img src="/Socialyte/img/subscribers_US.png"  id="subscribers" onmouseover="this.src='/Socialyte/img/subscribers_S.png'" onmouseout="this.src='/Socialyte/img/subscribers_US.png'"/></a>
				<a href="/Socialyte/Subscriptions" class="tooltip" title="Subscriptions"><img src="/Socialyte/img/subscriptions_US.png"  id="subscriptions" onmouseover="this.src='/Socialyte/img/subscriptions_S.png'" onmouseout="this.src='/Socialyte/img/subscriptions_US.png'"/></a>
			</div>
			
			<div id="search_div">
				<form id="search_box" action="" method="GET" >
					<input class="blue_textbox" type="text" name="search" size="25" placeholder="Search for anything..">
					<input class="blue_button" id="submit_search" type="submit" value="Go" >
				</form>
			</div>
		</div>
			
		<div id="content">
		
				<div id="user_pane">
					<div id="user_avatar">
						<% 
							String user_avatar = thisSession.getAvatar();
						%>
						<img src="<%=user_avatar %>" />
					</div>
					<div id="user_info">
						<% 
							String session_full_name = thisSession.getFullName();
						%>
						<strong><%=session_full_name %></strong><br/>
						{post count}<br/>
						{info}
					</div>
					<div id="user_controls">
						<form name="logout_form" method="POST" action="/Socialyte/Login">
							<input type="hidden" id="logout" name="logout">
		        			<input class="blue_button" id="logout_button" type="submit" value="Logout" >
	    				</form>
					</div>					
				</div>
				
				<% 
					ProfileStore profile = (ProfileStore)request.getAttribute("profile");
					String first_name = profile.getFirstName();
					String surname = profile.getSurname();
					String full_name = first_name + " " + surname;
					String dob = profile.getDOBAsString();
					String city = profile.getCity();
					String country = profile.getCountry();
					String email = profile.getEmail();
					String avatar = profile.getAvatar();
				%>
				
				<div id="profile_pane">
					<div id="avatar">
						<img src="<%=avatar %>"/>
					</div>
					
					<div id="full_name">
						<span class="huge_text"><%=full_name %></span><br/>
						<table id="profile_stats">
						<tr>
							<td>Joined: DD-MM-YYYY</td>
							<td>Subscribers: ###</td>
						</tr>
							<td>Posts: ###</td>
							<td>Subscribes to: ###</td>
						</tr>
						</table>
					</div>
					
					<div id="location">
						<span class="big_text">Location:</span><br/>
						<%=city %>, <%=country %>
					</div>
					
					<div id="dob">
						<span class="big_text">Date of Birth:</span><br/>
						<%=dob %>
					</div>
					
					<div id="bio">
						<span class="big_text">Bio:</span><br/>
					
					</div>
					
					<div id="contact">					
						<span class="big_text">Contact:</span><br/>
						<%=email %>
					</div>
					
					
					
					
					
				
				</div>
	</div>
</div>
	
</body>
</html>