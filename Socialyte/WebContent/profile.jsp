<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ page import="java.util.*" %>
<%@ page import="org.sclyt.store.*" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<% 
		ProfileStore profile = (ProfileStore)request.getAttribute("profile");
	String first_name = profile.getFirstName();
	String surname = profile.getSurname();
	String dob = profile.getDOBAsString();
	String city = profile.getCity();
	String country = profile.getCountry();
	String email = profile.getEmail();
	String avatar = profile.getAvatar();
	%>
	<%=first_name %><br/>
	<%=surname %><br/>
	<%=dob %><br/>
	<%=city %><br/>
	<%=country %><br/>
	<%=email %><br/>
	<%=avatar %><br/>
</body>
</html>