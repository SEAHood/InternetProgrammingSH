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
					List<ProfileStore> subs = (List<ProfileStore>)request.getAttribute("subs");
					Iterator<ProfileStore> iterator;
					
					iterator = subs.iterator();
					
					if (!iterator.hasNext()) //No subscriptions found
					{ %>
						No subs found!
						
					<% }
					
					
					while (iterator.hasNext())
					{
						ProfileStore single_profile = (ProfileStore)iterator.next();
						String full_name = single_profile.getFirstName() + " " + single_profile.getSurname();
						String avatar = single_profile.getAvatar();
						%>
							<img src="<%=avatar %>"/><%=full_name %><br/>
						<%
					}
					
					%>

</body>
</html>