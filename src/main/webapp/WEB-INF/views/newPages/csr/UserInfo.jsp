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
        <jsp:param name="pageName" value="Users"/>
    </jsp:include>
    <div class="col-md-2">
        <jsp:include page="../includes/csrTabMenuBegin.jsp">
            <jsp:param name="page" value="UserInfo"/>
        </jsp:include>
    </div>
    <div class="col-md-2"></div>
    <div class="col-md-4">
        <form id="details-form" modelAttribute="user" action="${pageContext.request.contextPath}/csr/editUser" method="post">
            <div class="login-form">
                <h1 style="text-align: center">Personal information</h1>
                <br>
                <div class="form-group form-group-lg">
                    <label class="col-sm-3 col-xs-3 control-label">Name</label>
                    <div class="col-sm-9 col-xs-9">
                        <input style="border-bottom-width: 0;" type="text" class="form-control disabled" name="name"
                               id="name" value=${user.name} required maxlength="16"><br>
                    </div>
                </div>
                <div class="form-group form-group-lg">
                    <label class="col-sm-3 col-xs-3 control-label">Surname</label><br>
                    <div class="col-sm-9 col-xs-9">
                        <input type="text" class="form-control" name="surname" id="surname"
                               value=${user.surname} required
                               maxlength="16"><br>
                    </div>
                </div>
                <div class="form-group form-group-lg">
                    <label class="col-sm-3 col-xs-3 control-label">Email</label><br>
                    <div class="col-sm-9 col-xs-9">
                        <input type="email" class="form-control" name="email" id="email"
                               value=${user.email} required><br>
                    </div>
                </div>
                <div class="form-group form-group-lg">
                    <label class="col-sm-3 col-xs-3 control-label">Phone</label><br>
                    <div class="col-sm-9 col-xs-9">
                        <input type="text" class="form-control" name="phone" id="phone" value=${user.phone} required
                               maxlength="12"><br>
                    </div>
                </div>
                <div class="form-group form-group-lg">
                    <label class="col-sm-3 col-xs-3 control-label">Address</label><br>
                    <div class="col-sm-9 col-xs-9">
                        <input type="text" class="form-control" name="address" id="address" value="${user.address}"
                               required><br>
                    </div>
                </div>
                <br>
                    <button type="submit" class="btn btn-primary col-sm-5 col-xs-5">Save</button>
            </div>
        </form>
        <div class="col-sm-2 col-xs-2"></div>
        <button class="btn btn-success col-sm-5 col-xs-5" onclick="sendPassword(${user.id})">Send password</button>
    </div>
    </div>
    <div class="col-md-2"></div>
    <div class="col-md-2"></div>
    <div class="container" style="margin-bottom: 30px; width:60%; max-width: 400px;">


<%--<form action="${contextPath}/csr/userTariffs" method="POST">--%>
    <%--<button type="submit" class="btn btn-primary">User tariffs</button>--%>
<%--</form>--%>
<%--<form action="${contextPath}/csr/userServices" method="post">--%>
<%--<button type="submit" class="log-btn">Services</button>--%>
<%--</form>--%>
<%--<form action="${pageContext.request.contextPath}/csr/userOrders" method="post">--%>
    <%--<button type="submit" class="log-btn">Orders</button>--%>
<%--</form>--%>
<%--<form action="${pageContext.request.contextPath}/csr/getCsrComplaint" method="get">--%>
    <%--<button type="submit" class="log-btn">Write complain</button>--%>
<%--</form>--%>
<%--<form action="${pageContext.request.contextPath}/csr/userOrders" method="POST">--%>
    <%--<button type="submit" class="btn btn-primary">User orders</button>--%>
<%--</form>--%>
<%--<form action="${pageContext.request.contextPath}/csr/getCsrComplaint" method="get">--%>
    <%--<button type="submit" class="log-btn">Write complain</button>--%>
<%--</form>--%>
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

    function sendPassword(userId) {
        $.ajax({
            url: 'sendPassword',
            data: {userId: userId},
            type: "POST",
            dataType: 'text',
            success: function (resultMsg) {
               swal(resultMsg);
            },
            error: function () {
                swal("Sorry, an error on server has occurred", "please, try again.", "error");
                console.log("error");
            }
        })
    }
</script>
</html>
