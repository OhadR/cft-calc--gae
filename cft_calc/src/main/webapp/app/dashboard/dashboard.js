(function () {
    'use strict';
    var controllerId = 'dashboard';
    angular.module('app').controller(controllerId, ['common', 'datacontext', dashboard]);

    function dashboard(common, datacontext) {
        var getLogFn = common.logger.getLogFn;
        var log = getLogFn(controllerId);

        var vm = this;
        vm.news = {
            title: 'my area',
            description: 'Hot Towel Angular is a SPA template for Angular developers.'
        };
        vm.registeredResults = 0;
        vm.registeredUsers = 0;
        vm.people = [];
        vm.title = 'Leaderboard';
        vm.resultMapPerWorkoutIndex = '';
        vm.traineesPerWorkout = [];
        //boolean: true of we wanna show men, false if we wanna show women:
        vm.show_men = true; 
        vm.filter_gender = 'Men';	//attached to ComboBox

        //functions:
        vm.onWorkoutChanged = onWorkoutChanged;
        vm.onGenderChanged = onGenderChanged;

        activate();

        function activate() {
            var promises = [getPeople(), loadWorkoutNames()];
            common.activateController(promises, controllerId)
                .then(function () { log('Activated Dashboard View'); });
        }

        function getPeople() {
            return datacontext.getPeople().then(function (data) {
                //store all data in 'vm.people', and filter by gender here:
            	vm.people = data;
            	//sort by the result:
            	vm.people.sort( compareByTotalGrade );
        		vm.people.reverse();

            	return vm.people;                
            });
        }
        

        function loadWorkoutNames() 
        {
            datacontext.getAllWorkoutsMetadata().
            then(function(data) 
            {
                vm.workoutNames = data;
            });
        }
        
        function onWorkoutChanged()
        {
        	//do not call backend again - we have data in vm.people. just filter it.

        	//clean the table:
        	vm.traineesPerWorkout = [];
        	var index = 0;
        	
        	angular.forEach( vm.people, function(value, key)
        	{
        		//get the result-map of this trainee:
        		var resultsMap = value.resultsMap;
           		var result = resultsMap[vm.workout.name];
           		if( result > 0 )
           		{
           			vm.traineesPerWorkout[index] = [];
               		vm.traineesPerWorkout[index].firstName 	= value.firstName;
            		vm.traineesPerWorkout[index].lastName 	= value.lastName;
            		vm.traineesPerWorkout[index].male 		= value.male;
               		vm.traineesPerWorkout[index].result 	= result;
               		++index;
           		}
        	});
        	
        	//sort by the result:
        	vm.traineesPerWorkout.sort( compareByResult );
        	
        	if( vm.workout.repetitionBased )
        	{
        		vm.traineesPerWorkout.reverse();
        	}
        }
        
        function compareByResult(a,b) 
        {
        	  if (a.result < b.result)
        	     return -1;
        	  if (a.result > b.result)
        	    return 1;
        	  return 0;
        }

        function compareByTotalGrade(a,b) 
        {
        	  if (a.totalGrade < b.totalGrade)
        	     return -1;
        	  if (a.totalGrade > b.totalGrade)
        	    return 1;
        	  return 0;
        }


        
        function onGenderChanged()
        {
        	if( vm.filter_gender == 'Men' )
        	{
        		vm.show_men = true; 
        	}
        	else
        	{
        		vm.show_men = false; 
        	}
        }        
        
        
        
    }
})();