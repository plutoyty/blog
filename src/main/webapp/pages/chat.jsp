<%--
  Created by IntelliJ IDEA.
  User: 杨pc
  Date: 2021/10/7
  Time: 0:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%
        pageContext.setAttribute("chat_user",session.getAttribute("chat_user"));
        pageContext.setAttribute("chat_message",session.getAttribute("chat_message"));
        pageContext.setAttribute("chat_userList",session.getAttribute("chat_userList"));
    %>
    <title>聊天</title>
    <base href="http://localhost:8080/bolg/">
    <link type="text/css" rel="stylesheet" href="css/chat.css">
    <script type="text/javascript" src="jquery-3.4.1/jquery-3.4.1.js"></script>
    <script type="text/javascript" src="jquery-3.4.1/alert.js"></script>
    <%--    <link rel="stylesheet" type="text/css" href="js/font_Icon/iconfont.css">--%>
    <%--    <link rel="stylesheet" type="text/css" href="js/chat.css"/>--%>

</head>
<body>
<%@ include file="/pages/header.jsp" %>
<%--<script>--%>
<%--    var ws =  new WebSocket("ws://localhost:8080")--%>
<%--</script>--%>

<div class="chat-main-father">
    <div class="chat-back">
        <img class="back" src="imags/backgroud.png">
        <div class="chat-body">
            <div class="chat-people">
                <div class="chat-people-header">
                    <img src="${sessionScope.user.head}">
                    <span>${sessionScope.user.name}</span>
                </div>
                <div class="chat-people-items">
                    <c:forEach items="${pageScope.chat_userList}" var="item" varStatus="status">
                        <div class="chat-people-item">
                            <img class="chat-people-item-head" src="${item.head}" alt="">
                            <div class="chat-people-item-name">${item.name}</div>
                        </div>
                    </c:forEach>
                </div>
            </div>
            <div class="chat-message">
                <div class="chat-message-header">
                    <span>${pageScope.chat_user.name}</span>
                </div>
                <div class="chat-message-content">
                    <c:set var="sd" value="${sessionScope.user.id}"></c:set>
                    <c:forEach items="${pageScope.chat_message}" var="item">
                        <c:if test="${item.send eq sd}">
                            <div class="msg-right">
                                <div class="msg-time">13:13</div>
                                <div class="msg-right-user-head">
                                    <div class="msg-content">${item.content}</div>
                                    <img src="${sessionScope.user.head}" alt="">
                                </div>
                            </div>
                        </c:if>
                        <c:if test="${item.send ne sd}">
                            <div class="msg-left">
                                <div class="msg-time">12:41</div>
                                <div class="msg-left-user-head">
                                    <img src="${pageScope.chat_user.head}" alt="">
                                    <div class="msg-content">${item.content}</div>
                                </div>
                            </div>
                        </c:if>
                    </c:forEach>
                </div>
                <div class="chat-input">
                    <textarea name="msg" class="msg-content-msg"></textarea></div>
                <div class="msg-send">
                    <button class="send">发&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;送</button>
                </div>
                <script>
                    $(".send").click(function (){
                        var vlu = $(".msg-content-msg").val().trim();
                        if (vlu=="")return false;
                        $(".msg-content-msg").val("");
                        $.getJSON("http://localhost:8080/bolg/userServlet","action=chatMsg&receive="+'${pageScope.chat_user.id}'+"&msg="+vlu,function (data){
                            var s = "<div class='msg-right'>"+
                                "<div class='msg-time'>"+"13:13"+"</div>"+
                                "<div class='msg-right-user-head'>"+
                                    "<div class='msg-content'>"+data.message.content+"</div>"+
                                    "<img src='"+'${sessionScope.user.head}'+"' alt=''>"+
                                "</div>"+
                            "</div>";
                                    // alert(s);
                            $(".chat-message-content").append(s);
                        });
                    })
                </script>
            </div>
        </div>
    </div>
</div>
</body>
</html>
