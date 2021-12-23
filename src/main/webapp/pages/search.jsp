<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List" %>
<%@ page import="bean.Article" %><%--
  Created by IntelliJ IDEA.
  User: 杨pc
  Date: 2021/9/21
  Time: 2:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%
        List<Article> list = (List<Article>) session.getAttribute("search_article");
        if (list != null) {
            pageContext.setAttribute("search_article", list);
            pageContext.setAttribute("search_user", null);
        } else {
            pageContext.setAttribute("search_user", session.getAttribute("search_user"));
            pageContext.setAttribute("fan", session.getAttribute("fan"));
            pageContext.setAttribute("search_article", null);
        }
    %>
    <title>${sessionScope.q}</title>
    <base href="http://localhost:8080/bolg/">
    <link type="text/css" rel="stylesheet" href="css/search.css">
    <script type="text/javascript" src="jquery-3.4.1/jquery-3.4.1.js"></script>
    <script type="text/javascript" src="jquery-3.4.1/alert.js"></script>
    <script type="text/javascript">
        $(function () {
            fun1 = function () {
                $(".search-blog").css({color: "#00c4ff", "font-size": "18px"});
            }
            fun2 = function () {
                $(".search-user").css({color: "#00c4ff", "font-size": "18px"});
            }
            fun3 = function () {
                $(".search-tag").css({color: "#00c4ff", "font-size": "18px"});
            }
            fun4 = function () {
                $(".search-sort").css({color: "#00c4ff", "font-size": "18px"});
            }
            if (${sessionScope.search_type=='blog'}) {
                fun1();
            }
            if (${sessionScope.search_type=='sort'}) {
                fun4()
            }
            if (${sessionScope.search_type=='user'}) {
                fun2()
            }
            if (${sessionScope.search_type=='tag'}) {
                fun3()
            }
            ;
        })
    </script>
</head>
<body>
<%@include file="header.jsp" %>
<div class="main-father">
    <div class="search-father">
        <div class="search-title">
            <input class="search-input" value="${sessionScope.q}"/>&nbsp;&nbsp;&nbsp;&nbsp;
            <button></button>
        </div>
    </div>
    <br>

    <script>
        $(".search-title button").click(function () {
            window.location.href = "articleServlet?action=search&q=" + $(".search-input").val() + "";
        })
    </script>

    <div class="search-body">
        <div class="search-body-in">
            <ul class="search-body-in-head">
                <li class="search-blog">博客</li>&nbsp;&nbsp;&nbsp;&nbsp;
                <li class="search-user">用户</li>&nbsp;&nbsp;&nbsp;&nbsp;
                <li class="search-tag">标签</li>&nbsp;&nbsp;&nbsp;&nbsp;
                <li class="search-sort">分栏</li>&nbsp;&nbsp;&nbsp;&nbsp;
            </ul>
        </div>
        <script>
            $(".search-blog").click(function () {
                if ($(".search-input").val().trim()==""){
                    $.MsgBox.Alert("提示","不能为空")
                    return false;
                }
                window.location.href = "articleServlet?action=search&q=" + $(".search-input").val() + "";
            })
            $(".search-user").click(function () {
                if ($(".search-input").val().trim()==""){
                    $.MsgBox.Alert("提示","不能为空")
                    return false;
                }
                window.location.href = "articleServlet?action=searchUser&q=" + $(".search-input").val() + "";
            })
            $(".search-tag").click(function () {
                if ($(".search-input").val().trim()==""){
                    $.MsgBox.Alert("提示","不能为空")
                    return false;
                }
                window.location.href = "articleServlet?action=searchTag&q=" + $(".search-input").val() + "";
            })
            $(".search-sort").click(function () {
                if ($(".search-input").val().trim()==""){
                    $.MsgBox.Alert("提示","不能为空")
                    return false;
                }
                window.location.href = "articleServlet?action=searchSort&q=" + $(".search-input").val() + "";
            })
        </script>
        <div class="search-data">
            <c:forEach items="${search_user}" varStatus="status" var="item">
                <input class="id" name="id" value="${item.id}" hidden="hidden">
                <input name="email" class="email" value="${item.email}" hidden="hidden"/>
                <div class="search-data-user">
                    <div class="search-user-head">
                        <img src="${item.head}" alt=""/>
                    </div>
                    <div class="search-user-mid">
                        <div class="search-user-name">${item.name}</div>
                        <div class="search-user-brief">${item.brief}</div>
                    </div>
                    <div class="search-user-right">
                        <button class="con${status.index}">关注</button>
                        <c:set var="flag" value="0"/>
                        <c:forEach items="${pageScope.fan}" var="sat" varStatus="staus">
                            <c:if test="${flag==0}">
                                <c:if test="${sat==item.id}">
                                    <script>
                                        $(".con${status.index}").text("已关注");
                                    </script>
                                    <c:set var="flag" value="1"/>
                                </c:if>
                            </c:if>
                        </c:forEach>
                    </div>
                </div>
            </c:forEach>
            <script>
                $(".search-user-name").click(function () {
                    var em = $(this).parent().parent().prev(".email").val();
                    window.location = ("articleServlet?action=hisBlog&email=" + em + "");
                })
                $(".search-user-right button").click(function () {
                    // alert($(this).text())
                    var idd = $(this).parent().parent().prev().prev(".id").val();
                    if ($(this).text() == "关注") {
                        $(this).text("已关注");
                        $.getJSON("http://localhost:8080/bolg/articleServlet", "action=concern&userid=" + idd + "&fan=" +${sessionScope.user.id}, function (data) {

                        })
                    } else {
                        $(this).text("关注")
                        $.getJSON("http://localhost:8080/bolg/articleServlet", "action=cancleconcern&userid=" +idd+"&fan="+${sessionScope.user.id}, function (data) {

                        })
                    }
                })
            </script>
            <c:forEach items="${pageScope.search_article}" var="item" varStatus="staus">
                <input id="id" name="id" hidden="hidden" value="${item.id}">
                <div class="search-data-item">
                    <div class="search-data-item-title">${item.title}</div>
                    <div class="search-data-item-content">${item.content}</div>
                    <div class="search-data-item-bottom">
                        <div class="search-data-item-bottom-read">
                            <img src="imags/read.png">&nbsp;&nbsp;<span>${item.read}</span>&nbsp;&nbsp;
                        </div>
                        <div class="search-data-item-bottom-like">
                            <img src="imags/like.png">&nbsp;&nbsp;<span>${item.like}</span>&nbsp;&nbsp;
                        </div>
                        <div class="search-data-item-bottom-right">
                            <div class="search-data-item-bottom-name">${item.author}</div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <div class="search-data-item-bottom-data">${item.time}</div>
                        </div>
                    </div>
                </div>
            </c:forEach>
            <script>
                $(".search-data-item-title").click(function () {
                    var s = $(this).parent().prev("#id").val();
                    // alert($(this).children(".id").val())
                    window.location = ("articleServlet?action=show-article&id=" + s + "");
                })
            </script>
        </div>
    </div>
</div>
</body>
</html>
