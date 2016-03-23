<%--
  Created by IntelliJ IDEA.
  User: Amysue
  Date: 2016/3/21
  Time: 19:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>test</title>
</head>
<body>
<h1>TEST</h1>
<form action="/logTest" method="post">
    <input type="text" name="query" value="${param.query}">
    <input type="submit" value="提交">
</form>
${method}
${param.method}
${param.amy}
${param.query}
${log}
</body>
</html>
