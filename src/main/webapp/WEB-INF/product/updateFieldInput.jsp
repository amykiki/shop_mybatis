<%--
  Created by IntelliJ IDEA.
  User: Amysue
  Date: 2016/4/11
  Time: 17:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <c:set var="method" value="${method}"/>
    <c:set var="field" value="${field}"/>
    <c:set var="title" value=""/>
    <c:set var="typestr" value=""/>
    <c:if test="${field == 'name'}">
        <c:set var="title" value="名"/>
    </c:if>
    <c:if test="${field == 'stock'}">
        <c:set var="title" value="库存"/>
    </c:if>
    <c:if test="${param.type == 'add'}">
        <c:set var="typestr" value="增加"/>
    </c:if>
    <c:if test="${param.type == 'reduce'}">
        <c:set var="typestr" value="减少"/>
    </c:if>
    <title>修改商品${title}</title>
    <style>
        .but-a {
            background: #ffffff;
            border: 1px solid #A4CDF2;
            color: #1E7ACE;
            width: 60px;
            margin: auto 6px;
            float: left;
            height: 20px;
            line-height: 20px;
        }
        #but2 {
            float: left;
        }
        .button {
            width: 240px;
        }
    </style>
</head>
<body>
<div id="formwrapper">
    <form action="/product.do" method="post">
        <fieldset>
            <legend>修改商品${title}</legend>
            <div>
                <label>${typestr}商品${title}</label>
                <input type="text" name="${field}" value="${param.get(field)}">
                <label class="error">${errMap.get(field)}</label>
            </div>
            <div class="button">
                <input type="hidden" name="pid" value="${param.pid}">
                <input type="hidden" name="method" value="${method}">
                <input type="hidden" name="toPage" value="${param.toPage}">
                <input type="hidden" name="type" value="${param.type}">
                <input id="but1" type="submit" value="修改"/>
                <input id="but2" type="reset" value="重置"/>
                <a class="but-a" href="/product.do?method=list&toPage=${param.toPage}">取消</a>
                <div class="clear-float"></div>
            </div>
        </fieldset>
    </form>
</div>

</body>
</html>
