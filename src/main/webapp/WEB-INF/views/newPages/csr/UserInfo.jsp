<%--
  Created by IntelliJ IDEA.
  User: Petro
  Date: 09.05.2017
  Time: 23:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../includes/head.jsp" %>
<html>
<head>
    <jsp:include page="../includes/head.jsp">
        <jsp:param name="tittle" value="User info"/>
    </jsp:include>
</head>
<body>
<jsp:include page="../includes/headers/csrHeader.jsp">
    <jsp:param name="pageName" value="UserInfo"/>
</jsp:include>
<div class="container" style="margin-bottom: 30px; width:60%; max-width: 400px;">
    <form id="details-form" modelAttribute="user" action="${contextPath}/csr/editUser" method="post">
        <div class="login-form">
            <h1 style="text-align: center">Personal information</h1>
            <br>
            <div class="form-group form-group-lg">
                <label class="col-sm-2 control-label">Name</label>
                <div class="col-sm-10">
                    <input style="border-bottom-width: 0;" type="text" class="form-control disabled" name="name"
                           id="name" value=${user.name} required maxlength="16"><br>
                </div>
            </div>
            <div class="form-group form-group-lg">
                <label class="col-sm-2 control-label">Surname</label><br>
                <div class="col-sm-10">
                    <input type="text" class="form-control" name="surname" id="surname"
                           value=${user.surname} required
                           maxlength="16"><br>
                </div>
            </div>
            <div class="form-group form-group-lg">
                <label class="col-sm-2 control-label">Email</label><br>
                <div class="col-sm-10">
                    <input type="email" class="form-control" name="email" id="email"
                           value=${user.email} required><br>
                </div>
            </div>
            <div class="form-group form-group-lg">
                <label class="col-sm-2 control-label">Phone</label><br>
                <div class="col-sm-10">
                    <input type="text" class="form-control" name="phone" id="phone" value=${user.phone} required
                           maxlength="12"><br>
                </div>
            </div>
            <div class="form-group form-group-lg">
                <label class="col-sm-2 control-label">Address</label><br>
                <div class="col-sm-10">
                    <input type="text" class="form-control" name="address" id="address" value="${user.address}"
                           required><br>
                </div>
            </div>
            <br>
            <div class="row">
                <button type="submit" class="btn btn-primary col-sm-12 col-xs-12">Save</button>
            </div>
        </div>
    </form>
</div>

<%--<form action="${contextPath}/csr/userTariffs" method="post">--%>
<%--<button type="submit" class="log-btn">Tariffs</button>--%>
<%--</form>--%>
<%--<form action="${contextPath}/csr/userServices" method="post">--%>
<%--<button type="submit" class="log-btn">Services</button>--%>
<%--</form>--%>
<form action="${contextPath}/csr/userOrders" method="post">
    <button type="submit" class="log-btn">Orders</button>
</form>
<form action="${contextPath}/csr/getCsrComplaint" method="get">
    <button type="submit" class="log-btn">Write complain</button>
</form>
<%--<form action="${conextPath}/csr/sendPassword" method="post">--%>
<%--<button type="submit" class="log-btn">Send password</button>--%>
<%--</form>--%>
</div>
<h2 style="text-align: center" id="infoMessage" hidden disabled="true">${msg}</h2>
<jsp:include page="../includes/footer.jsp"/>
</body>
<script type="text/javascript"
        src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBhhghVMdW1rIbJCJupKdngdNk0k5JwaQE&libraries=places"></script>
<script type="text/javascript" src="<c:url value="/resources/js/profile.js"/>"></script>
<script>
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
</html>
