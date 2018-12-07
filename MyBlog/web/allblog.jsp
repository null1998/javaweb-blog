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
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
    ServletContext context = request.getServletContext();
    ArrayList<Essay> arrayList =(ArrayList<Essay>)context.getAttribute("allEssay");
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    for(Essay essay : arrayList){
%>
<div class="essay essay-"<%=essay.getId()%>">
<hr>
<div class="title">
    <%=essay.getTitle()%>
</div>
<hr>
<div class="username">
    作者：<%=essay.getUsername()%>
</div>
<hr>
<div class="content">
    <%=essay.getArticle()%>
</div>
<hr>
<footer>
    创建时间：<%=format.format(essay.getCreation_time())%><hr>
    修改时间：<%=format.format(essay.getModify_time())%><hr>
</footer>
</div>

<%}%>
</body>
</html>
