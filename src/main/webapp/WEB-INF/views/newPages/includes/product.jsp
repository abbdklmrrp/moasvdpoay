<%--
  Created by IntelliJ IDEA.
  User: Yuliya Pedash
  Date: 12.05.2017
  Time: 10:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="container">
    <h1>${product.name}</h1>
    <div class="row">
        <label>Name:</label> ${product.name}
    </div>
    <div class="row">
        <label>Description:</label> ${product.description}
    </div>
    <div class="row">
        <label>Price:</label> ${product.basePrice} hryvnas
    </div>
    <div class="row">
        <label>Processing Strategy:</label> ${product.processingStrategy.name}
    </div>

