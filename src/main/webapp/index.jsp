<%@ page import="bean.Article" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="service.ArticleService" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <%
        List<Article> list = ArticleService.getRanking();
        if (list!=null){
            pageContext.setAttribute("article_rank",list);
        }else {
            pageContext.setAttribute("article_rank",null);
        }
    %>
    <title>MyBlog</title>
    <base href="http://localhost:8080/bolg/">
    <link href="css/index.css" rel="stylesheet"/>
</head>
<body>
<%@ include file="pages/header.jsp" %>
<div class="main-father">
    <div class="index-position">
        <div class="index-left">
            <div class="news"><h2>头条:</h2>
                <div class="news-first"><br>iPhone将被迫换成USB-C接口？</div>
            </div>
        </div>
        <div class="index-mid">
            <h2>每日一读:</h2>
            <div class="today-recommend"><br>
                <a href="javascript:void(0);" class="recommend">${applicationScope.today_show.title}</a>
            </div>
        </div>
        <div class="index-right">
            <div class="news"><h2>排行榜:</h2>
                <div class="index-left-item"><br>
                    <c:forEach items="${pageScope.article_rank}" var="item" varStatus="staus">
                        <input value="${item.id}" hidden="hidden" name="id" class="id">
                        <a class="index-left-item-s">${staus.index+1}: ${item.title}</a><br>
                    </c:forEach>
                </div>
                <script>
                    $(".index-left-item-s").click(function (){
                        var s = $(this).prev(".id").val();

                        window.location.href = "articleServlet?action=show-article&id="+s;
                    })
                </script>
            </div>
        </div>
    </div>
</div>
<script>
    $(function () {
        $(".recommend").click(function () {
            if (${applicationScope.today_show ne null})
                window.location.href = "articleServlet?action=show-article&id=" + '${applicationScope.today_show.id}';
        })
        $(".news-first").click(function (){
            window.location.href = "articleServlet?action=show-article&id=43";
        })

    })
</script>
</body>
</html>