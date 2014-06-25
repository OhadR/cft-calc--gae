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
            getPeople: getPeople,
            getMessageCount: getMessageCount
        };

        return service;

        function login(userName, password) {
            var d = $q.defer();

            $http({
                method: 'POST',
                url: 'http://cbenchmarkr.appspot.com/j_spring_security_check',
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
                url: 'http://cbenchmarkr.appspot.com/rest/createAccount',
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
                url: 'http://cbenchmarkr.appspot.com/rest/forgotPasswordPage',
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

        function getPeople() {
            var people = [
                { firstName: 'John', lastName: 'Papa', age: 25, location: 'Florida' },
                { firstName: 'Ward', lastName: 'Bell', age: 31, location: 'California' },
                { firstName: 'Colleen', lastName: 'Jones', age: 21, location: 'New York' },
                { firstName: 'Madelyn', lastName: 'Green', age: 18, location: 'North Dakota' },
                { firstName: 'Ella', lastName: 'Jobs', age: 18, location: 'South Dakota' },
                { firstName: 'Landon', lastName: 'Gates', age: 11, location: 'South Carolina' },
                { firstName: 'Haley', lastName: 'Guthrie', age: 35, location: 'Wyoming' }
            ];
            return $q.when(people);
        }
    }
})();