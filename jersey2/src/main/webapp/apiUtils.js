
if (!window.console) {
	window.console = {
		log : function() {
		},
		warn : function() {
		},
		error : function() {
		}
	};
}


(function(window) {
	'use strict';

	if(!window.API_UTIL){
		window.API_UTIL = {};
	} 
	
	var self = window.API_UTIL;
	
	if(self.getContextPath){
		return;
	}
	
	self.getContextPath = function() {
		if( "file:" === window.location.protocol){			
			var docUrl = window.location.href;
			var separator = "/src/main/webapp/";
			var res = docUrl.split(separator, 2);
			var contextPath = res[0] + "/src/main/webapp" ;
			return contextPath;	
		}else{
			var secondSlashLoc = window.location.pathname.indexOf("/",2);  //  http://localhost:8080/Jersey2/index.html
			var contextPath =  window.location.pathname.substring(0, secondSlashLoc);
			return contextPath;	
		}	
		   
	};
	
	self.getAPIUrlBase = function ( settings) {
		// point to another server:
		// to avoid CORS trouble and test it local, please add this on the Chrome command line :  --disable-web-security
		//return "http://localhost:8080/Jersey2/api/public";

		
		if( "file:" === window.location.protocol){
			// the page is opened on the local, so use mock data
			//to avoid CORS trouble, please add this on the Chrome command line :  --disable-web-security
			
			var apiBaseUrl = self.getContextPath() + "/data/mock";
			return apiBaseUrl;	
		}else{			
			var apiBaseUrl = self.getContextPath() + "/api/public";
			return apiBaseUrl;
		}
		
	};
	
	self._getParameterByName =  function (urlSearch, name) {
        name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
        var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"), results = regex.exec(urlSearch);
        return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
    };
	
	self.getParameterByName =  function (name) {
		return self._getParameterByName(location.search, name);
    };	

})(window);

