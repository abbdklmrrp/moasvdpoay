<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Sign up</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/meyer-reset/2.0/reset.min.css">
    <link href="${contextPath}/resources/css/basic.css" rel="stylesheet"/>

    <script src="https://cdnjs.cloudflare.com/ajax/libs/prefixfree/1.0.7/prefixfree.min.js"></script>
<body>

<form method="POST" action="verificationCompany">
    <div class="login-form">
        <h1>Please input company name and secret key</h1>
        <div class="form-group ">
            <input type="text" class="form-control" placeholder="Company name " id="CompanyName" name="companyName">
            <i class="fa fa-user"></i>
        </div>
        <div>
            <input type="password" class="form-control" placeholder="Secret key" id="SecretKey" name="secretKey">
            <i class="fa fa-lock"></i>
        </div>
        <button type="submit" class="log-btn">Check</button>
    </div>
</form>
<script class="cssdeck" src="//cdnjs.cloudflare.com/ajax/libs/jquery/1.8.0/jquery.min.js"></script>
</body>
</html>
