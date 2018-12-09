<%@ page import="java.util.ArrayList" %>
<%@ page import="com.sduhyd.blog.Essay" %>
<%@ page import="java.text.SimpleDateFormat" %><%--
  Created by IntelliJ IDEA.
  User: test
  Date: 2018/8/21
  Time: 15:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<c:forEach var="essay" items="${applicationScope.all_essays}">
    <div id="essay">
        <h3><a href="SingleEssayServlet?id=${essay.id}">${essay.title}</a></h3>
    </div>
</c:forEach>

</body>
</html>
