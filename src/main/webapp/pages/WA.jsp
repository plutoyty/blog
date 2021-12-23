<%--
  Created by IntelliJ IDEA.
  User: 杨pc
  Date: 2021/11/19
  Time: 19:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script rel="script" src="Test.js"/>
</head>
<body>
<script>
    // var f(){
    // }
    // var  payByPromotion(prices,promtions,discount,order){
    //     var num = new Array();
    //     var ans = 0;
    //     for (var i = 0; i < order.length; i += 2) {
    //         ans += prices[order[i]] * order[i + 1];
    //     }
    //     for (var i = 0; i < order.length; i += 2) {
    //         num[order[i]] += order[i + 1];
    //     }
    //     for (var i = 0; i < promtions.length; i++) {
    //         var flag = 1;
    //         for (var j = 0; j < promtions[i].length; j += 2) {
    //             if (num[promtions[i][j]] < promtions[i][j + 1]) {
    //                 flag = 0;
    //             }
    //         }
    //         if (flag == 1) {
    //             ans -= discount[i];
    //             for (let j = 0; j < promtions[i].length;j += 2)
    //             {
    //                 num[promtions[i][j]] -= promtions[i][j + 1];
    //             }
    //             i--;
    //         }
    //     }
    //     return ans;
    // }
    Test obj = new Test(10);
    obj.addRule();
</script>
</body>
</html>
