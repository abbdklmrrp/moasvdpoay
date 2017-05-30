/**
 * @author Anna Rysakova
 */
$(document).ready(function () {
    $('#btn-edit-product-info').click(function () {
        $('#edit-and-changes').addClass("hide");
        $('#save-product').removeClass("hide");
        $("#name").prop('readonly', false);
        $("#description").prop('readonly', false);
        $("#basePrice").prop('readonly', false);
        $("#customerTypeId").removeAttr('disabled');
        $("#durationInDays").prop('readonly', false);
        $("#processingStrategy").removeAttr('disabled');
        $("#notProcessingStrategy").removeAttr('disabled');
        $("#availableStatus").removeAttr('disabled');
        $("#notAvailableStatus").removeAttr('disabled');
    });

    $('#btn-cancel-product').click(function () {
        location.reload();
    });
});
