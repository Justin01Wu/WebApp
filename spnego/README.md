Spnego authenticate center
=========

+ This is a demo for Spnego authenticate center
+ It used Spnego protocol to authenticate user
+ Then it generates a JWT Token
+ Then it redirect to original request, or home page if no original request

## it is designed to replace KeyCloak which has those defects:

+ KeyCloak is over-delivery, most of functions are not needed for us:
	+ dozens of login methods and workflow for all kinds of systems
	+ dozens of social network authenticate
	+ session status monitor	
+ KeyCloak bring troubles more than its benefits for applications because of over-designed:	
	+ try to authorize user, which is incorrect: Every application has different requirements to manage permissions.
	+ realm, client, roles, groups and resources are unnecessary for authentication
	+ Every client has different setting, which is hard to do troubleshooting 
+ also it has many bugs because of the complication: 
	+ Session doesn't have required client
	+ Failed to parse JWT
	+ Token is not active
	+ Refresh token expired
	+ Could not obtain access token for user	
	+ HttpFailure didn't override getMessage
	+ Hard code status : response.sendError(403); 	
+ It is open source project, no vendor is supporting it
+ KeyCloak and related design forced microServices stay inside enterprise network rather than the cloud because:
	+ KeyCloak is using Spnego protocol which has to talk to KDC
	+ client applications directly access its URL to get public key
	+ client applications has to talk to KeyCloak when they are renewing access token
+ Client applications is fully coupled with KeyCloak, they depends on KeyCloak code:
	+ JavaScript: `import Keycloak from 'keycloak-js'`
	+ Java: 
	```
	import org.keycloak.AuthorizationContext;
	import org.keycloak.KeycloakPrincipal;
	import org.keycloak.representations.IDToken;
	import org.keycloak.adapters.KeycloakDeploymentBuilder;
	...
	```
 

## new project benefits
+ It is much simpler than KeyCloak, just 3 java classes, about 200 lines code
+ It is more powerful than KekCloak on what we need
	+ it covered all functions we really need 
	+ it gives you JWT token directly, which KeyCloak didn't
+ It is stateless, KeyCloak is stateful
+ Every environment has the same setting, easy to do troubleshooting
+ Easy to maintain: It has no database, no administrator 
+ It has much simpler setting than KeyCloak, KeyCloak has different setting for each server because of the two ways dependency
+ It is solid because it is so simple
+ Won't sync status after the token is created, so no performance issue
+ Client side also become simpler:
	+ 3 filters are merged into one filter
	+ no code dependency on this project
	+ easy to logout, which is hard in KeyCloak solution
+ Modern applications are even more simpler, because JavaScript can directly get token now 
+ No refresh token which is the big trouble maker in KeyCloak solution, Clients now can easily:
	+ get a new token from existing token
	+ get a new token from it on the fly
	+ call this auth center to exchange new token with old one
