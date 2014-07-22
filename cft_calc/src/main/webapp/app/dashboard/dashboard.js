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
        vm.onWorkoutChanged = onWorkoutChanged;
        vm.traineesPerWorkout = [];

        activate();

        function activate() {
            var promises = [getRegisteredUsers(), getRegisteredResults(), getPeople(), loadWorkoutNames()];
            common.activateController(promises, controllerId)
                .then(function () { log('Activated Dashboard View'); });
        }

        function getRegisteredUsers() {
            return datacontext.getRegisteredUsers().then(function (data) {
                return vm.registeredUsers = data;
            });
        }

        function getRegisteredResults() {
            return datacontext.getMessageCount().then(function (data) {
                return vm.registeredResults = data;
            });
        }

        function getPeople() {
            return datacontext.getPeople().then(function (data) {
                return vm.people = data;
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
               		vm.traineesPerWorkout[index].firstName = value.firstName;
            		vm.traineesPerWorkout[index].lastName = value.lastName;
               		vm.traineesPerWorkout[index].result = result;
               		++index;
           		}
        	});
        	
        	//sort by the result:
        	vm.traineesPerWorkout.sort( compare );
        }
        
        function compare(a,b) 
        {
        	  if (a.result < b.result)
        	     return -1;
        	  if (a.result > b.result)
        	    return 1;
        	  return 0;
        }

        	
        
        
        
        
    }
})();