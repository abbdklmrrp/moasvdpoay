var saveBtn = document.getElementById('btn-save-price-in-region');
$(document).ready(function () {
    $('#btn-edit-price-in-region-info').click(function () {
        $('#edit-and-changes-price-in-region').addClass("hide");
        $('#save-price-in-region').removeClass("hide");
        $("#priceByRegion").prop('readonly', false);
    });

    $('#btn-cancel-price-in-region').click(function () {
        $('#edit-and-changes-price-in-region').removeClass("hide");
        $('#save-price-in-region').addClass("hide");
        $("#priceByRegion").prop('readonly', true);
        saveBtn.removeAttribute("disabled");
    });
});

window.onload = function () {
    if (document.getElementById('errorMessage').innerHTML.trim() != '') {
        sweetAlert(document.getElementById('errorMessage').innerHTML);
    }
};
setTimeout(function () {
    document.getElementById("message").style.display = "none";
}, 4000);
