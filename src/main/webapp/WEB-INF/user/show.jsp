<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Amysue
  Date: 2016/3/29
  Time: 14:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${cuser.nickname}用户信息</title>
    <style>
        .info {
            margin: 10px auto;
        }

        .info h4 {
            color: #1E7ACE;
        }

        .info .addr-info {
            text-align: left;
        }

        .button {
            text-align: center;
            width: auto;
        }

        .button input {
            width: auto;
        }
    </style>
    <script type="text/javascript">
        function checkAll(ele) {
            var cbs = document.getElementsByTagName('input');
            for (var i = 0; i < cbs.length; i++) {
                if (cbs[i].type == 'checkbox') {
                    cbs[i].checked = true;
                }
            }
        }
        function unCheckAll(ele) {
            var cbs = document.getElementsByTagName('input');
            for (var i = 0; i < cbs.length; i++) {
                if (cbs[i].type == 'checkbox') {
                    cbs[i].checked = false;
                }
            }
        }
        function onSubmit(butName) {
            if (butName == "addAddr") {
                document.getElementById("aMethod").value = "add";
            } else if (butName == "delselected") {
                document.getElementById("aMethod").value = "delete";
            }
            document.aForm.submit();
        }
    </script>
</head>
<body>
<c:set var="addrs" value="${cuser.addresses}"/>
<div class="info">
    <h4>用户信息</h4>
    <table class="list">
        <tr>
            <th>id</th>
            <th>用户名</th>
            <th>用户密码</th>
            <th>用户昵称</th>
            <th>用户权限</th>
        </tr>
        <tr>
            <td>${cuser.id}</td>
            <td>${cuser.username}</td>
            <td>${cuser.password}</td>
            <td>${cuser.nickname}</td>
            <c:choose>
                <c:when test="${cuser.role == 'ADMIN'}">
                    <td>管理员</td>
                </c:when>
                <c:otherwise>
                    <td>普通用户</td>
                </c:otherwise>
            </c:choose>

        </tr>
    </table>
</div>
<form action="/address.do" method="post" name="aForm">
    <div class="info">
        <h4>用户地址</h4>
        <c:if test="${not empty addrs}">
            <table class="list">
                <tr>
                    <th>收件人</th>
                    <th>联系电话</th>
                    <th>收货地址</th>
                    <c:if test="${cuser.id == lguser.id}">
                        <th>操作</th>
                        <th>选择</th>
                    </c:if>
                </tr>
                <c:forEach items="${addrs}" var="addr">
                    <tr>
                        <td>${addr.recipient}</td>
                        <td class="addr-info">${addr.phone}</td>
                        <td class="addr-info">
                                ${addr.addressInfo}
                            <c:if test="${not empty addr.zip}">
                                (邮编:${addr.zip})
                            </c:if>
                        </td>
                        <c:if test="${cuser.id == lguser.id}">
                            <td>
                                <a href="/address.do?method=update&addrid=${addr.id}">编辑</a>&nbsp;
                                <a href="/address.do?method=delete&addrids=${addr.id}&userid=${cuser.id}">删除</a>
                            </td>
                            <td>
                                <input type="checkbox" name="addrids"
                                       value="${addr.id}"/>
                            </td>
                        </c:if>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
        <div class="button">
            <c:if test="${cuser.id == lguser.id}">
                <input type="hidden" name="method" value="list" id="aMethod">
                <input type="hidden" name="userid" value="${cuser.id}">
                <input type="submit" value="添加地址" name="addAddr"
                       onclick="onSubmit('addAddr')"/>
                <c:if test="${not empty addrs}">
                    <input type="button" value="全选" onclick="checkAll(this)"/>
                    <input type="button" value="取消全选" onclick="unCheckAll(this)"/>
                    <input type="submit" name="delselected" value="删除选择用户"
                           onclick="onSubmit('delselected')">
                    <div class="clear-float"></div>
                </c:if>
            </c:if>

        </div>
    </div>
</form>

</body>
</html>
