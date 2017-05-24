<div class="container" style="margin-bottom: 30px; width:60%; max-width: 400px;">

    <form id="details-form" modelAttribute="user" action="${pageContext.request.contextPath}/${pattern}/editProfile"
          method="post">
        <div class="login-form">
            <h1 style="text-align: center">Personal information</h1>
            <br>
            <div class="form-group form-group-lg">
                <label class="col-sm-2 col-xs-3 control-label">Name</label>
                <div class="col-sm-10 col-xs-9">
                    <input readonly style="border-bottom-width: 0;" type="text" class="form-control" name="name"
                           id="name" pattern="^[a-zA-Z]+$" title="-   Must be only characters A-z" value=${user.name} maxlength="16" required><br>
                </div>
            </div>
            <div class="form-group form-group-lg">
                <label class="col-sm-2 col-xs-3 control-label">Surname</label><br>
                <div class="col-sm-10 col-xs-9">
                    <input readonly type="text" class="form-control" name="surname" pattern="^[a-zA-Z-]+$" title="-   Must be only characters A-z" id="surname"
                           value=${user.surname} required
                           maxlength="16"><br>
                </div>
            </div>
            <div class="form-group form-group-lg">
                <label class="col-sm-2 col-xs-3 control-label">Email</label><br>
                <div class="col-sm-10 col-xs-9">
                    <input readonly type="email" class="form-control" name="email" id="email" title="-   Must contain @"
                           value=${user.email} required><br>
                </div>
            </div>
            <div class="form-group form-group-lg">
                <label class="col-sm-2 col-xs-3 control-label">Phone</label><br>
                <div class="col-sm-10 col-xs-9">
                    <input readonly type="text" class="form-control" name="phone" id="phone" title="Phone number" value=${user.phone} required
                           maxlength="12"><br>
                </div>
            </div>
            <div class="form-group form-group-lg">
                <label class="col-sm-2 col-xs-3 control-label">Address</label><br>
                <div class="col-sm-10 col-xs-9">
                    <input readonly type="text" class="form-control" name="address" id="address" value="${user.address}"
                           required><br>
                </div>
            </div>
            <div class="form-group form-group-lg hide change-pass" id="div-oldPassword">
                <label class="col-sm-4 control-label" for="oldPassword">Old password</label><br>
                <div class="col-sm-8">
                    <input type="password" autocomplete="false" class="form-control" name="oldPassword" id="oldPassword"><br>
                </div>
            </div>
            <div class="form-group form-group-lg hide change-pass">
                <label class="col-sm-4 control-label">New password</label><br>
                <div class="col-sm-8">
                    <input type="password" autocomplete="false" class="form-control" name="password" id="password"
                           pattern="^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\s).*$" minlength="6" maxlength="24" title="-   Must be 6-24 characters
-   Must contain uppercase and lowercase letters, numbers"><br>
                </div>
            </div>
            <div class="form-group form-group-lg hide change-pass" id="div-confirmPassword">
                <label class="col-sm-4 control-label" for="confirmPassword">Confirm password</label><br>
                <div class="col-sm-8">
                    <input type="password" autocomplete="false" class="form-control" name="confirmPassword" id="confirmPassword"><br>
                </div>
            </div>
            <div class="row hide" id="save-profile">
                <button type="button" class="btn btn-danger col-sm-5 col-xs-5" id="btn-cancel">Cancel
                </button>
                <div class="col-sm-2 col-xs-2"></div>
                <button type="submit" class="btn btn-success col-sm-5 col-xs-5" id="btn-save-profile">Save
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
    <h2 style="text-align: center" id="errorMessage" hidden disabled="true">${msg}</h2>
</div>
