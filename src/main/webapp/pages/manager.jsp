<%@ page import="bean.User" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 杨pc
  Date: 2021/9/23
  Time: 0:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%@include file="header.jsp"%>
<head>
    <%
        String type = (String) session.getAttribute("manager_type");
        if (type.equals("user")) {
            List<User> UserList = (List<User>) session.getAttribute("AllUser");
            pageContext.setAttribute("user", UserList);
            pageContext.setAttribute("article", null);
            pageContext.setAttribute("checkArticles",null);
        } else if (type.equals("article")) {
            pageContext.setAttribute("article", session.getAttribute("AllArticle"));
            pageContext.setAttribute("user", null);
            pageContext.setAttribute("checkArticles",null);
        }else if ("checkArticle".equals(type)){
            pageContext.setAttribute("checkArticles",session.getAttribute("checkArticles"));
            pageContext.setAttribute("user", null);
            pageContext.setAttribute("article", null);
        }
    %>
    <title>管理</title>
    <base href="http://localhost:8080/bolg/">
    <link type="text/css" rel="stylesheet" href="css/manager.css">
    <script type="text/javascript" src="jquery-3.4.1/jquery-3.4.1.js"></script>
    <script type="text/javascript" src="jquery-3.4.1/alert.js"></script>
</head>
<body>
<div class="main-father">
    <div class="manager-container">
        <div class="manager-menu">
            <div class="manager-menu-user">用户</div>
            <div class="manager-menu-blog">博客</div>
            <div class="manager-menu-check">博客审核</div>
        </div>
        <script>
            $(".manager-menu-user").click(function () {
                window.location.href = "articleServlet?action=openManager";
            })
            $(".manager-menu-blog").click(function () {
                window.location.href = "articleServlet?action=articleManager";
            })
            $(".manager-menu-check").click(function (){
                window.location.href = "articleServlet?action=articleCheck";
            })
        </script>
        <div class="manager-list">
            <c:forEach items="${pageScope.user}" var="item">
                <input name="id" class="id" value="${item.id}" hidden="hidden"/>
                <input name="email" class="email" hidden="hidden" value="${item.email}">
                <div class="manager-data-user">
                    <div class="manager-user-head">
                        <img src="imags/backgroud.png" alt=""/>
                    </div>
                    <div class="manager-user-mid">
                        <div class="manager-user-name">${item.name}</div>
                        <div class="manager-user-brief">${item.brief}</div>
                    </div>
                    <div class="manager-user-right">
                        <button class="check">审核</button>
                        <button class="delete">删除</button>
                    </div>
                </div>
            </c:forEach>
            <script>

                $(".manager-user-right .check").click(function () {
                    var id = $(this).parent().parent().prev().prev().val();
                    var email = $(this).parent().parent().prev().val();
                    window.location.href = "userServlet?action=checkUser&id=" + id + "";
                })

                $(".manager-user-right .delete").click(function () {
                    var id = $(this).parent().parent().prev().prev().val();
                    var email = $(this).parent().parent().prev().val();
                    $.MsgBox.Confirm("警告", "尊敬的管理员你确定删除用户" + "<h3 style='color: #00c4ff' >" + email + "</h3>", function () {
                        window.location.href = "userServlet?action=deleteUser&id=" + id + "";
                    });
                })
            </script>
            <c:forEach items="${pageScope.article}" var="item">
                <input id="id" name="id" hidden="hidden" value="${item.id}">
                <div class="manager-data-item">
                    <div class="manager-data-item-title">${item.title}</div>
                    <div class="manager-data-mid">
                        <div class="manager-data-item-content">${item.content}</div>
                        <div class="manager-data-button">
                            <button class="today" value="${item.id}">今日推荐</button>
                            <button class="check">修改</button>
                            <button class="delete">删除</button>
                        </div>
                    </div>
                    <div class="manager-data-item-bottom">
                        <div class="manager-data-item-bottom-read">
                            <img src="imags/read.png">&nbsp;&nbsp;<span>${item.read}</span>&nbsp;&nbsp;
                        </div>
                        <div class="manager-data-item-bottom-like">
                            <img src="imags/like.png">&nbsp;&nbsp;<span>${item.like}</span>&nbsp;&nbsp;
                        </div>
                        <div class="manager-data-item-bottom-right">
                            <div class="manager-data-item-bottom-name">${item.author}</div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <div class="manager-data-item-bottom-data">${item.time}</div>
                        </div>
                    </div>

                </div>
            </c:forEach>
            <script>
                $(".manager-data-item-title").click(function () {
                    var s = $(this).parent().prev("#id").val();
                    // alert($(this).children(".id").val())
                    window.location = ("articleServlet?action=show-article&id=" + s + "");
                })
                $(".manager-data-button .check").click(function () {
                    var id = $(this).parent().parent().parent().prev().val();
                    var title = $(this).parent().parent().prev().text();
                    // $.MsgBox.Confirm("提示", "尊敬的管理员你确定审核博客" + "<h3 style='color: #00c4ff' >" + title + "</h3>", function () {
                        window.location.href = "articleServlet?action=editArticle&id=" + id + "";
                    // });
                })
                $(".manager-data-button .delete").click(function () {
                    var id = $(this).parent().parent().parent().prev().val();
                    var title = $(this).parent().parent().prev().text();
                    $.MsgBox.Confirm("警告", "尊敬的管理员你确定删除博客" + "<h3 style='color: #00c4ff' >" + title + "</h3>", function () {
                        window.location.href = "articleServlet?action=deleteArticle&id=" + id + "";
                    });
                })
                $(".today").click(function (){
                    $.getJSON("http://localhost:8080/bolg/articleServlet", "action=recommend&id=" + $(this).val(), function (data) {
                        $.MsgBox.Alert("提示","设置成功");
                    })
                })
            </script>
            <c:forEach items="${pageScope.checkArticles}" var="item">
                <input id="id" name="id" hidden="hidden" value="${item.id}">
                <div class="manager-data-item">
                    <div class="manager-data-item-title">${item.title}</div>
                    <div class="manager-data-mid">
                        <div class="manager-data-item-content">${item.content}</div>
                        <div class="manager-data-button">
                            <button class="check-Bolg">通过</button>
                            <button class="read-Blog">查看</button>
                        </div>
                    </div>
                    <div class="manager-data-item-bottom">
                        <div class="manager-data-item-bottom-read">
                            <img src="imags/read.png">&nbsp;&nbsp;<span>${item.read}</span>&nbsp;&nbsp;
                        </div>
                        <div class="manager-data-item-bottom-like">
                            <img src="imags/like.png">&nbsp;&nbsp;<span>${item.like}</span>&nbsp;&nbsp;
                        </div>
                        <div class="manager-data-item-bottom-right">
                            <div class="manager-data-item-bottom-name">${item.author}</div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <div class="manager-data-item-bottom-data">${item.time}</div>
                        </div>
                    </div>
                </div>
            </c:forEach>
            <script>
                $(".check-Bolg").click(function (){
                    var id = $(this).parent().parent().parent().prev().val();
                    var title = $(this).parent().parent().prev().text();
                    $.MsgBox.Confirm("提示", "尊敬的管理员你确定让" + "<h3 style='color: #00c4ff' >" + title + "</h3>"+"通过审核", function () {
                        window.location.href = "articleServlet?action=agreeCheckArticle&id=" + id + "";
                    });
                })
                $(".read-Blog").click(function () {
                    var id = $(this).parent().parent().parent().prev().val();
                    var title = $(this).parent().parent().prev().text();
                    $.MsgBox.Confirm("提示", "尊敬的管理员你确定审核博客" + "<h3 style='color: #00c4ff' >" + title + "</h3>", function () {
                        window.location.href = "articleServlet?action=readCheckArticle&id=" + id + "";
                    });
                })
            </script>
        </div>

    </div>
</div>
</body>
</html>
