<%@ page import="shop.enums.Role" %>
<%@ page import="shop.enums.PStatus" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %><%--
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
            padding: 20px;
            width: 800px;
        }
        #formwrapper fieldset {
            width: 780px;
        }
        .c-search {
            overflow: auto;
            border: 1px solid #e8e8e8;
        }
        .row {
            border-top: 1px dashed #dedede;
            margin: 0 8px;
            position: relative;
            overflow: auto;
        }
        .c-search-head {
            color: red;
            overflow: auto;
            width: 100px;
            left: 11px;
            position: absolute;
            top: 3px;
        }
        .title {
            float: left;
            width: 75px;
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
        }
        .text {
            float: left;
            max-width: 140px;
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
        }
        .text-depth1 {
            color: #e74c3c;
        }
        .row input {
            float: left;
            display: inline-block;
            height: 16px;
        }
        .c-search-body {
            overflow: auto;
            width: 500px;
            right: 0;
            margin-left: 105px;
        }
        .item {
            clear: none;
            margin-right: 7px;
            display: inline-block;
            width: 65px;
        }
        .button {
            text-align: center;
            width: auto;
        }
    </style>
    <script type="text/javascript">
        function unCheckAll(ele) {
            var cbs = document.getElementsByTagName('input');
            for (var i = 0; i < cbs.length; i++) {
                if (cbs[i].type == 'checkbox') {
                    cbs[i].checked = false;
                }
            }
        }
    </script>
</head>
<c:set var="allPageNums" value="${pLists.allPageNums}"/>
<c:set var="currentPage" value="${pLists.currentPage}"/>
<c:url var="pageurl"
       value="product.do?method=list&name=${param.name}&status=${param.staus}&price1=${param.price1}&price2=${param.price2}"/>
<c:set var="lastDepth" value="-1"/>
<c:set var="curDepth" value="0"/>
<c:set var="ca_depth_name" value=""/>
<c:set var="ca_depth0_name" value=""/>
<c:set var="ca_depth1_name" value=""/>
<c:set var="ca_depth2_name" value=""/>
<c:set var="pstatus" value="${param.status}"/>
<%
    Map<String, String> statusMap = new HashMap<>();
    statusMap.put(PStatus.All.toString(), "所有商品");
    statusMap.put(PStatus.InSale.toString(), "上架商品");
    statusMap.put(PStatus.OffSale.toString(), "下架商品");
    pageContext.setAttribute("statusMap", statusMap);
%>
<body>
<c:forEach items="${selectcids}" var="scid">
    <c:url var="pageurl"
           value="${pageurl}&cids=${scid}"/>
</c:forEach>
<div id="formwrapper">
    <form action="/product.do?method=list" method="post">
        <fieldset>
            <legend>商品查询</legend>
            <div class="c-search">
                <c:forEach items="${cLists}" var="ca">
                    <c:set var="curDepth" value="${ca.depth}"/>
                    <c:if test="${curDepth == 0}">
                        <c:if test="${lastDepth >= 0}">
                            <c:if test="${lastDepth > 0}">
                                </div>
                            </c:if>
                            </div>
                        </c:if>
                        <div class="row">
                            <div class="c-search-head">
                                <c:choose>
                                    <c:when test="${ca.checked == 1}">
                                        <input type="checkbox" name="cids" value="${ca.id}" checked>
                                    </c:when>
                                    <c:otherwise>
                                        <input type="checkbox" name="cids" value="${ca.id}">
                                    </c:otherwise>
                                </c:choose>
                                <span class="title" title="${ca.name}">${ca.name}:</span>
                            </div>
                    </c:if>
                    <c:if test="${curDepth > 0}">
                        <c:if test="${lastDepth == 0}">
                            <div class="c-search-body">
                        </c:if>
                                <div class="item">
                                    <c:choose>
                                        <c:when test="${ca.checked == 1}">
                                            <input type="checkbox" name="cids" value="${ca.id}" checked>
                                        </c:when>
                                        <c:otherwise>
                                            <input type="checkbox" name="cids" value="${ca.id}">
                                        </c:otherwise>
                                    </c:choose>
                                    <c:if test="${curDepth == 1}">
                                        <span class="text text-depth1" title="${ca.name}">${ca.name}</span>
                                    </c:if>
                                    <c:if test="${curDepth == 2}">
                                        <span class="text" title="${ca.name}">${ca.name}</span>
                                    </c:if>
                                </div>
                    </c:if>
                    <c:set var="lastDepth" value="${ca.depth}"/>
                </c:forEach>
                        <c:if test="${lastDepth > 0}">
                            </div>
                        </c:if>
                        </div>
            </div>
            <div class="radio-div">
                <label for="name">商品名</label>
                <input type="text" name="name" id="name"
                       value="${param.name}"/>
            </div>
            <div class="radio-div s-price">
                <label for="price1">价格</label>
                <input type="text" name="price1" id="price1"
                       placeholder="￥" value="${param.price1}"/>
                -
                <input type="text" name="price2"
                       placeholder="￥" value="${param.price2}"/>
            </div>
            <c:if test="${lguser.role == ADMIN}">
                <p></p>
                <div class="last-radio-div radio-div">
                    <c:if test="${empty param.status}">
                        <c:set var="pstatus" value="<%=PStatus.All%>"/>
                    </c:if>
                    <c:forEach items="${statusMap}" var="item">
                        <c:choose>
                            <c:when test="${item.key == pstatus}">
                                <input type="radio" name="status" id="radio-${item.key}"
                                       value="${item.key}" checked>
                            </c:when>
                            <c:otherwise>
                                <input type="radio" name="status" id="radio-${item.key}"
                                       value="${item.key}">
                            </c:otherwise>
                        </c:choose>
                        <label for="radio-${item.key}">${item.value}</label>
                    </c:forEach>
                </div>
            </c:if>

            <div class="button">
                <input type="button" value="取消选择" onclick="unCheckAll(this)"/>
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
                    商品已下架
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
