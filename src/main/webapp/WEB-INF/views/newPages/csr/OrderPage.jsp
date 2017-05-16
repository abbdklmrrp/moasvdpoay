<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../includes/head.jsp" %>
<html>
<head>
    <jsp:include page="../includes/head.jsp">
        <jsp:param name="tittle" value="Order"/>
    </jsp:include>
</head>
<body>
<jsp:include page="../includes/headers/csrHeader.jsp">
    <jsp:param name="pageName" value="Order"/>
</jsp:include>
<div class="container" style="margin-bottom: 30px; width:60%; max-width: 400px;">
    <div class="login-form">
        <h1 style="text-align: center">Order info</h1>
        <br>
        <div class="form-group form-group-lg">
            <label class="col-sm-2 control-label">Product name</label>
            <div class="col-sm-10">
                <input readonly style="border-bottom-width: 0;" type="text" class="form-control" name="productName"
                       id="productName" value=${order.productName}><br>
            </div>
        </div>
        <div class="form-group form-group-lg">
            <label class="col-sm-2 control-label">Product type</label><br>
            <div class="col-sm-10">
                <input readonly type="text" class="form-control" name="productType" id="productType"
                       value=${order.productType} ><br>
            </div>
        </div>
        <div class="form-group form-group-lg">
            <label class="col-sm-2 control-label">Customer type</label><br>
            <div class="col-sm-10">
                <input readonly type="text" class="form-control" name="customerType" id="customerType"
                       value=${order.customerType} ><br>
            </div>
        </div>
        <div class="form-group form-group-lg">
            <label class="col-sm-2 control-label">Region</label><br>
            <div class="col-sm-10">
                <input readonly type="text" class="form-control" name="place" id="place" value="${order.place}"><br>
            </div>
        </div>
        <h1 style="text-align: center">Customer info</h1>
        <div class="form-group form-group-lg">
            <label class="col-sm-2 control-label">Name</label><br>
            <div class="col-sm-10">
                <input readonly type="text" class="form-control" name="userName" id="userName" value="${order.userName}"><br>
            </div>
        </div>
        <div class="form-group form-group-lg">
            <label class="col-sm-2 control-label">Surname</label><br>
            <div class="col-sm-10">
                <input readonly type="text" class="form-control" name="userSurname" id="userSurname" value="${order.userSurname}"><br>
            </div>
        </div>
        <div class="form-group form-group-lg">
            <label class="col-sm-2 control-label">Phone</label><br>
            <div class="col-sm-10">
                <input readonly type="text" class="form-control" name="phone" id="phone" value="${order.phone}"><br>
            </div>
        </div>
        <div style="text-align: center" id="actvBtn${order.orderId}">
            <button class="btn btn-success"  onclick="activateOrder(${order.orderId})">Activated</button>
        </div>
</div>
</div>
<jsp:include page="../includes/footer.jsp"/>
</body>
</html>
<script src="${pageContext.request.contextPath}/resources/js/activateOrderByCsr.js"></script>

