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
    <link rel="stylesheet" href="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
    <link href="/css/singleblog.css" rel="stylesheet">
</head>
<body>
    <div>
      <div>
          <div>
              <a href="/AllEssayServlet"><span class="glyphicon glyphicon-home">返回主页</span></a>
          </div>
      </div>
        <!--文章基本信息-->
        <br>
        <div>
            <div>
                <h1>${requestScope.current_essay.title}</h1>
            </div>
            <br>
            <div>
                <p class="intro">
                    <span>${requestScope.current_essay.creation_time}&nbsp;&nbsp;|</span>
                    <span>访问量:${requestScope.current_essay.visitor}&nbsp;&nbsp;</span>
                    <span>喜欢${requestScope.current_essay.star}&nbsp;</span>
                    <span>差评${requestScope.current_essay.diss}&nbsp;</span>
                    <span>评论${requestScope.current_essay.comments}</span>
                </p>
            </div>
        </div>
        <!--文章主题-->
        <div>
            <p class="intro">${requestScope.current_essay.article}</p>
            <p class="intro">${requestScope.current_essay.modify_time}</p>
        </div>
        <!--点赞-->
        <div>
            <a href="/StarServlet?id=${requestScope.current_essay.id}"><span class="glyphicon glyphicon-thumbs-up">&nbsp;</span></a>
            <a href="/DissServlet?id=${requestScope.current_essay.id}"><span class="glyphicon glyphicon-thumbs-down"></span></a>
        </div>
        <!-- 评论 -->
        <div>
            <div>
                <a href="write_comment.jsp"><span class="glyphicon glyphicon-edit">写评论</span></a>
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
                            <span>${comment.star}</span>
                            <span>${comment.diss}</span>
                        </div>
                    </div>
                </c:forEach>
            </c:if>
        </div>

    </div>



</body>
</html>
