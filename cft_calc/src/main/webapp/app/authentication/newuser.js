(function () {
    'use strict';
    var controllerId = 'newuser';
    angular.module('app').controller(controllerId, ['$location', 'common', 'auth', newUser]);

    function newUser($location, common, auth) {
        var getLogFn = common.logger.getLogFn;
        var log = getLogFn(controllerId);
        var log_error = getLogFn(controllerId, 'error');

        var vm = this;
        vm.title = 'Sign Up';
        vm.failedCreatingUser = false;
        vm.userInfo = {
            userName: '',
            password: '',
            firstName: '',
            lastName: '',
            gender: 'Male',
            dob: '01 Jan 1990'
        };
        vm.createUser = createUser;

        activate();

        function activate() {
            common.activateController([], controllerId);
        }
        
        function createUser() 
        {
    		//log( "creating account for " + vm.userInfo.userName + "..." );    
            //do not try to login, instead show a "message was sent" page
        	auth.createUser(vm.userInfo.userName, vm.userInfo.password, 
        			vm.userInfo.firstName, vm.userInfo.lastName,
        			(vm.userInfo.gender == 'Male'),
        			vm.userInfo.dob	).
        	then(function (loginData) 
        	{
        		log( "account created for user " + vm.userInfo.userName );    
        		$location.params = vm.userInfo.userName;
        		$location.path('/accountCreatedSuccess');
            },
            function(error) {
            	 log_error( "error creating account: " + error );
            	 vm.failedCreatingUser = true;
            });
        }
    }
})();