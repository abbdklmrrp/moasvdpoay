/**
 * @author Revniuk Aleksandr
 */

/**
 * Field with old password.
 * @type {Element}
 */
var oldPass = document.getElementById('oldPassword');
/**
 * Field with new password.
 * @type {Element}
 */
var newPass = document.getElementById('password');
/**
 * Field need to confirm new password.
 * @type {Element}
 */
var confirmPass = document.getElementById('confirmPassword');
/**
 * Save button.
 * @type {Element}
 */
var saveBtn = document.getElementById('btn-save-profile');

/**
 * This function has main logic.
 */
$(document).ready(function () {
    $('#btn-edit-personal-info').click(function () {
        $('#edit-and-change').addClass("hide");
        $('#save-profile').removeClass("hide");
        $("#name").prop('readonly', false);
        $("#surname").prop('readonly', false);
        $("#address").prop('readonly', false);
        $("#phone").prop('readonly', false);
    });
    $('#btn-change-password').click(function () {
        $('#edit-and-change').addClass("hide");
        $('.change-pass').removeClass("hide");
        $('#save-profile').removeClass("hide");
        newPass.setAttribute("required", "required");
        confirmPass.setAttribute("required", "required");
        oldPass.setAttribute("required", "required")
    });
    $('#btn-cancel').click(function () {
        location.reload();
    });
});

/**
 * This function disable button 'save' if confirm password is not equal new password.
 */
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

/**
 * This function show modal window with error message.
 */
window.onload = function () {
    if (document.getElementById('errorMessage').innerHTML.trim() != '') {
        sweetAlert(document.getElementById('errorMessage').innerHTML);
    }
};

/**
 * This function initialize Google Maps API.
 */
function initialize() {

    var input = document.getElementById('address');
    var autocomplete = new google.maps.places.Autocomplete(input);
}
google.maps.event.addDomListener(window, 'load', initialize);