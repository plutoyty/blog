<%--
  Created by IntelliJ IDEA.
  User: 杨pc
  Date: 2021/9/10
  Time: 22:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <base href="http://localhost:8080/bolg/">
    <script type="text/javascript" src="jquery-3.4.1/jquery-3.4.1.js"></script>
    <script type="text/javascript" src="jquery-3.4.1/jquery-3.4.1.min.js"></script>
    <script type="text/javascript" src="jquery-3.4.1/jcrop.js"></script>
    <link type="text/css" src="jquery-3.4.1/jcrop.css"/>
    <script src="jquery-3.4.1/jquery.Jcrop.min.js"></script>
    <link rel="stylesheet" href="jquery-3.4.1/jquery.Jcrop.css" type="text/css"/>
    <script type="text/javascript" src="js/index.js"></script>
    <link rel="stylesheet" href="js/index.css"/>
</head>
<body>
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

<script>

    let paging = new Paging({
        total: 1000,
    })

    Paging.prototype.toPage = function(page) {
        let PageTotalNum = this.getPageTotalNum();
        if(page<1) {
            page=1;
        }
        if(page>PageTotalNum){
            page = PageTotalNum;
        }
        this.options.current = page;
        console.log(page) //获取当前页码
        this.show()
    }
</script>
</body>
</html>
