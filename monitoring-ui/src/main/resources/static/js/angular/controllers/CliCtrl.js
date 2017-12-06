angular.module('monitoring')
    .controller('CliCtrl', ['$scope', '$state', 'SettingsService', 'ConfigurationService', function($scope, $state, SettingsService, ConfigurationService){
    	
    	
    	$scope.clis = [];
    	
    	$scope.devices = {};
        	
    	ConfigurationService.getClis().then(function(response){
    		angular.forEach(response.result, function(item) {
    			$scope.clis.push(item);
    		});
    	});
    	
    	$scope.addClick = function() {
    		$scope.clis.push({metrics: []});
    	}
    	
    	$scope.addMetric = function(index) {
    		var metricId = 0;
    		angular.forEach($scope.clis[index].metrics, function(item) {
    			if (metricId < item['metric-id']) {
    				metricId = item['metric-id'];
    			}
    		});
    		$scope.clis[index].metrics.push({
    			'metric-id': ++metricId,
	    		command : "",
	    		regexp : ""
    		});
    	}
    	
    	$scope.deleteMetric = function(parent, index) {
    		$scope.clis[parent].metrics.splice(index, 1);
    	}
    	
    	$scope.save = function(index) {
    		ConfigurationService.saveCli($scope.clis[index]);
    	}
    	
    	SettingsService.getDevices().then(function(response){
    		angular.forEach(response.result, function(item) {
    			$scope.devices[item.id] = item.name;
    		});
    	});
    }]);