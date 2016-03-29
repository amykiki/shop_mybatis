<%--
  Created by IntelliJ IDEA.
  User: Amysue
  Date: 2016/3/29
  Time: 16:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style type="text/css">
    .container {
        margin: 30px auto;
        width: 640px;
    }

    .vnav {
        background: white none repeat scroll 0 0;
        border: 1px solid darkgrey;
        box-shadow: 0 1px 2px rgba(0, 0, 0, 0.4);
        overflow: hidden;
        width: 100px;
    }

    .vnav-menu {
        list-style: outside none none;
        color: #404040;
    }

    .vnav a {
        display: block;
        line-height: 34px;
        text-decoration: none;
    }

    .vnav-item {
        background: #505968 linear-gradient(to bottom, #707a84, #505968) repeat scroll 0 0;
        color: white;
        font-size: 13px;
        position: relative;
        text-shadow: 0 1px rgba(0, 0, 0, 0.35);
        transition: opacity 0.15s ease 0s;
    }

    a.vnav-item:hover, a.vnav-item:active{
        background: #259bdb linear-gradient(to bottom, #44c5ec, #259bdb) repeat scroll 0 0;
        border-bottom-color: #1c638f;
        border-top-color: #6ad2ef;
        box-shadow: 0 1px #bbbbbb, 0 2px #e9ebe8;
        opacity: 1;
    }

    a.vnav-item:visited {
        background: #259bdb linear-gradient(to bottom, #44c5ec, #259bdb) repeat scroll 0 0;
        border-bottom-color: #1c638f;
        border-top-color: #6ad2ef;
        box-shadow: 0 1px #bbbbbb, 0 2px #e9ebe8;
        opacity: 1;
    }

    .vnav li {
        margin: 0;
    }
</style>
<nav class="vnav">
    <ul class="vnav-menu">
        <li><a href="#" class="vnav-item" id="info">账户信息</a></li>
        <li><a href="#" class="vnav-item" id="address">用户地址</a></li>
        <li><a href="#" class="vnav-item" id="order">用户订单</a></li>
    </ul>
</nav>