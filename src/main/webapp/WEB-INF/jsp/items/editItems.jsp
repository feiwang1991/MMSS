<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: 王斐
  Date: 2017/2/6
  Time: 16:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>商品修改页面</title>
</head>
<body>
<%--enctype="multipart/form-data"这种方式会导致Post方式提交时候，服务端获取不到数据
  需要在spring配置文件中配置<bean id="multipartResolver" class="org.springframework.web.multipart.commons.CommonsMultipartResolver">
</bean> 参考http://blog.csdn.net/xulianboblog/article/details/51660835
并将commons-fileupload-1.2.1.jar和commons-io-2.5.jar包引入到项目中--%>
<form id="editItems" action="${pageContext.request.contextPath}/items/editItemSubmit.action" method="post" enctype="multipart/form-data">
    <input type="hidden" name="id" value="${itemsCustom.id}"/>
    修改商品信息：
    <table width="100%" border="1">
        <tr>
            <td>商品名称</td>
            <td><input type="text" name="name" value="${itemsCustom.name}"/></td>
        </tr>
        <tr>
            <td>生产日期</td>
            <td><input type="text" name="createtime" value="<fmt:formatDate value="${itemsCustom.createtime}" pattern="yyyy-MM-dd HH:mm:ss" />"/> </td>
        </tr>
        <tr>
            <td>商品价格</td>
            <td><input type="text" name="price" value="${itemsCustom.price}"/></td>
        </tr>
        <tr>
            <td>商品简介</td>
            <td><textarea rows="3" cols="30" name="detail">${itemsCustom.detail}</textarea></td>
        </tr>
    </table>
    <input type="submit" name="submit" value="提交"/>
</form>
</body>
</html>
