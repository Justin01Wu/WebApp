<%@ taglib uri='/WEB-INF/myTag.tld' prefix='myTag' %>
<html>
    <body bgcolor="#ffffff">
    No matter if doEndTag return skip_page or not,
    you can't put anything after this tag if it is forward:
    you can put something after this tag if it is redirect:
    <myTag:forwardTag/><br/>
    <br/>
    </body> 
</html>

