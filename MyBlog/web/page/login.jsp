<%--
  Created by IntelliJ IDEA.
  User: test
  Date: 2018/12/9
  Time: 18:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <link href='http://fonts.googleapis.com/css?family=Varela+Round' rel='stylesheet'>
    <link href="css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<h1>Please Login</h1>
<div style="padding: 100px 100px 10px;">
    <form class="bs-example bs-example-form" role="form" id="loginForm"  method="get"  action="/LoginServlet">
        <div class="input-group">
            <span class="input-group-addon">你的账号</span>
            <input type="text" class="form-control" placeholder="twitterhandle" name="username" id="username">
        </div>
        <br>
        <div class="input-group">
            <span class="input-group-addon">你的密码</span>
            <input type="passwrd" class="form-control" name="password" id="password">
        </div>
        <br>
        <div class="input-group">
            <input type="submit" class="form-control" id="login" value="登陆">
        </div>
    </form>
</div>
</body>
</html>
