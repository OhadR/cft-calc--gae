(function () {
    'use strict';

    var serviceId = 'auth';
    angular.module('app').factory(serviceId, ['common', auth]);

    function auth(common) {
        var $q = common.$q;

        var loggedInUser = undefined;

        var service = {
            isUserLoggedIn: false,
            login: login,
            createUser: createUser,
            signOut: signOut,
            currentUser: loggedInUser,
            getPeople: getPeople,
            getMessageCount: getMessageCount
        };

        return service;

        function login(userName, password) {
            // TEMP
            service.currentUser = userName;
            service.isUserLoggedIn = true;

            return $q.when(true);
        }

        function signOut() {
            service.currentUser = '';
            service.isUserLoggedIn = false;

            sessionStorage.userName = '';
            sessionStorage.password = '';

            return $q.when(true);
        }

        function createUser(userName, password, firstName, lastName) {
            return $q.when(true);
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