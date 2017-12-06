angular.module('monitoring')
    .service('ConfigurationService', ['Restangular', function (Restangular) {
        var serviceUrl = "api/configuration"
    
        var service = {    			
            saveCli: function(item) {
    			return Restangular.all(serviceUrl + '/cli').post(item);
    		},
    		
    		saveSnmp: function(item) {
    			return Restangular.all(serviceUrl + '/snmp').post(item);
    		},
    		
    		getClis: function(item) {
    			return Restangular.one(serviceUrl + '/cli').get();
    		},
    		
    		getSnmps: function(item) {
    			return Restangular.one(serviceUrl + '/snmp').get();
    		}
        }
        
        return service;
	
}]);