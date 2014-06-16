(function () {
    'use strict';
    var controllerId = 'addworkout';
    angular.module('app').controller(controllerId, ['common', 'datacontext', addworkout]);

    function addworkout(common, datacontext) {
        var getLogFn = common.logger.getLogFn;
        var log = getLogFn(controllerId);

        var vm = this;
        vm.title = "Add Workout";
        vm.workout = { };
        vm.saveWorkout = saveWorkout;

        activate();


        function activate() {
            common.activateController([], controllerId);
        }

        function saveWorkout() {
        }
    }
})();