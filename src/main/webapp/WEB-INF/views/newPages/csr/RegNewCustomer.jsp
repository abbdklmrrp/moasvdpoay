<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="../includes/head.jsp">
        <jsp:param name="tittle" value="Registration new customer"/>
    </jsp:include>
</head>
<body>
<jsp:include page="../includes/headers/csrHeader.jsp"/>
<div class="container">
    <form action="${contextPath}/csr/createCustomer" modelAttribute="customer" method="post">
        <div class="login-form">
            <h2>Create new customer</h2>
            <div class="form-group ">
                <input type="text" class="form-control" placeholder="Company name " id="name" name="name">
                <i class="fa fa-user"></i>
            </div>
            <div class="form-group ">
                <input type="password" class="form-control" placeholder="Secret key" id="SecretKey" name="secretKey">
                <i class="fa fa-lock"></i>
            </div>
            <button type="submit" class="log-btn">Create</button>
        </div>
    </form>
</div>
<jsp:include page="../includes/footer.jsp"/>
</body>
</html>
