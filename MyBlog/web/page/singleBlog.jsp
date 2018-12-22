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
              <a href="/AllEssayServlet"><span class="glyphicon glyphicon-home">返回首页</span></a>
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
            <a href="/DisServlet?id=${requestScope.current_essay.id}"><span class="glyphicon glyphicon-thumbs-down"></span></a>
        </div>
        <!-- 评论 -->
        <div>
            <div>
                <c:choose>
                    <c:when test="${sessionScope.current_user!=null}">
                        <span class="glyphicon glyphicon-edit">写评论</span>
                        <br>
                        <!--隐藏input，好东西-->
                        <form role="form" action="/CommentServlet" method="post">
                            <input type="hidden" name="id" value="${requestScope.current_essay.id}">
                            <div class="form-group">
                                <label for="content">文本框</label>
                                <textarea id="content" name="comment" class="form-control" rows="3"></textarea>
                            </div>
                            <div class="form-group">
                                <div>
                                    <button type="submit" class="btn btn-default">提交</button>
                                </div>
                            </div>
                        </form>
                    </c:when>
                    <c:otherwise>
                        <a href="/page/login.jsp"><span class="glyphicon glyphicon-edit">登陆后方可评论</span></a>
                        <br>
                    </c:otherwise>
                </c:choose>
            </div>

            <c:if test="${requestScope.current_comments!=null}">
                <c:forEach var="comment" items="${requestScope.current_comments}">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h3 class="panel-title">${comment.username}</h3>
                            <span>${comment.creation_time}</span>
                            <a href="/StarComServlet?comment_id=${comment.id}&essay_id=${requestScope.current_essay.id}"><span class="glyphicon glyphicon-thumbs-up">${comment.star}&nbsp;</span></a>
                            <a href="/DisComServlet?comment_id=${comment.id}&essay_id=${requestScope.current_essay.id}"><span class="glyphicon glyphicon-thumbs-down">${comment.diss}</span></a>
                        </div>
                        <div class="panel-body">
                            ${comment.content}
                        </div>
                    </div>
                </c:forEach>
            </c:if>
        </div>

    </div>



</body>
</html>
