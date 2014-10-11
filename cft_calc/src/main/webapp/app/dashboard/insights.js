(function () {
    'use strict';
    var controllerId = 'insights';
    angular.module('app').controller(controllerId, ['$scope', 'common', 'datacontext', insights]);

    function insights($scope, common, datacontext) {
//        var getLogFn = common.logger.getLogFn;
//        var log = getLogFn(controllerId);

        var vm = this;

        vm.registeredResults = 0;
        vm.registeredUsers = 0;

        //functions:
//        vm.onWorkoutChanged = onWorkoutChanged;

        
        
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

          //init data, so we will not see exceptions/errors:
          $scope.chartData = {
          	    series: [''],
          	    data: [{
              	      x: "",
              	      y: [0]
              	    }]
          	  };        

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
//                  vm.workout.name = vm.workoutNames[0];		
              });
          }
          
          function getTraineeById()
          {
              datacontext.getTraineeById().
              then(function(data) {
                  vm.userInfo = data;
              	//fix dob from timestamp to Date obj:
                  var dobDate = new Date( data.dateOfBirth );
              	vm.userInfo.dateOfBirth = formatDate( dobDate ); 
//              	vm.userInfo.dateOfBirth = '2011-09-29';
              });
          }
          
          function formatDate( date )
          {
          	var month = date.getMonth()+1;
          	if( month < 10 )
          	{
          		month = '0' + month;
          	}
          	var day = date.getUTCDate();
          	if( day < 10 )
          	{
          		day = '0' + day;
          	}
          	var retVal = date.getFullYear() + "-" + month + "-" + day;
      		return retVal;
          }
          
          function onWorkoutChanged()
          {
          	loadWorkouts();
          }
          
          
          
        activate();

        function activate() {
            var promises = [getRegisteredUsers(), getRegisteredResults(), loadStatistics()];
            common.activateController(promises, controllerId)
                .then(function () 
                	{ });
        }

        function getRegisteredUsers() {
            return datacontext.getRegisteredUsers().then(function (data) {
                return vm.registeredUsers = data;
            });
        }

        function getRegisteredResults() {
            return datacontext.getRegisteredResults().then(function (data) {
                return vm.registeredResults = data;
            });
        }
        
        function loadStatistics()
        {
            datacontext.getStatistics().then( function(data) 
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

    }
})();