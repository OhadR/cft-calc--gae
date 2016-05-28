(function () {
    'use strict';
    var controllerId = 'workouts';
    var mudule = angular.module('app');
    mudule.controller(controllerId, ['$scope', 'common', 'datacontext', 'auth', workouts]);

    
    function workouts($scope, common, datacontext, auth) {
        var getLogFn = common.logger.getLogFn;
        var log = 		getLogFn(controllerId);
        var log_error = getLogFn(controllerId, 'error');

        var vm = this;
        vm.title = 'My workouts';
        vm.workout = { };
        vm.workouts = [];		//objects to the UI. each contains name, result and timestamp
        vm.workoutNames = [];
        vm.userInfo = { };
        //functions:
        vm.onWorkoutChanged = onWorkoutChanged;
        vm.updateUser = updateUser;
        vm.onRemoveWorkout = onRemoveWorkout;

        $scope.config = {
  			  title: '',
  			  tooltips: true,
  			  labels: false,
  			  mouseover: function() {},
  			  mouseout: function() {},
  			  click: function() {},
  			  legend: {
  			    display: true,
  			    //could be 'left, right'
  			    position: 'left'
  			  },
  			  innerRadius: 0, // applicable on pieCharts, can be a percentage like '50%'
  			  lineLegend: 'lineEnd' // can be also 'traditional'
  			};

        $scope.chartType = "line";

        /*$scope.chartData = {
        	    series: ['Sales', 'Income', 'Expense', 'Laptops', 'Keyboards'],
        	    data: [{
        	      x: "Laptops",
        	      y: [100, 500, 0],
        	      tooltip: "this is tooltip"
        	    }, {
        	      x: "Desktops",
        	      y: [300, 100, 100]
        	    }, {
        	      x: "Mobiles",
        	      y: [351]
        	    }, {
        	      x: "Tablets",
        	      y: [54, 0, 879]
        	    }]
        	  };   */     

        //init data, so we will not see exceptions/errors:
        $scope.chartData = {
        	    series: [''],
        	    data: [{
            	      x: "",
            	      y: [0]
            	    }]
        	  };        

        activate();

        function activate() 
        {
            common.activateController( [loadWorkoutNames()], controllerId );
        }

        function loadWorkouts() {
            datacontext.getWorkoutHistoryForTrainee( vm.workout.name ).
            then(function(data) 
            {
                vm.workouts = data;
                //update the graph:
                $scope.chartData = {
                	    series: [vm.workout.name],
                	    data: [{
                  	      x: "No Records for " + vm.workout.name,
                	      y: [0]
                	    }]
                	  }; 
                var i;
                for(i = 0; i < data.length; ++i)
                {
                	//log(data[i].result + " / " + data[i].timestamp);
                	var d = new Date( data[i].timestamp );
                	
                	$scope.chartData.data[i] = { x: d.toDateString(), y: [data[i].result] };
                }
                
            });
        }

        function loadWorkoutNames()
        {
            datacontext.getAllWorkoutsNames().
            then(function(data) {
                vm.workoutNames = data;
                //set a value, so we will not see an empty line:
//                vm.workout.name = vm.workoutNames[0];		
            });
        }
        
        function onWorkoutChanged()
        {
        	loadWorkouts();
        }
        
        function updateUser()
        {
        	datacontext.updateUser().
            then(function(data) 
            {
                vm.workouts = data;
                //update the graph:
                $scope.chartData = {
                	    series: [vm.workout.name],
                	    data: [{
                  	      x: "No Records for " + vm.workout.name,
                	      y: [0]
                	    }]
                	  }; 
                var i;
                for(i = 0; i < data.length; ++i)
                {
                	//log(data[i].result + " / " + data[i].timestamp);
                	var d = new Date( data[i].timestamp );
                	
                	$scope.chartData.data[i] = { x: d.toDateString(), y: [data[i].result] };
                }
                
            });
        }
        
        function onRemoveWorkout()
        {
            //call backend:
            datacontext.removeWorkout( vm.workout.name ).
            then(function (loginData) 
            {
                log( "workout " + vm.workout.name + " was removed");
            }, function(error) 
            {
            	log_error( "error: " + error );
                vm.hasError = true;
            });
        }

    }
})();