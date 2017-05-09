var newPass = document.getElementById('newPassword');
var confirmPass = document.getElementById('confirmPassword');
var saveBtn = document.getElementById('btn-save-profile');
$(document).ready(function () {
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
});
confirmPass.onkeyup = function () {
    if (this.value != newPass.value) {
        $('#div-confirmPassword').addClass("has-error");
        saveBtn.setAttribute("disabled", "disabled");
    }
    else {
        $('#div-confirmPassword').removeClass("has-error");
        saveBtn.removeAttribute("disabled");
    }
};
function initialize() {

    var input = document.getElementById('address');
    var autocomplete = new google.maps.places.Autocomplete(input);
}

google.maps.event.addDomListener(window, 'load', initialize);