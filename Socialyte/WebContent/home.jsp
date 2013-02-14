<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <%@ page import="java.util.*" %>
    <%@ page import="sclyt.store.*" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Socialyte</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="shortcut icon" href="img/favicon.ico" />
<link rel="stylesheet" type="text/css" href="/Socialyte/css/main_style.css">
<link rel="stylesheet" type="text/css" href="/Socialyte/css/post_style.css">

</head>

<body>
	<div id="wrapper">

		<div id="top_bar">
			
			<div id="logo_div">
				<a href="./Socialyte"><img src="img/logo.png" id="logo" /></a>
			</div>
			
			<div id="menu_items">
				<img src="img/profile_US.png" id="profile" onmouseover="this.src='img/profile_S.png'" onmouseout="this.src='img/profile_US.png'"/>
				<img src="img/newpost_US.png" id="new_post" onmouseover="this.src='img/newpost_S.png'" onmouseout="this.src='img/newpost_US.png'"/>
				<img src="img/subscriptions_US.png" id="subscriptions" onmouseover="this.src='img/subscriptions_S.png'" onmouseout="this.src='img/subscriptions_US.png'"/>
				
			</div>
			
			<div id="search_div">
				<form id="search_box" action="" method="GET" >
					<input class="blue_textbox" type="text" name="search" size="35" placeholder="Search for anything..">
					<input class="blue_button" id="submit_search" type="submit" value="Go" >
				</form>
			</div>
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
					
					<%
					List<PostStore> posts = (List<PostStore>)request.getAttribute("Posts");
					Iterator<PostStore> iterator;
					
					iterator = posts.iterator();
					
					while (iterator.hasNext())
					{ %>
						<div class="post">
							<div class="post_left">
								<div class="post_avatar">
									<img src="img/avatar.png" />
								</div>
							</div>
						<%
						PostStore row = (PostStore)iterator.next();
						String rowContents = row.getRowContents();
						String full_name = row.getFullName();
						String tags = row.getTags();
						String body = row.getBody();
						%>
						<div class="post_right">
							<div class="post_username">
								<%=full_name %> says..
							</div>
							
							<div class="post_body">
								<%=body %>
							</div>
							
							<div class="post_tags">
								<%=tags %>
							</div>
							
							<div class="post_date">
							{date}
							</div>
						
							</div>
						</div>
						<%
					}
					
					%>
				
									
				</div>		 
		 
		</div>
			
		<div id="footer">
			<!--<img src="http://i.imgur.com/Op9dO.gif"/>
			<p>Web sense, nigga!</p>  -->
			<p>Copyright &copy; Socialyte 2013</p>
		</div>
	</div>
</body>
</html>