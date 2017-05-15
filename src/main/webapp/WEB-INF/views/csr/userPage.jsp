<%--
  Created by IntelliJ IDEA.
  User: Petro
  Date: 29.04.2017
  Time: 16:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>User's details</title>
    <link href="${pageContext.request.contextPath}/resources/css/basic.css" rel="stylesheet"/>
    <script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBhhghVMdW1rIbJCJupKdngdNk0k5JwaQE&libraries=places"></script>

</head>
<body>
<form  id="details-form" modelAttribute="user" action="${pageContext.request.contextPath}/csr/editUser" method="post">
<div class="left-hight-login">
    <div class="form-group form-group-lg">
        <label class="col-sm-2 control-label">Name</label><br>
        <div class="col-sm-10">
            <input type="text" class="form-control" name="name" id="name" value=${user.name}><br>
        </div>
    </div>
    <div class="form-group form-group-lg">
        <label class="col-sm-2 control-label">Surname</label><br>
        <div class="col-sm-10">
            <input type="text" class="form-control" name="surname" id="surname" value=${user.surname}><br>
        </div>
    </div>
    <div class="form-group form-group-lg">
        <label class="col-sm-2 control-label">Email</label><br>
        <div class="col-sm-10">
            <input type="text" class="form-control" name="email" id="email" value=${user.email}><br>
        </div>
    </div>
    <div class="form-group form-group-lg">
        <label class="col-sm-2 control-label">Phone</label><br>
        <div class="col-sm-10">
            <input type="text" class="form-control" name="phone" id="phone" value=${user.phone}><br>
        </div>
    </div>
    <div class="form-group form-group-lg">
        <label class="col-sm-2 control-label">City</label><br>
        <div class="col-sm-10">
            <input type="text" class="form-control" name="address" id="address" value="${user.address}"><br>
        </div>
    </div>
    <button type="submit" class="log-btn">Save</button>
</div>
</form>
<div class="right-login">
    <form action="${pageContext.request.contextPath}/csr/userTariffs" method="post">
        <button type="submit" class="log-btn">Tariffs</button>
    </form>
    <form action="${pageContext.request.contextPath}/csr/userServices" method="post">
        <button type="submit" class="log-btn">Services</button>
    </form>
    <form action="${pageContext.request.contextPath}/csr/userOrders" method="post">
        <button type="submit" class="log-btn">Orders</button>
    </form>
    <form action="${pageContext.request.contextPath}/csr/writeComplaint" method="post">
        <button type="submit" class="log-btn">Write complain</button>
    </form>
    <form action="${pageContext.request.contextPath}/csr/sendPassword" method="post">
        <button type="submit" class="log-btn">Send password</button>
    </form>
</div>
<script>
    function initialize() {

        var input = document.getElementById('address');
        var autocomplete = new google.maps.places.Autocomplete(input);
    }

    google.maps.event.addDomListener(window, 'load', initialize);
</script>
</body>
</html>
