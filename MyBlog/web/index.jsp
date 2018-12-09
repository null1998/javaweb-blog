<%@ page import="com.sduhyd.blog.Utils" %>
<%@ page import="com.sduhyd.blog.User" %>
<%--
  Created by IntelliJ IDEA.
  User: test
  Date: 2018/8/18
  Time: 13:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!--port9191-->
<html>
  <head>
    <title>博客主页</title>
      <link href="https://fonts.googleapis.com/css?family=Raleway:400,300,600" rel="stylesheet" type="text/css">
      <link rel="stylesheet" href="css/normalize.css">
      <link rel="stylesheet" href="css/skeleton.css">
      <link rel="stylesheet" href="css/custom.css">
  </head>
  <body>
  <!--top-->
  <div class="section header">
      <div class="container">
          <div class="row">
              <div class="three columns">
                  <p class="logo">
                      My Blog
                  </p>
              </div>
              <div class="nav nine columns">
                  <a class="button button-primary" href="index.jsp">主页</a>
                  <a class="button" href="AllEssayServlet">论坛</a>
                  <c:choose>
                      <c:when test="${sessionScope.current_user==null}">
                          <a class="button" href="login.jsp">登录</a>
                      </c:when>
                      <c:otherwise>
                          <a class="button" href="/LogOutServlet">注销</a>
                      </c:otherwise>
                  </c:choose>
              </div>
          </div>
          <div class="row action">
              <h1>Good Good Study</h1>
              <h2>Day Day Up</h2>
              <c:if test="${sessionScope.current_user!=null}">
                  <a class="button button-primary" href="ShowEssayServlet">进入我的博客</a>
              </c:if>
          </div>
      </div>
  </div>
  <!--top-->

  </body>

</html>
