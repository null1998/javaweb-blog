<%@ page import="com.sduhyd.blog.Essay" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="com.sduhyd.blog.User" %><%--
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
        <jsp:useBean id="current_user" class="com.sduhyd.blog.User" scope="session"/>
        <jsp:getProperty name="current_user" property="username"/>的文章列表
    </title>
</head>
<body>

<c:forEach var="essay" items="${applicationScope.essays}">
<table>
    <tr>${essay.title}</tr><br>
    <tr>${essay.article}</tr><br>
    <tr>${essay.creation_time}</tr><br>
    <tr>${essay.modify_time}</tr><br>
</table>
</c:forEach>

</body>
<script>
    function applyEdit(essayId) {
        const $essay = document.querySelector('.essay-'+essayId+'');
        const $title = $essay.querySelector(".title");
        const $content = $essay.querySelector(".content");
        $title.contentEditable = false;
        $content.contentEditable = false;
        const newTitle = $title.innerText;
        const newContent = $content.innerHTML;
        const xhr = new XMLHttpRequest();
        var formdata = {
            id: essayId,
            title: newTitle,
            content: newContent
        };
        var encodedData = '';
        for(var key in formdata) {
            encodedData+= '&' + key + '=' + encodeURIComponent(formdata[key]);
        }
        xhr.open("POST", "UpdateEssayServlet", true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        xhr.send(encodedData.slice(1));
        xhr.onreadystatechange = function(e){
            console.info('state change', e);
        };
    }
    function editEssay(essayId) {
        const $essay = document.querySelector('.essay-'+essayId+'');
        const $title = $essay.querySelector(".title");
        const $content = $essay.querySelector(".content");
        $title.contentEditable = true;
        $content.contentEditable = true;
    }
</script>
</html>
