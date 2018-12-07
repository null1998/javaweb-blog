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
<!--port9191-->
<html>
  <head>
    <title>博客主页</title>
      <script type="text/javascript">
          window.onload=function (ev) {
              document.getElementById("username1").onblur=function (ev1){validDataLength(3,15,rigisterForm["username"],username1_help);}
              document.getElementById("password1").onblur=function (ev1) { validDataLength(3,15,rigisterForm["password"],password1_help); }
              document.getElementById("register").onclick=function(evl){return checkInput();};

          }
          function validDataLength(min,max,inputFiled,helpText){
              if(inputFiled.value.length==0){
                  if(helpText!=null){
                      helpText.innerHTML="请输入数据";
                      return false;
                  }
              }else if(inputFiled.value.length<min||inputFiled.value.length>max){
                  if(helpText!=null){
                      helpText.innerHTML="建议输入长度为"+min+"到"+max;
                      return false;
                  }
              }else{
                  if(helpText!=null){
                      helpText.innerHTML="";
                      return true;
                  }
              }
          }
          function checkInput(){
              if(validDataLength(3,15,rigisterForm["username1"],username1_help)&&validDataLength(3,15,rigisterForm["password1"],password1_help)){
                  return true;
              }else{
                  alert("很抱歉，注册失败");
                  return false;
              }
          }

      </script>
  </head>
  <body>

  <header>
      <h1>当前登录用户为：</h1>
      <%
        Cookie[]cookies=request.getCookies();
        for(int i=0;i<cookies.length;i++){
            Cookie cookie=cookies[i];
            if(cookie.getName().equals("username")){
                String username=cookie.getValue();
                out.println("欢迎回来!"+"["+username+"]"+"<br/>");
                break;
            }
        }

      %>
      <%
      User current_user = (User)session.getAttribute("current_user");
      if(current_user == null) {
          out.print("<<没有登录>>");
      } else {
          out.print("用户"+"["+current_user.getUsername()+"]");
      }
      %>
  </header>

  <form id="loginForm" method="post"  action="LoginServlet" >
    账号:<input type="text" name="username" id="username">
    <br/>
    密码:<input type="password" name="password" id="password">
      <br/>
    <input type="submit" id="login" value="登陆">
  </form>
  <form method="post" action="LogOutServlet">
    <input type="submit" id="logout" value="注销">
  </form>
  <form id="rigisterForm" method="post" action="RegisterServlet">
    账号:<input type="text" name="username" id="username1">
      <span id="username1_help"></span>
      <br/>
    密码:<input type="password" name="password" id="password1">
      <span id="password1_help"></span>
      <br/>
      <input type="submit" id="register" value="注册"><br/>
  </form>
  <%if(current_user!=null){%>
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
