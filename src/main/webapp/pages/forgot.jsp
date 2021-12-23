<%@ page import="service.UserService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>用户注册</title>
    <base href="http://localhost:8080/bolg/">
    <link type="text/css" rel="stylesheet" href="css/register.css">
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
            $("#repassword").blur(function () {
                var name = $("#repassword").val();
                var tes = /^(?=.*\d)(?=.*[a-zA-Z])[\da-zA-Z~!@#$%^&*]{6,18}$/;
                if (!tes.test(name)) {
                    flag = false;
                    $("#depasswordError").text("密码格式错误，必须包含数字且6-18位");
                } else {
                    flag = true;
                    $("#depasswordError").text("");
                }
                return false;
            });
            $("#submit").click(function () {
                var name = $("#repassword").val();
                var name1 = $("#password").val();
                if (name != name1) {
                    $("#passwordError").text("密码不一致");
                    $("#depasswordError").text("密码不一致");
                    return false;
                }
                if ($("#code").val()==""){
                    $("#msgError").text("验证码不能为空");
                    return false;
                }else {
                    $("#msgError").text("");
                }
                if (!flag) {
                    return false;
                }
                $.getJSON("http://localhost:8080/bolg/forgot","action=forgot&"+$("#form-forgot").serialize(),function (data){

                    $("#msgError").text(data.msg)
                    // if (data.msg)
                })
                return false;
            })
        }
    </script>
</head>
<body>
<!--<img class="imgs" src="imags/loginback.png">-->
<div class="container">
    <form id="form-forgot" action="forgot" method="post">
        <input hidden="hidden" name="action" value="forgot">
        <h1 align="center"><a class="m2" mattootip="首页" href="index.jsp">MyBlog</a></h1>
        <!--        <h3 class="m2">找回密码:</h3>-->
        <div class="neizhi">
            <input
                    type="text"
                    id="email"
                    class="form-control"
                    placeholder="邮箱"
                    name="email"
                    required>
            <span id="emailError"></span>
            <br>
            <input
                    type="password"
                    id="password"
                    class="form-control"
                    placeholder="新密码"
                    name="password"
                    required>
            <span id="passwordError"></span>
            <br>
            <input
                    type="password"
                    id="repassword"
                    class="form-control"
                    placeholder="确认密码"
                    name="password"
                    required>
            <span id="depasswordError"></span>
            <br>

            <div class="form-control">
                <input type="text" placeholder="验证码" id="code" name="code" class="form-control">
                <button id="get" onclick="this">发送验证码
                </button>
            </div>
            <script>
                $(function (){
                    $("#get").click(function () {
                        if($("#email").val()!=""&&$("#emailError").text()=="" && $("#passwordError").text()=="" && $("#depasswordError").text()==""){
                            $("#msgError").text("");
                        }
                        else {
                            $("#msgError").text("邮箱或密码错误");
                            return false;
                        }
                        $.getJSON("http://localhost:8080/bolg/forgot","action=sendCode&email="+$("#email").val(),function (data){

                        })
                        getcheckCodeTime()
                        return false;
                    })
                    var InterValObj; // timer变量，控制时间
                    var count = 60; // 间隔函数，1秒执行
                    var curCount;// 当前剩余秒数
                    function getcheckCodeTime() {
                        curCount = count;
                        $("#get").attr("disabled", "true");
                        $("#get").text("60" + "秒后重发");
                        InterValObj = window.setInterval(SetRemainTime, 1000); // 启动计时器，1秒执行一次

                    }
                    function SetRemainTime() {
                        if (curCount == 0) {
                            window.clearInterval(InterValObj);// 停止计时器
                            $("#get").removeAttr("disabled");// 启用按钮
                            $("#get").text("重新发送");
                            $.getJSON("http://localhost:8080/bolg/forgot","action=initCode",function (data){

                            })
                        } else {
                            curCount--;
                            $("#get").text(curCount + "秒后重发");
                        }
                    }
                })
            </script>
            <br>
            <span id="msgError">${requestScope.msg}</span>
            <button
                    class="btn btn-lg btn-primary btn-block"
                    type="submit"
                    id="submit">修&nbsp;改&nbsp;密&nbsp;码
            </button>
        </div>
    </form>
</div>
</body>
</html>