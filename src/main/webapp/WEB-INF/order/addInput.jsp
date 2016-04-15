<%--
  Created by IntelliJ IDEA.
  User: Amysue
  Date: 2016/4/14
  Time: 20:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>提交订单</title>
    <style>
        .p-item {
            width: 650px;
            margin: 2px auto;
            border-bottom: 1px solid #e5e5e5;
            overflow: auto;
            background: #fcfcfc none repeat scroll 0 0;
        }
        .head .col {
            height: auto;
            padding: 0;
        }
        .head .col2 {
            color: #666666;
        }
        .img {
            max-width: 80px;
            max-height: 80px;
        }
        .col {
            float: left;
            margin: 2px 4px;
            text-align: left;
            height: 85px;
        }
        .col0 {
            width: 109px;
            padding: 0;
            overflow: auto;
            height: 83px;
        }
        .col0 span {
            float: left;
            margin-right: 5px;
            margin-top: 30px;
            background: #dadada none repeat scroll 0 0;
            color: #5f5f5f;
        }
        .col0 input {
            float: left;
            width: 24px;
            margin-top: 30px;
            margin-right: 5px;
        }
        .col0 img {
            float: left;
        }
        .col1 {
            width: 220px;
            vertical-align: middle;
        }
        .col2 {
            width: 130px;
        }
        .col2 strong {
            color: #3c3c3c;
            font-weight: 700;
        }
        .col3 {
            width: 65px;

        }
        .col3 label {
            float: left;
            clear: left;
        }
        .num {
            font-weight: bold;
        }
        .col4 {
            width: 65px;
        }
        .col4 strong {
            color: #f40;
            font-weight: 700;
        }
        .col a {
            color: #7a991a;
        }
        .end {
            background: #e5e5e5 none repeat scroll 0 0;
            /*height: 50px;*/
        }
        .end .col {
            height: 50px;
            padding-top: 0;
            line-height: 50px;
        }
        .end span{
            vertical-align: middle;
            line-height: 50px;
            height: 50px;
        }
        .end em {
            font-size: 18px;
            color: #f40;
            vertical-align: middle;
            line-height: 50px;
            height: 50px;
        }
        .ecol3 {
            margin-left: 300px;
            width: 200px;
        }
        .ecol3 strong {
            line-height: 22px;
        }
        .elast {
            float: right;
            width: 120px;
            text-align: center;
        }
        .elast input {
            font-size: 20px;
            color: #ffffff;
            border: 0px;
            background: #f40 none repeat scroll 0 0;
            cursor: pointer;
            height: 46px;
            width: 120px;
        }
        .price_enable {
            background: #f40 none repeat scroll 0 0;
            cursor: pointer;
        }
        .address-div {
            width: 300px;
            margin-top: 5px;
            margin-bottom: 5px;
            margin-left: 302px;
            text-align: left;
        }
        .address-list li {
            float: left;
            clear: left;
            padding: 3px 2px;

        }
        .address-list input {
            height: 14px;
            line-height: 14px;
            vertical-align: bottom;
        }
        .address-list label {
            height: 14px;
            line-height: 14px;
            vertical-align: bottom;
        }
    </style>
    <script type="text/javascript">

    </script>
</head>
<body>
<c:set var="addrs" value="${lguser.addresses}"/>
<c:set var="totalPrice" value="${0}"/>
<form action="/order.do?method=addInput" method="post">
    <div class="address-div">
        <h3 class="address-h">确认收货地址</h3>
        <ul id="address-list" class="address-list">
            <c:forEach items="${addrs}" var="addr" varStatus="vstatus">
                <li>
                    <c:choose>
                        <c:when test="${vstatus.index == 0}">
                            <input type="radio" name="aid" value="${addr.id}" checked="checked">
                        </c:when>
                        <c:otherwise>
                            <input type="radio" name="aid" value="${addr.id}">
                        </c:otherwise>
                    </c:choose>
                    <label>${addr.addressInfo}&nbsp;<strong>(${addr.recipient})</strong>&nbsp;收&nbsp;<em><strong>${addr.phone}</strong></em></label>
                </li>
            </c:forEach>
        </ul>
    </div>
    <div>
        <div class="p-item">&nbsp;</div>
        <div class="p-item head">
            <div class="col col0"></div>
            <div class="col col1">商品名称</div>
            <div class="col col2">单价</div>
            <div class="col col3">购买数量</div>
            <div class="col col4">总价</div>
        </div>
        <c:forEach items="${orderProductMap}" var="cpm">
            <c:set var="cp" value="${cpm.value}"/>
            <c:set var="p" value="${cp.product}"/>
            <div class="p-item">
                <div class="col col0">
                    <img class="img" src="<c:url value="${p.img}"/>">
                </div>
                <div class="col col1">
                    <a href="/product.do?pid=${p.id}&method=show">${p.name}</a>
                </div>
                <div class="col col2">
                    <label>￥<strong>${p.price}</strong></label>
                    <input id="unitprice-${p.id}" type="hidden" value="${p.price}">
                </div>
                <div class="col col3">
                    <label class="num">x${cp.purchaseNum}</label>
                    <input id="num-${p.id}" type="hidden" value="${cp.purchaseNum}">
                </div>
                <div class="col col4">
                    <c:set var="total" value="${0}"/>
                    <c:set var="total" value="${total + p.price*cp.purchaseNum}" />
                    <c:set var="totalPrice" value="${total + totalPrice}" />
                    <strong>${total}</strong>
                </div>
            </div>
            </c:forEach>
            <div class="p-item end">
                <div class="col ecol3 price-sum">
                    <span class="txt">合计（不含运费）:</span>
                    <strong class="price">
                        ¥
                        <em id="J_Total">${totalPrice}</em>
                    </strong>
                </div>
                <div class="col elast price_enable" id="J_Go">
                    <input type="submit" value="提交订单" />
                </div>
            </div>
        </div>
</form>

</body>
</html>
