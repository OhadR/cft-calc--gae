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
                        content: '<i class="fa fa-dashboard"></i> Leaderboard'
                    }
                }
            }, {
                url: '/workouts',
                config: {
                    title: 'my profile',
                    templateUrl: 'app/workouts/workouts.html',
                    settings: {
                        nav: 3,
                        content: '<i class="fa fa-lock"></i> My Profile'
                    }
                }
            }, {
                url: '/addworkout',
                config: {
                    title: 'add workout',
                    templateUrl: 'app/workouts/addworkout.html',
                    settings: {
                        nav: 2,
                        content: '<i class="fa fa-lock"></i> Add Workout'
                    }
                }
            }, 
            {
                url: '/admin',
                config: {
                    title: 'Administration',
                    templateUrl: 'app/admin/admin.html',
                    settings: {
                        nav: 4,
                        content: '<i class="fa fa-lock"></i> Administration'
                    }
                }
            },
            {
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
            }, 
            {
                url: '/accountCreatedSuccess',
                config: {
                    title: 'account created successfully',
                    templateUrl: 'app/authentication/accountCreatedSuccess.html',
                }
            },
            {
                url: '/accountLocked',
                config: {
                    title: 'account locked',
                    templateUrl: 'app/authentication/accountLocked.html',
                }
            },
            {
                url: '/accountActivated',
                config: {
                    title: 'account activated',
                    templateUrl: 'app/authentication/accountActivated.html',
                }
            }
        ];
    }
})();