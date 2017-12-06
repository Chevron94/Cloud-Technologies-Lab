angular.module('monitoring')
    .controller('AppCtrl', ['$scope', '$state', function($scope, $state){
    	
    	$scope.currentNavItem = 'monitoring';
    	$state.go('app.monitoring');
    	
    	$scope.goto = function(page) {
    		$state.go(page);
    	}
    	
    }]);