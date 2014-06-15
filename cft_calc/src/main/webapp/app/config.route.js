(function () {
    'use strict';

    var app = angular.module('app');

    // Collect the routes
    app.constant('routes', getRoutes());
    
    // Configure the routes and route resolvers
    app.config(['$routeProvider', 'routes', routeConfigurator]);
    function routeConfigurator($routeProvider, routes) {

        routes.forEach(function (r) {
            $routeProvider.when(r.url, r.config);
        });
        $routeProvider.otherwise({ redirectTo: '/' });
    }

    // Define the routes 
    function getRoutes() {
        return [
            {
                url: '/',
                config: {
                    templateUrl: 'app/dashboard/dashboard.html',
                    title: 'dashboard',
                    settings: {
                        nav: 1,
                        content: '<i class="fa fa-dashboard"></i> Dashboard'
                    }
                }
            }, {
                url: '/workouts',
                config: {
                    title: 'workouts',
                    templateUrl: 'app/workouts/workouts.html',
                    settings: {
                        nav: 2,
                        content: '<i class="fa fa-lock"></i> Workouts'
                    }
                }
            }, {
                 url: '/login',
                 config: {
                     title: 'login',
                     templateUrl: 'app/authentication/login.html',
                 }
            }, {
                url: '/forgotpassword',
                config: {
                    title: 'forgot password',
                    templateUrl: 'app/authentication/forgotpassword.html',
                }
            }, {
                url: '/newuser',
                config: {
                    title: 'new user',
                    templateUrl: 'app/authentication/newuser.html',
                }
            }
        ];
    }
})();