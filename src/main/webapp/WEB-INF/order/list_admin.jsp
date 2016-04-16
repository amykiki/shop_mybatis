<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="shop.enums.OStatus" %>
<%@ page import="java.util.LinkedHashMap" %><%--
  Created by IntelliJ IDEA.
  User: Amysue
  Date: 2016/4/15
  Time: 18:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>管理所有订单</title>
    <style>
        .radio-id input{
            width: 110px;
        }
        fieldset {
            width: 500px;
        }
        .order-title {
            width: 690px;
            margin: 5px auto;
            background-color: #f5f5f5;
            border: 1px solid #e8e8e8;
            overflow: hidden;
            padding: 12px 5px;
        }
        .ot-cols {
            float: left;
            width: 80px;
        }
        .ot-col1 {
            width: 300px;
            text-align: center;
        }
        .ot-col2 {
            width: 75px;
        }
        .ot-col3 {
            width: 75px;
        }
        .order-main {
            border: 1px solid #daf3ff;
            margin-bottom: 10px;
            width: 700px;
            margin-left: auto;
            margin-right: auto;
            overflow: auto;
            /*border-collapse: collapse;*/
            height: auto;
            color: #3e3e3e;
        }
        .order-main:hover {
            border-color: #afd9ff;
        }
        .order-main a {
            color: #3c3c3c;
            text-decoration: none;
        }
        .order-main a:hover {
            color: #ff6000;
        }
        .order-head {
            background-color: #eaf8ff;
            padding-top: 10px;
            padding-bottom: 10px;
            padding-left: 10px;
        }
        .order-body {
            width: 700px;
            overflow: hidden;
            display: table;
        }
        .o-col1 {
            overflow: hidden;
            float: left;
        }
        .cp-row {
            clear: left;
            overflow: hidden;
            border-right: 1px solid #daf3ff;
            border-bottom: 1px solid #daf3ff;
        }
        .cp-row-last {
            border-bottom: 0px solid #daf3ff;
        }
        .cp-cols {
            float: left;
        }
        .cp-col1 {
        }
        .cp-col2 {
            width: 200px;
            margin-left: 5px;
        }
        .cp-col3 {
            width: 75px;
            text-align: center;
        }
        .cp-col4 {
            width: 75px;
            text-align: center;
        }
        .o-cols {
            display: inline-block;
            width: auto;
            vertical-align: top;
            border-right: 1px solid #daf3ff;
            display: table-cell;
            width: 85px;
            text-align: center;
        }
        .o-col3 span {
            float: left;
            clear: left;
            width: 85px;
        }
        .o-cols-last {
            border-right: 0px solid #daf3ff;
        }
        .o-cols-last a {
            background-color: #66b6ff;
            border-color: #66b6ff;
            border-style: solid !important;
            border-width: 1px;
            color: #fff;
            padding: 5px 8px;
            display: inline-block;
        }
        .o-cols-last a:hover {
            background-color: #118adb;
            border-color: #118adb;
            color: #fff;
            cursor: pointer;
        }
        .o-cols-last a:active {
            background-color: #afd9ff;
            border-color: #afd9ff;
            color: #fff;
        }
        .img {
            max-width: 80px;
            max-height:80px;
        }
    </style>
    <script type="text/javascript">
        function resetInput() {
            var ostatus = document.getElementById('radio1');
            ostatus.checked = true;
            var cbs = document.getElementsByTagName('input');
            for (var i = 0; i < cbs.length; i++) {
                if (cbs[i].type == 'text') {
                    cbs[i].value = '';
                }
            }
        }
    </script>
</head>
<body>
<c:url var="pageurl"
       value="/order.do?method=list&name=${param.name}&oid=${param.oid}&uid=${param.uid}&ostatus=${param.ostatus}"/>
<c:url var="actionurl" value="/order.do?type=all&name=${param.name}&uid=${param.uid}&oid=${param.oid}&ostatus=${param.ostatus}"/>
<c:set var="allPageNums" value="${pLists.allPageNums}"/>
<c:set var="currentPage" value="${pLists.currentPage}"/>
<c:set var="ToPAID" value="<%=OStatus.ToPAID%>"/>
<c:set var="ToDELIVERED" value="<%=OStatus.ToDELIVERED%>"/>
<c:set var="ToCONFIRMED" value="<%=OStatus.ToCONFIRMED%>"/>
<c:set var="FINISHED" value="<%=OStatus.FINISHED%>"/>
<c:set var="CANCELED" value="<%=OStatus.CANCELED%>"/>
<%
    LinkedHashMap<String, String> statusmap = new LinkedHashMap<>();
    statusmap.put(OStatus.ToPAID.toString(), "待付款");
    statusmap.put(OStatus.ToDELIVERED.toString(), "待发货");
    statusmap.put(OStatus.ToCONFIRMED.toString(), "待收货");
    statusmap.put(OStatus.FINISHED.toString(), "完成");
    statusmap.put(OStatus.CANCELED.toString(), "取消");
    pageContext.setAttribute("statusmap", statusmap);
%>
<div id="formwrapper">
    <form action="/order.do" method="post" name="oForm">
        <fieldset>
            <legend>订单查询</legend>
            <div class="radio-div">
                <label for="name">产品名</label>
                <input type="text" name="name" id="name"
                       value="${param.name}"/>
            </div>
            <div class="radio-div radio-id">
                <label for="oid">订单号</label>
                <input type="text" name="oid" id="oid"
                       value="${param.oid}"/>
            </div>
            <div class="radio-div radio-id">
                <label for="uid">用户id</label>
                <input type="text" name="uid" id="uid"
                       value="${param.uid}"/>
            </div>
            <div class="last-radio-div radio-div">
                <input type="radio" name="ostatus" id="radio1" value="All" checked>
                <label for="radio1">所有订单</label>
                <c:forEach items="${statusmap}" var="item">
                    <c:choose>
                        <c:when test="${item.key == param.ostatus}">
                            <input type="radio" name="ostatus" id="radio-${item.key}"
                                   value="${item.key}" checked>
                        </c:when>
                        <c:otherwise>
                            <input type="radio" name="ostatus" id="radio-${item.key}"
                                   value="${item.key}">
                        </c:otherwise>
                    </c:choose>
                    <label for="radio-${item.key}">${item.value}</label>
                </c:forEach>
            </div>
            <div class="button">
                <input type="hidden" name="method" value="list">
                <input type="hidden" name="type" value="all">
                <input type="submit" name="search" value="查询"/>
                <input type="button" value="重置" onclick="resetInput()"/>
            </div>
        </fieldset>
        <div class="order-title">
            <div class="ot-col1 ot-cols">
                <span>宝贝</span>
            </div>
            <div class="ot-col2 ot-cols">
                <span>单价(元)</span>
            </div>
            <div class="ot-col3 ot-cols">
                <span>数量</span>
            </div>
            <div class="ot-col4 ot-cols">
                <span>实付款(元)</span>
            </div>
            <div class="ot-col5 ot-cols">
                <span>交易状态</span>
            </div>
            <div class="ot-col6 ot-cols">
                <span>交易操作</span>
            </div>
        </div>
        <c:forEach items="${pLists.tLists}" var="oitems">
            <div class="order-main">
                <div class="order-head">
                    <span>
                        <strong>
                            <fmt:formatDate value="${oitems.buyDate}" pattern="yyyy-MM-dd"/>
                        </strong>&nbsp;
                        订单号:&nbsp;${oitems.id}
                    </span>
                </div>
                <div class="order-body">
                    <div class="o-col1">
                        <c:forEach items="${oitems.products}" var="cp" varStatus="varstatus">
                        <c:choose>
                        <c:when test="${varstatus.last}">
                        <div class="cp-row cp-row-last">
                            </c:when>
                            <c:otherwise>
                            <div class="cp-row">
                                </c:otherwise>
                                </c:choose>
                                <c:set var="pt" value="${cp.product}"/>
                                <div class="cp-col1 cp-cols">
                                    <img class="img" src="<c:url value="${pt.img}"/>">
                                </div>
                                <div class="cp-col2 cp-cols">
                                    <span><a target="_blank" href="/product.do?pid=${pt.id}&method=show">${pt.name}</a></span>
                                </div>
                                <div class="cp-col3 cp-cols">
                                    <span>${cp.price}</span>
                                </div>
                                <div class="cp-col4 cp-cols">
                                    <span>${cp.purchaseNum}</span>
                                </div>
                            </div>
                            </c:forEach>
                        </div>
                        <div class="o-cols o-col2">
                            <span><strong>${oitems.totalPrice}</strong></span>
                        </div>
                        <div class="o-cols o-col3">
                            <span><a target="_blank" href="#">订单详情</a></span>
                            <c:if test="${oitems.status == ToPAID}">
                                <span>待付款</span>
                            </c:if>
                            <c:if test="${oitems.status == ToDELIVERED}">
                                <span>待发货</span>
                            </c:if>
                            <c:if test="${oitems.status == ToCONFIRMED}">
                                <span>物流派件中</span>
                            </c:if>
                            <c:if test="${oitems.status == FINISHED}">
                                <span>已收货</span>
                            </c:if>
                            <c:if test="${oitems.status == CANCELED}">
                                <span>订单取消</span>
                            </c:if>
                        </div>
                        <div class="o-cols o-col4 o-cols-last">
                            <c:if test="${oitems.status == ToDELIVERED}">
                                <a href="${actionurl}&method=deliverOrder&orderid=${oitems.id}"><span>去发货</span></a>
                            </c:if>
                        </div>
                </div>

            </div>
        </c:forEach>
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
