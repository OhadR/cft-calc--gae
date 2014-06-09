﻿(function () {
    'use strict';
    var controllerId = 'login';
    angular.module('app').controller(controllerId, ['$location', 'common', 'auth', login]);

    function login($location, common, auth) {
        var getLogFn = common.logger.getLogFn;
        var log = getLogFn(controllerId);

        var vm = this;
        vm.title = 'Login';
        vm.userInfo = {
            userName: '',
            password: '',
            rememberMe: false
        };
        vm.loginUser = loginUser;
        vm.fotgotPassword = fotgotPassword;
        vm.newUser = newUser;

        activate();

        function activate() {
            common.activateController([], controllerId);
        }

        function loginUser() {
            log("user info - " + vm.userInfo.userName + ": " + vm.userInfo.password);
            auth.login(vm.userInfo.userName, vm.userInfo.password).then(function(data) {
                $location.path('/');
            });
        }

        function fotgotPassword() {
            log("navigating...");
            $location.path('/forgotpassword');
        }

        function newUser() {
            log("navigating...");
            $location.path('/newuser');
        }
    }
})();