<html>
<head>
	<script type="text/javascript" src="https://code.jquery.com/jquery-1.4.2.js"></script>
	<script src="./benchmarkr.js"></script>
	<script src="./addWorkout.js"></script>

	<title>Add Workout Page</title>
</head>

<body onload='loadAddWorkout();'>

	<div>
		<img src="http://cbenchmarkr.appspot.com/reebok-crossfit.jpg" width="256"
		height="52" alt="crossfit" title="crossfit" border="0" /> 
	</div> 

	<h3>Add Workout</h3>

	<%   
	    if ( null != request.getParameter("err_msg") ) {
	%>	
	<div style="margin-top:  25px ;position: relative; color: red; font:15px">
		<span style="font-weight:bold"><%= request.getParameter("err_msg") %></span>
	</div>
	<%   } %>
	
	<div>
		<table>
			<tr>
				<td>Workout Name:</td>
				<td><select id="add_workoutsCombobox" /></td>
			</tr>
			<tr>
				<td>Date:</td>
				<td><input id='addProfile_date' type='date' name='date' /></td>
			</tr>
			<tr>
				<td>Result:</td>
				<td><input id='addProfile_result' type='number' name='result' /></td>
			</tr>
			<tr>
				<td><button name="addWorkout" onclick="OnAddWorkout();" id="addWorkout">Add Workout</button></td>
			</tr>
		</table>
	</div>
</body>
</html>