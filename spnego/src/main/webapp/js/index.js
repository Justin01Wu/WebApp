(function() {
	// use anonymous function to avoid JS global variable conflict

	'use strict';

	var request = new XMLHttpRequest()

	// Open a new connection, using the GET request on the URL endpoint
	request.open('GET', '/spnego/JWTTokenServlet', true)

	request.onload = function() {
		 
		var data = JSON.parse(this.response);
		document.getElementById("userName").innerHTML  = data.userName;
		var date = new Date(data.JWTCreated);
		document.getElementById("createdTime").innerHTML = date.toString();
		date = new Date(data.JWTExpired);
		document.getElementById("expiredTime").innerHTML = date;
		document.getElementById("JWTToken").value = data.JWTToken;
	}

	// Send request
	request.send()

}()); // directly run anonymous function
