<%@ page import="com.sduhyd.blog.Utils" %>
<%@ page import="com.sduhyd.blog.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!--port9191-->
<html>
<head>
    <title>博客主页</title>
    <link href="https://fonts.googleapis.com/css?family=Raleway:400,300,600" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="/cs/main.css">
    <script src="../js/main.js"></script>
</head>
<body>
<div class="jumbotron text-center" style="margin-bottom:0">
    <h1>我的第一个 Blog 页面</h1>
    <p>重置浏览器窗口大小查看效果！</p>
</div>
<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">网站名</a>
        </div>
        <div class="collapse navbar-collapse" id="myNavbar">
            <ul class="nav navbar-nav">
                <li class="active"><a href="main.jsp"><span class="glyphicon glyphicon-home"></span>首页</a></li>
                <li><a href="AllEssayServlet">页面2</a></li>
                <li><a href="#">页面 3</a></li>
            </ul>
            <c:choose>
                <c:when test="${sessionScope.current_user==null}">
                    <ul class="nav navbar-nav navbar-right">
                        <li><a href="rigister.jsp"><span class="glyphicon glyphicon-user"></span> 注册</a></li>
                        <li><a href="login.jsp"><span class="glyphicon glyphicon-log-in"></span> 登录</a></li>
                    </ul>
                </c:when>
                <c:otherwise>
                    <ul class="nav navbar-nav navbar-right">
                        <li><a href="/LogOutServlet"><span class="glyphicon glyphicon-arrow-right"></span>退出</a></li>
                    </ul>
                    <ul class="nav navbar-nav navbar-right">
                        <li><a href="#"> <span class="glyphicon glyphicon-cog"></span>设置</a></li>
                    </ul>
                    <ul class="nav navbar-nav navbar-right">
                        <li><a href="#"><span class="glyphicon glyphicon-star"></span>收藏</a></li>
                    </ul>
                    <ul class="nav navbar-nav navbar-right">
                        <li><a href="writeEssay.jsp"><span class="glyphicon glyphicon-edit"></span>写文章</a></li>
                    </ul>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</nav>
<div class="container">
    <div class="row">
        <div class="col-sm-8">
            <h2>标题</h2>
            <h5>副标题</h5>
            <div class="fakeimg">图像</div>
            <p>一些文本..</p>
            <p></p>
            <br>
            <h2>标题</h2>
            <h5>副标题</h5>
            <div class="fakeimg">图像</div>
            <p>一些文本..</p>
            <p></p>
        </div>
        <div class="col-sm-4">
            <h2>关于我</h2>
            <h5>我的照片:</h5>
            <div class="fakeimg">这边插入图像</div>
            <p>关于我的介绍..</p>
            <h3>链接</h3>
            <p>描述文本。</p>
            <ul class="nav nav-pills nav-stacked">
                <li class="active"><a href="#">链接 1</a></li>
                <li><a href="#">链接 2</a></li>
                <li><a href="#">链接 3</a></li>
            </ul>
            <hr class="hidden-sm hidden-md hidden-lg">
        </div>
    </div>
    <div class="row">
        <div class="col-sm-8">
            <div class="list-group">
                <c:forEach var="essay" items="${applicationScope.all_essays}">
                <div  class="list-group-item">
                    <h4>${essay.title}</h4>
                    <br/>
                    <span>${essay.creation_time}&nbsp;&nbsp;|</span>
                    <span>阅读次数:</span>
                    <br/><br/>
                    <span>${essay.article}</span>
                    <br/><br/><br/>
                    <a href="/SingleEssayServlet?id=${essay.id}">阅读全文</a>
                    <br/>
                </div>
                </c:forEach>
            </div>
        </div>
        <div class="col-sm-4"></div>
    </div>
</div>

<div class="jumbotron text-center" style="margin-bottom:0">
    <p>底部内容</p>
</div>


</body>

</html>

