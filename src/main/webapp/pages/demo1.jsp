<%--
  Created by IntelliJ IDEA.
  User: 杨pc
  Date: 2021/10/3
  Time: 15:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <base href="http://localhost:8080/bolg/">
    <script src="jquery-3.4.1/jquery.min.js"></script>
    <script src="jquery-3.4.1/jquery.Jcrop.min.js"></script>
    <link rel="stylesheet" href="jquery-3.4.1/jquery.Jcrop.css" type="text/css"/>
</head>
<body>

<img src="imags/user.png" id="target"/>
<img src="imags/user.png" id="preview"/>
<script language="Javascript">
    $(function(){

        $('#target').Jcrop({
            onChange: showPreview,
            onSelect: showPreview,
            aspectRatio: 1
        });

    });
    function showPreview(coords)
    {
        var rx = 100 / coords.w;
        var ry = 100 / coords.h;

        $('#preview').css({
            width: Math.round(rx * 300) + 'px',
            height: Math.round(ry * 300) + 'px',
            marginLeft: '-' + Math.round(rx * coords.x) + 'px',
            marginTop: '-' + Math.round(ry * coords.y) + 'px'
        });
    }

</script>


</body>
</html>
