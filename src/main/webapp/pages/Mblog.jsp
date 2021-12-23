<%@ page import="bean.Article" %>
<%@ page import="java.util.Collections" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 杨pc
  Date: 2021/9/17
  Time: 2:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%
        List<Article> list = (List<Article>) session.getAttribute("article");
        pageContext.setAttribute("article", list);
    %>
    <title>博客管理</title>
    <base href="http://localhost:8080/bolg/">
    <script type="text/javascript" src="jquery-3.4.1/jquery-3.4.1.js"></script>
    <script type="text/javascript" src="jquery-3.4.1/alert.js"></script>
    <link rel="stylesheet" href="css/Mblog.css"/>
</head>
<body>
<%@ include file="/pages/header.jsp" %>
<div class="main">
    <div class="blog-view-container">
        <div class="q1022">
            <form class="form-search" action="articleServlet">
                <div class="blog-search">
                    <span class="year">年:&nbsp;</span>
                    <input name="year" class="blog-search-year" min="2021" type="number"/>&nbsp;&nbsp;&nbsp;
                    <span class="month">月:&nbsp;</span>
                    <input name="month" class="blog-search-month" min="1" max="12" type="number"/>&nbsp;&nbsp;&nbsp;
                    <span class="month">文章类型:&nbsp;</span>
                    <select name="original" class="blog-search-original">
                        <option> 不限</option>
                        <option> 原创</option>
                        <option> 转载</option>
                    </select>&nbsp;&nbsp;&nbsp;
                    <span class="month">文章专栏:&nbsp;</span>
                    <input name="sort" class="blog-search-sort"/>&nbsp;&nbsp;&nbsp;
                    <span class="month">关键词:&nbsp;</span>
                    <input name="key" class="blog-search-key"/>&nbsp;&nbsp;&nbsp;
                    <button id="blog-search-MyBlog" onclick="this">搜索</button>
                </div>
            </form>
            <script type="text/javascript">
                $(function () {
                    $("#blog-search-MyBlog").click(function () {
                        $.getJSON("http://localhost:8080/bolg/articleServlet", "action=searchMyBlog&" + $(".form-search").serialize(), function (data) {
                            $(".blog-MyBlog-list").empty();
                            $.each(data.article, function (index, item) {
                                var s = "<input id='id' name='id' hidden='hidden' value='" + item.id + "'>";
                                $(".blog-MyBlog-list").append(s)
                                var cd = "<div class='blog-MyBlog-list-item'>" +
                                    "<div class='blog-MyBlog-list-item-title'>" + item.title + "</div>" +
                                    "<div class='blog-MyBlog-list-item-data'>" + item.time + "</div>";
                                var w;
                                if (item.original == true) {
                                    w = "<div class='blog-MyBlog-list-item-or'>原创</div>";
                                } else {
                                    w = "<div class='blog-MyBlog-list-item-or' style='color: #00d95a;background-color: #dbdcee;'>转载</div>";
                                }
                                var cd1 = "<div class='blog-MyBlog-list-item-or-father'>" + w + "</div><br>";
                                var cd2 = "<div class='blog-MyBlog-list-item-info'>" +
                                    "<div class='blog-MyBlog-list-item-info-left'>" +
                                    "<div class='blog-MyBlog-list-item-read'>" +
                                    "<span>阅读: </span>" + item.read + "</div>&nbsp;&nbsp;&nbsp;&nbsp;" +
                                    "<div class='blog-MyBlog-list-item-collect'><span>收藏: </span>" + item.collect + "</div></div>" +
                                    "<div class='blog-MyBlog-list-item-info-right'>" +
                                    "<div class='blog-MyBlog-list-item-edit'><a href='javascript:void(0)' class='blog-edit'>编辑</a>" +
                                    "</div>&nbsp;&nbsp;&nbsp;&nbsp;" +
                                    "<div class='blog-MyBlog-list-item-lookup'><a href='javascript:void(0)' class='blog-look'  onclick='this'>浏览</a>" +
                                    "</div>&nbsp;&nbsp;&nbsp;&nbsp;" +
                                    "<div class='blog-MyBlog-list-item-delete'><a href='javascript:void(0)' class='blog-delete'>删除</a></div>" +
                                    "</div>" +
                                    "</div>" +
                                    "</div>";
                                $(".blog-MyBlog-list").append(cd+cd1+cd2)
                                $(".blog-look").on("click",function (){
                                    var s = $(this).parent().parent().parent().parent().prev("#id").val()
                                    window.location = ("articleServlet?action=show-article&id=" + s + "");
                                });
                                $(".blog-edit").on("click",function (){
                                    var s = $(this).parent().parent().parent().parent().prev("#id").val()
                                    window.location = ("articleServlet?action=edit-article&id=" + s + "");
                                });
                                $(".blog-delete").on("click",function (){
                                    var s = $(this).parent().parent().parent().parent().prev("#id").val()
                                    window.location = ("articleServlet?action=delete-article&id=" + s + "");
                                });
                                // alert(cd+cd1+cd2)
                            });
                        })
                        return false;
                    })
                })
            </script>
            <div class="blog-MyBlog-list">
                <c:forEach items="${pageScope.article}" var="item">
                    <input id="id" name="id" hidden="hidden" value="${item.id}">
                    <div class="blog-MyBlog-list-item">
                        <div class="blog-MyBlog-list-item-title">
                                ${item.title}
                        </div>
                        <div class="blog-MyBlog-list-item-data">${item.time}</div>
                        <div class="blog-MyBlog-list-item-or-father">
                            <c:if test="${item.original==true}">
                                <div class="blog-MyBlog-list-item-or">原创</div>
                            </c:if>
                            <c:if test="${item.original==false}">
                                <div class="blog-MyBlog-list-item-or" style="color: #00d95a;background-color: #dbdcee;">
                                    转载
                                </div>
                            </c:if>
                        </div>
                        <br>
                        <div class="blog-MyBlog-list-item-info">
                            <div class="blog-MyBlog-list-item-info-left">
                                <div class="blog-MyBlog-list-item-read">
                                    <span>阅读: </span>${item.read}</div>&nbsp;&nbsp;&nbsp;&nbsp;
                                <div class="blog-MyBlog-list-item-collect"><span>收藏: </span>${item.collect}</div>
                            </div>
                            <div class="blog-MyBlog-list-item-info-right">
                                <div class="blog-MyBlog-list-item-edit"><a href="javascript:void(0)" class="blog-edit">编辑</a>
                                </div>&nbsp;&nbsp;&nbsp;&nbsp;
                                <div class="blog-MyBlog-list-item-lookup"><a href="javascript:void(0)"
                                                                             class="blog-look" onclick="this">浏览</a>
                                </div>&nbsp;&nbsp;&nbsp;&nbsp;
                                <div class="blog-MyBlog-list-item-delete"><a href="javascript:void(0)"
                                                                             class="blog-delete">删除</a></div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
        <script>
            $(".blog-look").click(function () {
                var s = $(this).parent().parent().parent().parent().prev("#id").val()
                window.location = ("articleServlet?action=show-article&id=" + s + "");
            })
            $(".blog-edit").click(function () {
                var s = $(this).parent().parent().parent().parent().prev("#id").val()
                window.location = ("articleServlet?action=edit-article&id=" + s + "");
            })
            $(".blog-delete").click(function () {
                var title = $(this).parent().parent().parent().parent().children(".blog-MyBlog-list-item-title").text();
                var s = $(this).parent().parent().parent().parent().prev("#id").val();
                $.MsgBox.Confirm("提示","你确定删除你的博客"+title,function (){
                    // alert(s);
                    window.location = ("articleServlet?action=delete-article&id=" + s + "");
                })
            })
        </script>
    </div>
</div>
</body>
</html>
