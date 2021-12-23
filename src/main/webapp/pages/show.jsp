<%@ page import="bean.Article" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%--
  Created by IntelliJ IDEA.
  User: 杨pc
  Date: 2021/9/12
  Time: 22:12
  To change this template use File | Settings | File Templates.
--%>
<html>
<%
    pageContext.setAttribute("show", session.getAttribute("show"));
    pageContext.setAttribute("show_user", session.getAttribute("show_user"));
    pageContext.setAttribute("show_comment", session.getAttribute("show_comment"));
    pageContext.setAttribute("fan", session.getAttribute("fan"));
%>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>${pageScope.show.title}</title>
    <!-- 这个文件用来转换 Markdown 到Html 显示 -->
    <base href="http://localhost:8080/bolg/">
    <!-- preview的css -->
    <link rel="stylesheet" href="editormd10010/css/style.css"/>
    <link rel="stylesheet" href="editormd10010/css/editormd.preview.css"/>
    <!-- 引入editormd相关 -->
    <script src="editormd10010/js/zepto.min.js"></script>
    <script src="editormd/editormd.js"></script>
    <script type="text/javascript" src="jquery-3.4.1/jquery-3.4.1.js"></script>
    <script src="editormd10010/lib/marked.min.js"></script>
    <script src="editormd10010/lib/prettify.min.js"></script>
    <script src="editormd10010/lib/raphael.min.js"></script>
    <script src="editormd10010/lib/underscore.min.js"></script>
    <script src="editormd10010/lib/sequence-diagram.min.js"></script>
    <script src="editormd10010/lib/flowchart.min.js"></script>
    <script src="editormd10010/lib/jquery.flowchart.min.js"></script>
    <link type="text/css" rel="stylesheet" href="css/show.css"/>
</head>
<body style="background:#eee;">
<%@ include file="/pages/header.jsp" %>
<script>
    if (${sessionScope.show_type eq "show"}){
        $.getJSON("http://localhost:8080/bolg/articleServlet", "action=addRead&id=" +${pageScope.show.id}, function (data) {

        })
    }else{

    }

</script>
<div class="main-father">
    <div class="blog-user">
        <div class="blog-user-head">
            <img src="${pageScope.show_user.head}" class="blog-user-head-img" alt="">
            <div class="blog-user-name">
                ${pageScope.show_user.name}
            </div>
        </div>
        <div id="user-use">
            <a>
                <div class="user-send"><span>发消息</span></div>
            </a>
            <a>
                <div class="user-ass"><span>关注</span></div>
            </a></div>
    </div>
    <script>
        $(".user-send").click(function (){
            if (<%=session.getAttribute("user")==null%>){
                $.MsgBox.Alert("温馨提示", "请您先登录！");
                return false;
            }
            window.location.href = "chatServlet?action=chat&id="+${pageScope.show_user.id};
        })
    </script>
    <div class="blog-body">
        <div class="blog-top">
            <div class="blog-body-title">${pageScope.show.title}</div>
            <div class="blog-body-info">
                <c:if test="${pageScope.show.original==true}">
                    <div class="blog-body-bool">原创</div>
                </c:if>
                <c:if test="${pageScope.show.original==false}">
                    <div class="blog-body-bool" style="color: #00d95a;background-color: #dbdcee;">转载</div>
                </c:if>
                <div class="blog-body-info-top">
                    <div class="blog-author-name">${pageScope.show_user.name}</div>
                    <div class="blog-body-time">${pageScope.show.time}</div>
                    <div class="blog-body-read"><img class="blog-footer" src="imags/look.png"/>
                        <span class="comment-text">${pageScope.show.read}阅读</span></div>
                </div>
                <div class="blog-body-info-bottom">
                    <div class="blog-body-info-sort"><span>分类专栏:&nbsp;&nbsp; &nbsp;</span>
                        <c:if test="${pageScope.show.sort!=null}">
                            <div class="blog-body-info-sort-10">${pageScope.show.sort}</div>
                        </c:if>
                    </div>
                    <div class="blog-body-info-tags"><span>文章标签: </span>
                        <%
                            Article s = (Article) pageContext.getAttribute("show");
                            String a = null;
                            if (s != null) a = s.getTag();
                            if (s == null) {
                                pageContext.setAttribute("tags", null);
                            } else if (a.equals("{}")) {
                                pageContext.setAttribute("tags", null);
                            } else {
                                List<String> list = new ArrayList<>();
                                for (int i = 0; i < a.length(); i++) {
                                    if (a.charAt(i) == '{') {
                                        i++;
                                        String w = "";
                                        int j = i;
                                        for (; j < a.length(); j++) {
                                            if (a.charAt(j) != '}')
                                                w += a.charAt(j);
                                            else break;
                                        }
                                        list.add(w);
                                        i = j;
                                    }
                                }
                                pageContext.setAttribute("tags", list);
                                System.out.println(list.size());
                            }

                        %>
                        <c:forEach items="${pageScope.tags}" var="item">
                            <c:if test="${item!=null}">
                                <div class="blog-body-info-tags-10">
                                        ${item}
                                </div>
                            </c:if>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>
        <div id="mdView">
            <textarea id="article_content">${pageScope.show.content}</textarea>
        </div>
        <script>
            $(function () {
                if (${sessionScope.user.id == pageScope.show_user.id}) {
                    $(".blog-body-bottom-att").css({display: "none"})
                    $("#user-use").css({display: "none"})
                }

            })
        </script>
        <div class="blog-body-bottom">
            <div class="blog-body-bottom-user">
                <div class="blog-body-bottom-user-01">
                    <img class="blog-body-bottom-head" src="${pageScope.show_user.head}" alt="个人头像">
                    <span class="blog-body-bottom-user-name">${pageScope.show_user.name}</span>
                    <div class="blog-body-bottom-att">
                        <a class="blog-bogy-bottom-concern">关注</a>
                    </div>
                </div>
                <script>
                    $(".blog-bogy-bottom-concern").click(function () {
                        var s = $(".blog-bogy-bottom-concern").text();
                        if ($(this).text() == "关注") {
                            $(this).text("已关注");
                            $(".user-ass").text("已关注")
                            $.getJSON("http://localhost:8080/bolg/articleServlet", "action=concern&userid=" + '${pageScope.show_user.id}' + "&fan=" +${sessionScope.user.id}, function (data) {

                            })
                        } else {
                            $(".user-ass").text("关注")
                            $(this).text("关注");
                            $.getJSON("http://localhost:8080/bolg/articleServlet", "action=cancleconcern&userid=" + '${pageScope.show_user.id}' + "&fan=" +${sessionScope.user.id}, function (data) {

                            })
                        }
                    })
                    $(".user-ass").click(function () {
                        if ($(this).text() == "关注") {
                            $(this).text("已关注");
                            $(".blog-bogy-bottom-concern").text("已关注")
                            $.getJSON("http://localhost:8080/bolg/articleServlet", "action=concern&userid=" + '${pageScope.show_user.id}' + "&fan=" +${sessionScope.user.id}, function (data) {

                            })
                        } else {
                            $(".blog-bogy-bottom-concern").text("关注")
                            $(this).text("关注");
                            $.getJSON("http://localhost:8080/bolg/articleServlet", "action=cancleconcern&userid=" + '${pageScope.show_user.id}' + "&fan=" +${sessionScope.user.id}, function (data) {

                            })
                        }
                    })
                </script>
                <div class="toolbox-middle">
                    <ul class="toolbox-list">
                        <li class="is-like" id="is-like">
                            <a class="tool-item-href">
                                <img style="display:none;" id="is-like-imgactive-animation-like"
                                     class="animation-dom"
                                     src="imags/点赞.png" alt="">
                                <img class="isdefault" style="display:block" id="is-like-img"
                                     src="imags/点赞1.png" alt="">
                                <sqan id="spanCount" class="count "
                                      style="color: rgb(153, 154, 170);">${pageScope.show.like}</sqan>
                            </a>
                        </li>
                        <li class="is-collection">
                            <a class="tool-item-href" href="javascript:;"
                               data-report-click="{&quot;mod&quot;:&quot;popu_824&quot;,&quot;spm&quot;:&quot;1001.2101.3001.4130&quot;,&quot;ab&quot;:&quot;new&quot;}">
                                <img style="display:none" id="is-collection-img-collection"
                                     class="animation-dom"
                                     src="imags/收藏1.png"
                                     alt="">
                                <img class="isdefault" id="is-collection-img" style="display:block"
                                     src="imags/收藏.png" alt="">
                                <span class="count get-collection" id="get-collection"
                                      style="color: rgb(153, 154, 170);">${pageScope.show.collect}</span>
                            </a>
                        </li>
                        <script>
                            <c:forEach items="${sessionScope.collect}" var="item">
                            <c:if test="${item==pageScope.show.id}">
                            $("#is-collection-img-collection").css({"display": "block"});
                            $("#is-collection-img").css({"display": "none"});
                            </c:if>
                            </c:forEach>
                        </script>
                        <script>
                            $(function () {
                                $("#is-like-img").click(function () {
                                    $("#is-like-imgactive-animation-like").css({"display": "block"});
                                    $("#is-like-img").css({"display": "none"});
                                    $.getJSON("http://localhost:8080/bolg/articleServlet", "action=like&id=" + "${pageScope.show.id}" + "&yes=" +${pageScope.show.like}, function (data) {
                                        <%--alert("action=like&id="+"${pageScope.show.id}"+"&"+${pageScope.show.like})--%>
                                        $("#spanCount").text(data.qaq);
                                    })
                                })
                                $("#is-like-imgactive-animation-like").click(function () {
                                    $("#is-like-imgactive-animation-like").css({"display": "none"});
                                    $("#is-like-img").css({"display": "block"});
                                    $.getJSON("http://localhost:8080/bolg/articleServlet", "action=cancelLike&id=" + "${pageScope.show.id}" + "&yes=" + "${pageScope.show.like}", function (data) {
                                        $("#spanCount").text(data.qaq);
                                    })
                                })
                                $("#is-collection-img-collection").click(function () {
                                    $("#is-collection-img-collection").css({"display": "none"});
                                    $("#is-collection-img").css({"display": "block"});
                                    $.getJSON("http://localhost:8080/bolg/articleServlet", "action=cancelCollect&id=" + "${pageScope.show.id}" + "&yes=" + "${pageScope.show.collect}", function (data) {
                                        // alert(data.qaq)
                                        $("#get-collection").text(data.qaq);
                                    })
                                })
                                $("#is-collection-img").click(function () {
                                    $("#is-collection-img-collection").css({"display": "block"});
                                    $("#is-collection-img").css({"display": "none"});
                                    $.getJSON("http://localhost:8080/bolg/articleServlet", "action=collect&id=" + "${pageScope.show.id}" + "&yes=" + "${pageScope.show.collect}", function (data) {
                                        // alert(data.qaq)
                                        $("#get-collection").text(data.qaq);
                                    })
                                })
                            })
                        </script>
                    </ul>
                </div>
            </div>
        </div>
        <div class="blog-comment">
            <div class="blog-comment-write">
                <div class="blog-comment-write-10">
                    <img src="${pageScope.show_user.head}" alt="">&nbsp;&nbsp;
                    <textarea id="comment" placeholder="发表神妙评论..."></textarea>
                    <button>发表评论</button>
                </div>
            </div>
            <br class="ddd">
            <c:forEach items="${pageScope.show_comment}" var="item">
                <div class="blog-comment-item">
                    <div><img src="${item.user.head}" alt=""/></div>
                    <div class="blog-comment-item-name">${item.user.name}:</div>
                    <div class="blog-comment-item-content">${item.comment}</div>
                </div>
            </c:forEach>
        </div>
        <script>
            $(function () {
                $(".blog-comment-write-10 button").click(function () {
                    let s = $("#comment").val();
                    if (s.trim()=="")return false;
                    $.getJSON("http://localhost:8080/bolg/articleServlet", "action=sendComment&vlu=" + s + "&id=" +${pageScope.show.id}, function (data) {
                        $("#comment").val("");
                        var qq = "<div class='blog-comment-item'>" +
                            "<div><img src='"+data.user.head+"' alt=''/>" + "</div>" +
                            "<div class='blog-comment-item-name'>" + data.user.name + ":" + "</div>" +
                            "<div class='blog-comment-item-content'>" + data.com + "</div>" +
                            "</div>";
                        $(qq).insertAfter(".ddd");
                    })
                })
            })
        </script>
    </div>
</div>
<br/>
<script>
    <c:set var="flag" value="0"/>
    <c:forEach items="${pageScope.fan}" varStatus="staus" var="item">
    <c:if test="${flag==0}">
    <c:if test="${item == pageScope.show_user.id}">
    $(".blog-bogy-bottom-concern").text("已关注")
    $(".user-ass").text("已关注")
    </c:if>
    </c:if>
    </c:forEach>
</script>
<script type="text/javascript">
    $(function mdToHtml() {
        //获取要显示的内容
        var content = $("#article_content").text();
        editormd.markdownToHTML("mdView", {
            // width: "50%",
            // height: "5%",
            htmlDecode: "style,script,iframe",
            emoji: true,
            taskList: true,
            tex: true, // 默认不解析
            flowChart: true, // 默认不解析
            sequenceDiagram: true, // 默认不解析
        });
    });
</script>
</body>
</html>