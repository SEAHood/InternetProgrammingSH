<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Socialyte</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="shortcut icon" href="/Socialyte/img/favicon.ico" />
<link rel="stylesheet" type="text/css" href="/Socialyte/css/main.css">
<script type="text/JavaScript" src="js/loading.js"></script> 

<script type="text/JavaScript">
<!--
function timedRefresh(timeoutPeriod) {
	setTimeout("location.reload(true);",timeoutPeriod);
}
//   -->
</script>

</head>

<body onload="JavaScript:timedRefresh(5000);">
	<div id="wrapper">

		<div id="top_bar">
			
			<a href="/"><img src="/Socialyte/img/logo.png" id="logo" /></a>
			<form id="search_box" action="" method="GET" >
				<input class="blue_textbox" type="text" name="search" size="35" placeholder="Search for anything..">
				<input class="blue_button" id="submit_search" type="submit" value="Go" >
			</form>

		</div>
			
		<div id="content">
			<div id="user_pane">
				<div id="user_avatar">
					<img src="/Socialyte/img/avatar.png" />
				</div>
				<div id="user_info">
					{username}<br/>
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

			<div id="new_post_pane">
				
				<div id="new_post">
					<div class="big_text">Say..</div>
					<form name="new_post_form" method="POST" action="#">
						<textarea id="body_text" name="body" rows="5" cols="30" placeholder="Message"></textarea><br/>
						<div class="big_text">Tag it!</div>
						<div class="small_text">Seperate tags with commas (,)</div>
						<input type="text" id="tags_text" name="tags" placeholder="Tags"><br/>
						<input class="blue_button" id="make_new_post" type="submit" value="Post">
					</form>
				
				</div>
			
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