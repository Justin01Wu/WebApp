Spnego authenticate center
=========

+ This is a demo for Spnego authenticate center
+ It used Spnego protocol to authenticate user
+ Then it generates a JWT Token
+ Then it redirect to original request if it exists, or home page if no original request

## it is designed to replace KeyCloak which has those defects:

+ KeyCloak is over-designed, most of functions are not needed for us, like:
	+ dozens of social network authenticate
	+ session status monitor
	+ realm, client, roles, groups are unnecessary
+ also its code quality is not so good, like HttpFailure didn't override getMessage 
+ it is open source project, no vendor is supporting it
+ Also services are using its public key to verify token, which forces services stay inside enterprise network, which should go to cloud
