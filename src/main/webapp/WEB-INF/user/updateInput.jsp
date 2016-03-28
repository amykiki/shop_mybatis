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
<c:choose>
    <c:when test="${cuser.id == lguser.id}">
        <c:set var="title" value="个人信息修改"/>
    </c:when>
    <c:otherwise>
        <c:set var="title" value="${cuser.username}信息修改"/>
    </c:otherwise>
</c:choose>
<html>
<head>
    <title>${title}</title>
    <style type="text/css">
        #username-label {
            text-align: left;
            border: 0.5px solid #666666;
            width: 140px;
            vertical-align: text-bottom;
            height: 17px;
        }
        #formwrapper {
            margin: 5px auto;
        }
        fieldset label.error{
            color: red;
            font-weight: bold;
            float: none;
            width: auto;
            /*text-align: center;*/
        }
        fieldset {
            width: auto;
            width: 480px;
        }
    </style>
    <script type="text/javascript">
        function resetOld() {
            document.getElementById("nickname").value = document.getElementById("oldnickname").value;
            document.getElementById("password").value = document.getElementById("oldpassword").value;
        }
    </script>
</head>
<body>
<div id="formwrapper">
    <form action="/user.do?method=updateUserInput" method="post">
        <fieldset>
            <legend>${cuser.username}信息修改</legend>
            <div>
                <label>用户名</label>
                <label id="username-label">${cuser.username}</label>
                <div class="clear-float"></div>
            </div>
            <div>
                <label for="password">密码</label>
                <input type="password" name="password" id="password" value="${cuser.password}"/>
                <label class="error">${errMap.password}</label>
                <br/>
            </div>
            <div>
                <label for="nickname">用户昵称</label>
                <input type="text" name="nickname" id="nickname" value="${cuser.nickname}"/>
                <label class="error">${errMap.nickname}</label>
                <br/>
            </div>
            <input type="hidden" id="oldnickname" value="${olduser.nickname}"/>
            <input type="hidden" id="oldpassword" value="${olduser.password}"/>
            <input type="hidden" name="userid" value="${cuser.id}"/>
            <div class="button">
                <input id="but1" type="submit" value="修改"/>
                <input id = "but2" type="button" value="重置" onclick="resetOld()"/>
                <div class="clear-float"></div>
            </div>
        </fieldset>
    </form>
</div>
</body>
</html>
