/**
 * 
 */
function loadAddWorkout()
{
	$.ajax({
		url: "/getAllWorkoutsNames",
		type: 'GET',
		dataType: 'json',						//what i get
		success: function(result, textStatus, jqXHR)
		{
			var fff = '';
			for(var i = 0; i < result.length; ++i)
			{
				fff += (result[i] + '\n');
			}
//			alert( fff );
			populateWorkouts( 'add_workoutsCombobox', result);		//result is json-ed workouts-names
			
		},
		error: function(jqXHR, textStatus, errorThrown)
		{
			alert('error: ' + jqXHR + '; status: ' + status + '; errorThrown: ' + errorThrown);
		}
	});
	
}


function OnAddWorkout()
{
	var name = getItemFromCombobox( 'add_workoutsCombobox' ); 
	var date_str = $("#addProfile_date").val();
	var date = new Date( date_str ).getTime();
	var result = $("#addProfile_result").val();

	var requestData = {
			name: name,
			date: date,
			result: result
		};

	$.ajax({
		url: "/secured/addWorkoutForTrainee",
		data: JSON.stringify( requestData ),
		type: 'POST',
		dataType: 'text',						//what i get
		contentType: 'application/json',		//what i send
		success: function(data, textStatus, jqXHR)
		{
			alert('workout added successfully');
		},
		error: function(jqXHR, textStatus, errorThrown)
		{
			alert('error: ' + jqXHR + '; status: ' + status + '; errorThrown: ' + errorThrown);
		}
	});
}