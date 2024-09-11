# API tips

## General rules:
1.	To make RESTful developer friendly, URL and response should explain itself well	
	+ bad example: /api/v2/support/producer/{systemId}
	+ bad example: /api/v2/udpf/{programId}/{udpfColumnId}	
	+ bad example of response: /api/v2/support/originOfBusiness.json
1.	Respect Restful naming conventions, don’t use verbs…
1.	Don't use user role on API url…
	+ bad sample: /api/v2/uw/dnf/vformat/accounts/list 
1.	Path parameter should always be Integer and a kind of PK, its prefix noun tell the meaning:
	+ good example: /student/{studentId}/course/{courseId}
	+ bad example: /student/{studentId}/{courseId}
	+ bad example: /student/{courseId}
	+ bad example: /api/v2/programs/{programIds}
	+ bad example: /api/v2/support/producer/{systemId}
	+ bad example: /api/v2/udpf/{programId}/{udpfColumnId}
	+ bad example: /api/v2/support/servers/{serverType}
1.	Don’t add string parameter into URLs path, reason
	+ It will get trouble from Apache kind of servers like tomcat when string has / even you do URL encode. 
	+ if you really want to do it, please double encode and double decode it. 
	+ It also have a chance to fall into another API:  we have a/b/{c} and a/b/d. We have big trouble if string c is “d”	
	+ bad example: /api/v2/air/databases/{databaseIdOrName}
	+ bad example: /api/v2/cmc/deals/{dealId}/databases/{databaseType}/versions/{version}
	+ bad example: /api/v2/modelFiles/{modelFileId}
1.	To make UI easy mock API, query URL should not be a part of single entity url
	+ for example: two API URLs students and students/1234 will have a trouble to save mock data for UI
	+ so better to use students and student/1234
1.	Http get, post and put should have exact same data structure
	+ ie. GET should return exact data structure of PUT and POST input
	+ output JSON can directly used for input JSON	
	+ But some fields should be read only: like createdTime, updatedBy, pk 
	+ good example:  /api/v2/dealModelFiles/{dealModelFileId}
	+ bad example:  /api/v2/deals/{dealId}
1.	Try to make JSON structure flat, one Restful API should only focus on one entity
	+ Creating huge complicated API response usually is a bad idea
	+ If it has too many layers, it usually mean you have some design issue on API 
	+ and will make JAVA code difficult because it is usually on JAVA object reflection, which need more useless Java Class.
	+ and it is diffcult to match the same structure of HTTP GET , POST and PUT
	+ and it is difficult to test 
	+ good example:  /api/v2/deals/{dealId}
	+ bad example:  /api/v2/pricing-comparison/life-cycle/{programNumber}	
1.	Restful API should be stateless, don’t save status into http session, 
	+ better to use token rather than user session
	+ better to return a seq id rather than save status in the session
	+ good example: /api/v2/pricing/validusVers/{validusVerId}/EPCurveResultJob.json
1.	Api should not return Response Java type
	+ it will give a trouble on API doc and confuse developers
	+ bad sample: /api/v2/uw/dnf/vformat/accounts/list 
	+ bad sample: /api/v2/dnf/accounts/{accountId}/ccr/layer-details
1.	Api should return minimize fields in the begining because adding a new field is much easier than removing a field
1.	API should not formatting data
	+ bad sample: 123,456.000
	+ bad sample: createdTime in /api/v2/programs/{programId}/notes/{noteId}, which is server local time format	
	+ good sample: /api/v2/programs/{programId}/pricingResult.json
	+ good sample: /api/v2/dealModelFile/{dealModelFileId}/simulations
1.	API should have info level logs in the entry, specially on PUT POST or DELETE operation
	+ bad sample: DealModelFileCommonApi.deleteModelFile
	+ good sample: DealModelFileApi.getDealModelFileSimulations
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
	+ bad example: /api/v2/deals/{dealId}
	+ good example: /api/v2/portfolio/{portfolioId}/affectedPrograms
1.	If first API directly get client name, then it will be not good:
	+ It is costly if UI don't want to get client Name
	+ It is hard if UI want to get client type rather than client name, we need another API with this small changing
	+ it can't do lazy loading: some columns loading are pretty heavy, users don't need to wait for basic list while heavy loading is processing
	+ lazy loading good canadiate: model file RMS page, the Source Account or Portfolio column need 4 minutes in local dev
		
	
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
1.	Most of APIs are using reflection of Java object to generate JSON structure. 
	+ Sometimes we don't know if it's field name is changed. Specially it is extended or deeply embedded in the parent class.
	+ It will change API request and response structure when a Java class is refactoring
 	+ So we need to test if JSON structure is silently changed or not, which is most dangerous thing on Jackson.
	+ so please try your best to write a unit test to avoid accidental refactoring,
	+ sample: AuthorizeDTOtest or ContractPricingresultDTOTest or UserExtTest in jesery2
1.	We used Jackson ObjectMapper to verify if Java class structure aligned with a JSON structure:
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
	// expectedJson can has less fields than actual Json for backward compatibility
	// this is why RESTful API is more flexible than Web service  
```	
	+ good example: ContractBaseTest
	+ good example: ProgramBase2Test
	+ good example: DnfLocationVO
1.	having integration testing on API level is also a good idea, specially on external APIs
	+ for HTTP GET, it is easy, because you can connect the system to prod Db copy. 
	+ But sometimes it is hard to prepare good data for post and put. 
		+ So we can add a test flag on it: 
		+ if it is true, we skip or roll back real job on server side. 
		+ sample: ContractApi.updateContract
		+ In this way, it can test most of function without change the real data.
		+ It means the test cases can run for ever on the same data.
	+ Use @exteralApi to mark an API when it will be called from outside
1.	Sometimes, we can add extra fields on API for troubleshooting or integration testing, for example: 
	+ You can add sequence Id or updated date to test if a record was updated properly in previous step
	+ sample:
		
## Jackson
Jackson is the main framework for Java Object JSON mapping, So we discuss mainly on it:

+ @JsonIgnore can't be overwrote, sub class has to use another method to get it back:	        
```java
    @JsonProperty("categoryId")
    public Integer getCategoryId2() {
        return super.getCategoryId();
    }
```
+ Enum by default uses literal value, it is good enough for most of case:
	+ `Public enum ContractStatusEnum{ Quote(10); ...}` will return 'Quote' in API
	+ So you don't need to do anything for it.

+ Sometimes, it is hard to reflect on Java object, then we can use flexible JSON:
	+ UI using JSON.stringfy convert JSON into a string, 
		+ then save a whole JSON string into a field in a table, most of DB can do it, 
		+ like MS SQL have nvarchar(max), it can save 2G data into it.
		+ sample: globalJsonSetting table
	+ API can direct return a JSON String or input a JSON string on a field:
	+ raw JSON string: return `"{\"id\":\""+id+"\"}"`;
	+ return map: `map.put("id", 1223); map.put("name", "Justin");` 
	+ map can be nested: `map.put("myData", anotherMap);`
