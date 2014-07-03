﻿(function () {
    'use strict';
    var controllerId = 'workouts';
    angular.module('app').controller(controllerId, ['$location', 'common', 'datacontext', workouts]);

    function workouts($location, common, datacontext) {
        var getLogFn = common.logger.getLogFn;
        var log = getLogFn(controllerId);

        var vm = this;
        vm.title = 'My workouts';
        vm.workout = { };
        vm.workouts = [];		//objects to the UI. each contains name, result and timestamp
        vm.workoutNames = [];
        vm.onWorkoutChanged = onWorkoutChanged;

        activate();

        function activate() {
            common.activateController( [loadWorkoutNames()], controllerId );
        }

        function loadWorkouts() {
            datacontext.getWorkoutHistoryForTrainee( vm.workout.name ).then(function(data) {
                vm.workouts = data;
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
        
        function onWorkoutChanged()
        {
        	loadWorkouts();
        }

    }
})();