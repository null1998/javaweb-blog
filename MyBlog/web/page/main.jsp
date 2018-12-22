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
    <link rel="stylesheet" href="/css/main.css">
    <script src="../js/main.js"></script>
</head>
<body>
<div class="jumbotron text-center" style="margin-bottom:0">
    <h1>我的第一个博客页面</h1>
    <p>good good study,day day up!</p>
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
            <div class="fakeimg">一张图片</div>
            <p>一些文本</p>
            <p></p>
            <h2>标题</h2>
            <h5>副标题</h5>
            <div></div>
            <p>第一段</p>
            <p>第二段</p>
            <p>第三段</p>
            <p></p>
        </div>
        <div class="col-sm-4">
            <h2>关于我</h2>
            <h5>我的照片:</h5>
            <div class="fakeimg"></div>
            <p>关于我的介绍..</p>
            <h3>热门排行</h3>
            <p>点赞量最多的三篇文章</p>
            <ul class="nav nav-pills nav-stacked">
                <c:forEach var="top_essay" items="${applicationScope.top_essays}">
                    <li><a href="/SingleEssayServlet?id=${top_essay.id}"><span class="glyphicon glyphicon-thumbs-up">${top_essay.star}&nbsp;${top_essay.title}&nbsp;${top_essay.username}&nbsp;</span></a></li>
                </c:forEach>
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
                    <span>访问量${essay.visitor}&nbsp;&nbsp;</span>
                    <span class="glyphicon glyphicon-thumbs-up">${essay.star}&nbsp;</span>
                    <span class="glyphicon glyphicon-thumbs-down">${essay.diss}&nbsp;</span>
                    <span class="glyphicon glyphicon-comment">${essay.comments}</span>
                    <br/><br/>
                    <span>${essay.article}</span>
                    <br/><br/><br/>
                    <a href="/SingleEssayServlet?essay_id=${essay.id}">阅读全文</a>
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

