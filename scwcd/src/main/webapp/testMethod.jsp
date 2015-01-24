<html>
    <head>
        <title>
            testMethod
        </title>
    </head>
    <body bgcolor="#ffffff">
        <h1>
            test submit method
        </h1>
        
        Method attribute depends on the form method ,if no method is defined
        then GET is the default.<br/>
        By default, the browser uses the HTTP GET method in all of the the following events:
        <br/>
        A user clicks on a hyperlink displayed in an HTML page.<br/>
        A user fills out a form in an HTML page and submits it.<br/>
        A user enters a URL in the browser's address field and presses Enter.<br/>
        <br/>
        <br/>
        <form action="testmethod" >
            File Name: <input type="file" name="fileName"/><br/>
            Name: <input type="text" name="name"/><br/>
            Comment: <input type="text" name="comment"/><br/>
            <input type="submit" value="POST"/><br/>
        </form>
    </body>
</html>
