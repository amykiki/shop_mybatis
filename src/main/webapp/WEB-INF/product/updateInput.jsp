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
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>更新商品</title>
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
    <form action="/product.do" method="post" enctype="multipart/form-data">
        <fieldset>
            <legend>更新商品</legend>
            <div class="img-div">
                <img class="img" src="<c:url value="${cp.img}"/>">
                <div class="clear-float"></div>
            </div>
            <div>
                <label for="name">商品名称</label>
                <input type="text" name="name" id="name" value="${cp.name}">
                <label class="error">${errMap.name}</label>
            </div>
            <div>
                <label for="price">商品价格</label>
                <input type="text" name="price" id="price" value="${cp.price}">
                <label class="error">${errMap.price}</label>
            </div>
            <div>
                <label for="stock">商品库存</label>
                <input type="text" name="stock" id="stock" value="${cp.stock}">
                <label class="error">${errMap.stock}</label>
            </div>
            <div>
                <label for="cid">商品类别</label>
                <select id="cid" name="cid">
                    <option value="">请选择商品类别</option>
                    <c:forEach items="${cLists}" var="ca">
                        <c:choose>
                            <c:when test="${ca.id == cp.category.id}">
                                <option value="${ca.id}" selected>
                                    <c:if test="${ca.depth > 0}">
                                        <c:forEach var="i" begin="1" end="${ca.depth}">
                                            &nbsp;&nbsp;
                                        </c:forEach>
                                    </c:if>
                                    ${ca.name}
                                </option>
                            </c:when>
                            <c:otherwise>
                                <option value="${ca.id}">
                                    <c:if test="${ca.depth > 0}">
                                        <c:forEach var="i" begin="1" end="${ca.depth}">
                                            &nbsp;&nbsp;
                                        </c:forEach>
                                    </c:if>
                                    ${ca.name}
                                </option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </select>
                <label class="error">${errMap.cid}</label>
            </div>
            <div>
                <label>商品类型</label>
                <div class="last-radio-div radio-div">
                    <c:forEach items="${statusMap}" var="item">
                        <c:choose>
                            <c:when test="${item.key == cp.status}">
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
                <label class="error">${errMap.status}</label>
            </div>
            <div class="imgfile-div">
                <label for="imgfile">上传图片</label>
                <input type="file" name="img" id="imgfile">
                <label class="error">${errMap.img}</label>
            </div>
            <div class="textarea-div">
                <label>商品描述</label>
                <textarea name="intro">${cp.intro}</textarea>
            </div>
            <div class="button">
                <input type="hidden" name="method" value="updateInput">
                <input type="hidden" name="pid" value="${param.pid}">
                <input type="hidden" name="toPage" value="${param.toPage}">
                <input id="but1" type="submit" value="修改"/>
                <input id="but2" type="reset" value="重置"/>
                <div class="clear-float"></div>
            </div>
        </fieldset>
    </form>
</div>
</body>
</html>
