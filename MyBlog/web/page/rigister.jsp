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
    <link rel="stylesheet" href="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="/css/style.css">
    <script src="../js/rigister.js"></script>
</head>
<body>
<div>
<div class="container">
    <form action="/BaseServlet/UserActionProxy/register" method="post" class="form-horizontal" name="rigisterForm">
        <div class="row">
            <h1 class="col-md-6 col-md-offset-3 col-xs-10 col-xs-offset-1 page_title "><br><br></h1>
        </div>
        <div class="row">
        <div class="col-md-6 col-md-offset-3 col-xs-10 col-xs-offset-1 register">
            <div class="form-group">
                <label for="username" class="col-sm-3 control-label">用户名：</label>
                <div class="col-sm-8">
                    <div class="input-group">
                        <div class="input-group-addon">
                            <span class="glyphicon glyphicon-user"></span>
                        </div>
                        <input type="text" class="form-control" name="username" id="username"placeholder="请输入用户名">
                    </div>
                    <span id="username_help"></span>
                </div>
            </div>

            <div class="form-group">
                <label for="password" class="col-sm-3 control-label">密码：</label>
                <div class="col-sm-8">
                    <div class="input-group">
                        <div class="input-group-addon">
                            <span class="glyphicon glyphicon-lock"></span>
                        </div>
                        <input type="password" class="form-control" name="password" id="password"placeholder="请输入密码">
                    </div>
                    <span id="password_help"></span>
                </div>
            </div>
            <div class="form-group">
                <label for="rigister" class="col-sm-3 control-label">注册</label>
                    <div class="col-sm-8">
                        <div class="input-group">
                        <button type="submit" class="btn btn-info btn-block" id="rigister"><b>&nbsp&nbsp注册&nbsp&nbsp</b>
                            <span class="glyphicon glyphicon-arrow-right"></span></button>
                        </div>
                    </div>
            </div>
        </div>
        </div>
    </form>
</div>
</div>
</body>
</html>
