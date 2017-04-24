<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: Anna
  Date: 23.04.2017
  Time: 23:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>ADD NEW PRODUCT</title>
</head>
<body>

<div align="center">
    <form method="POST" action="<%=request.getContextPath()%>/admin/addService">
        <table cellpadding="5" border="3" bgcolor="#138050">

            <tr>
                <td>category id</td>
                <td>
                    <select name="productCategories">
                        <c:forEach var="categoy" items="${productCategories}">
                            <option value="${categoy.id}">${categoy.name}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td>duration</td>
                <td>
                    <select name="duration">
                        <option value="12">12 month</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>type id</td>
                <td>
                    <select name="productTypes">
                        <c:forEach var="types" items="${productTypes}">
                            <option value="${types.id}">${types.name}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td>need processing</td>
                <td>
                    <select name="needProcessing">
                        <option value="1">yes</option>
                        <option value="0">no</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>name</td>
                <td><input type="text" name="name"></td>
            </tr>
            <tr>
                <td>description</td>
                <td><input type="text" name="description"></td>
            </tr>
            <tr>
                <td>status</td>
                <td>
                    <select name="status">
                        <option value="1">activated</option>
                        <option value="0">suspended</option>
                    </select>
                </td>
            </tr>
        </table>
        <input type="submit" value="save">
    </form>
</div>
</body>
</html>
