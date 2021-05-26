<%@ taglib uri='/WEB-INF/myTag.tld' prefix='myTag' %>
<html>
<head>
<title>
tagTest
</title>
</head>
<body bgcolor="#ffffff">
<h1>
Tag Test
</h1>


<myTag:firstTag/><br/>
<br/>

<myTag:nestedTag2>
    000
    <myTag:nestedTag2/>
</myTag:nestedTag2>

<br/>

<myTag:nestedTag><myTag:nestedTag/></myTag:nestedTag>

This is tagDependent:
<myTag:tagDependent>
  <%= 3*4 %>
</myTag:tagDependent>
<br/>

This is tagJsp:
<myTag:tagJsp>
  <%= 3*4 %>
</myTag:tagJsp>
<br/>

<br/>
tagJsp and tagDependent is using the <b>same</b> class, <br/>
but tagDependent use tagDependent as bodycontent<br/>
while tagJsp use jsp as bodycontent<br/>
<br/>

This is skipContentTag,it skips the rest of both body and page because all its methods return skip_flag :
<myTag:skipContent>
  <%= 3*4 %>
</myTag:skipContent>
<br/>

The rest:<br/>
  <%= 3*4 %>  
  
  
</body>
</html>
