angular.module('monitoring')
    .controller('SettingCtrl', ['$scope', '$state', 'SettingsService', function($scope, $state, SettingsService){
    	
    	$scope.servers = [];
    	
    	$scope.devices = [];
    	
    	$scope.selectedServers = [];
    
    	$scope.addServer = function() {
    		$scope.servers.push({'name': '', 'uri': '', 'change': true});
    	}
    	
    	$scope.updateServer = function(index) {
    		SettingsService.updateServer($scope.servers[index]).then(function(response){
        		var item = response.result;
    			$scope.servers[index].change = false;
        		$scope.servers[index].name = item.name;
        		$scope.servers[index].uri = item.uri;
        		$scope.servers[index].id = item.id;
        		changeSelectService();
    		});
    	}
    	
    	$scope.deleteServer = function(index) {
    		if ($scope.servers[index].id != null) {
    			SettingsService.deleteServer($scope.servers[index].id).then(function(){
            		$scope.servers.splice(index, 1);
            		changeSelectService();
    			});
    		} else {
        		$scope.servers.splice(index, 1);
    		}
    	}
    	
    	$scope.addDevice = function() {
    		$scope.devices.push({'name': '', 'serverId': '', 'change': true});
    	}
    	
    	$scope.updateDevice = function(index) {
    		SettingsService.updateDevice($scope.devices[index]).then(function(response){
        		var item = response.result;
    			$scope.devices[index].change = false;
        		$scope.devices[index].name = item.name;
        		$scope.devices[index].serverId = item.serverId;
        		$scope.devices[index].id = item.id;
    		});
    	}
    	
    	$scope.deleteDevice = function(index) {
    		if ($scope.devices[index].id != null) {
    			SettingsService.deleteDevice($scope.devices[index].id).then(function(){
            		$scope.devices.splice(index, 1);         
    			});
    		} else {
        		$scope.devices.splice(index, 1);
    		}
    	}
    	
    	SettingsService.getDevices().then(function(response){
    		angular.forEach(response.result, function(item) {
    			$scope.devices.push({'name': item.name, 'serverId': item.serverId, 'change': false, id: item.id})
    		});
    	});
    	
    	SettingsService.getServices().then(function(response){
    		angular.forEach(response.result, function(item) {
    			$scope.servers.push({'name': item.name, 'uri': item.uri, 'change': false, id: item.id})
    		});
    		changeSelectService();
    	});
    	
    	function changeSelectService() {

    		SettingsService.getSelectedServices().then(function(response){
        		$scope.selectedServers = [];
        
        		angular.forEach(response.result, function(item) {
        			$scope.selectedServers.push({'name': item.name, id: item.id});
        		});
        	});
    	}
    }]);