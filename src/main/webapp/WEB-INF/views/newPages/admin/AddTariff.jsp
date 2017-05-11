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
    <jsp:include page="../includes/head.jsp">
        <jsp:param name="title" value="New Tariff"/>
    </jsp:include>
</head>
<body>
<jsp:include page="../includes/headers/adminHeader.jsp"/>
<div class="container" style="margin-bottom: 30px; width:60%; max-width: 600px;">
    <form method="POST" modelAttribute="product" action="${contextPath}/admin/addTariff">
        <div class="login-form">
            <h1 style="text-align: center;">Create new tariff</h1>

            <c:if test="${not empty error}">
                <span style="float:right ; color: #10CE88;">${error}</span>
            </c:if>

            <div class="form-group row">
                <label class="col-sm-4 control-label">Name</label>
                <div class="col-sm-8"> <input type="text" class="form-control" placeholder="Name " id="Name" name="name">
                    <i class="fa fa-user"></i>
                </div>
            </div>
            <div class="form-group row">
                <label class="col-sm-4 control-label">Description</label>
                <div class="col-sm-8">
                    <input type="text" class="form-control" placeholder="Description " id="Description" name="description">
                    <i class="fa fa-user"></i>
                </div>
            </div>
            <div class="form-group row">
                <label class="col-sm-4 control-label">Base price</label>
                <div class="col-sm-8">
                    <input type="text" class="form-control" placeholder="0 "
                           id="basePrice" name="basePrice" required>
                    <i class="fa fa-user"></i>
                </div>
            </div>
            <div class="form-group row">
                <label class="col-sm-4 control-label">Customer type</label>
                <div class="col-sm-8">
                    <select name="customerTypeId" class="form-control" id="customerTypeId">
                        <option value="Business">Business</option>
                        <option value="Residential">Residential</option>
                    </select>
                </div>
            </div>
            <div class="form-group row">
                <label class="col-sm-4 control-label">Duration in days</label>
                <div class="col-sm-8">
                    <select name="durationInDays" class="form-control" id="durationInDays">
                        <option value="365">365</option>
                    </select>
                </div>
            </div>
            <div class="form-group row">
                <label class="col-sm-4 control-label">Need processing</label>
                <div class="col-sm-8">
                    <div class="row">
                        <input type="radio" name="processingStrategy" class="col-sm-1" value="NeedProcessing">
                        <label class="col-sm-5 control-label">Yes</label>
                        <input type="radio" name="processingStrategy" class="col-sm-1" value="DoNotNeedProcessing"
                               checked>
                        <label class="col-sm-5 control-label">No</label>
                    </div>
                </div>
            </div>

            <div class="form-group row">
                <label class="col-sm-4 control-label">Select status service</label>
                <div class="col-sm-8">
                    <div class="row">
                        <input type="radio" name="status" class="col-sm-1" value="Available">
                        <label class="col-sm-5 control-label">Available</label>
                        <input type="radio" name="status" class="col-sm-1" value="NotAvailable" checked>
                        <label class="col-sm-5 control-label">Not Available</label>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-4 col-xs-0"></div>
                <button type="submit" class="btn btn-primary col-sm-4 col-xs-12">Save</button>
                <div class="col-sm-4 col-xs-0"></div>
            </div>
        </div>
    </form>
</div>
<jsp:include page="../includes/footer.jsp"/>
<script type="text/javascript" src="http://www.google.com/jsapi"></script>
</form>
</body>
</html>