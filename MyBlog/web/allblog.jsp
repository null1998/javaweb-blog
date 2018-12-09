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
    <link href='http://fonts.googleapis.com/css?family=Varela+Round' rel='stylesheet'>
    <link href="/css/styles.css" rel="stylesheet">
</head>
<body>
<c:forEach var="essay" items="${applicationScope.all_essays}">
    <h1>${essay.title}</h1>
    <p class="intro">${essay.article}</p>
    <address>${essay.modify_time}</address>
    <br/><br/><br/>
</c:forEach>

</body>
</html>
