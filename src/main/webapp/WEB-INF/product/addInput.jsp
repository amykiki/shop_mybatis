<%--
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
    <title>添加商品</title>
    <style>
        fieldset .error-div {
            text-align: center;
        }
        fieldset .error {
            float: none;
            width: auto;
            display: block;
            text-align: center;
        }
    </style>
</head>
<body>
<div id="formwrapper">
    <form action="/product.do" method="post" enctype="multipart/form-data">
        <fieldset>
            <legend>添加商品</legend>
            <div>
                <label for="name">商品名称</label>
                <input type="text" name="name" id="name" value="${param.name}">
                <label class="error">${errMap.name}</label>
            </div>
            <div>
                <label for="price">商品价格</label>
                <input type="text" name="price" id="price" value="${param.price}">
                <label class="error">${errMap.price}</label>
            </div>
            <div>
                <label for="stock">商品库存</label>
                <input type="text" name="stock" id="stock" value="${param.stock}">
                <label class="error">${errMap.stock}</label>
            </div>
            <div>
                <label for="cid">商品类别</label>
                <select id="cid" name="cid">
                    <option value="">请选择商品类别</option>
                    <c:forEach items="${cLists}" var="ca">
                        <c:choose>
                            <c:when test="${ca.id == param.cid}">
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
            <div class="img-div">
                <label for="imgfile">上传图片</label>
                <input type="file" name="img" id="imgfile" value="${param.img}">
                <label class="error">${errMap.img}</label>
            </div>
            <div class="textarea-div">
                <label>商品描述</label>
                <textarea name="intro">${param.intro}</textarea>
            </div>
            <div class="button">
                <input type="hidden" name="method" value="addInput">
                <input id="but1" type="submit" value="添加"/>
                <input id="but2" type="reset" value="重置"/>
                <div class="clear-float"></div>
            </div>
        </fieldset>
    </form>
</div>
</body>
</html>
