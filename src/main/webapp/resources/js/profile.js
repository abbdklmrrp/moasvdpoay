var newPass = document.getElementById('password');
var confirmPass = document.getElementById('confirmPassword');
var saveBtn = document.getElementById('btn-save-profile');
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
    });//$("#name").prop('readonly', false);
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
window.onload = function () {
    if (document.getElementById('message').innerHTML.trim() != '') {
        swal(document.getElementById('message').innerHTML);
    }
};
setTimeout(function () {
    document.getElementById("message").style.display = "none";
}, 4000);
function initialize() {

    var input = document.getElementById('address');
    var autocomplete = new google.maps.places.Autocomplete(input);
}
google.maps.event.addDomListener(window, 'load', initialize);