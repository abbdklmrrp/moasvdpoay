<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="../includes/head.jsp">
        <jsp:param name="tittle" value="Registration new customer"/>
    </jsp:include>
</head>
<body>
<jsp:include page="../includes/headers/csrHeader.jsp"/>
<div class="container" style="margin-bottom: 30px; width:60%; max-width: 400px;">
    <form action="${contextPath}/csr/createCustomer" modelAttribute="customer" method="post">
        <div class="login-form">
            <h1 style="text-align: center">Registration new customer</h1>
            <div class="form-group ">
                <input type="text" class="form-control" placeholder="Company name " id="name" name="name" required
                       maxlength="16">
                <i class="fa fa-user"></i>
            </div>
            <div class="form-group ">
                <input type="password" class="form-control" name="Secret key" id="SecretKey" placeholder="Secret key"
                       pattern="^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\s).*$" minlength="6" maxlength="24" required
                       title="•   Must be 6-24 characters
•   Must contain uppercase and lowercase letters, numbers">
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
</body>
<script>
    window.onload = function () {
        if (document.getElementById('infoMessage').innerHTML.trim() != '') {
            sweetAlert(document.getElementById('infoMessage').innerHTML);
        }
    };
    setTimeout(function () {
        document.getElementById("message").style.display = "none";
    }, 4000);
</script>
</html>
