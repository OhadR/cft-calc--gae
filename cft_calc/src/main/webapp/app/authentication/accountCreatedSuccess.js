(function () {
    'use strict';
    var controllerId = 'accountCreatedSuccess';
    angular.module('app').controller(controllerId, ['$location', 'common', 'auth', accountCreatedSuccess]);

    function accountCreatedSuccess($location, common, auth) {

        var vm = this;
        vm.firstLine = 'A confirmation email has been sent to ' + $location.params + '.';
        vm.secondLine = 'Click on the confirmation link in the email to activate your account.';

        activate();

        function activate() 
        {
        	$location.params;
            common.activateController([], controllerId);
        }

    }
})();