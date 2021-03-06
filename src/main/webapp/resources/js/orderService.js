/**
 * @author Yuliya Pedash
 * @since 06.05.2017.
 */
$(document).ready(function () {
    $(".dropdown-toggle").dropdown();

});
function activateService(serviceId) {
    $.ajax({
        url: 'activateService',
        data: {serviceId: serviceId},
        type: "POST",
        dataType: 'text',
        success: function (resultMsg) {
            swal(resultMsg);
            var $statusElement = $('#service' + serviceId);
            $statusElement.empty();
            var $newStatus = '<input type="button" class="btn btn-danger btn-block"  value="Deactivate" onclick="deactivateService(' + serviceId + ')">';
            $statusElement.html($newStatus);
        },
        error: function () {
            swal("Sorry, an error on server has occurred", "please, try again.", "error");
            console.log("error");
        }
    })
}


function deactivateService(serviceId) {
    swal({
            title: "Are you sure you want to deactivate this service?",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "DD6B55",
            confirmButtonText: "Yes, deactivate it",
            closeOnConfirm: false
        },
        function () {
            $.ajax({
                url: 'deactivateService',
                data: {serviceId: serviceId},
                type: "POST",
                dataType: 'text',
                success: function (resultMsg) {
                    if (resultMsg == '"success"') {
                        swal({
                            title: "This product for you was deactivated.",
                            type: "success"
                        });
                        var $statusElement = $('#service' + serviceId);
                        $statusElement.empty();
                        var $newStatus = '<input type="button" class="btn btn-success btn-block"  value="Activate" onclick="activateService(' + serviceId + ')">';
                        $statusElement.html($newStatus);
                    }
                    else {
                        swal("Sorry, an error occurred while deactivating this product for you!", "Please, try again", "error");
                    }
                },

                error: function () {
                    swal("Sorry, an error on server has occurred", "please, try again.", "error");
                }
            })

        }
    )
    ;
}
