angular.module('monitoring')
    .controller('SnmpCtrl', ['$scope', '$state', 'SettingsService', 'ConfigurationService', function($scope, $state, SettingsService, ConfigurationService){
    	
    	$scope.snmps = [];
    	
    	$scope.devices = {};
    	
    	ConfigurationService.getSnmps().then(function(response){
    		angular.forEach(response.result, function(item) {
    			$scope.snmps.push(item);
    		});
    	});
    	
    	$scope.addClick = function() {
    		$scope.snmps.push({metrics: []});
    	}
    	
    	$scope.addMetric = function(index) {
    		var metricId = 0;
    		angular.forEach($scope.snmps[index].metrics, function(item) {
    			if (metricId < item['metric-id']) {
    				metricId = item['metric-id'];
    			}
    		});
    		$scope.snmps[index].metrics.push({
    			'metric-id': ++metricId,
	    		oid : ""
    		});
    	}
    	
    	$scope.deleteMetric = function(parent, index) {
    		$scope.snmps[parent].metrics.splice(index, 1);
    	}
    	
    	$scope.save = function(index) {
    		ConfigurationService.saveSnmp($scope.snmps[index]);
    	}
    	
    	SettingsService.getDevices().then(function(response){
    		angular.forEach(response.result, function(item) {
    			$scope.devices[item.id] = item.name;
    		});
    	});
    	
    	
    }]);