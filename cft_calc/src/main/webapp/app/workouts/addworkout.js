(function () {
    'use strict';
    var controllerId = 'addworkout';
    angular.module('app').controller(controllerId, ['common', 'datacontext', addworkout]);

    function addworkout(common, datacontext) {
        var getLogFn = common.logger.getLogFn;
        var log = getLogFn(controllerId);
        var log_error = getLogFn(controllerId, 'error');

        var vm = this;
        vm.title = "Add Workout";
        vm.workout = { };
        vm.onSaveWorkout = onSaveWorkout;
        vm.workoutNames = [];

        activate();


        function activate() {
            common.activateController( [loadWorkoutNames()], controllerId );
        }

        function onSaveWorkout() 
        {
            if ( !vm.workout.name ) 
            {
            	log_error( 'workout name is empty' );
                vm.error = 'workout name is empty';
                vm.hasError = true;
                return;
            }

            //call backend:
            datacontext.addWorkout( vm.workout.name, vm.workout.result, vm.workout.date ).
            then(function (loginData) 
            {
                log( "workout " + vm.workout.name + " : " + vm.workout.result + " added");
//                $location.path('/');
            }, function(error) 
            {
            	log_error( "error: " + error );
                vm.hasError = true;
            });
        }

        function loadWorkoutNames() {
            datacontext.getAllWorkoutsNames().
            then(function(data) {
                vm.workoutNames = data;

                //set a value, so we will not see an empty line:
                vm.workout.name = vm.workoutNames[0];		
            });
        }
        
    }
})();