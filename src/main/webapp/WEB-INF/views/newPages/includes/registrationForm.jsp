<link href="/resources/css/basic.css" rel="stylesheet"/>
<script src="/resources/js/user.type.js"></script>
<script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBhhghVMdW1rIbJCJupKdngdNk0k5JwaQE&libraries=places"></script>
<form action="${contextPath}/signUp" modelAttribute="user" method="post">
    <div class="login-form">
        <h1>Registration</h1>
        <div class="form-group ">
            <select id="userType" name="userType" class="form-control" aria-required="true"
                    onChange="Selected(this)">
                <option value="INDIVIDUAL">Individual</option>
                <option value="LEGAL">Legal Entity</option>
            </select>
        </div>
        <div id='Block1' style='display: none;'>
            <div class="form-group ">
                <input type="text" class="form-control" placeholder="Company name " id="CompanyName" name="companyName">
                <i class="fa fa-user"></i>
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
            <input type="text" class="form-control" placeholder="Address" id="address" name="address">
            <i class="fa fa-user"></i>
        </div>
        <div class="form-group log-status">
            <input type="password" class="form-control" placeholder="Password" id="Password" name="password">
            <i class="fa fa-lock"></i>
        </div>
        <button type="submit" class="btn btn-primary">Create</button>
    </div>
</form>
<script class="cssdeck" src="//cdnjs.cloudflare.com/ajax/libs/jquery/1.8.0/jquery.min.js"></script>
<script>
    function initialize() {
        var input = document.getElementById('address');
        var autocomplete = new google.maps.places.Autocomplete(input);
    }
    google.maps.event.addDomListener(window, 'load', initialize);
</script>