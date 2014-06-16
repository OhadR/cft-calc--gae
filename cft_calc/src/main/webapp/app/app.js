﻿(function () {
    'use strict';
    
    var app = angular.module('app', [
        // Angular modules 
        'ngAnimate',        // animations
        'ngRoute',          // routing
        'ngSanitize',       // sanitizes html bindings (ex: sidebar.js)

        // Custom modules 
        'common',           // common functions, logger, spinner
        'common.bootstrap', // bootstrap dialog wrapper functions

        // 3rd Party Modules
        'ui.bootstrap',     // ui-bootstrap (ex: carousel, pagination, dialog)

    ]);
    
    // Handle routing errors and success events
    app.run(['$route', '$rootScope', '$location', 'auth',  function ($route, $rootScope, $location, auth) {
        // Include $route to kick start the router.

        $rootScope.$on("$locationChangeStart", function () {
            if (!auth.isUserLoggedIn) {
                var nextPath = $location.path();

                if (nextPath != '/login' && nextPath != '/forgotpassword' && nextPath != '/newuser') {
                    $location.path('/login');
                }
            }
        });
    }]);        
})();