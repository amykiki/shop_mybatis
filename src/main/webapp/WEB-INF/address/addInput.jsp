<%--
  Created by IntelliJ IDEA.
  User: Amysue
  Date: 2016/3/29
  Time: 21:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>添加${lguser.nickname}地址</title>
</head>
<body>
<div id="formwrapper">
    <form action="/address.do?method=addInput" method="post">
        <fieldset>
            <legend>添加用户[${lguser.nickname}]地址</legend>
            <div>
                <label for="recipient">收件人</label>
                <input type="text" name="recipient" id="recipient" value="${caddr.recipient}"/>
                <label class="error">${errMap.recipient}</label>
                <br/>
            </div>
            <div>
                <label for="phone">联系电话</label>
                <input type="text" name="phone" id="phone"
                       value="${caddr.phone}"/>
                <label class="error">${errMap.phone}</label>
                <br/>
            </div>
            <div>
                <label for="addressInfo">收货地址</label>
                <input type="text" name="addressInfo" id="addressInfo"
                       value="${caddr.addressInfo}"/>
                <label class="error">${errMap.password}</label>
                <br/>
            </div>
            <div>
                <label for="zip">邮政编码</label>
                <input type="text" name="zip" id="zip" value="${caddr.zip}"/>
                <br/>
            </div>
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
