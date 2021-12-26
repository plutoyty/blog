<%--
  Created by IntelliJ IDEA.
  User: 杨pc
  Date: 2021/9/5
  Time: 1:51
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Title</title>
    <base href="http://localhost:8080/bolg/">
    <link type="text/css" rel="stylesheet" href="css/header.css">
    <script type="text/javascript" src="jquery-3.4.1/jquery-3.4.1.js"></script>
    <script type="text/javascript" src="jquery-3.4.1/alert.js"></script>
    <script type="text/javascript">
        $(function () {
            $("#index-tab").click(function () {
                $("#index-tab").css("border-bottom", "2px #00c4ff solid");
                $("#charge-tab").css("border-bottom", "2px #fefefe solid");
                $("#groupintro-tab").css("border-bottom", "2px #fefefe solid");
                $("#hardware-shop").css("border-bottom", "2px #fefefe solid");
            })
            $("#charge-tab").click(function () {
                if (<%=session.getAttribute("user")==null%>) {
                    $.MsgBox.Alert("消息", "未检测到用户，请您先登录！");
                    return false;
                }
                $("#index-tab").css("border-bottom", "2px #fefefe solid");
                $("#charge-tab").css("border-bottom", "2px #00c4ff solid");
                $("#groupintro-tab").css("border-bottom", "2px #fefefe solid");
                $("#hardware-shop").css("border-bottom", "2px #fefefe solid");
            })
            $("#groupintro-tab").click(function () {
                $("#index-tab").css("border-bottom", "2px #fefefe solid");
                $("#charge-tab").css("border-bottom", "2px #fefefe solid");
                $("#groupintro-tab").css("border-bottom", "2px #00c4ff solid");
                $("#hardware-shop").css("border-bottom", "2px #fefefe solid");
            })
            $("#hardware-shop").click(function () {
                $("#index-tab").css("border-bottom", "2px #fefefe solid");
                $("#charge-tab").css("border-bottom", "2px #fefefe solid");
                $("#groupintro-tab").css("border-bottom", "2px #fefefe solid");
                $("#hardware-shop").css("border-bottom", "2px #00c4ff solid");
            })
        })
    </script>
</head>
<body>
<div id="header">
    <div class="header-shadow">
        <div id="header-warp">
            <div id="header-right">
                <a id="btn-top" href="pages/edArticle.jsp" onclick="this">创作</a>
                <script>
                    $("#btn-top").click(function () {
                        if (<%=session.getAttribute("user")==null%>) {
                            $.MsgBox.Alert("消息", "未检测到用户，请您先登录！");
                            return false;
                        }
                        window.location = ("articleServlet?action=open_write");
                        return false;
                    })
                </script>
                <div id="user-info">
                    <div class="user-drop">
                        <c:if test="${sessionScope.user==null}">
                            <a id="dropdown-button" href="pages/login.jsp">登录/注册</a>
                        </c:if>
                        <c:if test="${!(sessionScope.user==null?true:false)}">
                            <div class="a10010">
                            <img class="user-head" src="${sessionScope.user.head}"/>
                            <div class="logout">注销</div></div>
                        </c:if>
                        <script>
                            $(".user-head").click(function (){
                                window.location.href = "articleServlet?action=hisBlog&email="+'${sessionScope.user.email}';
                            })
                            $(".logout").click(function (){
                                var s= "${sessionScope.user.name}"
                                $.MsgBox.Confirm("温馨提示","尊敬的用户"+"<h3 style='color: #00c4ff;'>"+s+"</h3>"+"你确定要离开吗？",function (){
                                    window.location = ("userServlet?action=logout");
                                })
                            })
                        </script>
                        <span id="dropdown">
                            <i></i>
                        </span>
                    </div>
                    <ul id="dropdown-list" style="">
                        <li id="logout">
                            <a>注销</a>
                        </li>
                    </ul>
                </div>
            </div>
            <div id="search">
                <input id="search-input" autocomplete="off" type="text" value="" placeholder="请输入查找的内容...">
                <a id="search-button" target="_blank">
                    <sqan class="search-query">搜索</sqan>
                </a>
                <script type="text/javascript">
                    $("#search-button").click(function () {
                        if ($("#search-input").val().trim()==""){
                            $.MsgBox.Alert("提示","不能为空")
                            return false;
                        }
                        window.location.href = "articleServlet?action=search&q=" + $("#search-input").val() + "";
                    })
                </script>
                <input type="password" autocomplete="new-password" readonly="" disabled="true"
                       style="display: none; position:absolute;left:-9999999px;width:0;height:0;">
            </div>
            <a id="logo" href="index.jsp" title="@@title">
                <img src="imags/logo.png">
            </a>
            <ul id="header-nav">
                <li id="index-tab" class="nav-current" title="首页">
                    <a href="index.jsp">首页</a>
                </li>
                <li id="charge-tab">
                    <a href="pages/personal.jsp" onclick="this">个人中心</a>
                </li>
                <li id="groupintro-tab">
                    <a href="javascript:void(0);">我的粉丝</a>
                </li>
                <li id="hardware-shop">
                    <a style="display:none;" href="javascript:void(0);" id="manager">管理</a>
                </li>
            </ul>
            <script>
                if (${sessionScope.user.id=='1'})
                    $("#hardware-shop a").css({display: "block"});
                $("#manager").click(function () {
                    window.location.href = "articleServlet?action=openManager"
                })
            </script>
        </div>
    </div>
</div>
</body>
</html>
