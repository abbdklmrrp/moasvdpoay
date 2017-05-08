<%--
  Created by IntelliJ IDEA.
  User: Petro
  Date: 17.04.2017
  Time: 18:58
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sign up</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/meyer-reset/2.0/reset.min.css">
    <link href="${contextPath}/resources/css/basic.css" rel="stylesheet"/>
    <script src="${contextPath}/resources/js/user.type.js"></script>
    <script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBhhghVMdW1rIbJCJupKdngdNk0k5JwaQE&libraries=places"></script>

</head>
<body>
<form action="${contextPath}/admin/signUpCoworker" modelAttribute="user" method="post">
    <div class="login-form">
        <h1>Sign up</h1>
       <div class="form-group">
           <select id="userType" name="userType" class="form-control" aria-required="true">
               <option value="ADMIN">Admin</option>
               <option value="CSR">CSR</option>
               <option value="PMG">PMG</option>
           </select>
       </div>
        <div class="form-group ">
            <input type="text" class="form-control" placeholder="First name " id="name" name="name">
            <i class="fa fa-user"></i>
        </div>
        <div class="form-group ">
            <input type="text" class="form-control" placeholder="Last Name " id="surname" name="surname">
            <i class="fa fa-user"></i>
        </div>
        <div class="form-group ">
            <input type="text" class="form-control" placeholder="Email " id="email" name="email">
            <i class="fa fa-user"></i>
        </div>
        <div class="form-group ">
            <input type="text" class="form-control" placeholder="Phone number " id="phone" name="phone">
            <i class="fa fa-user"></i>
        </div>
        <div class="form-group ">
            <input type="text" class="form-control" placeholder="Address " id="address" name="address">
            <i class="fa fa-user"></i>
        </div>
        <button type="submit" class="log-btn">Sign up</button>
    </div>
</form>
<script class="cssdeck" src="//cdnjs.cloudflare.com/ajax/libs/jquery/1.8.0/jquery.min.js"></script>
<script>
    function initialize() {

        var input = document.getElementById('address');
        var autocomplete = new google.maps.places.Autocomplete(input);
    }

    google.maps.event.addDomListener(window, 'load', initialize);
</script>
</body>
</html>
