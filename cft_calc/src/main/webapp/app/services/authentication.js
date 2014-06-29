(function () {
    'use strict';

    var serviceId = 'auth';
    angular.module('app').factory(serviceId, ['$http', '$q', 'common', auth]);

    function auth($http, $q, common) {
        var $q = common.$q;

        var loggedInUser = undefined;

        var service = {
            isUserLoggedIn: false,
            login: login,
            createUser: createUser,
            restorePassword: restorePassword,
            signOut: signOut,
            currentUser: loggedInUser,
//            getPeople: getPeople,
            getMessageCount: getMessageCount
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
            service.currentUser = '';
            service.isUserLoggedIn = false;

            sessionStorage.userName = '';
            sessionStorage.password = '';

            return $q.when(true);
        }

        function createUser(userName, password) {
            var d = $q.defer();

            $http({
                method: 'POST',
                url: '/rest/createAccount',
                data:  $.param({ email: userName, password: password, confirm_password: password }),
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            }).success(function (data, status, headers, config) {
                d.resolve();
            }).error(function (data, status, headers, config) {
                d.reject(status);
            });

            return d.promise;
        }

        function restorePassword( email ) {
            var d = $q.defer();

            $http({
                method: 'POST',
                url: 'http://cbenchmarkr.appspot.com/rest/forgotPassword',
                data:  $.param({ email: email  }),
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            }).success(function (data, status, headers, config) {
                d.resolve();
            }).error(function (data, status, headers, config) {
                d.reject(status);
            });

            return d.promise;
        }

        function getMessageCount() { return $q.when(72); }

    }
})();