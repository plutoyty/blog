<%@ page import="bean.User" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 杨pc
  Date: 2021/9/10
  Time: 22:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <%
        User user = (User) session.getAttribute("showUser");
        if (user != null) {
            pageContext.setAttribute("showArticles", session.getAttribute("showArticles"));
            pageContext.setAttribute("user", user);
        } else {
            pageContext.setAttribute("showArticles", null);
            pageContext.setAttribute("user", null);
        }
    %>
    <title>${pageScope.user.name}的博客</title>
    <base href="http://localhost:8080/bolg/">
    <script type="text/javascript" src="jquery-3.4.1/jquery-3.4.1.js"></script>
    <script type="text/javascript" src="jquery-3.4.1/jquery-3.4.1.min.js"></script>
    <link type="text/css" rel="stylesheet" href="css/blog.css"/>
    <script type="text/javascript" src="js/index.js"></script>
    <link rel="stylesheet" href="js/index.css"/>
</head>
<body>
<%@ include file="/pages/header.jsp" %>
<div class="container">
    <div class="container-top">
        <div class="imags-head">
            <img id="head" src="${pageScope.user.head}" style="height: 96px; width: 96px"/>
        </div>
        <div class="name">${pageScope.user.name}</div>
        <div class="head-md">${pageScope.user.brief}</div>
    </div>
    <div class="container-left">
        <div></div>
    </div>
    <div class="container-right">
        <div class="blog-sort">
        </div>
        <div class="blog-all">
            <c:forEach items="${pageScope.showArticles}" var="item">
                <div class="blog-min" onclick="this">
                    <input name="action" value="show-article" hidden="hidden">
                    <input class="id" name="id" value="${item.id}" hidden="hidden">
                    <div class="title">
                        <input name="id" value="" hidden="hidden">
                        <a class="blog-title">${item.title}</a>
                    </div>
                    <div class="blog-content">
                            ${item.content}
                    </div>
                    <div class="flog-foot1">
                        <c:if test="${item.original==true}">
                            <div class="blog-bool">原创</div>
                        </c:if>
                        <c:if test="${item.original==false}">
                            <div class="blog-bool" style="color: #00d95a;background-color: #dbdcee;">转载</div>
                        </c:if>
                        <div class="blog-foot10"><img class="blog-footer" src="imags/comment.png"/>
                            <span class="comment-text">0评论</span></div>
                        <div class="blog-foot10"><img class="blog-footer" src="imags/read.png"/>
                            <span class="comment-text">${item.read}阅读</span></div>
                        <div class="blog-foot10"><img class="blog-footer" src="imags/like.png"/>
                            <span class="comment-text">${item.like}点赞</span></div>
                    </div>
                </div>
                <br>
            </c:forEach>
        </div>
        <script type="text/javascript">
            $(function () {
                $(document).on("click touchstart", '.blog-min', function () {
                    var s = $(this).children(".id").val();
                    // alert($(this).children(".id").val())
                    window.location = ("articleServlet?action=show-article&id=" + s + "");
                })
            })
        </script>
        <div class="paging">
            <div class="first ">首页</div>
            <div class="prev ">上一页</div>
            <div class="pagingDiv active">1</div>
            <div class="pagingDiv ">2</div>
            <div class="pagingDiv ">3</div>
            <div class="pagingDiv ">4</div>
            <div class="pagingDiv ">5</div>
            <div class="next ">下一页</div>
            <div class="last ">尾页</div>
            <span><i>1</i> /<i>10</i></span>
        </div>
    </div>
</div>

<script>

    let paging = new Paging({
        total: ${sessionScope.showArticleSize},
    })

    Paging.prototype.toPage = function (page) {
        let PageTotalNum = this.getPageTotalNum();
        if (page < 1) {
            page = 1;
        }
        if (page > PageTotalNum) {
            page = PageTotalNum;
        }
        this.options.current = page;
        // alert(page)
        console.log(page) //获取当前页码
        this.show()
        $.getJSON("http://localhost:8080/bolg/articleServlet", "action=Paging&page=" + page + "&email=" + '${pageScope.user.email}', function (data) {
            $(".blog-all").empty();
            // alert(data.article)
            $.each(data.article, function (index, item) {
                var s = "<div class='blog-min' onclick='this'>" +
                    "<input name='action' value='show-article' hidden='hidden'>" +
                    "<input class='id' name='id' value=" + item.id + " hidden='hidden'>" +
                    "<div class='title'>" +
                    "<input name='id' value='' hidden='hidden'>" +
                    "<a class='blog-title'>" + item.title + "</a>" + "</div>" +
                    "<div class='blog-content'>" + item.content + "</div>" + "<div class='flog-foot1'>"
                var w;
                if (item.original == true) {
                    w = "<div class='blog-bool'>" + "原创" + "</div>";

                } else {
                    w = "<div class='blog-bool' style='color: #00d95a;background-color: #dbdcee;'>" + "转载" + "</div>";
                }
                var q = "<div class='blog-foot10'><img class='blog-footer' src='imags/comment.png'/>" +
                    "<span class='comment-text'>" + "0评论</span></div>" +
                    "<div class='blog-foot10'><img class='blog-footer' src='imags/read.png'/>" +
                    "<span class='comment-text'>" + item.read + "阅读</span></div>" +
                    "<div class='blog-foot10'><img class='blog-footer' src='imags/like.png'/>" +
                    "<span class='comment-text'>" + item.like + "点赞</span></div>" +
                    "</div>" +
                    "</div>" +
                    "<br>"
                // alert(s+w+q);
                $(".blog-all").append(s + w + q);
                // $(".blog-look").on("click",function (){
                //     var s = $(this).parent().parent().parent().parent().prev("#id").val()
                //     window.location = ("articleServlet?action=show-article&id=" + s + "");
                // });
                $(document).on("click touchstart", '.blog-min', function () {
                    var s = $(this).children(".id").val();
                    // alert($(this).children(".id").val())
                    window.location = ("articleServlet?action=show-article&id=" + s + "");
                })
            })
        })
    }
</script>
</body>
</html>
