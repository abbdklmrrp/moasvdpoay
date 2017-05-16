function assignToMe(orderId) {
    $.ajax({
        url: 'assignOrder',
        data: {orderId: orderId},
        type: "POST",
        dataType: 'text',
        success: function (resultMsg) {
            if (resultMsg === '"success"') {
                swal({
                    title: "Order assigned",
                    type: "success"
                });
                var $statusElement = $('#assign' + orderId);
                $statusElement.empty();
                var $newStatus = $('Assigned');
                $newStatus.appendTo($statusElement);
            }
            else {
                swal("Sorry, an error occurred while you assigned order", "Please, refresh page", "error");
            }
        },
        error: function () {
            swal("Sorry, an error on server has occurred", "please, try again.", "error");
            console.log("error");
        }
    })
}
