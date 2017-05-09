<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="../includes/head.jsp">
        <jsp:param name="tittle" value="Registration new user"/>
    </jsp:include>
</head>
<body>
<jsp:include page="../includes/headers/adminHeader.jsp"/>
<div class="container" style="width:60%; max-width: 400px;">
    <form action="${contextPath}/admin/signUpCoworker" modelAttribute="user" method="post">
        <div class="login-form">
            <h1 style="text-align: center">Registration new user</h1>
            <div class="form-group">
                <select id="userType" name="userType" class="form-control" aria-required="true" required>
                    <option value="ADMIN">Admin</option>
                    <option value="CSR">CSR</option>
                    <option value="PMG">PMG</option>
                </select>
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
<jsp:include page="../includes/footer.jsp"/>
<script type="text/javascript"
        src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBhhghVMdW1rIbJCJupKdngdNk0k5JwaQE&libraries=places"></script>
<script>
    function Selected(a) {
        var label = a.value;
        if (label == "LEGAL") {
            var d = document.getElementById("Block1");
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
</script>
</body>
</html>

