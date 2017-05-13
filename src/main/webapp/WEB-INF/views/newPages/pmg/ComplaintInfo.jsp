<%--
  Created by IntelliJ IDEA.
  User: Aleksandr Revniuk
  Date: 13.05.17
  Time: 22:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <jsp:include page="../includes/head.jsp">
        <jsp:param name="tittle" value="All complaints"/>
    </jsp:include>
</head>
<body>
<jsp:include page="../includes/headers/pmgHeader.jsp">
    <jsp:param name="pageName" value="allComplaints"/>
</jsp:include>
<div class="container" style="margin-bottom: 30px; width:60%; max-width: 300px;">
    <h1 style="text-align: center">Complaint info</h1>
    <div class="row">
        <label class="col-sm-4 control-label" for="date">Creating date</label>
        <div class="col-sm-8" id="date"><p class="text-info"><fmt:formatDate value="${complaint.creatingDate.time}"
                                                                             type='date'
                                                                             pattern="dd MMMM yyyy"/></p></div>
    </div>
    <div class="row">
        <label class="col-sm-4 control-label" for="description">Description</label>
        <div class="col-sm-8" id="description"><p class="text-info">${complaint.description}</p></div>
    </div>
    <div class="row">
        <label class="col-sm-4 control-label" for="status">Status</label>
        <div class="col-sm-8" id="status"><p class="text-info">${complaint.status.toString()}</p></div>
    </div>
    <div class="row">
        <label class="col-sm-4 control-label" for="product-name">Product name</label>
        <div class="col-sm-8" id="product-name"><p class="text-info">${complaint.productName}</p></div>
    </div>
    <div class="row">
        <label class="col-sm-4 control-label" for="user-name">User name</label>
        <div class="col-sm-8" id="user-name"><p class="text-info">${complaint.userName}</p></div>
    </div>
    <div class="row">
        <label class="col-sm-4 control-label" for="user-surname">User surname</label>
        <div class="col-sm-8" id="user-surname"><p class="text-info">${complaint.userSurname}</p></div>
    </div>
    <div class="row">
        <label class="col-sm-4 control-label" for="user-phone">User phone</label>
        <div class="col-sm-8" id="user-phone"><p class="text-info">${complaint.userPhone}</p></div>
    </div>

</div>
<jsp:include page="../includes/footer.jsp"/>
</body>
</html>
