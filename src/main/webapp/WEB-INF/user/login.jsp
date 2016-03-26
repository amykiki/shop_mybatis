<%--
  Created by IntelliJ IDEA.
  User: Amysue
  Date: 2016/3/23
  Time: 18:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>用户登录</title>
</head>
<body>
<div id="formwrapper">
    <form action="/user.do?method=login" method="post" id="Login">
        <fieldset>
            <legend>用户登录</legend>
            <div>
                <label for="name">用户名</label>
                <input type="text" name="username" id="name" size="16" maxlength="50" value="${username}"/>
                <label class="error">${errMap.username}</label>
                <br/>
            </div>
            <div>
                <label for="password">密码</label>
                <input type="password" name="password" id="password" size="16" maxlength="30"/>
                <label class="error">${errMap.password}</label>
                <br/>
            </div>
            <div class="button">
                <input id="but1" type="submit" value="提交"/><input id = "but2" type="reset" value="重置"/>
                <div class="clear-float"></div>
            </div>
        </fieldset>
    </form>
</div>

</body>
</html>
