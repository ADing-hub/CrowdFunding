<%--
  Created by IntelliJ IDEA.
  User: Ding
  Date: 2021/10/24
  Time: 20:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <base href="http://${pageContext.request.serverName }:${pageContext.request.serverPort }/"/>
    <link href="jquery/jquery-2.1.1.min.js">
    <%@include file="/WEB-INF/includex/include-head.jsp" %>
    <script type="text/javascript">
        $(function (){
            $("#asyncBtn").click(function () {
                console.log("ajax函数之前")

                $.ajax({
                    "url": "test/ajax/async.html",
                    "type": "post",
                    "dataType": "text",
                    "async":"fasle",
                    "success": function (response) {
                        console.log("ajax函数内部" + response);
                    }
                });
                setTimeout(function () {
                    console.log("ajax之后")
                },5000)
            });
        });
    </script>
</head>
<body>
<button id="asyncBtn">发送Ajax</button>

</body>
</html>
