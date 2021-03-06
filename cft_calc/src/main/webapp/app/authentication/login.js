﻿(function () {
    'use strict';
    var controllerId = 'login';
    angular.module('app').controller(controllerId, ['$location', 'common', 'auth', login]);

    function login($location, common, auth) {
        var getLogFn = common.logger.getLogFn;
        var log = 		getLogFn(controllerId);
        var log_error = getLogFn(controllerId, 'error');

        var vm = this;
        vm.userInfo = {
            userName: '',
            password: '',
            rememberMe: false
        };
        vm.loginUser = loginUser;

        activate();

        var userName = sessionStorage.userName;
        var password = sessionStorage.password;

        if (userName && password) {
            vm.userInfo.userName = userName;
            vm.userInfo.password = password;
            vm.loginUser();
        }
            

        function activate() {
            common.activateController([], controllerId);
        }

        function loginUser() {
        	//log( "user info - " + vm.userInfo.userName );
            auth.login(vm.userInfo.userName, vm.userInfo.password).then(function(data) {
                $location.path('/');
                sessionStorage.userName = vm.userInfo.userName;
                sessionStorage.password = vm.userInfo.password;
            }, function(error) 
            {
            	if( error == 423 )	//locked
            	{
            		//handle account lockout = redirect:
                    $location.path('/accountLocked');
            	}
            	else
            	{
            		log_error( "login failed for user " + vm.userInfo.userName + ": " + error );
            	}            	
            });
        }
    }
})();