<%--
  Created by IntelliJ IDEA.
  User: Amysue
  Date: 2016/4/8
  Time: 20:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>删除商品分类</title>
</head>
<body>
<div id="formwrapper">
    <form action="/category.do?method=deleteInput" method="post">
        <fieldset>
            <legend>删除商品分类</legend>
            <div>
                <label for="name1">类名</label>
                <input type="text" name="name" id="name1" value="${param.name}"/>
                <label class="error">${errMap.name}</label>
            </div>
            <div class="button">
                <input type="submit"value="删除"/>
                <input type="reset" value="重置"/>
            </div>
        </fieldset>
    </form>
</div>
</body>
</html>
