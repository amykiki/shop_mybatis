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
    <title>添加商品分类</title>
</head>
<body>
<div id="formwrapper">
    <form action="/category.do?method=addInput" method="post">
        <fieldset>
            <legend>添加商品分类</legend>
            <div class="last-radio-div radio-div">
                <input type="radio" name="type" id="radio2" value="friend" checked>
                <label for="radio2">添加友分类</label>
                <input type="radio" name="type" id="radio1" value="child">
                <label for="radio1">添加子分类</label>
                <input type="radio" name="type" id="radio3" value="parent">
                <label for="radio3">添加父分类</label>
            </div>
            <div>
                <label for="name1">新类名</label>
                <input type="text" name="name" id="name1" value="${param.name}"/>
                <label class="error">${errMap.name}</label>
            </div>
            <div>
                <label for="name2">目标类名</label>
                <input type="text" name="oldname" id="name2" value="${param.oldname}"/>
                <label class="error">${errMap.oldname}</label>
            </div>
            <div>
                <label for="name3">目标类名2</label>
                <input type="text" name="oldname2" id="name3" value="${param.oldname2}"/>
                <label class="error">${errMap.oldname2}</label>
            </div>
            <div class="button">
                <input type="submit" name="add" value="添加"/>
                <input type="reset" value="重置"/>
            </div>
        </fieldset>
    </form>

</div>
</body>
</html>
