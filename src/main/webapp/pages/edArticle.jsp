<%@ page import="bean.Article" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 杨pc
  Date: 2021/9/6
  Time: 1:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <%
        Article article = (Article) session.getAttribute("edit");
        if (article == null) {
            pageContext.setAttribute("flag", "save-article");
        } else {
            pageContext.setAttribute("edit", session.getAttribute("edit"));
            pageContext.setAttribute("flag", "edit");
        }
    %>
    <title>编辑博客</title>
    <base href="http://localhost:8080/bolg/">
    <script type="text/javascript" src="jquery-3.4.1/jquery-3.4.1.js"></script>
    <link rel="stylesheet" href="editormd/css/editormd.min.css"/>
    <link rel="stylesheet" href="editormd/css/editormd.css"/>
    <script src="jquery-3.4.1/jquery-3.4.1.min.js"></script>
    <script src="editormd/editormd.min.js"></script>
    <link type="text/css" rel="stylesheet" href="css/edArticle.css">
    <script type="text/javascript" src="jquery-3.4.1/alert.js"></script>
    <script type="text/javascript">
        var testEditor;
        $(function () {
            testEditor = editormd("editormd", {
                width: "98%",
                height: "95%",
                syncScrolling: "single",
                path: "editormd/lib/", //依赖lib文件夹路径
                emoji: true,
                taskList: true,
                tocm: true,
                imageUpload: true,
                imageFormats: ["jpg", "jpeg", "gif", "png", "bmp", "webp"],
                imageUploadURL: "imageUpload" //上传图片控制器Mapping
            });
        });
    </script>
</head>

<body>
<%
    Article s = (Article) pageContext.getAttribute("edit");
    if (s == null) {
        pageContext.setAttribute("tags", null);
    } else {
        String a = s.getTag();
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
    }
%>
<form action="articleServlet" method="post">
    <input id="num" name="number" value="1" hidden="hidden">
    <input id="action" name="action" value="" hidden="hidden">
    <input name="id" value="${pageScope.edit.id}" hidden="hidden">
    <div id="head-top">
        <div id="title"><input class="title" name="title" maxlength="100" placeholder="文章标题(小于100个字符)" type="text"
                               value="${pageScope.edit.title}"/>
        </div>
        <div class="title-right">
            <button class="save-drafts">保存草稿</button>
            <button id="save" class="save-article" onclick="this">保存文章</button>
            <script type="text/javascript">
                $(function () {
                    var num = 1 +${pageScope.tags.size()-1};
                    $(".save-drafts").click(function () {
                        <%--alert("tag-${requestScope.number}");--%>
                        $("#action").val("save-drafts");
                    })
                    $(".save-article").click(function () {
                        $("#action").val("${pageScope.flag}");
                        if ($(".title").val() == "") {
                            $.MsgBox.Alert("提示", "标题不能为空");
                            return false;
                        }
                    })
                    $("#new").click(function () {
                        num++;
                        if (num < 6) {
                            $("#num").val(num);
                            var s = "tag-" + num;
                            var ad = "<input type='text' name='" + s + "' value=''>&nbsp;";
                            // alert($("#num").val());
                            $(".con-4").append(ad);
                        }
                        return false;
                    })
                    initnum = function (data) {
                        num = data;
                    }
                })
            </script>
        </div>

    </div>
    <br>
    <div class="tag">
        <div class="con-1">是否原创:</div>
        <div class="con-2">
            <input type="radio" name="original" class="radio" id="radio-1" checked="checked" value="true"><span>是</span>
            <input type="radio" name="original" class="radio" id="radio-2" value="false"><span>否</span>
            <c:if test="${pageScope.edit.original==true}">
                <script>
                    $("input[id='radio-1']").attr("checked", true);
                </script>
            </c:if>
            <c:if test="${pageScope.edit.original==false}">
                <script>
                    $("input[id='radio-2']").attr("checked", true);
                </script>
            </c:if>
        </div>
    </div>
    <div class="tags">
        <div class="con-3">
            <div class="text-1">标签:</div>
            <div class="con-4">
                <button id="new" onclick="this">添加标签</button>
                <c:if test="${pageScope.flag=='save-article'}">
                    <input type="text" name="tag-1" value="">
                </c:if>
                <c:if test="${pageScope.flag=='edit'}">
                    <c:forEach items="${pageScope.tags}" var="item" varStatus="status">
                        <input value="${item}" name="tag-${status.index+1}">
                    </c:forEach>
                </c:if>
            </div>
        </div>
    </div>

    <div class="sort">
        <div class="text-sort">分类专栏:</div>
        <div class="sort-in">
        <span class="cls_select_span">
        <select id="id_select" class="cls_select" onChange="selectOnChangeFunc()">
            <option value="" class="cls_option_defined">---自定义---</option>
        </select>

        </span>
            <span class="cls_input_span">
        <input type="text" name="sort" id="id_input" class="cls_input" placeholder="可选择也可自定义输入"
               value="${pageScope.edit.sort}">
        </span>
        </div>
        <script>
            function selectOnChangeFunc() {
                document.getElementById('id_input').value = document.getElementById('id_select').options[document.getElementById('id_select').selectedIndex].value;
            }
        </script>
    </div>
    <div id="editormd">
        <textarea name="content" class="editormd" style="display: none">${pageScope.edit.content}</textarea>
    </div>
</form>
</body>
</html>
