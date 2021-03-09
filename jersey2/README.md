# API tips

## General rules:
+ To make RESTful developer friendly, url should explain itself well
+ Path parameter should be always be Integer and a kind of PK,  its prefix noun tell the meaning:
	+ good example: /students/{studentId}/courses/{courseId}
	+ bad example: /students/{studentId}/{courseId}
	+ bad example: /students/{courseId}
	
## Foreign key
+ To make RESTful simple, we don’t extend reference object in GETmethod, we only return foreign key itself.
+ UI side use another API to get details if they need.
+ If the result is a list, we can also call the second API for foreign key list.
	+ For example:
	+ The program has clientId, Then API to get program or programList only return clientId itself
	+ If UI side wants to display client name, then it needs to call  clients/51,1023 to get those client information	
		
## Jackson
Jackson is the main framework for Java Object Json mapping, So we discuss mainly on it:

+ Please use symmetric rule when a java Object is used on input abd output API:
	+ output json can directly used for input json
+ 


