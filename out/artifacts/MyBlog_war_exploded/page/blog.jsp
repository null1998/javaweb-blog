<%@ page import="com.sduhyd.blog.bean.Essay" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="com.sduhyd.blog.bean.User" %><%--
  Created by IntelliJ IDEA.
  User: test
  Date: 2018/8/20
  Time: 21:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>
        ${sessionScope.current_user.username}
        <c:out value="的文章"></c:out>
    </title>
    <link href='http://fonts.googleapis.com/css?family=Varela+Round' rel='stylesheet'>
</head>
<body>
<c:forEach var="my_essay" items="${sessionScope.my_essays}">
    <h1>${my_essay.title}</h1>
    <p class="intro">${my_essay.article}</p>
    <address>${my_essay.modify_time}</address>
    <br/><br/><br/>
</c:forEach>
</body>
</html>
