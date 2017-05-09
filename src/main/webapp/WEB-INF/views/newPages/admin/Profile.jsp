<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <jsp:include page="../includes/head.jsp">
        <jsp:param name="tittle" value="Profile"/>
    </jsp:include>
</head>
<body>
<jsp:include page="../includes/headers/adminHeader.jsp"/>
<div class="container" style="width:60%; max-width: 400px;">
    <jsp:include page="../includes/Profile.jsp"/>
</div>
<jsp:include page="../includes/footer.jsp"/>
<script type="text/javascript"
        src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBhhghVMdW1rIbJCJupKdngdNk0k5JwaQE&libraries=places"></script>
<script>
    $(document).ready(function () {
        var newPass = document.getElementById('newPassword');
        var confirmPass = document.getElementById('confirmPassword');
        $('#btn-edit-personal-info').click(function () {
            $('#edit-and-change').addClass("hide");
            $('#save-profile').removeClass("hide");
        });
        $('#btn-change-password').click(function () {
            $('#edit-and-change').addClass("hide");
            $('.change-pass').removeClass("hide");
            $('#save-profile').removeClass("hide");
            newPass.setAttribute("required", "required");
            confirmPass.setAttribute("required", "required");
        });
        function checkPass(password) {
            if (password!=$('#newPassword').value){
                $('#div-confirmPassword').addClass("has-error");
            }
            else {
                $('#div-confirmPassword').removeClass("has-error");
            }
        }
    });
    function initialize() {

        var input = document.getElementById('address');
        var autocomplete = new google.maps.places.Autocomplete(input);
    }

    google.maps.event.addDomListener(window, 'load', initialize);
</script>
</body>
</html>
