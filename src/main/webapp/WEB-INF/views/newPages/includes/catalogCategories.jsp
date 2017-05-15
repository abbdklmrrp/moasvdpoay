<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Yuliya Pedash
  Date: 13.05.2017
  Time: 14:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link href="<c:url value="/resources/css/servicesCatalog.css"/>" rel="stylesheet"/>
<ul class="nav navbar-nav">
    <li class="dropdown">
        <a href="#" class="dropdown-toggle" data-toggle="dropdown"> Categories </a>
        <c:forEach var="productCategory" items="${productsCategories}">
            <ul class="dropdown-menu">
                <li id="category${productCategory.id}">
                    <a href="<%=request.getContextPath()%>/${userRole}/orderService?category=${productCategory.id}">${productCategory.name}</a>
                </li>
                <li class="divider"></li>
            </ul>
        </c:forEach>
    </li>
</ul>