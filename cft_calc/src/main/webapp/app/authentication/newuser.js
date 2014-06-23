(function () {
    'use strict';
    var controllerId = 'newuser';
    angular.module('app').controller(controllerId, ['$location', 'common', 'auth', newUser]);

    function newUser($location, common, auth) {
        var getLogFn = common.logger.getLogFn;
        var log = getLogFn(controllerId);

        var vm = this;
        vm.title = 'Create new user';
        vm.failedCreatingUser = false;
        vm.userInfo = {
            userName: '',
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
            auth.createUser(vm.userInfo.userName, vm.userInfo.password).then(function(newUserData) {
                auth.login(vm.userInfo.userName, vm.userInfo.password).then(function (loginData) {
                    $location.path('/');
                });
            }, function(error) {
                vm.failedCreatingUser = true;
            });
        }
    }
})();