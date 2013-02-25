<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ page import="java.util.*" %>
<%@ page import="org.sclyt.store.*" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Socialyte</title>
<link rel="shortcut icon" href="img/favicon.ico" />
<link rel="stylesheet" type="text/css" href="/Socialyte/css/main_style.css">
<link rel="stylesheet" type="text/css" href="/Socialyte/css/subs_style.css">

<link rel="stylesheet" type="text/css" href="/Socialyte/css/tooltipster.css" />
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script type="text/javascript" src="/Socialyte/js/jquery.tooltipster.min.js"></script>
<script>
	$(document).ready(function() {
	    $('.tooltip').tooltipster();
	});
	
	function deleteSub(username) {
		$.ajax({
		url: '/Socialyte/Subscriptions/' + username,
		type: 'DELETE',
		error: function(jqXHR, textStatus, errorThrown) { 
			console.log("Error: " + jqXHR.responseText);
			console.log("Error: " + textStatus);
			console.log("Error: " + errorThrown);
		},
        success:function(response){
        	window.location.href = "/Socialyte/feedbackpages/subscription_removed.jsp";
        }
		});
	};
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
					<form name="logout_form" method="POST" action="./Login">
						<input type="hidden" id="logout" name="logout">
	        			<input class="blue_button" id="logout_button" type="submit" value="Logout" >
    				</form>
				</div>
				
			</div>
			
			<div id="title_pane">
				<span class="huge_text">Subscriptions</span>
			</div>

			<div id="subs_pane">
<%
					List<ProfileStore> subscriptions = (List<ProfileStore>)request.getAttribute("subscriptions");
					Iterator<ProfileStore> iterator;
					
					iterator = subscriptions.iterator();
					
					if (!iterator.hasNext()) //No subscriptions found
					{ %>
						No subs found!
						
					<% }
					
					
					while (iterator.hasNext())
					{
						ProfileStore single_profile = (ProfileStore)iterator.next();
						String profile_full_name = single_profile.getFirstName() + " " + single_profile.getSurname();
						String profile_avatar = single_profile.getAvatar();
						String profile_username = single_profile.getUsername();
						%>
						<div class="single_sub">
							<div class="sub_avatar">
								<img src="<%=profile_avatar %>"/>
							</div>
							<div class="sub_info">
									 <span class="big_text"><%=profile_full_name %></span><br/>
									 <a href="/Socialyte/Profile/<%=profile_username %>"><button class="blue_button">View Profile</button></a><br/>
									 <button class="blue_button" onclick="deleteSub('<%=profile_username %>');">Remove</button><br/>
								</div>
						</div>
						<%
					}
					
					%>
					
					<div class="single_sub" id="add_new">
						<a href="/Socialyte/Search" >Add a new subscription!</a>
					</div>
			</div>
		</div>
		
		
			
		<div id="footer">
			<p>Copyright &copy; Socialyte 2013</p>
		</div>
	</div>
</body>
</html>