(function () {
    'use strict';
    var controllerId = 'insights';
    angular.module('app').controller(controllerId, ['common', 'datacontext', insights]);

    function insights(common, datacontext) {
//        var getLogFn = common.logger.getLogFn;
//        var log = getLogFn(controllerId);

        var vm = this;

        vm.registeredResults = 0;
        vm.registeredUsers = 0;

        //functions:
//        vm.onWorkoutChanged = onWorkoutChanged;

        activate();

        function activate() {
            var promises = [getRegisteredUsers(), getRegisteredResults()];
            common.activateController(promises, controllerId)
                .then(function () 
                	{ });
        }

        function getRegisteredUsers() {
            return datacontext.getRegisteredUsers().then(function (data) {
                return vm.registeredUsers = data;
            });
        }

        function getRegisteredResults() {
            return datacontext.getRegisteredResults().then(function (data) {
                return vm.registeredResults = data;
            });
        }

    }
})();