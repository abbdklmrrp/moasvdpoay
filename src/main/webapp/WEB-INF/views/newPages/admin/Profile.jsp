<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../includes/head.jsp" %>
<html>
<head>
    <jsp:include page="../includes/head.jsp">
        <jsp:param name="tittle" value="Profile"/>
    </jsp:include>
</head>
<body>
<jsp:include page="../includes/headers/adminHeader.jsp"/>
<div class="container">
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
</div>
<jsp:include page="../includes/footer.jsp"/>
</body>
</html>
