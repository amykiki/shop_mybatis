<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="shop.enums.PStatus" %><%--
  Created by IntelliJ IDEA.
  User: Amysue
  Date: 2016/4/10
  Time: 12:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>商品详情</title>
    <style>
        fieldset {
            width: 520px;
        }
        fieldset .error-div {
            text-align: center;
        }
        fieldset .error {
            float: none;
            width: auto;
            display: block;
            text-align: center;
        }
        fieldset div {
            float: left;
            clear: right;
            width: 280px;
        }
        .img {
            max-width: 230px;
            max-height:230px;
            /*float: left;*/
        }
        fieldset div.img-div {
            float: left;
            width: 230px;
            height: 230px;

        }
        .textarea-div {
            clear: both;
            float: none;
        }
        fieldset label {
            width: 80px;
            text-align: right;
        }
        fieldset input[type=text] {
            width: 160px;
        }
        fieldset select {
            width: 160px;
        }
        fieldset .radio-div{
            width: auto;
            margin: 0px;
            text-align: center;
        }
        .imgfile-div {
            width: 280px;
        }
        .imgfile-div label {
            /*text-align: left;*/
            /*width: auto;*/
            /*line-height: 22px;*/
        }
        .imgfile-div input {
            width: 180px;
            overflow: scroll;
            line-height: 20px;
            height: 20px;
        }
        .button {
            float: none; ;
        }
        fieldset .error {
            text-align: center;
        }
        .but-a {
            background: #ffffff;
            border: 1px solid #A4CDF2;
            color: #1E7ACE;
            width: 60px;
            margin: auto 6px;
            float: left;
            height: 20px;
            line-height: 20px;
        }
        #but1 {
            width: auto;
        }
        #but2 {
            float: left;
        }
        .button {
            width: 280px;
        }
    </style>
</head>
<body>
<%
    Map<String, String> statusMap = new HashMap<>();
    statusMap.put(PStatus.InSale.toString(), "上架商品");
    statusMap.put(PStatus.OffSale.toString(), "下架商品");
    pageContext.setAttribute("statusMap", statusMap);
%>
<div id="formwrapper">
    <form action="/orders.do" method="post" enctype="multipart/form-data">
        <fieldset>
            <legend>商品详情</legend>
            <div class="img-div">
                <img class="img" src="<c:url value="${cp.img}"/>">
                <div class="clear-float"></div>
            </div>
            <div>
                <label for="name">商品名称</label>
                <input type="text" name="name" id="name" value="${cp.name}" readonly="true">
            </div>
            <div>
                <label for="price">商品价格</label>
                <input type="text" name="price" id="price" value="${cp.price}" readonly="true">
            </div>
            <div>
                <label for="sales">商品销量</label>
                <input type="text" name="sales" id="sales" value="${cp.sales}" readonly="true">
            </div>
            <div>
                <label for="stock">商品库存</label>
                <input type="text" name="stock" id="stock" value="${cp.stock}" readonly="true">
            </div>
            <div>
                <label for="cid">商品类别</label>
                <input type="text" name="category" id="cid" value="${cp.category.name}" readonly="true">
            </div>
            <div>
                <label>商品类型</label>
                <c:forEach items="${statusMap}" var="item">
                    <c:if test="${item.key == cp.status}">
                        <input type="text" name="status" value="${item.value}" readonly="true">
                    </c:if>
                </c:forEach>
            </div>
            <div class="textarea-div">
                <label>商品描述</label>
                <textarea name="intro" readonly="true">${cp.intro}</textarea>
            </div>
            <div class="button">
                <input type="hidden" name="name" value="addToCart">
                <input type="hidden" name="pid" value="${param.pid}">
                <input type="hidden" name="toPage" value="${param.toPage}">
                <input id="but1" type="submit" value="添加到购物车"/>
                <a class="but-a" href="/product.do?method=list&toPage=${param.toPage}">返回</a>
                <div class="clear-float"></div>
            </div>
        </fieldset>
    </form>
</div>
</body>
</html>
