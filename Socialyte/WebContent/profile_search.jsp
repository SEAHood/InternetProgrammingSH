<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <%@ page import="java.util.*" %>
    <%@ page import="org.sclyt.store.*" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Socialyte</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="shortcut icon" href="img/favicon.ico" />
<link rel="stylesheet" type="text/css" href="/Socialyte/css/main_style.css">
<link rel="stylesheet" type="text/css" href="/Socialyte/css/search_style.css">

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
				<a href="/Socialyte/"><img src="img/logo.png" id="logo" /></a>
			</div>
			
			<%
					
				Session thisSession = (Session)request.getAttribute("session");
				String username = thisSession.getUsername();
			%>
			
			<div id="menu_items">
				<a href="/Socialyte/Feed" class="tooltip" title="Home"><img src="/Socialyte/img/home_US.png" id="home" onmouseover="this.src='/Socialyte/img/home_S.png'" onmouseout="this.src='/Socialyte/img/home_US.png'"/></a>
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
							String avatar = thisSession.getAvatar();
						%>
						<img src="<%=avatar %>" />
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
			
			<div id="search_pane">
				<span class="big_text">Search Options</span>
				<form id="search_controls" action="/Socialyte/Search" method="POST">
					<input class="blue_textbox" type="text" name="first_name" size="25" placeholder="First name">
					<input class="blue_textbox" type="text" name="surname" size="25" placeholder="Surname"><br/>
					<input class="blue_textbox" type="text" name="email" size="25" placeholder="Email">
					<input class="blue_textbox" type="text" name="city" size="25" placeholder="City"><br/>
					<input class="blue_button" id="search_button" type="submit" value="Search">
				</form>
			</div>
			
			
			
			<div id="results_pane">
			<span class="big_text">Results</span><br/>
			
			<%
			List<ProfileStore> results = (List<ProfileStore>)request.getAttribute("search_results");
			
			if (results != null)
			{
				Iterator<ProfileStore> iterator;
				
				iterator = results.iterator();
				
				if (!iterator.hasNext()) //No subscribers found
				{ %>
					0 Results Found!
				<% }
				
				
				while (iterator.hasNext())
				{
					ProfileStore single_profile = (ProfileStore)iterator.next();
					String profile_full_name = single_profile.getFirstName() + " " + single_profile.getSurname();
					String profile_avatar = single_profile.getAvatar();
					String profile_username = single_profile.getUsername();
					%>
						<div class="single_profile">
							<div class="profile_avatar">
								<img src="<%=profile_avatar %>"/>
							</div>
							<div class="profile_info">
								 <span class="big_text"><%=profile_full_name %></span><br/>
								 <a href="/Socialyte/Profile/<%=profile_username %>"><button class="blue_button">View Profile</button></a><br/>
								 <form id="add_sub" method="POST" action="/Socialyte/Subscriptions">
									 <input type="hidden" name="add_sub" value="<%=profile_username %>">
									 <input class="blue_button" type="submit" name="submit" value="Subscribe">
								 </form>
							</div>
						</div>
					<%
				}
			}
			else
			{
			%>
				<span>0 Results Found!</span>
			<% } %>
			</div>
			
			
		</div>
	</div>
</body>
</html>