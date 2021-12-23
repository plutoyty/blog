<%@ page import="bean.User" %><%--
  Created by IntelliJ IDEA.
  User: 杨pc
  Date: 2021/9/24
  Time: 2:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%
        User user = (User) session.getAttribute("userData");
        if (user != null) {
            pageContext.setAttribute("user", user);
        } else {
            pageContext.setAttribute("user", null);
        }
    %>
    <title>${pageScope.user.name}的个人资料</title>
    <base href="http://localhost:8080/bolg/">
    <link type="text/css" rel="stylesheet" href="css/editData.css">
    <script type="text/javascript" src="jquery-3.4.1/jquery-3.4.1.js"></script>
    <script type="text/javascript" src="jquery-3.4.1/alert.js"></script>
</head>
<body>
<div class="main-father">
    <div class="user-container">
        <form class="from-user">
            <input value="${pageScope.user.id}" hidden="hidden" name="id">
        <ul id="ed">
            <li>
                <div>用户昵称&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
                <br>
                <input id="name" name="name" maxlength="10" title="最长10个字符" value="${pageScope.user.name}">
            </li>
            <br><br>
            <li>
                <div>邮箱&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
                <br>
                <input name="email" disabled="disabled" id="email" value="${pageScope.user.email}">
            </li>
            <br><br>
            <li>
                <div>性别&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
                <br>
                <input name="sex" id="sex" value="${pageScope.user.sex}">
            </li>
            <br><br>
            <li>
                <div> 生日&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
                <br>
                <input name="birth" type="date" value=${pageScope.user.birth}  max=""/>
            </li>
            <br><br>
            <li>
                <div>个人简介&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
                <br>
                <textarea name="brief" maxlength="300" placeholder="你很懒，还没有添加简介"
                          id="brief">${pageScope.user.brief}</textarea>
            </li>
            <br>
            <div class="bn-div">
                <button class="bn-1" type="button" id="cancel" onclick="this">取消</button>
                <button class="bn-1" type="button" id="save" onclick="this">保&nbsp;存</button>
            </div>
        </ul>
        </form>
        <script>
            $(function (){
                $("#cancel").click(function (){
                    var id = ${pageScope.user.id};
                    window.location.href = "userServlet?action=cancleEditUser&id=" + id + "";
                })
                $("#save").click(function (){
                    // alert($(".from-user").serialize())
                    window.location.href = "userServlet?action=editUser&" + $(".from-user").serialize() + "";
                })
            })
        </script>
    </div>
</div>
</body>
</html>
