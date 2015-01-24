<%@ taglib uri='/WEB-INF/myTag.tld' prefix='myTag' %>
<html>
<head>
<title>
tagTest
</title>
</head>
<body bgcolor="#ffffff">
<h1>
Nested Tag Test
</h1>

<div style="background-color: #eeeeee;">
<myTag:AdjustContent>
    <b>nested html content</b>
</myTag:AdjustContent>
<br/><br/>
</div>

<br/><br/>

<div style="background-color: #eeddee;">
<myTag:AdjustContent>
    <myTag:firstTag/>
</myTag:AdjustContent>
<br/><br/>
</div>

<div style="background-color: #eeeedd;">
<myTag:AdjustContent>
    <myTag:InnerAdjuster/>
</myTag:AdjustContent>
<br/><br/>
</div>

<br/><br/>

<myTag:tagJsp>
    aaabbb
</myTag:tagJsp>

<myTag:nestedTag2>
    ttt
</myTag:nestedTag2>

<br/><br/>

    <myTag:tagJsp>
        cccddd
        <myTag:nestedTag2/>
        eeefff
    </myTag:tagJsp>
    <br/>
<br/>

  
  
</body>
</html>
