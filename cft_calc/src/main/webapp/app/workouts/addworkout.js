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
        vm.show;		//boolean, indicates whether to show time-units. o/w we show regular "number" input (weight-units/reps).
        vm.workout = { };		//input object from the form.
        vm.workoutNames = [];

        //functions:
        vm.onWorkoutChanged = onWorkoutChanged;
        vm.onSaveWorkout = onSaveWorkout;

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

            //take the relevant results from UI:
            if( vm.workout.units == 'secs' )
            {
            	var parts = vm.workout.result_time.split(':');
            	vm.workout.result = parseInt(parts[0] * 60) + parseInt( parts[1] );
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

        function loadWorkoutNames() 
        {
            datacontext.getAllWorkoutsMetadata().
            then(function(data) 
            {
                vm.workoutNames = data;

                //set a value, so we will not see an empty line:
//                vm.workout.name = vm.workoutNames[0];		
            });
        }
        
        function onWorkoutChanged()
        {
       		vm.show = (vm.workout.units == 'secs');
        }        
        
    }
})();