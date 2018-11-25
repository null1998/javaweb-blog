<%@ page import="com.sduhyd.blog.Utils" %>
<%--
  Created by IntelliJ IDEA.
  User: test
  Date: 2018/8/18
  Time: 13:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!--port9191-->
<html>
  <head>
    <title>博客主页</title>
  </head>
  <body>

  <header>
      <h1>当前登录用户为：</h1>
      <%
      String username = (String)session.getAttribute("current-user");
      if(username == null) {
          out.print("<<没有登录>>");
      } else {
          out.print("用户"+"["+username+"]");
      }
      %>
  </header>

  <form method="post" action="LoginServlet" >
    账号:<input type="text" name="username" id="username">
    <br/>
    密码:<input type="password" name="password" id="password">
      <br/>
    <input type="submit" id="login" value="登陆">
  </form>
  <form method="post" action="LogOutServlet">
    <input type="submit" id="logout" value="注销">
  </form>
  <form method="post" action="RegisterServlet">
    账号:<input type="text" name="username" id="username1"><br/>
    密码:<input type="password" name="password" id="password1"> <br/>
   <input type="submit" id="register" value="注册"><br/>
  </form>
  <%if(username!=null){%>
  <a href="ShowEssayServlet" target="_blank">显示我的文章列表</a>
  <fieldset>
      <legend>Personal information:</legend>
  <form method="post" action="CreateEssayServlet">
    <input type="text" name="create_title"><br/>
    <textarea name="create_article" cols="30" rows="10"></textarea>
    <input type="submit" value="新增">
  </form>
  <%}%>
      <a href="AllEssayServlet" target="_blank">全部文章</a>
          <button onclick="f()">外部js</button>
      <script type="text/javascript" src="js/example2.js"></script>
  </body>
</html>
