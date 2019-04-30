<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: test
  Date: 2019/2/12
  Time: 1:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title><link href="https://fonts.googleapis.com/css?family=Raleway:400,300,600" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="/css/main.css">
</head>
<body>
<div>
    <a href="/InitServlet"><span class="glyphicon glyphicon-home">返回首页</span></a>
</div>
<br>
<div class="row">
    <div class="col-sm-4">
        <ul class="nav nav-pills nav-stacked">
            <c:forEach var="myfavorite_essay" items="${requestScope.myfavorite_essays}">
                <li><a href="/InitEssayPageServlet?essay_id=${myfavorite_essay.id}"><span class="glyphicon glyphicon-heart-empty">${myfavorite_essay.favorite}&nbsp;${myfavorite_essay.title}&nbsp;${myfavorite_essay.username}&nbsp;</span></a><a href="DeleteMyFavoriteServlet?essay_id=${myfavorite_essay.id}&user_id=${sessionScope.current_user.id}"><span class="glyphicon glyphicon-trash"></span></a></li>
            </c:forEach>
        </ul>
        <hr class="hidden-sm hidden-md hidden-lg">
    </div>
</div>

</body>
</html>
