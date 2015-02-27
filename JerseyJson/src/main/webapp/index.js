
if (!window.console) {
	window.console = {
		log : function() {
		}
	};
}

function getAPIUrlBase(){
	return "api";
	
	// to avoid CORS trouble and test it local, please add this on the Chrome command line :  --disable-web-security
	//return "http://localhost:8080/api";
}


function callMenuApi(){
	var menuUrl = getAPIUrlBase() + '/user/current/menu';
	//var menuUrl = 'WEB-INF/menus_uw.json';  // for local testing
	//var menuUrl = 'WEB-INF/menus_analysis2.json';  // for local testing
	//var menuUrl = 'WEB-INF/menus_admin.json';  // for local testing
	callApi(menuUrl);	
}
function callxmlAddress(){
	var url = getAPIUrlBase() + '/user/xml/currentAddress';
	callXmlApi(url);	
}


function callUserJsonString(){
	var url = getAPIUrlBase() + '/user/json';
	callApi(url);	
}

function callHideFieldsApi(){
	var url = getAPIUrlBase() + '/user/currentAddress';
	callApi(url);	
}

function callInvalidChar(){
	var url = getAPIUrlBase() + '/user/invalidChar';
	callApi(url);	
}

function callUserApi(){
	var url = getAPIUrlBase() + '/user/current';
	callApi(url);	
}

function callUserNoPasswordApi(){
	var url = getAPIUrlBase() + '/user/currentNoPassword';
	callApi(url);	
}


function callXmlApi(url){

	console.log("going to call API: " + url);
	
	jQuery("#urlStr").text(url);
	jQuery.ajax({
		url : url,
		dataType : 'xml',
		success : function(xmlDoc) {
			console.log("got xml...");
			
		    var xmlString;
		    //IE
		    if (window.ActiveXObject){
		        xmlString = xmlDoc.xml;
		    }
		    // code for Mozilla, Firefox, Opera, etc.
		    else{
		        xmlString = (new XMLSerializer()).serializeToString(xmlDoc);
		    }
			jQuery("#responseJson").text(xmlString);			
			
		},
		error: function(jqXHR,error, errorThrown) {  
			console.error("loading ajax error with status: " + jqXHR.status);
            alert("loading xml failed, please refresh page or contact admin");
       }
	});
}


function callApi(url){

	console.log("going to call API: " + url);
	
	jQuery("#urlStr").text(url);
	jQuery.ajax({
		url : url,
		dataType : 'json',
		success : function(json) {
			console.log("got json...");
			jQuery("#responseJson").text(JSON.stringify(json, null, 4));			
			
		},
		error: function(jqXHR,error, errorThrown) {  
			console.error("loading ajax error with status: " + jqXHR.status);
            if(jqXHR.status&&jqXHR.status==400){
                 alert(jqXHR.responseText); 
            }else{
                alert("loading json failed, please refresh page or contact admin");
            }
       }
	});
}
