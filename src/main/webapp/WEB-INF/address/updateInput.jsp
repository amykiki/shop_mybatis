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
    <title>修改${lguser.nickname}地址</title>
    <script type="text/javascript">
        function resetOld() {
            document.getElementById("recipient").value = document.getElementById("oldrecipient").value;
            document.getElementById("phone").value = document.getElementById("oldphone").value;
            document.getElementById("addressInfo").value = document.getElementById("oldaddressInfo").value;
            document.getElementById("zip").value = document.getElementById("oldzip").value;
        }
    </script>
</head>
<body>
${errMsg}
<div id="formwrapper">
    <form action="/address.do?method=updateInput" method="post">
        <fieldset>
            <legend>修改用户[${lguser.nickname}]地址</legend>
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
                <label class="error">${errMap.addressInfo}</label>
                <br/>
            </div>
            <div>
                <label for="zip">邮政编码</label>
                <input type="text" name="zip" id="zip" value="${caddr.zip}"/>
                <label class="error">${errMap.zip}</label>
                <br/>
            </div>
            <input type="hidden" name="addrid" value="${caddr.id}">
            <input type="hidden" name="userid" value="${lguser.id}">
            <input type="hidden" id="oldrecipient" value="${oldaddr.recipient}"/>
            <input type="hidden" id="oldphone" value="${oldaddr.phone}"/>
            <input type="hidden" id="oldaddressInfo" value="${oldaddr.addressInfo}"/>
            <input type="hidden" id="oldzip" value="${oldaddr.zip}"/>
            <div class="button">
                <input id="but1" type="submit" value="修改"/>
                <input id="but2" type="button" value="重置" onclick="resetOld()"/>
                <div class="clear-float"></div>
            </div>
        </fieldset>
    </form>
</div>
</body>
</html>
