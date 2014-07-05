(function () {
    'use strict';

    var serviceId = 'datacontext';
    angular.module('app').factory(serviceId, ['$http', '$q', 'common', datacontext]);

    function datacontext($http, $q, common) {
        var $q = common.$q;

        var service = {
            getPeople: getPeople,
            getMessageCount: getMessageCount,
            getWorkoutHistoryForTrainee: getWorkoutHistoryForTrainee,
            getAllWorkoutsNames: getAllWorkoutsNames,
            addWorkout: addWorkout,
            addWorkoutMetadata: addWorkoutMetadata,
        };

        return service;

        function getMessageCount() 
        { 
            var d = $q.defer();

            var x = getPeople().
        	then(function (data) 
        	{
                d.resolve( data.length );
        	});
            return d.promise;
        }

        function getPeople()
        {
            var d = $q.defer();

            $http({
                method: 'GET',
                url: '/secured/getSortedTraineesByGrade',
                data:  $.param({ json: 'json' }),
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            }).success(function (data, status, headers, config) 
            {
                d.resolve(data);
                $q.when( data );
            }).error(function (data, status, headers, config) {
                d.reject( data, status );
            });

            return d.promise;
        }
        

        function addWorkout(workoutName, result, timestamp)
        {
            var d = $q.defer();

            $http({
                method: 'POST',
                url: '/secured/addWorkoutForTrainee',
                data:  $.param({ name: workoutName, result: result, date: timestamp }),
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },

//                data:  '{"date":1402071766642,"result":' + result + ',"name":"cindy"}',
            }).success(function (data, status, headers, config) 
            {
                d.resolve( data );
                $q.when( data );
            }).error(function (data, status, headers, config) {
                d.reject( data, status );
            });

            return d.promise;
        }

        /**
         * example to a GET call with params
         */
        function getWorkoutHistoryForTrainee( workoutName ) 
        {
            var d = $q.defer();

            $http({
                method: 'GET',
                url: '/secured/getWorkoutHistoryForTrainee',
                params:  { json: workoutName },
            }).success(function (data, status, headers, config) 
            {
                d.resolve(data);
                $q.when( data );
            }).error(function (data, status, headers, config) {
                d.reject( data, status );
            });

            return d.promise;

        }
        
        function getAllWorkoutsNames()
        {
            var d = $q.defer();

            $http({
                method: 'GET',
                url: '/getAllWorkoutsNames',
//                params:  { json: workoutName },
            }).success(function (data, status, headers, config) 
            {
                d.resolve(data);
                $q.when( data );
            }).error(function (data, status, headers, config) {
                d.reject( data, status );
            });

            return d.promise;        
        }
        
        
        
//        called by addworkout_metadata.js
        function addWorkoutMetadata(workoutName, repetitionBased, description)
        {
            var d = $q.defer();

            $http({
                method: 'POST',
                url: '/secured/admin/addWorkout',
                data:  $.param({ name: workoutName, isRepetitionBased: repetitionBased, description: description }),
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            }).success(function (data, status, headers, config) 
            {
                d.resolve( data );
                $q.when( data );
            }).error(function (data, status, headers, config) {
                d.reject( data, status );
            });

            return d.promise;
        }
    }
})();