/**
 * Created by Yuliya Pedash on 06.05.2017.
 */
function activateService(serviceId) {
    $.ajax({
        url: 'activateService',
        data: {serviceId: serviceId},
        type: "POST",
        dataType: 'text',
        async: false,
        success: function (resultMsg) {
            swal(resultMsg);
            setNewProductStatus(serviceId)
        },
        error: function () {
            swal("Sorry, an error on server has occurred", "please, try again.", "error");
            console.log("error");
        }
    })
    // event.preventDefault();
}
function setNewProductStatus(serviceId) {
    jQuery.ajax({
        url: 'getNewOrder',
        data: {serviceId: serviceId},
        type: "GET",
        dataType: 'json',
        success: function (order) {
            var $statusElement = $('#' + serviceId);
            $statusElement.empty();
            //TODO  fix new buttons
            if (order.currentStatus === 'Active') {
                var $newStatus = $('<input/>', {
                    type: 'button',
                    value: 'Deactivate',
                    class: "btn btn-danger"
                });
                $newStatus.onclick = function () {
                    deactivateService(serviceId);
                }
                // var $newStatus = document.createElement("INPUT");
                // $newStatus.setAttribute("type", "button");
                // $newStatus.setAttribute("value" ,"Deactivate");
                // $newStatus.setAttribute("class", "btn btn-danger");
                // $newStatus.addEventListener('click', function () {
                //     deactivateService(serviceId);
                // }, false);
                $newStatus.appendTo($statusElement)
            }
            else {
                $statusElement.html(order.currentStatus);
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
                        var $newStatus = $('<input/>', {
                            type: 'button',
                            value: 'Activate',
                            class: "btn btn-success"
                        });
                        // $newStatus.addEventListener('click', function(){
                        //     alert(serviceId);
                        //     activateService(serviceId);
                        // });
                        $newStatus.onclick = function () {
                            activateService(serviceId);
                        }
                        $newStatus.appendTo($statusElement);
                    }
                    else {
                        swal("Sorry, an error occurred while deactivating this product for you!", "Please, try again", "error");
                    }
                },

                error: function () {
                    swal("Sorry, an error on server has occurred", "please, try again.", "error");
                    console.log("error");
                }
            })
        }
    )
    ;
}
