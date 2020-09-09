# 练习Java基础知识的博客后端

## 1、使用技术

前端：jsp、jstl、bootstrap、css、html

后端：java

数据库：mysql

容器：tomcat

## 2、功能

发布文章，浏览，评论，收藏，点赞

### 3、实现

没有使用框架，使用基础的知识，实现简易的前后端分离。

使用一个Servlet用于接收请求。

自定义注解Controller，使用它自定义控制器。自定义注解RequestMapping，可以放在方法上用于指定路径。AuthCheck注解的方法只有在登录状态下可以调用。利用反射实现这些注解。

数据访问层使用一个简单的连接池和jdbc。

业务层处理普通业务，使用了session和request存储临时数据。

