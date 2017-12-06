angular.module('monitoring')
    .controller('MonitoringCtrl', ['$scope', '$state', 'ConfigurationService', function($scope, $state, ConfigurationService){
    	
    	$scope.devices = {};

    	$scope.datasetOverride = [{ yAxisID: 'y-axis-1' }];
	   	
    	$scope.options = {
	   	    scales: {
	   	      yAxes: [
	   	        {
	   	          id: 'y-axis-1',
	   	          type: 'linear',
	   	          display: true,
	   	          position: 'left'
	   	        }
	   	      ],
	   	      xAxes: [{
	                 display: false
	             }]
	   	    }
	   	};
   
	   	ConfigurationService.getSnmps().then(function(response){
    		angular.forEach(response.result, function(item) {
    			var objectId = item['object-id'];
    			if (!$scope.devices.hasOwnProperty(objectId)) {
    				$scope.devices[objectId] = { name: 'todo', metrics: {}};
    			}
    			angular.forEach(item.metrics, function(item) {
    				$scope.devices[objectId].metrics[item['metric-id']] = {};
    				$scope.devices[objectId].metrics[item['metric-id']].oid = item.oid;
    				$scope.devices[objectId].metrics[item['metric-id']].values = [];
    				$scope.devices[objectId].metrics[item['metric-id']].labels = [];
    			}); 
    		});
    	});
	   	
	   	var eventSource = new EventSource("/metrics");

	   	eventSource.onmessage = function(e) {
	   		$scope.$apply(function(){
	   			
	   			var data = JSON.parse(e.data);
		   		var values = [];
		   		var labels = [];
		   		
		   		angular.forEach(data['metric-values'], function(item) {
		   			values.push(item.value);
		   			labels.push(new Date(item.date));
				}); 
		   		values = $scope.devices[data['object-id']].metrics[data['metric-id']].values.concat(values);
		   		labels = $scope.devices[data['object-id']].metrics[data['metric-id']].labels.concat(labels);
		   		
		   		if (values.length > 20) {
		   			values = values.slice(values.length - 21, 20);
		   			labels = labels.slice(labels.length - 21, 20);
		   		}
		   		$scope.devices[data['object-id']].metrics[data['metric-id']].values = values;
		   		$scope.devices[data['object-id']].metrics[data['metric-id']].labels = labels;
		   		
	   		});
	   	};
    }]);