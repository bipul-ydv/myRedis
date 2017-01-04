<%--
  Created by IntelliJ IDEA.
  User: anshul
  Date: 20/12/16
  Time: 1:27 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title></title>
</head>

<body>
<tr><th>Country</th><th>Population</th></tr><br/>
<redis:memoize key = "hubbubCount" expire = "60000">
    Hubbub currently has ${ myredis.AnalyticsData.count()} registered users.
</redis:memoize>

<g:each in="${analytics}" var="db">
<tr><td>${db.country} </td>
    <td>${db.population} </td></tr><br/>
</g:each>

</body>
</html>

