(function () {
    'use strict';

    var serviceId = 'datacontext';
    angular.module('app').factory(serviceId, ['common', datacontext]);

    function datacontext(common) {
        var $q = common.$q;

        var service = {
            getPeople: getPeople,
            getMessageCount: getMessageCount,
            getWorkouts: getWorkouts
        };

        return service;

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

        function getWorkouts() {
            var workouts = [
                { date: '1402071766642', result: 100, name: 'aaa' },
                { date: '1402071766642', result: 200, name: 'bbb' },
                { date: '1402071766642', result: 300, name: 'ccc' },
                { date: '1402071766642', result: 400, name: 'ddd' },
                { date: '1402071766642', result: 500, name: 'eee' },
                { date: '1402071766642', result: 600, name: 'fff' },
                { date: '1402071766642', result: 700, name: 'hhh' },
                { date: '1402071766642', result: 800, name: 'ggg' },
                { date: '1402071766642', result: 900, name: '1313' },
                { date: '1402071766642', result: 100, name: '2323' },
            ];

            return $q.when(workouts);
        }
    }
})();