angular.module('monitoring')
    .service('SettingsService', ['Restangular', function (Restangular) {
        var serviceUrl = "api/settings"
		var service = {
			
        	getServices: function() {
				return Restangular.one(serviceUrl + '/services').get();
			},
			
			getSelectedServices: function() {
				return Restangular.one(serviceUrl + '/services/list').get();
			},
			
			updateServer: function(server) {
				return Restangular.all(serviceUrl + '/services').post(server);
			},
			
			deleteServer: function(id) {
				return Restangular.one(serviceUrl + '/services/' + id).remove();
			},	
			
			getDevices: function() {
				return Restangular.one(serviceUrl + '/devices').get();
			},
						
			updateDevice: function(device) {
				return Restangular.all(serviceUrl + '/devices').post(device);
			},
			
			deleteDevice: function(id) {
				return Restangular.one(serviceUrl + '/devices/' + id).remove();
			}
		}
		
		return service;
		
    }]);