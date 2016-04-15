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
    <title>修改购买数量</title>
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
    <form action="/shopcart.do" method="post">
        <fieldset>
            <legend>修改商品数量</legend>
            <div class="last-radio-div radio-div">
                <input type="radio" name="type" id="radio-1"
                       value="increase" checked>
                <label for="radio-1">增加购买数量</label>
            </div>
            <div class="last-radio-div radio-div">
                <c:choose>
                    <c:when test="${param.type == 'reduce'}">
                        <input type="radio" name="type" id="radio-2" value="reduce" checked>
                    </c:when>
                    <c:otherwise>
                        <input type="radio" name="type" id="radio-2" value="reduce">
                    </c:otherwise>
                </c:choose>
                <label for="radio-2">减少购买数量</label>
            </div>
            <div class="last-radio-div radio-div">
                <c:choose>
                    <c:when test="${param.type == 'modify'}">
                        <input type="radio" name="type" id="radio-3" value="modify" checked>
                    </c:when>
                    <c:otherwise>
                        <input type="radio" name="type" id="radio-3" value="modify">
                    </c:otherwise>
                </c:choose>
                <label for="radio-3">设定购买数量</label>
            </div>
            <div>
                <input type="text" name="num" id="num" value="${param.num}"/>
                <label class="error">${errMap.num}</label>
            </div>
            <div class="button">
                <input type="hidden" name="pid" value="${param.pid}">
                <input type="hidden" name="method" value="updateInput">
                <input id="but1" type="submit" value="修改"/>
                <input id="but2" type="reset" value="重置"/>
                <a class="but-a" href="/shopcart.do?method=list">取消</a>
                <div class="clear-float"></div>
            </div>
        </fieldset>
    </form>
</div>

</body>
</html>
