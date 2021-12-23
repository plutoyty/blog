<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>用户登录</title>
    <base href="http://localhost:8080/bolg/">
    <link type="text/css" rel="stylesheet" href="css/login.css">
    <script type="text/javascript" src="jquery-3.4.1/jquery-3.4.1.js"></script>
    <script type="text/javascript">
        window.onload = function () {
            var flag = false;
            $("#email").blur(function () {
                var name = $("#email").val();
                var tes = /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
                console.log("email");
                if (!tes.test(name)) {
                    $("#emailError").text("邮箱格式错误");
                    flag = false;
                } else {
                    $("#emailError").text("");
                    flag = true;
                }
                return false;
            });
            $("#password").blur(function () {
                var name = $("#password").val();
                var tes = /^(?=.*\d)(?=.*[a-zA-Z])[\da-zA-Z~!@#$%^&*]{6,18}$/;
                if (!tes.test(name)) {
                    flag = false;
                    $("#passwordError").text("密码格式错误，必须包含数字且6-18位");
                } else {
                    flag = true;
                    $("#passwordError").text("");
                }
                return false;
            });
            $("#submit").click(function () {
                if (!flag) {
                    return false;
                }
            })
        }
    </script>
</head>
<body>
<div class="container">
    <form action="loginServlet" method="post">
        <input hidden="hidden" name="action" value="login">
        <h1 align="center">
            <a class="m2" title="首页" href="index.jsp">MyBlog</a>
        </h1>
        <img src="imags/user1.png" class="imgs">
        <div class="neizhi">
            <input
                    type="text"
                    id="email"
                    class="form-control"
                    placeholder="邮箱"
                    name="email"
                    required
                    value="${cookie.username.value}">
            <span id="emailError"></span>
            <br>
            <input
                    type="password"
                    id="password"
                    class="form-control"
                    placeholder="密码"
                    name="password"
                    required>
            <span id="passwordError"></span>
            <br>
            <span id="msgError">${requestScope.msg}</span>
            <a href="pages/forgot.jsp" class="min1" id="forgot">忘记密码</a>
            <button
                    class="btn btn-lg btn-primary btn-block"
                    type="submit"
                    id="submit">登 &nbsp;录
            </button>
        </div>
        <a href="pages/register.jsp" class="min1" id="register">没有账号，立即注册</a>
    </form>
</div>
</body>
</html>