<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.springframework.security.core.Authentication" %>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ page import="org.springframework.stereotype.Controller" %>



<html>
<head>
    <title></title>
    <link href="http://cdn.kendostatic.com/2014.1.416/styles/kendo.common.min.css" rel="stylesheet" />
    <link href="http://cdn.kendostatic.com/2014.1.416/styles/kendo.default.min.css" rel="stylesheet" />
	<link href="style.css" rel="stylesheet" />
    <script src="http://code.jquery.com/jquery-1.9.1.min.js"></script>
    <script src="http://cdn.kendostatic.com/2014.1.416/js/kendo.all.min.js"></script>
	<script src="./nespresso.js"></script>
</head>
<body>
	<div>
		<img src="http://cbenchmarkr.appspot.com/reebok-crossfit.jpg" width="256"
		height="52" alt="NICE" title="NICE" border="0" /> 
	</div> 

<div>	
<%
	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	String name = auth.getName(); //get logged in username

//    UserService userService = UserServiceFactory.getUserService();
//    User user = userService.getCurrentUser();
%>
<p>Logged in as <%= name %>. | <a href="/secured/order.jsp">order page</a> | <a href="/j_spring_security_logout">logout</a></p>
	
			<input id="email" type="hidden" value=<%= name %>><br />

			<p><%= request.getAttribute("data") %></p>
			<br>
			<p>Total number of sleeves in DB: <%= request.getAttribute("totalNumberOfSleevesInDB") %></p>
			
			<a href="/secured/removeUserOrder">reset your order</a>
</div>
		
</body>
</html>