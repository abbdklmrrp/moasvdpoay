<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="../includes/head.jsp">
        <jsp:param name="tittle" value="Registration employee"/>
    </jsp:include>
</head>
<body>
<jsp:include page="../includes/headers/businessHeader.jsp">
    <jsp:param name="pageName" value="RegEmployee"/>
</jsp:include>
<section class="container">
    <div class="col-md-4"></div>
    <div class="col-md-4">
        <div class="col-md-1"></div>
        <div class="col-md-10">
            <form action="${pageContext.request.contextPath}/business/regEmployee" modelAttribute="user" method="post">
                <div class="login-form">
                    <h1 style="text-align: center">Registration</h1>
                    <br>
                    <div class="form-group ">
                        <input type="text" class="form-control" placeholder="First name " id="name" name="name" required
                               maxlength="16">
                        <i class="fa fa-user"></i>
                    </div>
                    <div class="form-group ">
                        <input type="text" class="form-control" placeholder="Last Name " id="surname" name="surname"
                               required
                               maxlength="16">
                        <i class="fa fa-user"></i>
                    </div>
                    <div class="form-group ">
                        <input type="email" class="form-control" placeholder="Email " id="email" name="email" required>
                        <i class="fa fa-user"></i>
                    </div>
                    <div class="form-group ">
                        <input type="text" class="form-control" placeholder="Phone number " id="phone" name="phone"
                               required
                               maxlength="12">
                        <i class="fa fa-user"></i>
                    </div>
                    <div class="form-group ">
                        <input type="text" class="form-control" placeholder="Address " id="address" name="address"
                               required>
                        <i class="fa fa-user"></i>
                    </div>
                    <div style="text-align: center">
                        <button type="submit" class="btn btn-primary">Confirm</button>
                    </div>
                </div>
            </form>
        </div>
        <div class="col-md-1"></div>
    </div>
    <div class="col-md-4"></div>
</section>
<script type="text/javascript"
        src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBhhghVMdW1rIbJCJupKdngdNk0k5JwaQE&libraries=places"></script>
<script>
    window.onload = function () {
        var result = "${result}";
        if (result != '') {
            sweetAlert(result);
        }
    };
    function initialize() {
        var input = document.getElementById('address');
        var autocomplete = new google.maps.places.Autocomplete(input);
    };
    google.maps.event.addDomListener(window, 'load', initialize);
</script>

<jsp:include page="../includes/footer.jsp"/>
</body>
</html>
