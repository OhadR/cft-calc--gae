<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.springframework.security.core.Authentication" %>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ page import="org.springframework.stereotype.Controller" %>



<html>
<head>
    <title></title>
    <script src="http://code.jquery.com/jquery-1.9.1.min.js"></script>
	<script src="./benchmarkr.js"></script>
</head>
<body>
	<div>
		<img src="http://cbenchmarkr.appspot.com/reebok-crossfit.jpg" width="256"
		height="52" alt="crossfit" title="crossfit" border="0" /> 
	</div> 

<div>	
<%
	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	String name = auth.getName(); //get logged in username

//    UserService userService = UserServiceFactory.getUserService();
//    User user = userService.getCurrentUser();
%>
<p>Logged in as <%= name %>. | <a href="/secured/addWorkout.jsp">Add Workout</a> | <a href="/j_spring_security_logout">logout</a></p>
	
			<input id="email" type="hidden" value=<%= name %>><br />

			<button name="showGrades" onclick="OnShowGrades();" id="showGrades">show grades</button>
			<br>

			<label for="history_workoutsCombobox">Workouts:</label>
			<select id="history_workoutsCombobox">
			    <option value="" disabled="disabled" selected="selected">Please select a workout</option>
			    <option value="annie">annie</option>
			    <option value="barbara">barbara</option>
			    <option value="cindy">cindy</option>
			</select>
			<button name="showHistory" onclick="OnShowHistory();" id="showHistory">show history</button>
</div>
		

</body>
</html>