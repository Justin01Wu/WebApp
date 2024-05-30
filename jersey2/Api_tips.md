# API tips

## General rules:
1.	To make RESTful developer friendly, url should explain itself well
1.	Respect Restful naming conventions, don’t use verbs…
1.	Path parameter should always be Integer and a kind of PK, its prefix noun tell the meaning:
	+ good example: /students/{studentId}/courses/{courseId}
	+ bad example: /students/{studentId}/{courseId}
	+ bad example: /students/{courseId}
1.	Don’t add string parameter into URLs path, reason
	+ it will get trouble from apache kind of servers like tomcat when string has / 
	+ even you do url encode, 
	+ if you really want to do it, please double encode and double decode it. 
	+ It also have chance to fall into another API:  we have a/b/(c) and a/b/d. We have big trouble if string c is “d”	
1.	To make UI easy mock api, query url should not be a part of single entity url
	+ for example: two API urls students and students/1234 will have a trouble to save mock data for UI
	+ so better to use students and student/1234
1.	Http get, post and put should have exact same data structure
	+ Ie. GET should return exact data structure of PUT and POST input
	+ output json can directly used for input json	
	+ But some fields should be read only: like createdTime, updatedBy, pk 
1.	Try to make JSON structure flat, one Restful API should only focus on one entity
	+ Creating huge complicated API response usually is a bad idea
	+ If it has too many layers, it usually mean you have some design issue on API 
	+ and will make JAVA code difficult because it is usually on JAVA object reflection, which need more useless Java Class.
1.	Restful API should be stateless, don’t save status into http session, better to use token, the token has user info	
1.	Api should not return Response Java type
	+ it will give a trouble on api doc and confuse developers
	+ bad sample: DnfVFormatImportApi or commcatRatingApi
1.	API should not formatting data, bad sample: 123,456.000	
1.	When a system has more than 100 apis, we worried about URL conflicts.
	+ So we suggest to use Java package to match the URL :  for example: URL is a/b/c/d then the package will be a.b.c
	+ And the class name is d

	
## Foreign key
1.	To make RESTful simple and flexibility, we don't add reference object in API, we only return foreign key itself.
1.	UI side use another API to get details if they need.
1.	If the result is a list, we can also call the second API for foreign key list.
	+ For example:
	+ The program has clientId, Then API to get program or programList only return clientId itself
	+ If UI side wants to display client name, then it needs to call  clients?ids=51,1023 to get those client information
1.	If first API directly get client name, then it will be not good:
	+ It is costly if UI don't want to get client Name
	+ It is hard if UI want to get client type rather than client name, we need another API with this small changing
	
	
## Field convert
To make RESTful developer friendly, data should explain itself well, So we need to do some convert on some kind of data type:

### Date field
Using ISO format on date type data rather than long timestamp, it is more developer friendly, 
Usually we use ISO 8601 format to display a date type field, like 2017-02-18T02:14:35.000Z

By default we use GMT zone. But client can send time on other time zone:  2017-02-16T12:00:00.000+08:00

So we created those serializer and deserializer:
+ ISO8601.ISO8601Serializer
+ ISO8601.ISO8601Deserializer
+ ISO8601ShortDateConverter.ISO8601ShortDateSerializer
+ ISO8601ShortDateConverter.ISO8601ShortDateDeserializer

 
This is how we use it:
```java
	@JsonSerialize(using = ISO8601ShortDateSerializer.class)
	public Timestamp getReferenceDate() {	
		return referenceDate;
	}
```
 
```java
	@JsonDeserialize(using = ISO8601ShortDateDeserializer.class)
	public void setReferenceDate(Timestamp referenceDate) {
		this.referenceDate = referenceDate;
	}	
```	

## Testing and Verify API Interface
1.	Most of APIs are using reflection of Java object to generate json structure. 
	+ Sometimes we don't know if it's field name is changed. Specially it is extended or deeply embedded in the parent class.
	+ It will change api request and response structure when a Java class is refactoring
 	+ So we need to test if json structure is silently changed or not, which is most dangerous thing on Jackson.
	+ so please try your best to write a unit test to avoid accidental refactoring,
	+ sample: AuthorizeDTOtest or contractPricingresultDTOTest or UserextTest in jesery2
1.	We use dJackson ObjectMapper to verify if Java class structure is aligned with a Json structure:
```java
    ObjectMapper mapper = new ObjectMapper();    	
    String origJsonDataFile = UserExtTest.class.getSimpleName() + ".json";
    String templateData = ApiTestUtil.readJSONFile(origJsonDataFile);
    UserExt dto = mapper.readValue(templateData, UserExt.class);        
    assertNull( dto.getId());
    String targetJson = mapper.writeValueAsString(dto);        
    JSONObject json = ApiTestUtil.convertJSONStr2Obj(targetJson);
    JSONObject expectedJson = ApiTestUtil.convertJSONStr2Obj(templateData);
    ApiTestUtil.verifyJson((Map<String, Object>)json, (Map<String, Object>)expectedJson);
```	
	+expectedJson can has less fields than actual Json for backward compatibility, this is why RESTful API is more flexible than Web service  
1.	having integration testing on API level is also a good idea, specially on external APIs
	+ for HTTP GET, it is easy, because you can connect the system to prod Db copy. 
	+ But sometimes it is hard to prepare good data for post and put. 
		+ So we can add a test flag on it: 
		+ if it is true, we skip or roll back real job on server side. 
		+ sample: ContractApi.updateContract
		+ In this way, it can test most of function without change the real data.
		+ It means the test cases can run for ever on the same data.
	+ Use @exteralApi to mark an api when it will be called from outside
1.	Sometimes, we can add extra fields on api for troubleshooting or integration testing, for example: 
	+ You can add sequence Id or updated date to test if a record was updated properly in previous step
	+ sample:
		
## Jackson
Jackson is the main framework for Java Object Json mapping, So we discuss mainly on it:

+ @JsonIgnore can't be overwrote, sub class has to use another method to get it back:	        
```java
    @JsonProperty("categoryId")
    public Integer getCategoryId2() {
        return super.getCategoryId();
    }
```
+ Enum by default uses literal value, it is good enough for most of case:
	+ `Public enum ContractStatusEnum{ Quote(10); ...}` will return 'Quoted' in API
	+ So you don't need to do anything for it.

+ Sometimes, it is hard to reflect on Java object, then we can use flexible json:
	+ UI using JSON.stringfy convert json into a string, then save a whole json string into a field in a table, most of DB can do it, like MS SQL have nvarchar(max), it can save 2G data into it.
	+ API can direct return a json String or input a json string on a field:
	+ raw json string: return `"{\"id\":\""+id+"\"}"`;
	+ return map: `map.put("id", 1223); map.put("name", "Justin");` 
	+ map can be nested: `map.put("myData", anotherMap);`
