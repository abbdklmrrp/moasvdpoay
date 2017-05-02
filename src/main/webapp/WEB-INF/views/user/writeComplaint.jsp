<%--
  Created by IntelliJ IDEA.
  User: Petro
  Date: 02.05.2017
  Time: 10:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Complaint</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/meyer-reset/2.0/reset.min.css">
    <link href="${contextPath}/resources/css/basic.css" rel="stylesheet"/>
</head>
<body>
<form method="post" action="${contextPath}/user/writeComplaint">
    <div class="login-form">
        <div class="form-group ">
            Product
            <select id="products" name="products" class="form-control" aria-required="true">
                <c:forEach var="product" items="${productList}">
                    <option value="${product.id}">${product.name}</option>
                </c:forEach>
            </select>
            <div class="form-group form-group-lg">
                <label class="col-sm-2 control-label" readonly="readonly">Description</label>
                <div class="col-sm-10">
                    <textarea  name="description" cols="38" rows="10"></textarea>
                </div>
            </div>
            <button type="submit" class="log-btn">Save</button>
        </div>
    </div>
</form>

</body>
</html>
