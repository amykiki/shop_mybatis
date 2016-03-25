<%--
  Created by IntelliJ IDEA.
  User: Amysue
  Date: 2016/3/21
  Time: 20:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title><decorator:title/></title>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/main.css">
    <decorator:head/>
</head>
<body>
<h3>Amy's 商城系统</h3>
<div id="nav">
    <div id="headright">
        <c:choose>
            <c:when test="${not empty lguser.username}">
                <span>欢迎[
                <a href="<%=request.getContextPath()%>/user.do?method=">${lguser.nickname}</a>
                ]登录商城
                <a href="<%=request.getContextPath()%>/user.do?method=logout">注销</a>
            </c:when>
            <c:otherwise>
                <a href="<%=request.getContextPath()%>/user.do?method=loginInput">用户登录</a>
                <a href="<%=request.getContextPath()%>/user.do?method=addInput">用户注册</a>
            </c:otherwise>
        </c:choose>
    </div>
    <hr/>
</div>
<decorator:body/>
<div align="center">
    CopyRight 2016-2018
</div>
</body>
</html>
