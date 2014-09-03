(function () {
    'use strict';

    var serviceId = 'auth';
    angular.module('app').factory(serviceId, ['$http', '$q', 'common', auth]);

    function auth($http, $q, common) {
        var $q = common.$q;

        var loggedInUser = undefined;

        var service = {
            isUserLoggedIn: false,
            currentUser: loggedInUser,
            login: login,
            createUser: createUser,
            restorePassword: restorePassword,
            signOut: signOut,
            changePassword: changePassword,
        };

        return service;

        function login(userName, password) {
            var d = $q.defer();

            $http({
                method: 'POST',
                url: '/j_spring_security_check',
                data:  $.param({ j_username: userName, j_password: password }),
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            }).success(function (data, status, headers, config) {
                service.isUserLoggedIn = true;
                service.currentUser = userName;
                d.resolve();
            }).error(function (data, status, headers, config) {
                d.reject(status);
            });

            return d.promise;
        }

        function signOut() {
        	/*
        	var d = $q.defer();

            $http({
                method: 'POST',
                url: '/j_spring_security_check',
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            }).success(function (data, status, headers, config) {
                service.isUserLoggedIn = true;
                service.currentUser = userName;
                d.resolve();
            }).error(function (data, status, headers, config) {
                d.reject(status);
            });

            return d.promise;
*/
        	service.currentUser = '';
            service.isUserLoggedIn = false;

            sessionStorage.userName = '';
            sessionStorage.password = '';

            return $q.when(true);
        }

        function createUser(userName, password, firstName, lastName, isMale, dateOfBirth) 
        {
            var d = $q.defer();

            $http({
                method: 'POST',
                url: '/rest/createAccount',
                data:  $.param({ email: userName, password: password, confirm_password: password, 
                				firstName: firstName,
                				lastName: lastName}),
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            })
            .success(function (data, status, headers, config) 
            {
                $http({
                    method: 'POST',
                    url: '/createBenchmarkrAccount',
                    data:  $.param({ traineeId: userName,
                    				isMale: isMale,
                    				dateOfBirth: dateOfBirth }),
                    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                })
	            .success(function (data, status, headers, config) 
	            {
	                d.resolve();
	            }).error(function (data, status, headers, config) {
	                d.reject(data, status);
	            });
            	
                d.resolve();
            }).error(function (data, status, headers, config) {
                d.reject(data, status);
            });

            return d.promise;
        }

        function restorePassword( email ) {
            var d = $q.defer();

            $http({
                method: 'POST',
                url: '/rest/forgotPassword',
                data:  $.param({ email: email  }),
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            }).success(function (data, status, headers, config) {
                d.resolve();
            }).error(function (data, status, headers, config) {
                d.reject(status);
            });

            return d.promise;
        }

        function changePassword( currentPass, newPass ) {
            var d = $q.defer();

            $http({
                method: 'POST',
                url: '/rest/changePassword',
                data:  $.param({ currentPassword: currentPass, newPassword: newPass, confirm_password: newPass  }),
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            }).success(function (data, status, headers, config) {
                d.resolve();
            }).error(function (data, status, headers, config) {
                d.reject(data, status);
            });

            return d.promise;
        }

    }
})();