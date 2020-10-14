Spnego authenticate center
=========

+ This is a demo for Spnego authenticate center
+ It used Spnego protocol to authenticate user
+ Then it generates a JWT Token
+ Then it redirect to original request, or home page if no original request

## it is designed to replace KeyCloak which has those defects:

+ KeyCloak is over-designed, most of functions are not needed for us, like:
	+ dozens of social network authenticate
	+ session status monitor
	+ realm, client, roles, groups are unnecessary
	+ try to authorize user, which is incorrect. Every application has different requirements to manage permission. 
+ also its code quality is not good, has many bugs: 
	+ Session doesn't have required client
	+ Failed to parse JWT
	+ Token is not active
	+ Refresh token expired
	+ Could not obtain access token for user	
	+ HttpFailure didn't override getMessage
	+ Hard code status : response.sendError(403); 
+ it is open source project, no vendor is supporting it
+ KeyCloak and related design forced microServices stay inside enterprise network rather than the cloud because:
	+ KeyCloak is using Spnego protocol which has to talk to KDC
	+ client applications using its public key to verify token
	+ client applications has to talk to KeyCloak when they are renewing access token
+ Client applications depends on KeyCloak code:
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
+ It is much simpler than KeyCloak, just 3 java classes, less than 200 code line
+ It is stateless, KeyCloak is stateful
+ Easy to maintain: It has no database, no administrator 
+ It is solid because it is so simple
+ It give you JWT token directly, which KeyCloak didn't
+ Won't sync status after the token is created, so no performance issue
+ Client side also become simpler, 3 filters are merged into one filter, no dependency on this project
+ Modern applications are even more simpler, because JavaScript can directly call it now 
+ No refresh token, Clients can easily:
	+ get a new token from existing token
	+ get a new token from it on the fly
	+ call this auth center to exchange new token
