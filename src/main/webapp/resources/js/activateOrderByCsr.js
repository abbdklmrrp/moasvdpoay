function activateOrder(orderId) {
    $.ajax({
        url: 'activateOrder',
        data: {orderId: orderId},
        type: "POST",
        dataType: 'text',
        success: function (resultMsg) {
            if (resultMsg === '"success"') {
                swal({
                    title: "Order  was activated.",
                    type: "success"
                });
                var $statusElement = $('#actvBtn' + orderId);
                $statusElement.empty();
                var $newStatus = $(' <label>Activated!</label>');
                $newStatus.appendTo($statusElement);
            }
            else {
                swal("Sorry, an error occurred while activating this order!", "Please, try again", "error");
            }
        },
        error: function () {
            swal("Sorry, an error on server has occurred", "please, try again.", "error");
            console.log("error");
        }
    })
}
