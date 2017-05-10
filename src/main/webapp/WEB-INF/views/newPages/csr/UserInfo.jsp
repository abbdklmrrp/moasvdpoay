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
<jsp:include page="../includes/headers/csrHeader.jsp"/>
<div class="container" style="...">
    <form  id="details-form" modelAttribute="user" action="${contextPath}/csr/editUser" method="post">
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
                <label class="col-sm-2 control-label">City</label><br>
                <div class="col-sm-10">
                    <input type="text" class="form-control" name="address" id="address" value="${user.address}"
                           required><br>
                </div>
            </div>
            <div class="form-group form-group-lg hide change-pass">
                <label class="col-sm-4 control-label">New password</label><br>
                <div class="col-sm-8">
                    <input type="password" class="form-control" name="newPassword" id="newPassword"
                           pattern="^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\s).*$" minlength="6" maxlength="24" title="•   Must be 6-24 characters
•   Must contain uppercase and lowercase letters, numbers"><br>
                </div>
            </div>
            <div class="form-group form-group-lg hide change-pass" id="div-confirmPassword">
                <label class="col-sm-4 control-label" for="confirmPassword">Confirm password</label><br>
                <div class="col-sm-8">
                    <input type="password" class="form-control" name="confirmPassword" id="confirmPassword"><br>
                </div>
            </div>
            <br>
            <div class="row hide" id="save-profile">
                <div class="col-sm-2 col-xs-2"></div>
                <button type="submit" class="btn btn-primary col-sm-10 col-xs-10" id="btn-save-profile">Save
                </button>
            </div>
        </div>
    </form>
    <div class="row" id="edit-and-change">
        <button class="btn btn-primary col-sm-5 col-xs-5" id="btn-edit-personal-info">Edit personal info
        </button>
        <div class="col-sm-2 col-xs-2"></div>
        <button class="btn btn-primary col-sm-5 col-xs-5" id="btn-change-password">Change password</button>
    </div>
</div>

        <form action="${contextPath}/csr/userTariffs" method="post">
            <button type="submit" class="log-btn">Tariffs</button>
        </form>
        <form action="${contextPath}/csr/userServices" method="post">
            <button type="submit" class="log-btn">Services</button>
        </form>
        <form action="${contextPath}/csr/userOrders" method="post">
            <button type="submit" class="log-btn">Orders</button>
        </form>
        <form action="${contextPath}/csr/writeComplaint" method="get">
            <button type="submit" class="log-btn">Write complain</button>
        </form>
        <form action="${conextPath}/csr/sendPassword" method="post">
            <button type="submit" class="log-btn">Send password</button>
        </form>
</div>
<jsp:include page="../includes/footer.jsp"/>
</body>
<script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBhhghVMdW1rIbJCJupKdngdNk0k5JwaQE&libraries=places"></script>
<script type="text/javascript" src="<c:url value="/resources/js/profile.js"/>"></script>
<script>
    function updateUser() {
        var user={
            name:$('#name').val(),
            surname:$('#surname').val(),
            phone:$('#phone').val(),
            address:$('#address').val()
        };
        var name=$('#name').val();
//        user["name"]=$('#name');
//        user["surname"]=$('#surname');
//        user["phone"]=$('#phone');
//        user["address"]=$('#address');
        $.ajax({
            url: 'editUser',
            data: {user:user},
            type: "POST",
            dataType: 'text',
            success: function (resultMsg) {
                if (resultMsg === '"success"') {
                    swal({
                        title: "The user was successfully updated.",
                        type: "success"
                    });
                }
                else {
                    swal("Sorry, an error occurred!", "Please, try again", "error");
                }
            },
            error: function () {
                swal("Sorry, an error on server has occurred", "please, try again.", "error");
                console.log("error");
            }
        })
    }
    function initialize() {

        var input = document.getElementById('address');
        var autocomplete = new google.maps.places.Autocomplete(input);
    }

    google.maps.event.addDomListener(window, 'load', initialize);
</script>
</html>
