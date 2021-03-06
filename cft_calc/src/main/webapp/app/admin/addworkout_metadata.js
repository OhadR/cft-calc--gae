﻿(function () {
    'use strict';
    var controllerId = 'adminAddWorkoutMetadata';
    angular.module('app').controller(controllerId, ['common', 'datacontext', addworkout]);

    function addworkout(common, datacontext) {
        var getLogFn = common.logger.getLogFn;
        var log = getLogFn(controllerId);
        var log_error = getLogFn(controllerId, 'error');

        var vm = this;
        vm.title = "Add Workout Metadata";
        vm.workout = { };		//input object from the form.
        vm.onSaveWorkoutMetadata = onSaveWorkoutMetadata;

        activate();


        function activate() {
            common.activateController( [], controllerId );
        }

        function onSaveWorkoutMetadata() 
        {
            if ( !vm.workout.name ) 
            {
            	log_error( 'workout name is empty' );
                return;
            }

            //call backend:
            datacontext.addWorkoutMetadata( vm.workout.name, vm.workout.repetitionBased, 
            		vm.workout.description,
            		vm.workout.units).
            then(function (loginData) 
            {
                log( "workout " + vm.workout.name + " added");
//                $location.path('/');
            }, function(error) 
            {
            	log_error( "error: " + error );
            });
        }
   
    }
})();