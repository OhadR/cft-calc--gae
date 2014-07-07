(function () {
    'use strict';
    var controllerId = 'forgotpassword';
    angular.module('app').controller(controllerId, ['common', 'auth', forgotpassword]);

    function forgotpassword(common, auth) {
        var getLogFn = common.logger.getLogFn;
        var log = getLogFn(controllerId);
        var log_error = getLogFn(controllerId, 'error');

        var vm = this;
        vm.title = 'Forgot password';
        vm.onForgotPassword = onForgotPassword;
        vm.passwordSent = false;
        vm.hasError = false;
        vm.error = '';

        vm.userInfo = {
            email: ''
        };

        activate();

        function activate() {
            common.activateController([], controllerId);
        }

        function onForgotPassword() {
            if ( !vm.userInfo.email ) 
            {
                vm.error = 'Email address is empty';
                vm.hasError = true;
                return;
            }

            auth.restorePassword( vm.userInfo.email ).then(function (loginData) 
            {
                log( "password sent to user " + vm.userInfo.email );
                vm.passwordSent = true;
//                $location.path('/');
            }, function(error) 
            {
            	log_error( "error: " + error );
                vm.hasError = true;
            	vm.failedCreatingUser = true;
            });

        }
    }
})();