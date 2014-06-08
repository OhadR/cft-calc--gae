
function OnShowGrades()
{
	$.ajax({
		url: "/secured/getSortedTraineesByGrade",
//		data: JSON.stringify(requestData),
		type: 'GET',
		dataType: 'text',
		contentType: 'application/json',
		success: function(data, textStatus, jqXHR)
		{
			alert('data: ' + data);
		},
		error: function(jqXHR, textStatus, errorThrown)
		{
			alert('error: ' + jqXHR + '; status: ' + status + '; errorThrown: ' + errorThrown);
		}
	});
}


function OnShowHistory()
{
	var requestData = getItemFromCombobox( 'history_workoutsCombobox' );
	
	$.ajax({
		url: "/secured/getWorkoutHistoryForTrainee",
		data: {json : requestData},
		type: 'GET',
		dataType: 'json',
		success: function(result, textStatus, jqXHR)
		{
			var fff = '';
			for(var i = 0; i < result.length; ++i)
			{
				fff += (result[i].result + ' : ' + result[i].timestamp + '\n');
			}
			alert( fff );
		},
		error: function(jqXHR, textStatus, errorThrown)
		{
			alert('error: ' + jqXHR + '; status: ' + status + '; errorThrown: ' + errorThrown);
		}
	});
}



function populateWorkouts( comboId, jsonedWorkouts )
{
	var cbx = document.getElementById( comboId );
	//clear previous values:
	cbx.options.length = 0;

	var instancesArray = new Array();

	var txt = '';
	for(var i = 0; i < jsonedWorkouts.length; ++i)
	{
		var instance = jsonedWorkouts[i];
		cbx.options[i] = new Option(instance, instance);
		
		txt += instance.instanceId;
		txt += ', ';
	}
}


/**
 * return item-name from a given combo. good for "workouts", where we have several combo's that does the same thing.
 * @param comboId
 * @returns
 */
function getItemFromCombobox( comboId )
{
	var combox = document.getElementById( comboId );
	var selIndex = combox.selectedIndex;
	if(selIndex < 0)
	{
		alert('please select a workout');
		return;
	}
	var workout = combox.options[selIndex].value;
	return workout;

}

$(document).ready(function() {


	function validateEmail(email) { 
		var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
		return re.test(email);
	} 

	
			
			$("#submit").click(function(){
				
				if (!validateEmail($("#email").val())){
					$(".emptyEmail").show();
					return;
				}
				
				var items = {};
				
				//var list = $("#listView").data('kendoListView');
				//var ds = list.getDataSource().items();
				for (var i = 0; i < products.length; i++){
					var currAmount = $("#" + products[i].ProductID).find("#amount" + products[i].ProductID).data("kendoNumericTextBox").value();
					if (currAmount > 0){
						items[products[i].ProductName] = currAmount;					
					}
				}
			
				var requestData = {
					email: $("#email").val(),
					order: items
				};
				
				$.ajax({
					url: "/order",
					data: JSON.stringify(requestData),
					type: 'POST',
					dataType: 'text',
					contentType: 'application/json',
					success: function(data, textStatus, jqXHR){
						alert('confirmed');
					},
					error: function(jqXHR, textStatus, errorThrown){
						alert('error: ' + jqXHR + '; status: ' + status + '; errorThrown: ' + errorThrown);
					}
				});
			});

});
