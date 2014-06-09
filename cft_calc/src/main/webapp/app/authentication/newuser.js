(function () {
    'use strict';
    var controllerId = 'newuser';
    angular.module('app').controller(controllerId, ['$location', 'common', 'auth', newUser]);

    function newUser($location, common, auth) {
        var getLogFn = common.logger.getLogFn;
        var log = getLogFn(controllerId);

        var vm = this;
        vm.title = 'Create new user';
        vm.userInfo = {
            email: '',
            password: '',
            firstName: '',
            lastName: '',
            rememberMe: false
        };
        vm.createUser = createUser;

        activate();

        function activate() {
            common.activateController([], controllerId);
        }
        
        function createUser() {
            auth.createUser(vm.userInfo.email, vm.userInfo.password, vm.userInfo.firstName, vm.userInfo.lastName).then(function(newUserData) {
                auth.login(vm.userInfo.email, vm.userInfo.password).then(function(loginData) {
                    $location.path('/');
                });
            });
        }
    }
})();