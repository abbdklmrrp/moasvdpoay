<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="../includes/head.jsp">
        <jsp:param name="tittle" value="Registration new user"/>
    </jsp:include>
</head>
<body>
<jsp:include page="../includes/headers/csrHeader.jsp"/>
<div class="container">
    <form action="${contextPath}/csr/signUpUser" method="post">
        <div class="login-form">
            <h1>Sign up</h1>
            <div class="form-group ">
                <select id="userType" name="userType" class="form-control" aria-required="true"
                        onChange="Selected(this)">
                    <option value="INDIVIDUAL">Individual</option>
                    <option value="LEGAL">Legal Entity</option>
                </select>
            </div>
            <div id='Block1' style='display: none;'>
                <div class="form-group ">
                    <select id="companyName" name="companyName" class="form-control" aria-required="true" >
                        <c:forEach var="customer" items="${customersName}">
                            <option value="${customer}">${customer}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group ">
                    <input type="password" class="form-control" placeholder="Secret key" id="SecretKey" name="secretKey">
                    <i class="fa fa-lock"></i>
                </div>
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
</div>
<jsp:include page="../includes/footer.jsp"/>
<script class="cssdeck" src="//cdnjs.cloudflare.com/ajax/libs/jquery/1.8.0/jquery.min.js"></script>
<script src="${contextPath}/resources/js/user.type.js"></script>
<script>
    function initialize() {

        var input = document.getElementById('address');
        var autocomplete = new google.maps.places.Autocomplete(input);
    }

    google.maps.event.addDomListener(window, 'load', initialize);
</script>
</body>
</html>
