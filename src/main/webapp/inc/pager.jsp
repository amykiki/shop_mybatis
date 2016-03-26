<%--
  Created by IntelliJ IDEA.
  User: Amysue
  Date: 2016/3/24
  Time: 20:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/pager.css">

<fmt:parseNumber var="currentPage" type="number" value="${param.currentPage}" />
<fmt:parseNumber var="allPageNums" type="number" value="${param.allPageNums}" />
<ul class="tsc_pagination tsc_paginationA tsc_paginationA05">
    <li><a href="${param.pageurl}&toPage=1">首页</a></li>
    <c:if test="${currentPage > 1}">
        <li><a href="${param.pageurl}&toPage=${currentPage - 1}">前一页</a></li>
    </c:if>
    <c:forEach var="i" begin="${param.begin}" end="${param.end}">
        <c:choose>
            <c:when test="${i == currentPage}">
                <li><a href="#" class="current">${i}</a></li>
            </c:when>
            <c:otherwise>
                <li><a href="${param.pageurl}&toPage=${i}">${i}</a></li>
            </c:otherwise>
        </c:choose>
        <c:if test="${i == currentPage}">

        </c:if>
    </c:forEach>
    <c:if test="${currentPage < allPageNums}">
        <li><a href="${param.pageurl}&toPage=${currentPage+1}">下一页</a></li>
    </c:if>
    <li><a href="${param.pageurl}&toPage=${allPageNums}">尾页</a></li>
</ul>

