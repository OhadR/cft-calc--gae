'use strict';
/**
 * @ngdoc overview
 * @name sbAdminApp
 * @description
 * # sbAdminApp
 *
 * Main module of the application.
 */
var app = angular.module('sbAdminApp', [
    'oc.lazyLoad',
    'ui.router',
    'ui.bootstrap',
    'angular-loading-bar',
  ]);


  app.config(['$stateProvider','$urlRouterProvider','$ocLazyLoadProvider',
              function ($stateProvider,$urlRouterProvider,$ocLazyLoadProvider) {
    
    $ocLazyLoadProvider.config({
      debug:false,
      events:true,
    });

    $urlRouterProvider.otherwise('/dashboard/home');

    $stateProvider
      .state('dashboard', {
        url:'/dashboard',
        templateUrl: 'views/dashboard/main.html',
        data : {requireLogin : true },
        resolve: {
            loadMyDirectives:function($ocLazyLoad){
                return $ocLazyLoad.load(
                {
                    name:'sbAdminApp',
                    files:[
                    'scripts/directives/header/header.js',
                    'scripts/directives/header/header-notification/header-notification.js',
                    'scripts/directives/sidebar/sidebar.js',
                    'scripts/directives/sidebar/sidebar-search/sidebar-search.js'
                    ]
                }),
                $ocLazyLoad.load(
                {
                   name:'toggle-switch',
                   files:["bower_components/angular-toggle-switch/angular-toggle-switch.min.js",
                          "bower_components/angular-toggle-switch/angular-toggle-switch.css"
                      ]
                }),
                $ocLazyLoad.load(
                {
                  name:'ngAnimate',
                  files:['bower_components/angular-animate/angular-animate.js']
                }),
                $ocLazyLoad.load(
                {
                  name:'ngCookies',
                  files:['bower_components/angular-cookies/angular-cookies.js']
                }),
                $ocLazyLoad.load(
                {
                  name:'ngResource',
                  files:['bower_components/angular-resource/angular-resource.js']
                }),
                $ocLazyLoad.load(
                {
                  name:'ngSanitize',
                  files:['bower_components/angular-sanitize/angular-sanitize.js']
                }),
                $ocLazyLoad.load(
                {
                  name:'ngTouch',
                  files:['bower_components/angular-touch/angular-touch.js']
                })
            }
        }//close resolve
    })	//close state
      .state('dashboard.home',{
        url:'/home',
        controller: 'MainCtrl',
        templateUrl:'views/dashboard/home.html',
        data : {requireLogin : true },
        resolve: {
          loadMyFiles:function($ocLazyLoad) {
            return $ocLazyLoad.load({
              name:'sbAdminApp',
              files:[
              'scripts/controllers/main.js',
              'scripts/directives/timeline/timeline.js',
              'scripts/directives/notifications/notifications.js',
              'scripts/directives/chat/chat.js',
              'scripts/directives/dashboard/stats/stats.js'
              ]
            })
          }
        }
      })	//close state
      .state('dashboard.form',{
        templateUrl:'views/form.html',
        url:'/form'
    })
      .state('dashboard.blank',{
        templateUrl:'views/pages/blank.html',
        url:'/blank'
    })
      .state('login',{
        templateUrl:'views/pages/login.html',
        url:'/login',
        controller : 'LoginCtrl',
    })
      .state('facebookLogin',{
        templateUrl:'views/pages/login.html',
        url:'/facebookLogin',
        controller : 'XXXLoginCtrl',
    })
      .state('dashboard.chart',{
        templateUrl:'views/chart.html',
        url:'/chart',
        controller:'ChartCtrl',
        resolve: {
          loadMyFile:function($ocLazyLoad) {
            return $ocLazyLoad.load({
              name:'chart.js',
              files:[
                'bower_components/angular-chart.js/dist/angular-chart.min.js',
                'bower_components/angular-chart.js/dist/angular-chart.css'
              ]
            }),
            $ocLazyLoad.load({
                name:'sbAdminApp',
                files:['scripts/controllers/chartContoller.js']
            })
          }
        }
    })	//close state.charts
      .state('dashboard.table',{
        templateUrl:'views/table.html',
        url:'/table'
    })
      .state('dashboard.leaderboard',{
        templateUrl:'views/leaderboard.html',
        url:'/leaderboard'
    })
      .state('dashboard.panels-wells',{
          templateUrl:'views/ui-elements/panels-wells.html',
          url:'/panels-wells'
      })
      .state('dashboard.buttons',{
        templateUrl:'views/ui-elements/buttons.html',
        url:'/buttons'
    })
      .state('dashboard.notifications',{
        templateUrl:'views/ui-elements/notifications.html',
        url:'/notifications'
    })
      .state('dashboard.typography',{
       templateUrl:'views/ui-elements/typography.html',
       url:'/typography'
   })
      .state('dashboard.icons',{
       templateUrl:'views/ui-elements/icons.html',
       url:'/icons'
   })
      .state('dashboard.grid',{
       templateUrl:'views/ui-elements/grid.html',
       url:'/grid'
   });	//close state.grid


   
  
  }])
  .factory('Auth',function() { return { isLoggedIn : false}; })
  .controller('LoginCtrl',['$scope', 'Auth', function($scope, Auth) 
  { 
    $scope.auth = Auth;
  }])		//close controller
  .controller('XXXLoginCtrl',['$scope', '$state', 'Auth', function($scope, $state, Auth) 
  { 
     $scope.auth = Auth;
     Auth.isLoggedIn = true;
     $state.go('dashboard');
  }]);

/*
  //Handle routing errors and success events
  app.run(['$rootScope', '$location', 'Auth', function ($rootScope, $location, Auth) 
  {
	  // Include $route to kick start the router.

	  $rootScope.$on("$locationChangeStart", function () {
		  if (!auth.isUserLoggedIn) {
			  var nextPath = $location.path();
			  var searchObject = $location.search();

			  if (nextPath == '/facebookLogin')
			  {
				  auth.isUserLoggedIn = true;
				  if(searchObject != null)
				  {
					  auth.currentUser = searchObject.name;
				  }
				  $location.path('/');
			  }

			  else if (nextPath != '/login'
				  && nextPath != '/accountLocked'
					  && nextPath != '/accountActivated') 
			  {
				  $location.path('/login');
			  }
		  }
	  });
  }]);
*/
  
  app.run(function ($rootScope, $state, $location, Auth) {
	  
	    $rootScope.$on('$stateChangeStart', function (event, toState, toParams, fromState) {
	      
	      var shouldLogin = toState.data !== undefined
	                    && toState.data.requireLogin 
	                    && !Auth.isLoggedIn ;
	      
	      // NOT authenticated - wants any private stuff
	      if(shouldLogin)
	      {
	        $state.go('login');
	        event.preventDefault();
	        return;
	      }
	      
	      
	      // authenticated (previously) comming not to root main
	      if(Auth.isLoggedIn) 
	      {
	        var shouldGoToMain = fromState.name === ""
	                          && toState.name !== "main" ;
	          
	        if (shouldGoToMain)
	        {
	            $state.go('main');
	            event.preventDefault();
	        } 
	        return;
	      }
	      
	      // UNauthenticated (previously) comming not to root public 
	      var shouldGoToPublic = fromState.name === ""
	                        && toState.name !== "public"
	                        && toState.name !== "login" ;
	        
	      if(shouldGoToPublic)
	      {
	          $state.go('public');console.log('p')
	          event.preventDefault();
	      } 
	      
	      // unmanaged
	    });
	});