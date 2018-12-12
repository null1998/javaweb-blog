<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: test
  Date: 2018/12/9
  Time: 11:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link href='http://fonts.googleapis.com/css?family=Varela+Round' rel='stylesheet'>
    <link href="/css/styles.css" rel="stylesheet">
</head>
<body>
<div class="head_line"></div>
    <div class="container" id="main">
      <div class="head">
          <div class="title">
              <a href="index.jsp">返回主页</a>
          </div>
      </div>
        <!--文章基本信息-->
        <div class="article">
            <div class="a_head">
                <h3>${requestScope.current_essay.title}</h3>
            </div>
            <div class="info">
                <h5>
                    <span>${requestScope.current_essay.creation_time}</span>
                    <span>访客问量</span>
                    <span>点赞量</span>
                    <span>评论量</span>
                </h5>
            </div>
        </div>
        <div class="line"></div>
        <!--文章主题-->
        <div class="a_content">
            <jsp:include page="a_content.jsp"/>
        </div>
        <div>
            <!--预留上下文章链接-->
        </div>
        <div class="line"></div>
        <!-- 评论 -->
        <div class="comment">
            <div class="write">
                <a href="#comment"><span>写评论</span></a>
            </div>
            <c:if test="${requestScope.comments!=null}">
                <c:forEach var="comment" items="requestScope.comments">
                    <div>
                        <div>
                          <span>${comment.username}</span>
                            <span>${comment.time}</span>
                        </div>
                        <div>
                            <span>${comment.content}</span>
                        </div>
                        <div>
                            <span>${comment.start}</span>
                            <span>${comment.diss}</span>
                        </div>
                    </div>
                </c:forEach>
            </c:if>


        </div>

    </div>

        <h1>${requestScope.current_essay.title}</h1>
        <p class="intro">${requestScope.current_essay.article}</p>
        <address>${requestScope.current_essay.modify_time}</address>

</body>
</html>
