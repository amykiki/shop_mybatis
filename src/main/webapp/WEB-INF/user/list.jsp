<%@ page import="shop.model.Role" %>
<%@ page import="shop.model.Pager" %>
<%@ page import="shop.model.User" %><%--
  Created by IntelliJ IDEA.
  User: Amysue
  Date: 2016/3/23
  Time: 21:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>用户列表</title>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/pager.css">
</head>
<body>
<c:set var="ADMIN" value="<%=Role.ADMIN%>"/>
<c:url var="pageurl" value="/user.do?method=list"/>
<c:set var="allPageNums" value="${pLists.allPageNums}"/>
<c:set var="currentPage" value="${pLists.currentPage}"/>
<table class="list">
    <tr>
        <th>id</th><th>用户名</th><th>用户密码</th><th>用户昵称</th>
        <th>用户权限</th><th>操作</th>
    </tr>
    <c:forEach items="${pLists.tLists}" var="u">
        <tr>
            <td>${u.id}</td>
            <td>${u.username}</td>
            <td>${u.password}</td>
            <td><a href="/user.do?method=show">${u.nickname}</a></td>
            <c:choose>
                <c:when test="${u.role == ADMIN}">
                    <td><a href="/user.do?method=">管理员</a></td>
                </c:when>
                <c:otherwise>
                    <td><a href="/user.do?method=">普通用户</a></td>
                </c:otherwise>
            </c:choose>
            <td><a href="#">编辑</a>&nbsp;<a href="#">删除</a> </td>
        </tr>
    </c:forEach>
</table>
<%--<ul class="tsc_pagination tsc_paginationA tsc_paginationA05">--%>
    <%--<li><a href="${pageurl}&toPage=1">首页</a></li>--%>
    <%--<c:if test="${currentPage > 1}">--%>
        <%--<li><a href="${pageurl}&toPage=${currentPage - 1}">前一页</a></li>--%>
    <%--</c:if>--%>
    <%--<c:forEach var="i" begin="${pLists.begin}" end="${pLists.end}">--%>
        <%--<c:choose>--%>
            <%--<c:when test="${i == currentPage}">--%>
                <%--<li><a href="#" class="current">${i}</a></li>--%>
            <%--</c:when>--%>
            <%--<c:otherwise>--%>
                <%--<li><a href="${pageurl}&toPage=${i}">${i}</a></li>--%>
            <%--</c:otherwise>--%>
        <%--</c:choose>--%>
        <%--<c:if test="${i == currentPage}">--%>

        <%--</c:if>--%>
    <%--</c:forEach>--%>
    <%--<c:if test="${currentPage < allPageNums}">--%>
        <%--<li><a href="${pageurl}&toPage=${currentPage+1}">下一页</a></li>--%>
    <%--</c:if>--%>
    <%--<li><a href="${pageurl}&toPage=${allPageNums}">尾页</a></li>--%>
<%--</ul>--%>
<jsp:include page="/inc/pager.jsp">
    <jsp:param name="pageurl" value="${pageurl}"/>
    <jsp:param name="currentPage" value="${currentPage}"/>
    <jsp:param name="allPageNums" value="${allPageNums}"/>
    <jsp:param name="begin" value="${pLists.begin}"/>
    <jsp:param name="end" value="${pLists.end}"/>
</jsp:include>
</body>
</html>
