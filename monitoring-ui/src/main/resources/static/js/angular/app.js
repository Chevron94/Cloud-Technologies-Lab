'use strict';

angular
    .module('monitoring', [
            'ui.router',
            'ngMaterial',
            'restangular',
            'chart.js'
    ]).config(function ($stateProvider, $locationProvider) {

    	
    	$locationProvider.html5Mode(true);
    	
    	$stateProvider
        	.state('app', {
	            abstract: true,
	            url: "",
	            template: '<ui-view/>'
	        })
        	.state('app.monitoring', {
                url: "/monitoring",
                controller: "MonitoringCtrl",
                templateUrl: "templates/monitoring.html"               
        	})
        	.state('app.snmp', {
                url: "/snmp",
                controller: "SnmpCtrl",
                templateUrl: "templates/snmp.html"               
        	})
        	.state('app.cli', {
                url: "/cli",
                controller: "CliCtrl",
                templateUrl: "templates/cli.html"               
        	})
        	.state('app.setting', {
                url: "/setting",
                controller: "SettingCtrl",
                templateUrl: "templates/setting.html"               
        	});
		
    }).run(function ($state) {
    	
    });