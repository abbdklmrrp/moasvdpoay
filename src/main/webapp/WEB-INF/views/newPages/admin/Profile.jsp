<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="../includes/head.jsp">
        <jsp:param name="tittle" value="Profile"/>
    </jsp:include>
</head>
<body>
<jsp:include page="../includes/headers/adminHeader.jsp"/>
<div class="container" style="width:60%; max-width: 400px;">
    <form id="details-form" modelAttribute="user" action="${contextPath}/${pattern}/editProfile" method="post">
        <div class="login-form">
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
                    <input type="text" class="form-control" name="surname" id="surname" value=${user.surname} required maxlength="16"><br>
                </div>
            </div>
            <div class="form-group form-group-lg">
                <label class="col-sm-2 control-label">Email</label><br>
                <div class="col-sm-10">
                    <input type="email" class="form-control" name="email" id="email" value=${user.email} required><br>
                </div>
            </div>
            <div class="form-group form-group-lg">
                <label class="col-sm-2 control-label">Phone</label><br>
                <div class="col-sm-10">
                    <input type="text" class="form-control" name="phone" id="phone" value=${user.phone} required maxlength="12"><br>
                </div>
            </div>
            <div class="form-group form-group-lg">
                <label class="col-sm-2 control-label">City</label><br>
                <div class="col-sm-10">
                    <input type="text" class="form-control" name="address" id="address" value="${user.address}" required><br>
                </div>
            </div>
            <div class="form-group form-group-lg hide change-pass">
                <label class="col-sm-4 control-label">New password</label><br>
                <div class="col-sm-8">
                    <input type="password" class="form-control" name="newPassword" id="newPassword" minlength="8" pattern="^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\s).*$"><br>
                </div>
            </div>
            <div class="form-group form-group-lg hide change-pass">
                <label class="col-sm-4 control-label">Confirm password</label><br>
                <div class="col-sm-8">
                    <input type="password" class="form-control" name="confirmPassword" id="confirmPassword" minlength="8" pattern="^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\s).*$"><br>
                </div>
            </div>
            <div class="row hide" id="save-profile">
                <div class="col-sm-2 col-xs-2"></div>
                <button type="submit" class="btn btn-primary col-sm-10 col-xs-10" id="btn-save-profile">Save</button>
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
<jsp:include page="../includes/footer.jsp"/>
<script type="text/javascript"
        src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBhhghVMdW1rIbJCJupKdngdNk0k5JwaQE&libraries=places"></script>
<script>
    $(document).ready(function () {
        $('#btn-edit-personal-info').click(function () {
            $('#edit-and-change').addClass("hide");
            $('#save-profile').removeClass("hide");
        });
        $('#btn-change-password').click(function () {
            $('#edit-and-change').addClass("hide");
            $('.change-pass').removeClass("hide");
            $('#save-profile').removeClass("hide");
            var newPass = document.getElementById('newPassword');
            var confirmPass = document.getElementById('confirmPassword');
            newPass.setAttribute("required", "required");
            confirmPass.setAttribute("required", "required");
        });
    });
    function initialize() {

        var input = document.getElementById('address');
        var autocomplete = new google.maps.places.Autocomplete(input);
    }

    google.maps.event.addDomListener(window, 'load', initialize);
</script>
</body>
</html>
