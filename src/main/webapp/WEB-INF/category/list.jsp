<%--
  Created by IntelliJ IDEA.
  User: Amysue
  Date: 2016/4/8
  Time: 15:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>--%>
<html>
<head>
    <title>商品分类</title>
    <style>
        .ca_container {
            width: 600px;
            text-align: left;
            margin-left: 35%;
        }

        .ca_item_0 {
            width: 80px;
            float: left;
            clear: left;
            background-color: #7f8c8d;
            color: white;
            font-weight: bold;
            margin-bottom: 2px;
            margin-right: 3px;
            padding: 2px;
        }

        .ca_item_1, .ca_item_1_first {
            width: 50px;
            float: left;
            padding: 2px;
            text-align: justify;
            background-color: #9b59b6;
            margin-bottom: 2px;
            margin-right: 3px;
            color: white;
            font-weight: bold;
        }

        .ca_item_1 {
            clear: left;
            margin-left: 87px;
        }

        .ca_item_2 {
            width: 50px;
            display: inline-block;
            text-align: justify;
            padding: 2px 1px;
            margin-bottom: 1px;
            background-color: #e74c3c;
            color: white;
            font-weight: bold;
        }
        .ca_depth2 {
            width: 300px;
            /*display: inline-block;*/
            /*margin-left: 200px;*/
            float: left;
        }
        .button {
            width: 300px;
            margin: 0 auto;
        }
    </style>
    <script type="text/javascript">
        function onSubmit(butName) {
            if (butName == "addselected") {
                document.getElementById("cMethod").value = "add";
            } else if (butName == "updateselected") {
                document.getElementById("cMethod").value = "update";
            } else if (butName == "deleteselected") {
                document.getElementById("cMethod").value = "delete";
            }
            document.cForm.submit();
        }
    </script>
</head>
<body>
<c:set var="lastDepth" value="0"/>
<c:set var="curDepth" value="0"/>
<c:set var="ca_depth2_name" value=""/>
<div class="ca_container">
    <c:forEach items="${cLists}" var="ca">
        <c:set var="curDepth" value="${ca.depth}"/>
        <c:if test="${(lastDepth == 2) && (curDepth != 2)}">
            <div class="ca_depth2">
                <c:forTokens items="${ca_depth2_name}" delims="," var="name">
                    <a class="ca_item_2" href="#">${name}</a>
                </c:forTokens>
            </div>

            <c:set var="ca_depth2_name" value=""/>
        </c:if>
        <c:if test="${ca.depth == 2}">
            <c:if test="${lastDepth == 2}">
                <c:set var="ca_depth2_name" value="${ca_depth2_name},${ca.name}"/>
            </c:if>
            <c:if test="${lastDepth != 2}">
                <c:set var="ca_depth2_name" value="${ca.name}"/>
            </c:if>
        </c:if>
        <c:if test="${ca.depth == 0}">
            <a class="ca_item_0" href="#">
                    ${ca.name}
            </a>
        </c:if>
        <c:if test="${ca.depth == 1}">
            <c:choose>
                <c:when test="${lastDepth != 0}">
                    <a class="ca_item_1" href="#">
                            ${ca.name}
                    </a>
                </c:when>
                <c:otherwise>
                    <a class="ca_item_1_first" href="#">
                            ${ca.name}
                    </a>
                </c:otherwise>
            </c:choose>
        </c:if>
        <c:set var="lastDepth" value="${ca.depth}"/>
    </c:forEach>
    <div class="clear-float"></div>
</div>
<c:if test="${lguser.role == 'ADMIN'}">
    <div id="formwrapper">
        <form action="/category.do" method="post" name="cForm">
            <input type="hidden" name="method" value="list" id="cMethod">
            <div class="button">
                <input type="submit" name="add" value="添加分类" onclick="onSubmit('addselected')"/>
                <input type="submit" name="update" value="修改分类" onclick="onSubmit('updateselected')"/>
                <input type="submit" name="delete" value="删除分类" onclick="onSubmit('deleteselected')"/>
            </div>
        </form>

    </div>
</c:if>
</body>
</html>
