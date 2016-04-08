<%@ page import="shop.enums.Role" %>
<%@ page import="shop.enums.PStatus" %><%--
  Created by IntelliJ IDEA.
  User: Amysue
  Date: 2016/4/9
  Time: 2:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<c:set var="ADMIN" value="<%=Role.ADMIN%>"/>
<c:set var="All" value="<%=PStatus.All%>"/>
<c:set var="InSale" value="<%=PStatus.InSale%>"/>
<c:set var="OffSale" value="<%=PStatus.OffSale%>"/>
<head>
    <title>商品列表</title>
    <style>
        .pt_div {
            <c:if test="${lguser.role == ADMIN}">
                width: 750px;
            </c:if>
            <c:if test="${lguser.role != ADMIN}">
                width: 720px;
            </c:if>
            margin: 4px auto;
            overflow: auto;
            border-bottom: 1px solid #e5e5e5;
            padding: 10px 10px 10px 0;
            vertical-align: middle;
        }

        .col {
            float: left;
        }
        .col-1 {
            text-align: center;
        }
        .img {
            max-width: 80px;
            max-height: 80px;
        }
        .col-2 {
            margin-left: 20px;
            width: 240px;
            text-align: left;
            font-size: 14px;
        }
        .col-3 {
            margin-left: 20px;
            text-align: center;
            width: 140px;
        }
        .g_price {
            font-size: 18px;
            color: #f40;
            font-family: verdana,arial;
        }
        .g_price strong {
            font-weight: 700;
        }
        .deal-cnt {
            color: #888;
        }

        .col-4 {
            margin-left: 20px;
            width: 100px;
            text-align: left;
            font-size: 12px;
        }
        .col a {
            color: #3d3d3d;
            text-decoration: none;
            display: inline-block;
            vertical-align: middle;
            line-height: normal;
        }
        .col-5 a {
            color: #7a991a;
        }
        .s-price input{
            width: 50px;
        }
        #formwrapper {
            margin: 5px auto;
            padding: 10px;
        }
    </style>
</head>
<c:set var="allPageNums" value="${pLists.allPageNums}"/>
<c:set var="currentPage" value="${pLists.currentPage}"/>
<c:url var="pageurl"
       value="product.do?method=list&name=${param.name}&status=${param.staus}&price1=${param.price1}&price2=${param.price2}&cid=${param.cid}"/>
<body>
<div id="formwrapper">
    <form action="/product.do?method=list" method="post">
        <fieldset>
            <legend>商品查询</legend>
            <div class="radio-div">
                <label for="name">商品名</label>
                <input type="text" name="name" id="name"
                       value="${param.name}"/>
            </div>
            <div class="radio-div s-price">
                <label for="price1">价格</label>
                <input type="text" name="price1" id="price1"
                       placeholder="￥"${param.price1}"/>
                -
                <input type="text" name="price2"
                       placeholder="￥"${param.price2}"/>
            </div>
            <c:if test="${lguser.role == ADMIN}">
                <div class="last-radio-div radio-div">
                    <input type="radio" name="status" id="radio1" value="${All}" checked>
                    <label for="radio1">所有商品</label>
                    <input type="radio" name="status" id="radio2" value="${InSale}">
                    <label for="radio2">上架商品</label>
                    <input type="radio" name="status" id="radio3" value="${OffSale}">
                    <label for="radio3">下架商品</label>
                </div>
            </c:if>
            <
            <div class="button">
                <input type="submit" name="search" value="查询"/>
                <input type="reset" value="重置"/>
                <div class="clear-float"></div>
            </div>
            <label class="error">${errMsg}</label>
        </fieldset>
    </form>
</div>
<c:forEach items="${pLists.tLists}" var="pt">
    <c:if test="${(pt.status == InSale) || (lguser.role == ADMIN)}">
        <div class="pt_div">
            <div class="col col-1">
                <img class="img" src="<c:url value="${pt.img}"/>">
            </div>
            <div class="col col-2">
                <a href="#">${pt.name}</a>
            </div>
            <div class="col col-3">
                <span class="g_price">
                    <span>￥</span>
                    <strong>${pt.price}</strong>
                </span>
                <p class="deal-cnt">${pt.sales}人付款</p>
            </div>
            <div class="col col-4">
                <c:if test="${pt.status == InSale}">
                    <a href="#">添加到购物车</a>
                </c:if>
                <c:if test="${pt.status == OffSale}">
                    已下架
                </c:if>
            </div>
            <c:if test="${lguser.role == ADMIN}">
                <div class="col col-5">
                    <span>
                        <a href="#">编辑</a>
                        <c:if test="${pt.status == InSale}">
                            <a href="#">下架</a>
                        </c:if>
                        <c:if test="${pt.status == OffSale}">
                            <a href="#">上架</a>
                        </c:if>
                        <a href="#">删除</a>
                    </span>
                </div>
            </c:if>
        </div>
    </c:if>
</c:forEach>
<jsp:include page="/inc/pager.jsp">
    <jsp:param name="pageurl" value="${pageurl}"/>
    <jsp:param name="currentPage" value="${currentPage}"/>
    <jsp:param name="allPageNums" value="${allPageNums}"/>
    <jsp:param name="begin" value="${pLists.begin}"/>
    <jsp:param name="end" value="${pLists.end}"/>
</jsp:include>
</body>
</html>
