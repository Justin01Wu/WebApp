
(function() {
	// use anonymous function to avoid JS global variable conflict
	
    'use strict';

	var app = angular.module('myApp', []);
	app.controller('myCtrl', function($scope, $http) {
		
		
		$scope.sendWorngUtilDateOnFormat = function(){
			var requestJson = {
					"utilDateOnFormat": "2016-05-12"
			};
			$scope.sendDateJson(requestJson);
		};

		$scope.sendWorngDateOnCustimized = function(){
			var requestJson = {
					"utilDateOnCustomized": "2016-05-12"
			};
			$scope.sendDateJson(requestJson);
		};
		
		
		$scope.sendCorrectDateJson = function(){
			var requestJson = {
					"dateOnCalendar":"2016-06-05T17:32:30Z",
					"sqlDate" : "2014-11-05",
					"sqlInUtilDate": "2016-04-05",					
					"timestamp": "2016-07-05T14:32:30Z",
					"utilDate" : 1467525543246,
					"utilDateOnCustomized": "2016-07-12T06:33:21+0000",
					"utilDateOnFormat": "Jul-2016-05"
			};			
			$scope.sendDateJson(requestJson);
		}
		
		$scope.sendDateJson = function(requestJson){
			console.log("sendDateJson...");
			
			$scope.requestJson = requestJson;
			
			$scope.requestJsonDisp = JSON.stringify($scope.requestJson, null, 4);
			
			$scope.errorMessage ="";
			$scope.responsJsonDisp ="";
			
			var apiUrl = API_UTIL.getAPIUrlBase() + "/public/dateFormat.json";

			console.log(apiUrl);
			
			$http({
				method : 'POST',
				url : apiUrl,
				data : $scope.requestJson,
				headers : {
					'Content-Type' : 'application/json'
				}

			}).then(function(response) {
				// success
				$scope.responsJsonDisp = JSON.stringify(response.data, null,4);				
			}, function(response) { 
				console.log("post fails");				
				$scope.errorMessage = "post fail with status: " + response.status;
				return false;
			});
			
		}; // end of sendDateJson
		
		
		$scope.getDateJson = function(){
			console.log("getDateJson...");

			$scope.errorMessage ="";
			$scope.responsJsonDisp ="";
			$scope.requestJsonDisp = JSON.stringify($scope.requestJson, null, 4);
			
			var apiUrl = API_UTIL.getAPIUrlBase() + "/public/dateFormat.json";

			console.log(apiUrl);
			
			$http({
				method : 'GET',
				url : apiUrl,
			}).then(function(response) {
				// success
				$scope.responsJsonDisp = JSON.stringify(response.data, null,4);				
			}, function(response) { 
				console.log("get fails");				
				$scope.errorMessage = "get fail with status: " + response.status;
				return false;
			});
			
		}; // end of getDateJson
		
		$scope.getUser2Json = function(){
			console.log("getting objectId.json...");

			$scope.errorMessage ="";
			$scope.responsJsonDisp ="";
			$scope.requestJsonDisp = JSON.stringify($scope.requestJson, null, 4);
			
			var apiUrl = API_UTIL.getAPIUrlBase() + "/public/objectId.json";

			console.log(apiUrl);
			
			$http({
				method : 'GET',
				url : apiUrl,
			}).then(function(response) {
				// success
				$scope.responsJsonDisp = JSON.stringify(response.data, null,4);				
			}, function(response) { 
				console.log("get fails");				
				$scope.errorMessage = "get fail with status: " + response.status;
				return false;
			});
			
		}; // end of getUser2Json
		
		$scope.getCurrentUser = function(){
			console.log("getting getCurrentUser.json...");

			$scope.errorMessage ="";
			$scope.responsJsonDisp ="";
			$scope.requestJsonDisp = JSON.stringify($scope.requestJson, null, 4);
			
			var apiUrl = API_UTIL.getAPIUrlBase() + "/users/user/current.json";
			

			console.log(apiUrl);
			
			$http({
				method : 'GET',
				url : apiUrl,
			}).then(function(response) {
				// success
				$scope.responsJsonDisp = JSON.stringify(response.data, null,4);				
			}, function(response) { 
				console.log("get fails");				
				$scope.errorMessage = "get fail with status: " + response.status;
				return false;
			});
			
		}; // end of getCurrentUser
				
		$scope.testSSO = function(){
			console.log("test SSO: external API...");

			$scope.errorMessage ="";
			$scope.responsJsonDisp ="";
			$scope.requestJsonDisp = JSON.stringify($scope.requestJson, null, 4);
			
			var apiUrl =  "http://localhost:8080/vcaps3/api/v2/users/current.json";

			console.log(apiUrl);
			
			$http({
				method : 'GET',
				url : apiUrl,
			}).then(function(response) {
				// success
				$scope.responsJsonDisp = JSON.stringify(response.data, null,4);				
			}, function(response) { 
				console.log("get fails");				
				$scope.errorMessage = "get fail with status: " + response.status;
				return false;
			});
			
		}; // end of testSSO		

		
		
		
		
		
	});

    

    
}());  // directly run anonymous function 