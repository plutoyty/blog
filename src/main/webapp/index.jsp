<%@ page import="bean.Article" %>
<%@ page import="java.util.List" %>
<%@ page import="service.ArticleService" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>

    <%
        List<Article> list = ArticleService.getRanking();
        if (list != null) {
            pageContext.setAttribute("article_rank", list);
        } else {
            pageContext.setAttribute("article_rank", null);
        }
    %>

    <title>MyBlog</title>
    <base href="http://localhost:8080/bolg/">
    <link href="css/index.css" rel="stylesheet"/>
    <script type="text/javascript" src="jquery-3.4.1/jquery-3.4.1.js"></script>

</head>

<body>
<%@ include file="pages/header.jsp" %>
<div class="toutiao">
    <div class="news"><h2>头条:</h2>
        <img src="imags/toutiao.png" style="width: 400px; height: 230px">
        <div class="toutiao-title">
            <div class="news-first"><br>Log4j 漏洞还没忙完，新的漏洞又出现了</div>
        </div>
    </div>
</div>
<div class="swiper-contione">
    <div class="swiper">
        <div class="swiper-item">
            <img src="imags/t1.jpg">
        </div>
        <div class="swiper-item">
            <img src="imags/t2.jpg">
        </div>
        <div class="swiper-item">
            <img src="imags/t3.jpg">
        </div>
        <div class="swiper-item">
            <img src="imags/t4.png">
        </div>
        <div class="swiper-item">
            <img src="imags/t5.jpg">
        </div>

    </div>
    <div class="swiper-point">
        <div class="swiper-point-item">
            <div class="point active"></div>
            <div class="point"></div>
            <div class="point"></div>
            <div class="point"></div>
            <div class="point"></div>
        </div>
    </div>
    <div class="swiper-left">
        <img src="imags/left.png">
    </div>
    <div class="swiper-right">
        <img src="imags/right.png">
    </div>
</div>
<script type="text/javascript">
    class Swiper {
        constructor() {
            this.w = $('.swiper-item').width();
            this.num = 0;
            this.len = $('.swiper .swiper-item').length - 1;
            this.timer = null;
        }

        init() {
            // alert(100);
            //设置定时器
            this.setTime();
            //滑上停止定时器
            this.hover();
            //点击指示
            this.pointClick();
            //点击左右箭头
            this.lrClick();
        }

        setTime() {
            this.timer = setInterval(() => {
                this.num++;
                if (this.num > this.len) {
                    this.num = 0;
                }
                let cssTrx = -this.num * this.w;
                $('.swiper-point-item .point').eq(this.num).addClass('active').siblings().removeClass('active');
                $('.swiper').css({transform: "translateX(" + cssTrx + "px)"})
            }, 3000)
        }

        hover() {
            $('.swiper-contione').hover(() => {
                clearInterval(this.timer)
            }, () => {
                this.setTime();
            });
        }

        pointClick() {
            let that = this;
            $('.swiper-point-item .point').click(function () {
                that.num = $(this).index();
                let cssTrx = -that.num * that.w;
                $(this).addClass('active').siblings().removeClass('active');
                $('.swiper').css({
                    transform: "translateX(" + cssTrx + "px)"
                })
            })
        }

        lrClick() {
            $('.swiper-left img').click(() => {
                // alert(1)
                this.num--;
                if (this.num < 0) {
                    this.num = this.len;
                }
                ;
                console.log(this.num)
                let cssTrx = -this.num * this.w;
                $('.swiper-point-item .point').eq(this.num).addClass('active').siblings().removeClass('active');
                $('.swiper').css({
                    transform: "translateX(" + cssTrx + "px)"
                })
            });

            $('.swiper-right img').click(() => {
                // alert(2)
                this.num++;
                if (this.num > this.len) {
                    this.num = 0;
                }
                let cssTrx = -this.num * this.w;
                $('.swiper-point-item .point').eq(this.num).addClass('active').siblings().removeClass('active');
                $('.swiper').css({
                    transform: "translateX(" + cssTrx + "px)"
                })
            })
        }
    }

    let sw = new Swiper();
    sw.init();
</script>

<div class="main-father">


    <div class="index-position">
        <%--        <div class="index-left">--%>
        <%--            <div class="news    "><h2>头条:</h2>--%>
        <%--                <div class="news-first"><br>iPhone将被迫换成USB-C接口？</div>--%>
        <%--            </div>--%>
        <%--        </div>--%>
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
                    $(".index-left-item-s").click(function () {
                        var s = $(this).prev(".id").val();

                        window.location.href = "articleServlet?action=show-article&id=" + s;
                    })
                </script>
            </div>
        </div>
    </div>
</div>
<ul class="tab-list">
    <li><a href="https://blog.csdn.net/nav/python" target="_blank"
           data-report-click="{&quot;spm&quot;:&quot;1000.2115.3001.4125&quot;,&quot;dest&quot;:&quot;https://blog.csdn.net/nav/python&quot;,&quot;extra&quot;:&quot;{\&quot;fId\&quot;:557,\&quot;fName\&quot;:\&quot;floor-nav\&quot;,\&quot;compName\&quot;:\&quot;index-nav\&quot;,\&quot;compDataId\&quot;:\&quot;index-nav\&quot;,\&quot;fTitle\&quot;:\&quot;\&quot;,\&quot;pageId\&quot;:141}&quot;}"
           data-report-query="spm=1000.2115.3001.4125">Python</a></li>
    <li><a href="https://blog.csdn.net/nav/java" target="_blank"
           data-report-click="{&quot;spm&quot;:&quot;1000.2115.3001.4125&quot;,&quot;dest&quot;:&quot;https://blog.csdn.net/nav/java&quot;,&quot;extra&quot;:&quot;{\&quot;fId\&quot;:557,\&quot;fName\&quot;:\&quot;floor-nav\&quot;,\&quot;compName\&quot;:\&quot;index-nav\&quot;,\&quot;compDataId\&quot;:\&quot;index-nav\&quot;,\&quot;fTitle\&quot;:\&quot;\&quot;,\&quot;pageId\&quot;:141}&quot;}"
           data-report-query="spm=1000.2115.3001.4125">Java</a></li>
    <li><a href="https://blog.csdn.net/nav/c" target="_blank"
           data-report-click="{&quot;spm&quot;:&quot;1000.2115.3001.4125&quot;,&quot;dest&quot;:&quot;https://blog.csdn.net/nav/c&quot;,&quot;extra&quot;:&quot;{\&quot;fId\&quot;:557,\&quot;fName\&quot;:\&quot;floor-nav\&quot;,\&quot;compName\&quot;:\&quot;index-nav\&quot;,\&quot;compDataId\&quot;:\&quot;index-nav\&quot;,\&quot;fTitle\&quot;:\&quot;\&quot;,\&quot;pageId\&quot;:141}&quot;}"
           data-report-query="spm=1000.2115.3001.4125">C/C++</a></li>
    <li><a href="https://blog.csdn.net/nav/web" target="_blank"
           data-report-click="{&quot;spm&quot;:&quot;1000.2115.3001.4125&quot;,&quot;dest&quot;:&quot;https://blog.csdn.net/nav/web&quot;,&quot;extra&quot;:&quot;{\&quot;fId\&quot;:557,\&quot;fName\&quot;:\&quot;floor-nav\&quot;,\&quot;compName\&quot;:\&quot;index-nav\&quot;,\&quot;compDataId\&quot;:\&quot;index-nav\&quot;,\&quot;fTitle\&quot;:\&quot;\&quot;,\&quot;pageId\&quot;:141}&quot;}"
           data-report-query="spm=1000.2115.3001.4125">前端</a></li>
    <li><a href="https://blog.csdn.net/nav/db" target="_blank"
           data-report-click="{&quot;spm&quot;:&quot;1000.2115.3001.4125&quot;,&quot;dest&quot;:&quot;https://blog.csdn.net/nav/db&quot;,&quot;extra&quot;:&quot;{\&quot;fId\&quot;:557,\&quot;fName\&quot;:\&quot;floor-nav\&quot;,\&quot;compName\&quot;:\&quot;index-nav\&quot;,\&quot;compDataId\&quot;:\&quot;index-nav\&quot;,\&quot;fTitle\&quot;:\&quot;\&quot;,\&quot;pageId\&quot;:141}&quot;}"
           data-report-query="spm=1000.2115.3001.4125">数据库</a></li>
    <li><a href="https://blog.csdn.net/nav/fund" target="_blank"
           data-report-click="{&quot;spm&quot;:&quot;1000.2115.3001.4125&quot;,&quot;dest&quot;:&quot;https://blog.csdn.net/nav/fund&quot;,&quot;extra&quot;:&quot;{\&quot;fId\&quot;:557,\&quot;fName\&quot;:\&quot;floor-nav\&quot;,\&quot;compName\&quot;:\&quot;index-nav\&quot;,\&quot;compDataId\&quot;:\&quot;index-nav\&quot;,\&quot;fTitle\&quot;:\&quot;\&quot;,\&quot;pageId\&quot;:141}&quot;}"
           data-report-query="spm=1000.2115.3001.4125">计算机基础</a></li>
    <li><a href="https://blog.csdn.net/nav/mobile" target="_blank"
           data-report-click="{&quot;spm&quot;:&quot;1000.2115.3001.4125&quot;,&quot;dest&quot;:&quot;https://blog.csdn.net/nav/mobile&quot;,&quot;extra&quot;:&quot;{\&quot;fId\&quot;:557,\&quot;fName\&quot;:\&quot;floor-nav\&quot;,\&quot;compName\&quot;:\&quot;index-nav\&quot;,\&quot;compDataId\&quot;:\&quot;index-nav\&quot;,\&quot;fTitle\&quot;:\&quot;\&quot;,\&quot;pageId\&quot;:141}&quot;}"
           data-report-query="spm=1000.2115.3001.4125">移动</a></li>
    <li><a href="https://blog.csdn.net/nav/ai" target="_blank"
           data-report-click="{&quot;spm&quot;:&quot;1000.2115.3001.4125&quot;,&quot;dest&quot;:&quot;https://blog.csdn.net/nav/ai&quot;,&quot;extra&quot;:&quot;{\&quot;fId\&quot;:557,\&quot;fName\&quot;:\&quot;floor-nav\&quot;,\&quot;compName\&quot;:\&quot;index-nav\&quot;,\&quot;compDataId\&quot;:\&quot;index-nav\&quot;,\&quot;fTitle\&quot;:\&quot;\&quot;,\&quot;pageId\&quot;:141}&quot;}"
           data-report-query="spm=1000.2115.3001.4125">AI</a></li>
    <li><a href="https://blog.csdn.net/nav/blockchain" target="_blank"
           data-report-click="{&quot;spm&quot;:&quot;1000.2115.3001.4125&quot;,&quot;dest&quot;:&quot;https://blog.csdn.net/nav/blockchain&quot;,&quot;extra&quot;:&quot;{\&quot;fId\&quot;:557,\&quot;fName\&quot;:\&quot;floor-nav\&quot;,\&quot;compName\&quot;:\&quot;index-nav\&quot;,\&quot;compDataId\&quot;:\&quot;index-nav\&quot;,\&quot;fTitle\&quot;:\&quot;\&quot;,\&quot;pageId\&quot;:141}&quot;}"
           data-report-query="spm=1000.2115.3001.4125">区块链</a></li>
    <li><a href="https://blog.csdn.net/nav/iot" target="_blank"
           data-report-click="{&quot;spm&quot;:&quot;1000.2115.3001.4125&quot;,&quot;dest&quot;:&quot;https://blog.csdn.net/nav/iot&quot;,&quot;extra&quot;:&quot;{\&quot;fId\&quot;:557,\&quot;fName\&quot;:\&quot;floor-nav\&quot;,\&quot;compName\&quot;:\&quot;index-nav\&quot;,\&quot;compDataId\&quot;:\&quot;index-nav\&quot;,\&quot;fTitle\&quot;:\&quot;\&quot;,\&quot;pageId\&quot;:141}&quot;}"
           data-report-query="spm=1000.2115.3001.4125">物联网</a></li>
    <li><a href="https://blog.csdn.net/nav/ops" target="_blank"
           data-report-click="{&quot;spm&quot;:&quot;1000.2115.3001.4125&quot;,&quot;dest&quot;:&quot;https://blog.csdn.net/nav/ops&quot;,&quot;extra&quot;:&quot;{\&quot;fId\&quot;:557,\&quot;fName\&quot;:\&quot;floor-nav\&quot;,\&quot;compName\&quot;:\&quot;index-nav\&quot;,\&quot;compDataId\&quot;:\&quot;index-nav\&quot;,\&quot;fTitle\&quot;:\&quot;\&quot;,\&quot;pageId\&quot;:141}&quot;}"
           data-report-query="spm=1000.2115.3001.4125">运维</a></li>
    <li><a href="https://ask.csdn.net/channel/3" target="_blank"
           data-report-click="{&quot;spm&quot;:&quot;1000.2115.3001.4125&quot;,&quot;dest&quot;:&quot;https://ask.csdn.net/channel/3&quot;,&quot;extra&quot;:&quot;{\&quot;fId\&quot;:557,\&quot;fName\&quot;:\&quot;floor-nav\&quot;,\&quot;compName\&quot;:\&quot;index-nav\&quot;,\&quot;compDataId\&quot;:\&quot;index-nav\&quot;,\&quot;fTitle\&quot;:\&quot;\&quot;,\&quot;pageId\&quot;:141}&quot;}"
           data-report-query="spm=1000.2115.3001.4125">微软技术</a></li>
    <li><a href="https://blog.csdn.net/nav/arch" target="_blank"
           data-report-click="{&quot;spm&quot;:&quot;1000.2115.3001.4125&quot;,&quot;dest&quot;:&quot;https://blog.csdn.net/nav/arch&quot;,&quot;extra&quot;:&quot;{\&quot;fId\&quot;:557,\&quot;fName\&quot;:\&quot;floor-nav\&quot;,\&quot;compName\&quot;:\&quot;index-nav\&quot;,\&quot;compDataId\&quot;:\&quot;index-nav\&quot;,\&quot;fTitle\&quot;:\&quot;\&quot;,\&quot;pageId\&quot;:141}&quot;}"
           data-report-query="spm=1000.2115.3001.4125">架构</a></li>
    <li><a href="https://blog.csdn.net/nav/game" target="_blank"
           data-report-click="{&quot;spm&quot;:&quot;1000.2115.3001.4125&quot;,&quot;dest&quot;:&quot;https://blog.csdn.net/nav/game&quot;,&quot;extra&quot;:&quot;{\&quot;fId\&quot;:557,\&quot;fName\&quot;:\&quot;floor-nav\&quot;,\&quot;compName\&quot;:\&quot;index-nav\&quot;,\&quot;compDataId\&quot;:\&quot;index-nav\&quot;,\&quot;fTitle\&quot;:\&quot;\&quot;,\&quot;pageId\&quot;:141}&quot;}"
           data-report-query="spm=1000.2115.3001.4125">游戏</a></li>
    <li><a href="https://blog.csdn.net/nav/cloud" target="_blank"
           data-report-click="{&quot;spm&quot;:&quot;1000.2115.3001.4125&quot;,&quot;dest&quot;:&quot;https://blog.csdn.net/nav/cloud&quot;,&quot;extra&quot;:&quot;{\&quot;fId\&quot;:557,\&quot;fName\&quot;:\&quot;floor-nav\&quot;,\&quot;compName\&quot;:\&quot;index-nav\&quot;,\&quot;compDataId\&quot;:\&quot;index-nav\&quot;,\&quot;fTitle\&quot;:\&quot;\&quot;,\&quot;pageId\&quot;:141}&quot;}"
           data-report-query="spm=1000.2115.3001.4125">云计算</a></li>
    <li><a href="https://blog.csdn.net/nav/career" target="_blank"
           data-report-click="{&quot;spm&quot;:&quot;1000.2115.3001.4125&quot;,&quot;dest&quot;:&quot;https://blog.csdn.net/nav/career&quot;,&quot;extra&quot;:&quot;{\&quot;fId\&quot;:557,\&quot;fName\&quot;:\&quot;floor-nav\&quot;,\&quot;compName\&quot;:\&quot;index-nav\&quot;,\&quot;compDataId\&quot;:\&quot;index-nav\&quot;,\&quot;fTitle\&quot;:\&quot;\&quot;,\&quot;pageId\&quot;:141}&quot;}"
           data-report-query="spm=1000.2115.3001.4125">程序人生</a></li>
</ul>
<script>
    $(function () {
        $(".recommend").click(function () {
            if (${applicationScope.today_show ne null})
                window.location.href = "articleServlet?action=show-article&id=" + '${applicationScope.today_show.id}';
        })
        $(".news-first").click(function () {
            window.location.href = "articleServlet?action=show-article&id=54";
        })
    })
</script>
</body>
</html>