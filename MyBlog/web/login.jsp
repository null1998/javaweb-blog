<%--
  Created by IntelliJ IDEA.
  User: test
  Date: 2018/12/9
  Time: 18:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <link href='http://fonts.googleapis.com/css?family=Varela+Round' rel='stylesheet'>
    <link href="/css/global.css" rel="stylesheet">
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
        function replaceNodeText(id,newText) {
            var node=document.getElementById(id);
            while(node.firstChild){
                node.removeChild(node.firstChild);
            }
            node.appendChild(document.createTextNode(newText));
        }

    </script>
</head>
<body>
<h1>Please Login</h1>
<form id="loginForm" class="subform" method="get"  action="LoginServlet" >
    <p>
    <label for="username" class="label">你的账号</label>
    <input type="text" name="username" id="username">
    </p>
    <p>
        <label for="password" class="label">你的密码</label>
        <input type="password" name="password" id="password">
    </p>
    <p>
        <input type="submit" id="login" value="登陆">
    </p>
</form>
<h1>Or Please Sign In</h1>
<form id="rigisterForm" class="subform" method="post" action="RegisterServlet">
    <p>
        <label for="username" class="label">你的账号</label>
        <input type="text" name="username" id="username1">
        <span id="username1_help"></span>
    </p>
    <p>
        <label for="password" class="label">你的账号</label>
        <input type="password" name="password" id="password1">
        <span id="password1_help"></span>
    </p>
    <p>
        <input type="submit" id="register" value="注册"><br/>
    </p>
</form>
</body>
</html>
