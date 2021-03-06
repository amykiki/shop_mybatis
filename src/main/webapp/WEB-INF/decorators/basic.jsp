<%@ page import="shop.enums.Role" %><%--
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
<body onload="<decorator:getProperty property='body.onload'/>">
<c:set var="ADMIN" value="<%=Role.ADMIN%>"/>
<c:set var="NORMAL" value="<%=Role.NORMAL%>"/>

<h3>Amy's 商城系统</h3>
<div id="nav">
    <div id="headright">
        <c:choose>
            <c:when test="${not empty lguser.username}">
                <span>欢迎[
                <a href="<%=request.getContextPath()%>/user.do?method=show&userid=${lguser.id}">${lguser.nickname}</a>
                ]登录商城
                <a href="<%=request.getContextPath()%>/user.do?method=logout">注销</a>
            </c:when>
            <c:otherwise>
                <a href="<%=request.getContextPath()%>/user.do?method=loginInput">用户登录</a>
                <a href="<%=request.getContextPath()%>/user.do?method=add">用户注册</a>
            </c:otherwise>
        </c:choose>
    </div>
    <ul>
        <c:choose>
            <c:when test="${not empty lguser.username}">
                    <c:choose>
                        <c:when test="${lguser.role == ADMIN}">
                            <li><a href="<%=request.getContextPath()%>/user.do?method=list">用户列表</a></li>
                            <li><a href="<%=request.getContextPath()%>/product.do?method=add">添加商品</a></li>
                            <li><a href="<%=request.getContextPath()%>/order.do?method=list&type=all">管理所有订单</a></li>
                        </c:when>
                        <c:otherwise>
                            <li><a href="<%=request.getContextPath()%>/user.do?method=updateUser&userid=${lguser.id}">用户编辑</a></li>
                        </c:otherwise>
                    </c:choose>
                <li><a href="<%=request.getContextPath()%>/order.do?method=list">订单列表</a></li>
            </c:when>
        </c:choose>
        <li><a href="<%=request.getContextPath()%>/category.do?method=list">商品分类</a></li>
        <li><a href="<%=request.getContextPath()%>/product.do?method=list">商品列表</a></li>
        <li><a href="<%=request.getContextPath()%>/shopcart.do?method=list">购物车</a></li>
    </ul>
    <hr/>
</div>
<decorator:body/>
<div align="center">
    CopyRight 2016-2018
</div>
</body>
</html>
