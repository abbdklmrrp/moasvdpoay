/**
 * Created by Yuliya Pedash on 06.05.2017.
 */
function activateService(serviceId) {
    $.ajax({
        url: 'activateService',
        data: {serviceId: serviceId},
        type: "POST",
        dataType: 'text',
        //  async: false,
        success: function (resultMsg) {
            swal(resultMsg);
            setNewProductStatus(serviceId)
        },
        error: function () {
            swal("Sorry, an error on server has occurred", "please, try again.", "error");
            console.log("error");
        }
    })
}
function setNewProductStatus(serviceId) {
    jQuery.ajax({
        url: 'getNewOrderStatus',
        data: {serviceId: serviceId},
        type: "GET",
        dataType: 'json',
        success: function (newStatus) {
            var $statusElement = $('#' + serviceId);
            $statusElement.empty();

            if (newStatus == 'Active') {

                var $newStatus = "<input type=\"button\" onclick=\"deactivateService(" + serviceId + ")\" value=\"Deactivate\" class=\"btn btn-danger\">";
                $statusElement.html($newStatus);
            }
            else {
                $statusElement.html(newStatus);
            }
        },
        error: function () {
            console.log("Error while change page's view dynamically.");
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
                    if (resultMsg === '"success"') {
                        swal({
                            title: "This product for you was deactivated.",
                            type: "success"
                        });
                        var $statusElement = $('#' + serviceId);
                        $statusElement.empty();
                        var $newStatus = "<input type=\"button\" onclick=\"activateService(" + serviceId + ")\" value=\"Activate\" class=\"btn btn-success\">";
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
