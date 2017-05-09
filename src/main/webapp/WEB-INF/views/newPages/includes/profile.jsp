<div class="container" style="width:60%; max-width: 400px;">
    <form id="details-form" modelAttribute="user" action="${contextPath}/${pattern}/editProfile" method="post">
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
