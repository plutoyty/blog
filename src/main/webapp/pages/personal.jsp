
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 杨pc
  Date: 2021/9/2
  Time: 20:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <title>个人主页</title>
    <base href="http://localhost:8080/bolg/">
    <link type="text/css" rel="stylesheet" href="css/personal.css">
    <script type="text/javascript" src="jquery-3.4.1/jquery-3.4.1.js"></script>
    <script type="text/javascript" src="jquery-3.4.1/alert.js"></script>
    <script type="text/javascript">
        window.onload = function () {
            // 编辑数据
            $(".data").click(function () {
                $(".data-1010").hide();
                $("#ed").show();
            })
            //个人资料
            $("#person-all").click(function () {
                $(".data-1010").show();
                $(".container-3").show();
                $("#Password").hide();
                $("#ed").hide();
                return false;
            })
            //取消按钮
            $("#cancel").click(function () {
                $("#ed").hide();
                $(".data-1010").show();
                return false;
            })
            //修改密码
            $("#edpassword").click(function () {
                $("#Password").show();
                $(".container-3").hide();
                return false;
            })
            var flag = false;
            $(".number1,.number2,.number3").blur(function () {
                var name = $(".number").val();
                var tes = /^(?=.*\d)(?=.*[a-zA-Z])[\da-zA-Z~!@#$%^&*]{6,18}$/;
                if (tes.test(name)) {
                    flag = false;
                    $("#error").text("密码格式错误，必须包含数字且6-18位");
                } else {
                    $("#error").text(" ");
                    flag = true;
                }
                return false;
            })

            $("#newPassword2").blur(function () {
                var namr1 = $("#newPassword2").val();
                var name2 = $("#newPassword1").val();
                if (name2 != namr1) {
                    $("#error").text("密码不一致");
                    flag = false
                } else {
                    $("#error").text(" ");
                    flag = true;
                }
                return false;
            })
            $(".save").click(function () {
                if (!flag)
                    return false;
            })
        }
    </script>
</head>
<body>
<%@ include file="/pages/header.jsp" %>
<div class="main">
    <div class="container">

        <div id="list" onclick="this">
            <table class="tb" id="tb1">
                <tr>
                    <td><a href="index.jsp" title="首页">首页</a></td>
                </tr>
                <tr>
                    <td><a id="person-all" href="javascript:void(0);" onclick="this"><span
                            class="glyphicon glyphicon-home"></span>
                        个人资料 </a></td>
                </tr>
                <tr>
                    <td><a id="edpassword" onclick="this" href="javascript:void(0);"><span
                            class="glyphicon glyphicon-list"></span>
                        修改密码</a></td>
                </tr>
                <tr>
                    <td><a href="pages/Mblog.jsp"><span class="glyphicon glyphicon-tags"></span>
                        内容管理</a></td>
                </tr>
                <tr>
                    <td class="active"><a href="AxisServlet"><span class="glyphicon glyphicon-book"></span>
                        收藏</a></td>
                </tr>
                <tr>
                    <td><a href="about.html"><span class="glyphicon glyphicon-user"></span>
                        关于</a></td>
                </tr>
            </table>
        </div>
        <!-- list -->
    </div>

    <div class="container-2">
        <div class="container-3">
            <div class="div-head">
                <div class="c_center" id="person_info">
                    <div class="aui-info" style="position: absolute">
                        <img src="${sessionScope.user.head}" id="img-txz" style="width: 100px; height: 100px;"
                             class="img-circle"/>
                        <input type="file" accept="image/*" id="file-txz" name="myfile"
                               onchange="upload('#file-txz', '#img-txz');"
                               class="fileInput"
                               value=""/>
                        <button disabled="disabled" class="submit">确定</button>
                    </div>
<%--                    头像修改--%>
                    <script>
                        $(".submit").click(function () {
                            var formData = new FormData();
                            formData.append('file', $('#file-txz')[0].files[0]);  //添加图片信息的参数
                            formData.append('sizeid', 123);  //添加其他参数
                            $.ajax({
                                url: "upHeadServlet",
                                type: 'POST',
                                cache: false, //上传文件不需要缓存
                                data: formData,
                                processData: false, // 告诉jQuery不要去处理发送的数据
                                contentType: false, // 告诉jQuery不要去设置Content-Type请求头
                                success: function (data) {
                                    // var rs  = eval("("+data+")");
                                    $.MsgBox.Alert("提示","设置成功")
                                },
                                error: function (data) {
                                    // alert("no")
                                }
                            })
                        })
                        var upload = function (c, d) {
                            "use strict";
                            $(".aui-info .submit").removeAttr("disabled");
                            var $c = document.querySelector(c),
                                $d = document.querySelector(d),
                                file = $c.files[0],
                                reader = new FileReader();
                            reader.readAsDataURL(file);
                            reader.onload = function (e) {
                                $d.setAttribute("src", e.target.result);
                            };
                        };
                    </script>
                </div>
            </div>
            <div class="data">
                <ul class="data-1010">
                    <li class="data-1011">
                        <div class="data-1012">昵&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;称:</div>
                        <div class="data-1013">${sessionScope.user.name}</div>
                    </li>
                    <br><br>
                    <li class="data-1011">
                        <div class="data-1012">邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;箱:</div>
                        <div class="data-1013">${sessionScope.user.email}</div>
                    </li>
                    <br><br>
                    <li class="data-1011">
                        <div class="data-1012">性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别:</div>
                        <div class="data-1013">${sessionScope.user.sex}</div>
                    </li>
                    <br><br>
                    <li class="data-1011">
                        <div class="data-1012">生&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;日:</div>
                        <div class="data-1013">${sessionScope.user.birth}</div>
                    </li>
                    <br><br>
                    <li class="data-1011">
                        <div class="data-1012">个人简介:</div>
                        <div class="data-1013">${sessionScope.user.brief}</div>
                    </li>
                    <br><br>
                </ul>
                <%--                更改资料表单--%>
                <form action="userServlet" method="get">
                    <input hidden="hidden" name="action" value="editData">
                    <input hidden="hidden" name="id" value="${sessionScope.user.id}">
                    <input hidden="hidden" name="head" value="${sessionScope.user.head}">
                    <ul style="display: none" id="ed">
                        <li>
                            <div>用户昵称&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
                            <br>
                            <input id="name" name="name" maxlength="10" title="最长10个字符"
                                   value="${sessionScope.user.name}">
                        </li>
                        <br><br>
                        <li>
                            <div>邮箱&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
                            <br>
                            <input name="email" disabled="disabled" id="email" value="${sessionScope.user.email}">
                        </li>
                        <br><br>
                        <li>
                            <div>性别&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
                            <br>
                            <input name="sex" id="sex" value="${sessionScope.user.sex}">
                        </li>
                        <br><br>
                        <li>
                            <div> 生日&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
                            <br>
                            <input name="birth" type="date" value=${sessionScope.user.birth}  max=""/>
                        </li>
                        <br><br>
                        <li>
                            <div>个人简介&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
                            <br>
                            <textarea name="brief" maxlength="300" placeholder="你很懒，还没有添加简介"
                                      id="brief">${sessionScope.user.brief}</textarea>
                        </li>
                        <br>
                        <div class="bn-div">
                            <button class="bn-1" type="button" id="cancel" onclick="this">取消</button>
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <button class="bn-1" type="submit" id="save">保&nbsp;存</button>
                        </div>
                    </ul>
                </form>
            </div>
        </div>
        <div id="Password" style="display: none">
            <form class="edPassword" action="userServlet" method="post">
                <div class="edPassword">
                    <input hidden="hidden" name="email" value="${sessionScope.user.email}">
                    <input hidden="hidden" name="action" value="edPassword">
                    <label class="label1">旧&nbsp;&nbsp;密&nbsp;码:&nbsp;&nbsp;&nbsp;&nbsp; </label>
                    <input name="oldPassword" class="number1" onblur="this" type="password"
                           placeholder="6-18位字母和数字组合"><br><br>
                    <label class="label2">密&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;码:&nbsp;&nbsp;&nbsp;&nbsp; </label>
                    <input name="password" id="newPassword1" class="number2" onblur="this" type="password"
                           placeholder="新密码"><br><br>
                    <label class="label3">确认密码: &nbsp;&nbsp;&nbsp;</label>
                    <input name="newPassword" id="newPassword2" type="password" onblur="this" class="number3"
                           placeholder="确认密码"><br><br><br><br>
                    <span id="error">${requestScope.msg}</span>
                    <button class="save" type="submit">确 &nbsp;认</button>
                </div>
            </form>
        </div>
    </div>
</div>
<br/>
</body>
</html>
