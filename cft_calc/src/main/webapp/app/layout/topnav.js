(function () {
    'use strict';
    var controllerId = 'topnav';
    angular.module('app').controller(controllerId, ['$location', 'common', 'auth', topnav]);

    function topnav($location, common, auth) {
        var getLogFn = common.logger.getLogFn;
        var log = getLogFn(controllerId);

        var vm = this;
        vm.auth = auth;
        vm.signOut = signOut;

        function signOut() {
            auth.signOut().then(function(result) {
                $location.path('/');
            });
        }
    }
})();