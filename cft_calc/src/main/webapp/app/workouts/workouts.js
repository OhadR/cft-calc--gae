(function () {
    'use strict';
    var controllerId = 'workouts';
    var mudule = angular.module('app');
    mudule.controller(controllerId, ['$scope', 'common', 'datacontext', workouts]);

    
    function workouts($scope, common, datacontext) {
        var getLogFn = common.logger.getLogFn;
        var log = getLogFn(controllerId);

        var vm = this;
        vm.title = 'My workouts';
        vm.workout = { };
        vm.workouts = [];		//objects to the UI. each contains name, result and timestamp
        vm.workoutNames = [];
        vm.onWorkoutChanged = onWorkoutChanged;

        $scope.config = {
  			  title: 'revital',
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
  			  totalHeight: 1001,
  			  width: 100,
  			  lineLegend: 'lineEnd' // can be also 'traditional'
  			};

        $scope.chartType = "line";

        $scope.chartData = {
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
        	  };        


        activate();

        function activate() 
        {
            common.activateController( [loadWorkoutNames()], controllerId );
        }

        function loadWorkouts() {
            datacontext.getWorkoutHistoryForTrainee( vm.workout.name ).then(function(data) {
                vm.workouts = data;
            });
        }

        function loadWorkoutNames() {
            datacontext.getAllWorkoutsNames().
            then(function(data) {
                vm.workoutNames = data;
                //set a value, so we will not see an empty line:
                vm.workout.name = vm.workoutNames[0];		
            });
        }
        
        function onWorkoutChanged()
        {
        	loadWorkouts();
        }

    }
})();