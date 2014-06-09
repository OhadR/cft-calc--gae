(function () {
    'use strict';
    var controllerId = 'forgotpassword';
    angular.module('app').controller(controllerId, ['common', 'auth', forgotpassword]);

    function forgotpassword(common, auth) {
        var getLogFn = common.logger.getLogFn;
        var log = getLogFn(controllerId);

        var vm = this;
        vm.title = 'Forgot password';
        vm.sendPassword = sendPassword;
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

        function sendPassword() {
            if (!vm.userInfo.email) {
                vm.error = 'Email address is empty';
                vm.hasError = true;
                return;
            }

            vm.hasError = false;
            //temp
            vm.passwordSent = true;
        }
    }
})();