<%--
  Created by IntelliJ IDEA.
  User: test
  Date: 2018/12/21
  Time: 1:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="../js/writeEssay.js"></script>
</head>
<body>
<form role="form" action="/BaseServlet/auth/createEssay" method="post" name="writeForm">
    <div class="form-group">
        <label for="title">标题</label>
        <input type="text" id="title" name="create_title" class="form-control" placeholder="文本输入">
        <span id="title_help"></span>
    </div>
    <div class="form-group">
        <label for="content">文本框</label>
        <textarea id="content" name="create_article" class="form-control" rows="12"></textarea>
        <span id="content_help"></span>
    </div>
    <div class="form-group">
        <div class="col-sm-offset-2 col-sm-10">
            <button type="submit" class="btn btn-default" id="write">提交</button>
        </div>
    </div>
</form>
</body>
</html>
