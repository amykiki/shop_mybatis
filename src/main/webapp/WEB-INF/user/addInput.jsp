<%--
  Created by IntelliJ IDEA.
  User: Amysue
  Date: 2016/3/28
  Time: 17:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>注册用户</title>
</head>
<body>
<div id="formwrapper">
    <form action="/user.do?method=addInput" method="post">
        <fieldset>
            <legend>注册用户</legend>
            <div>
                <label for="name">用户名</label>
                <input type="text" name="username" id="name"
                       value="${cuser.username}"/>
                <label class="error">${errMap.username}</label>
                <br/>
            </div>
            <div>
                <label for="password">密码</label>
                <input type="password" name="password" id="password"
                       value="${cuser.password}"/>
                <label class="error">${errMap.password}</label>
                <br/>
            </div>
            <div>
                <label for="nickname">用户昵称</label>
                <input type="text" name="nickname" id="nickname"
                       value="${cuser.nickname}"/>
                <label class="error">${errMap.nickname}</label>
                <br/>
            </div>
            <input type="hidden" name="userid" value="${cuser.id}">
            <div class="button">
                <input id="but1" type="submit" value="添加"/>
                <input id="but2" type="reset" value="重置"/>
                <div class="clear-float"></div>
            </div>
        </fieldset>
    </form>
</div>
</body>
</html>
