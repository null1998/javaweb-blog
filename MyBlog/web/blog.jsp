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
<html>
<head>
    <title><%=session.getAttribute("current-user")%>的文章列表</title>
</head>
<body>
<%
    ServletContext context = request.getServletContext();
    Essay[] essays = (Essay[])context.getAttribute("essays");
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    for(Essay essay : essays) {
        if(essay == null) {
            System.out.println("found null essay");
            continue;
        }
%>
<div class="essay essay-<%=essay.getId()%>">
    <hr>
    <div class="title">
        <%=essay.getTitle()%>
    </div>
    <hr>
    <div class="content">
        <%=essay.getArticle()%>
    </div>
    <hr>
    <footer>
        创建时间：<%=format.format(essay.getCreation_time())%> <br>
        修改时间： <%=format.format(essay.getModify_time())%>
    </footer>
    <footer>
        <button onclick="editEssay(<%=essay.getId()%>)">修改</button>
        <button onclick="applyEdit(<%=essay.getId()%>)">提交</button>
    </footer>
</div>
<hr>
<%}%>
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
