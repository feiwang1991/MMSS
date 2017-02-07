<%--
  Created by IntelliJ IDEA.
  User: 王斐
  Date: 2017/2/3
  Time: 22:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>查询商品列表</title>
    <script type="text/javascript">
        function editItemAllSubmit(){
            var form=document.getElementById("formItem");
            form.action="${pageContext.request.contextPath}/items/editItemAllSubmit.action";
            form.submit();
        }
        function queryItems(){
            var form=document.getElementById("formItem");
            form.action="${pageContext.request.contextPath}/items/findItemsList.action";
            form.submit();
        }
    </script>
</head>
<body>
<form id="formItem" action="${pageContext.request.contextPath}/items/findItemsList.action" method="post">
    查询条件：
    <table width="100%" border="1">
        <tr>
            <td>商品名称：<input type="input" name="itemsCustom.name"/></td>
        </tr>
        <tr>
            <td>
                <input type="button" value="查询" onclick="queryItems()" />
                <input type="button" value="批量修改" onclick="editItemAllSubmit()"/>
            </td>
        </tr>
    </table>
    商品列表：pageContext.request.contextPath:${pageContext.request.contextPath}
    <table width="100%" border="1">
        <tr>
            <td>商品名称</td>
            <td>商品价格</td>
            <td>生产日期</td>
            <td>商品描述</td>
        </tr>
        <c:forEach items="${itemsList}" var="item" varStatus="status">
            <tr>
                <td><input type="text" name="itemsCustomList[${status.index}].name" value="${item.name}" /></td>
                <td><input type="text" name="itemsCustomList[${status.index}].price" value="${item.price}" /></td>
                <%--<td><input type="text" name="itemsCustomList[${status.index}].detail" value="${item.detail}" /></td>--%>
                <td><input type="text" name="itemsCustomList[${status.index}].createtime" value="<fmt:formatDate value="${item.createtime}" pattern="yyyy-MM-dd HH:mm:ss"/>" /></td>

                <td><textarea rows="3" cols="30" name="itemsCustomList[${status.index}].detail">${item.detail}</textarea></td>
            </tr>
        </c:forEach>
    </table>
</form>
</body>
</html>
