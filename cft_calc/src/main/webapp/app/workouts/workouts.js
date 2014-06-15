(function () {
    'use strict';
    var controllerId = 'workouts';
    angular.module('app').controller(controllerId, ['common', 'datacontext', workouts]);

    function workouts(common, datacontext, ngTableParams) {
        var getLogFn = common.logger.getLogFn;
        var log = getLogFn(controllerId);

        var vm = this;
        vm.title = 'My workouts';
        vm.workouts = [];
        vm.addWorkout = addWorkout();

        activate();


        function activate() {
            common.activateController([loadWorkouts()], controllerId);
        }

        function loadWorkouts() {
            datacontext.getWorkouts().then(function(data) {
                vm.workouts = data;
            });
        }

        function addWorkout() {
            
        }
    }
})();