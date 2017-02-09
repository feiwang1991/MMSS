<%--
  Created by IntelliJ IDEA.
  User: 王斐
  Date: 2017/2/9
  Time: 17:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>json实际测试页面啦</title>
  <script type="text/javascript" src="${pageContext.request.contextPath}/javascript/jquery.js">
  </script>
  <script type="text/javascript">
    <%--注意：次方法测试传到controller中为json数据，故需要设置contentType--%>
    function requestBody(){
      $.ajax({
        type:'post',
        url:'${pageContext.request.contextPath}/requestBody.action',
        contentType:'application/json;charset=utf-8',
        data:'{"name":"华为","price":998}',
        success:function(data){
          alert(data.name+","+data.price);
      }
      });
    }
    function responseBody(){
      <%--注意：次方法测试传到controller中不是json数据，就是传统的key-value数据，故不需要设置contentType
      只要为默认类型即可--%>
      $.ajax({
        type:'post',
        url:'${pageContext.request.contextPath}/responseBody.action',
        data:'name=荣耀&price=996.0',
        success:function(data){
          alert(data.name+","+data.price);
        }
      });
    }
  </script>
</head>
<body>
    <input type="button" value="传入json数据，返回json数据" onclick="requestBody()"/>
    <input type="button" value="传入普通key-value数据，返回json数据" onclick="responseBody()"/>
</body>
</html>
