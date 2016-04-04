<%@ page import="shop.enums.Role" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%--Created by IntelliJ IDEA.
User: Amysue
Date: 2016/3/23
Time: 21:36
To change this template use File | Settings | File Templates.--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>用户列表</title>
    <%--<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/main.css">--%>
    <style>
        #formwrapper {
            margin: 5px auto;
            padding: 10px;
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
            if (butName == "delselected") {
                document.getElementById("uMethod").value = "delete";
            } else if (butName == "adminselected") {
                document.getElementById("uMethod").value = "changeAuth";
                document.getElementById("uRoles").value = "ADMIN";
            } else if (butName == "normalselected") {
                document.getElementById("uMethod").value = "changeAuth";
                document.getElementById("uRoles").value = "NORMAL";
            }
            document.uForm.submit();
        }
    </script>
</head>
<body>
<c:set var="ADMIN" value="<%=Role.ADMIN%>"/>
<c:url var="pageurl"
       value="/user.do?method=list&role=${cuser.role}&username=${cuser.username}&nickname=${cuser.nickname}"/>
<c:set var="allPageNums" value="${pLists.allPageNums}"/>
<c:set var="currentPage" value="${pLists.currentPage}"/>
<c:url var="configurl"
       value="/user.do?role=${cuser.role}&username=${cuser.username}&nickname=${cuser.nickname}&toPage=${currentPage}"/>
<%
    Map<String, String> rolemap = new HashMap<>();
    rolemap.put(Role.NORMAL.toString(), "普通用户");
    rolemap.put(Role.ADMIN.toString(), "管理员");
    pageContext.setAttribute("rolemap", rolemap);
%>
<div id="formwrapper">
    <form action="/user.do" method="post" name="uForm">
        <fieldset>
            <legend>用户查询</legend>
            <div class="radio-div">
                <label for="name">用户名</label>
                <input type="text" name="username" id="name"
                       value="${cuser['username']}"/>
            </div>
            <div class="radio-div">
                <label for="nickname">用户昵称</label>
                <input type="text" name="nickname" id="nickname"
                       value="${cuser['nickname']}"/>
            </div>
            <div class="last-radio-div radio-div">
                <label for="radio1">所有用户:</label>
                <c:choose>
                    <c:when test="${empty cuser.role}">
                        <input type="radio" name="role" id="radio1" value="All"
                               checked>
                    </c:when>
                    <c:otherwise>
                        <input type="radio" name="role" id="radio1" value="All">
                    </c:otherwise>
                </c:choose>
                <c:forEach items="${rolemap}" var="item">
                    <label for="radio-${item.key}">${item.value}:</label>
                    <c:choose>
                        <c:when test="${item.key == cuser.role}">
                            <input type="radio" name="role" id="radio-${item.key}"
                                   value="${item.key}" checked>
                        </c:when>
                        <c:otherwise>
                            <input type="radio" name="role" id="radio-${item.key}"
                                   value="${item.key}">
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </div>
            <div class="button">
                <input type="submit" name="search" value="查询"/>
                <input type="reset" value="重置"/>
                <div class="clear-float"></div>
            </div>
            <label class="error">${errMsg}</label>
        </fieldset>

        <table class="list">
            <tr>
                <th>id</th>
                <th>用户名</th>
                <th>用户密码</th>
                <th>用户昵称</th>
                <th>用户权限</th>
                <th>操作</th>
                <th>选择</th>
            </tr>
            <c:forEach items="${pLists.tLists}" var="u">
                <tr>
                    <td>${u.id}</td>
                    <td>${u.username}</td>
                    <td>${u.password}</td>
                    <td><a href="/user.do?method=show&userid=${u.id}">${u.nickname}</a></td>
                    <c:choose>
                        <c:when test="${u.id == lguser.id}">
                            <c:choose>
                                <c:when test="${u.role == ADMIN}">
                                    <td>管理员</td>
                                </c:when>
                                <c:otherwise>
                                    <td>普通用户</td>
                                </c:otherwise>
                            </c:choose>
                            <td><a href="/user.do?method=updateUser&userid=${u.id}">编辑</a></td>
                            <td>&nbsp;</td>
                        </c:when>
                        <c:otherwise>
                            <c:choose>
                                <c:when test="${u.role == ADMIN}">
                                    <td><a href="${configurl}&method=changeAuth&userids=${u.id}&roles=<%=Role.NORMAL.toString()%>">管理员</a></td>
                                </c:when>
                                <c:otherwise>
                                    <td><a href="${configurl}&method=changeAuth&userids=${u.id}&roles=<%=Role.ADMIN.toString()%>">普通用户</a></td>
                                </c:otherwise>
                            </c:choose>
                            <td><a href="/user.do?method=updateUser&userid=${u.id}">编辑</a>&nbsp;
                                <a href="${configurl}&method=delete&userids=${u.id}">删除</a>
                            </td>
                            <td><input type="checkbox" name="userids"
                                       value="${u.id}"/></td>
                        </c:otherwise>
                    </c:choose>
                </tr>
            </c:forEach>
        </table>
        <input type="hidden" name="toPage" value="${currentPage}">
        <input type="hidden" name="method" value="list" id="uMethod">
        <input type="hidden" name="roles" id="uRoles">
        <div class="button">
            <input type="button" value="全选" onclick="checkAll(this)"/>
            <input type="button" value="取消全选" onclick="unCheckAll(this)"/>
            <input type="submit" name="delselected" value="删除选择用户"
                   onclick="onSubmit('delselected')">
            <input type="submit" name="adminselected" value="成为管理员" onclick="onSubmit('adminselected')">
            <input type="submit" name="normalselected" value="成为普通用户" onclick="onSubmit('normalselected')">
            <div class="clear-float"></div>
        </div>
    </form>
</div>
<jsp:include page="/inc/pager.jsp">
    <jsp:param name="pageurl" value="${pageurl}"/>
    <jsp:param name="currentPage" value="${currentPage}"/>
    <jsp:param name="allPageNums" value="${allPageNums}"/>
    <jsp:param name="begin" value="${pLists.begin}"/>
    <jsp:param name="end" value="${pLists.end}"/>
</jsp:include>
</body>
</html>
