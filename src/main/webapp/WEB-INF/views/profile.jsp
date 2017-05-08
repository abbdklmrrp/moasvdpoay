<%--
  Created by IntelliJ IDEA.
  User: Petro
  Date: 05.05.2017
  Time: 22:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Profile</title>
    <link href="${contextPath}/resources/css/basic.css" rel="stylesheet"/>
    <script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBhhghVMdW1rIbJCJupKdngdNk0k5JwaQE&libraries=places"></script>

</head>
<body>
<form  id="details-form" modelAttribute="user" action="${contextPath}/${pattern}/editProfile" method="post">
    <div class="login-form">
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
<script>
    function initialize() {

        var input = document.getElementById('address');
        var autocomplete = new google.maps.places.Autocomplete(input);
    }

    google.maps.event.addDomListener(window, 'load', initialize);
</script>
</body>
</html>

