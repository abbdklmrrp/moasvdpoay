var saveBtn = document.getElementById('btn-save-product');
$(document).ready(function () {
    $('#btn-edit-product-info').click(function () {
        $('#edit-and-changes').addClass("hide");
        $('#save-product').removeClass("hide");
        $("#Name").prop('readonly', false);
        $("#Description").prop('readonly', false);
        $("#basePrice").prop('readonly', false);
        $("#customerTypeId").removeAttr('disabled');
        $("#durationInDays").prop('readonly', false);
        $("#processingStrategy").removeAttr('disabled');
        $("#notProcessingStrategy").removeAttr('disabled');
        $("#availableStatus").removeAttr('disabled');
        $("#notAvailableStatus").removeAttr('disabled');
    });

    $('#btn-cancel-product').click(function () {
        $('#edit-and-changes').removeClass("hide");
        $('#save-product').addClass("hide");
        $("#Name").prop('readonly', true);
        $("#Description").prop('readonly', true);
        $("#basePrice").prop('readonly', true);
        $("#customerTypeId").prop('disabled', 'disabled');
        $("#durationInDays").prop('readonly', true);
        $("#processingStrategy").prop('disabled', 'disabled');
        $("#notProcessingStrategy").prop('disabled', 'disabled');
        $("#availableStatus").prop('disabled', 'disabled');
        $("#notAvailableStatus").prop('disabled', 'disabled');
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