<%--
  Created by IntelliJ IDEA.
  User: Amysue
  Date: 2016/4/13
  Time: 16:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>购物车</title>
    <style>
        .p-item {
            width: 710px;
            margin: 2px auto;
            border-bottom: 1px solid #e5e5e5;
            overflow: auto;
            background: #fcfcfc none repeat scroll 0 0;
        }
        .p-item .invalid {
            color: #ccc;
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
        .item-invalid {
            background: #f0f0f0 none repeat scroll 0 0;
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
        .col5 {
            width: 65px;
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
        .ecol1 {
            width: 80px;
        }
        .ecol1 input {
            vertical-align: middle;
            line-height: 50px;
            height: 50px;
        }
        .ecol2 {
            width: 100px;
            margin-left: 120px;
        }
        .ecol3 {
            margin-left: 30px;
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
        .elast span {
            font-size: 20px;
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
        /*.elast .btn {*/
            /*display: inline-block;*/
            /*height: 50px;*/
            /*width: 120px;*/
            /*text-align: center;*/
        /*}*/
        .price_disable {
            color: #9c9c9c;
            cursor: not-allowed;
        }
        .price_enable {
            background: #f40 none repeat scroll 0 0;
            /*cursor: pointer;*/
        }
    </style>
    <script type="text/javascript">
        function getSelected() {
            var cbs = document.getElementsByTagName('input');
            for (var i = 0; i <cbs.length; i++) {
                if (cbs[i].type == 'checkbox' && cbs[i].checked == true) {

                }
            }
        }
        function checkAll(name) {
            var cbs = document.getElementsByName(name);
            for (var i = 0; i < cbs.length; i++) {
                if (cbs[i].type == 'checkbox') {
                    cbs[i].checked = true;
                }
            }
        }
        function unCheckAll(name) {
            var cbs = document.getElementsByName(name);
            for (var i = 0; i < cbs.length; i++) {
                if (cbs[i].type == 'checkbox') {
                    cbs[i].checked = false;
                }
            }
        }
        function reserveCheck(name){
            var action = document.getElementById('J_SelectAll').value;
            if (action == "all") {
                checkAll(name);
                document.getElementById('J_SelectAll').value = "none";
            } else {
                unCheckAll(name);
                document.getElementById('J_SelectAll').value = "all";
            }
        }
        function getCheckBoxLength(name){
            var length = 0;
            var checkboxes;
            var totalPrice = 0;
            var aPrice = document.getElementById('J_Go');

            checkboxes = document.getElementsByName(name);
            for(var i=0;i<checkboxes.length;i++){
                if(checkboxes[i].checked){
                    length+=1;
                    var pricestr = "unitprice-";
                    var pnum = "num-";
                    var pidstr = checkboxes[i].value;
                    pricestr = pricestr.concat(pidstr);
                    pnum = pnum.concat(pidstr);
                    var unitPrice = document.getElementById(pricestr).value;
                    var numCount = document.getElementById(pnum).value;
                    totalPrice += parseInt(unitPrice) * parseInt(numCount);
                }
            }
            if (length == 0) {
                document.getElementById("J_SelectAll").checked = false;
                aPrice.setAttribute("class", "col elast price_disable");
//                aPrice.href = "#";
                aPrice.innerHTML = '<span>结 算</span>';
            } else {
                aPrice.setAttribute("class", "col elast price_enable");
//                aPrice.href = "#";
                aPrice.innerHTML = '<input type="submit" value="结 算" />';
                if (length == checkboxes.length) {
                    document.getElementById("J_SelectAll").checked = true;
                }
            }
            document.getElementById("J_SelectedItemsCount").innerHTML = length.toString();
            document.getElementById("J_Total").innerHTML = totalPrice.toString();
        }
    </script>
</head>
<body onload="getCheckBoxLength('pids');">
<form action="/order.do?method=add" method="post">
    <div>
        <div class="p-item">&nbsp;</div>
        <div class="p-item head">
            <div class="col col0"></div>
            <div class="col col1">商品名称</div>
            <div class="col col2">单价</div>
            <div class="col col3">购买数量</div>
            <div class="col col4">总价</div>
            <div class="col col5">操作</div>
        </div>
        <c:forEach items="${cartProductMap}" var="cpm">
            <c:set var="cp" value="${cpm.value}"/>
            <c:set var="p" value="${cp.product}"/>
            <c:set var="valid" value="1"/>
            <c:if test="${cp.purchaseNum > p.stock}">
                <div class="p-item item-invalid">
                    <c:set var="valid" value="0"/>
            </c:if>
            <c:if test="${cp.purchaseNum <= p.stock}">
                <div class="p-item">
            </c:if>
                <div class="col col0">
                    <c:choose>
                        <c:when test="${valid == 1}">
                    <input type="checkbox" name="pids" value="${p.id}" onclick="getCheckBoxLength('pids')"/>
                        </c:when>
                        <c:otherwise>
                            <span class="invalid">失效</span>
                        </c:otherwise>
                    </c:choose>
                    <img class="img" src="<c:url value="${p.img}"/>">
                </div>
                <div class="col col1">
                    <c:choose>
                        <c:when test="${valid == 1}">
                            <a href="/product.do?pid=${p.id}&method=show">${p.name}</a>
                        </c:when>
                        <c:otherwise>
                            <a class="invalid" href="/product.do?pid=${p.id}&method=show">${p.name}</a>
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="col col2">
                    <c:choose>
                        <c:when test="${valid == 1}">
                            <label>￥<strong>${p.price}</strong></label>
                            <input id="unitprice-${p.id}" type="hidden" value="${p.price}">
                        </c:when>
                        <c:otherwise>
                            <label class="invalid">￥<strong class="invalid">${p.price}</strong></label>
                            <input id="unitprice-${p.id}" class="invalid" type="hidden" value="${p.price}">
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="col col3">
                    <c:choose>
                        <c:when test="${valid == 1}">
                            <label class="num">x${cp.purchaseNum}</label>
                        </c:when>
                        <c:otherwise>
                            <label class="num invalid">x${cp.purchaseNum}</label>
                        </c:otherwise>
                    </c:choose>
                    <input id="num-${p.id}" type="hidden" value="${cp.purchaseNum}">
                    <label><a href="/shopcart.do?method=update&pid=${p.id}">编辑</a></label>
                </div>
                <div class="col col4">
                    <c:set var="total" value="${0}"/>
                    <c:set var="total" value="${total + p.price*cp.purchaseNum}" />
                    <c:choose>
                        <c:when test="${valid == 1}">
                            <strong>${total}</strong>
                        </c:when>
                        <c:otherwise>
                            <strong class="invalid">${total}</strong>
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="col col5">
                    <a href="/shopcart.do?method=delete&pid=${p.id}">删除</a>
                </div>
            </div>
        </c:forEach>
        <div class="p-item end">
            <div class="col ecol1">
                <input type="checkbox" id="J_SelectAll" value="all" onclick="reserveCheck('pids');getCheckBoxLength('pids');"/>
                <span>全选</span>
            </div>
            <div class="col ecol2">
                <span>已选商品</span>
                <em id="J_SelectedItemsCount"></em>
                <span>件</span>
            </div>
            <div class="col ecol3 price-sum">
                <span class="txt">合计（不含运费）:</span>
                <strong class="price">
                    ¥
                    <em id="J_Total"></em>
                </strong>
            </div>
            <div class="col elast" id="J_Go">
                <%--<a id="J_Go"><span>结 算</span></a>--%>
            </div>
        </div>
    </div>
</form>
</body>
</html>
