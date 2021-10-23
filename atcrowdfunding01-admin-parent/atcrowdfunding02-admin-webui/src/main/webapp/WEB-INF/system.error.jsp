<%--
  Created by IntelliJ IDEA.
  User: Ding
  Date: 2021/10/23
  Time: 12:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>错误</title>
</head>
<body>
<h1>出错了</h1>

<!-- 从请求域取出Exception对象，再进一步访问message属性就能显示错误消息 -->
${requestScope.exception.message}
</body>
</html>
