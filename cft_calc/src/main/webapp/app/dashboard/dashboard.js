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
        vm.messageCount = 0;
        vm.people = [];
        vm.title = 'Leaderboard';
        vm.resultMapPerWorkoutIndex = '';
        vm.onWorkoutChanged = onWorkoutChanged;
        vm.traineesPerWorkout = [];

        activate();

        function activate() {
            var promises = [getMessageCount(), getPeople(), loadWorkoutNames()];
            common.activateController(promises, controllerId)
                .then(function () { log('Activated Dashboard View'); });
        }

        function getMessageCount() {
            return datacontext.getMessageCount().then(function (data) {
                return vm.messageCount = data;
            });
        }

        function getPeople() {
            return datacontext.getPeople().then(function (data) {
                return vm.people = data;
            });
        }
        

        function loadWorkoutNames() 
        {
            datacontext.getAllWorkoutsNames().
            then(function(data) 
            {
                vm.workoutNames = data;
            });
        }
        
        function onWorkoutChanged()
        {
        	//do not call backend again - we have data in vm.people. just filter it.

        	vm.traineesPerWorkout = vm.people;
        	angular.forEach( vm.people, function(value, key)
        	{
        		vm.traineesPerWorkout[key].firstName = value.firstName;
        		vm.traineesPerWorkout[key].lastName = value.lastName;
        		var resultsMap = value.resultsMap;
           		vm.traineesPerWorkout[key].result = resultsMap[vm.workout.name];
        	});
        }
        
        
        
        
    }
})();