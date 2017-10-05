<html>
<head>
  <title>${title}</title>
</head>
<body>
  <h1>${title}</h1>

  <ul>
    <#list allApis as one>
      <li>${one_index + 1} 
      <b>HtpMethod</b> ${one.httpMethod} 
      <b>URL: </b> ${one.url}   
      <b>class: </b>${one.className}
      <b>method: </b>${one.methodName}</li>
    </#list>
  </ul>

</body>
</html>