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
        vm.onSaveWorkout = onSaveWorkout;

        activate();


        function activate() {
            common.activateController([], controllerId);
        }

        function onSaveWorkout() 
        {
            if ( !vm.workout.name ) 
            {
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
            	log( "error: " + error );
                vm.hasError = true;
            });
        }
    }
})();