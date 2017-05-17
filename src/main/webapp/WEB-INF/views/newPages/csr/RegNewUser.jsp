<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <jsp:include page="../includes/head.jsp">
        <jsp:param name="tittle" value="Registration new user"/>
    </jsp:include>
</head>
<body>
<jsp:include page="../includes/headers/csrHeader.jsp">
    <jsp:param name="pageName" value="RegNewUser"/>
</jsp:include>
<div class="container" style="margin-bottom: 30px; width:60%; max-width: 400px;">
    <form action="${pageContext.request.contextPath}/csr/signUpUser"  modelAttribute="user" method="post">
        <div class="login-form">
            <h1 style="text-align: center">Registration new user</h1>
            <div class="form-group ">
                <select id="userType" name="userType" class="form-control" aria-required="true"
                        onChange="Selected(this)">
                    <option value="RESIDENTIAL">Residential</option>
                    <option value="BUSINESS">Business</option>
                </select>
            </div>
            <div id='Block1' style='display: none;'>
                <div class="form-group ">
                    <select id="companyName" name="companyName" class="form-control" aria-required="true">
                        <c:forEach var="customer" items="${customers}">
                            <option value="${customer.name}">${customer.name}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group ">
                    <input type="password" class="form-control" placeholder="Secret key" id="secretKey"
                           name="secretKey">
                    <i class="fa fa-lock"></i>
                </div>
            </div>
            <div class="form-group ">
                <input type="text" class="form-control" placeholder="First name " id="name" name="name" required
                       maxlength="16">
                <i class="fa fa-user"></i>
            </div>
            <div class="form-group ">
                <input type="text" class="form-control" placeholder="Last Name " id="surname" name="surname" required
                       maxlength="16">
                <i class="fa fa-user"></i>
            </div>
            <div class="form-group ">
                <input type="email" class="form-control" placeholder="Email " id="email" name="email" required>
                <i class="fa fa-user"></i>
            </div>
            <div class="form-group ">
                <input type="text" class="form-control" placeholder="Phone number " id="phone" name="phone" required
                       maxlength="12">
                <i class="fa fa-user"></i>
            </div>
            <div class="form-group ">
                <input type="text" class="form-control" placeholder="Address " id="address" name="address" required>
                <i class="fa fa-user"></i>
            </div>
            <div class="row">
                <button type="submit" class="btn btn-primary col-sm-12 col-xs-12">Sign up</button>
            </div>
        </div>
    </form>
</div>
<h2 style="text-align: center" id="infoMessage" hidden disabled="true">${msg}</h2>
<jsp:include page="../includes/footer.jsp"/>
<script class="cssdeck" src="//cdnjs.cloudflare.com/ajax/libs/jquery/1.8.0/jquery.min.js"></script>
<script type="text/javascript" src="<c:url value="/resources/js/user.type.js"/>"></script>
<script type="text/javascript"
        src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBhhghVMdW1rIbJCJupKdngdNk0k5JwaQE&libraries=places"></script>
<script>
    function Selected(a) {
        var label = a.value;
        if (label === "BUSINESS") {
            var d=document.getElementById("Block1");
            d.style.display = 'block';
        } else {
            document.getElementById("Block1").style.display = 'none';
        }
    }

    function initialize() {

        var input = document.getElementById('address');
        var autocomplete = new google.maps.places.Autocomplete(input);
    }

    google.maps.event.addDomListener(window, 'load', initialize);

    window.onload = function () {
        if (document.getElementById('infoMessage').innerHTML.trim() != '') {
            sweetAlert(document.getElementById('infoMessage').innerHTML);
        }
    };
    setTimeout(function () {
        document.getElementById("message").style.display = "none";
    }, 4000);
</script>
</body>
</html>
