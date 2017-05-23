
function orderInfo(orderId) {
    $.ajax({
        url: 'orderInfo',
        data: {orderId: orderId},
        type: "POST",
        dataType: 'json',
        success: function (resultMsg) {
            var length=resultMsg.length;
            var info=resultMsg.substring(0,length);
            swal({
                title:"Info",
                text:info
            })
        },
        error: function () {
            swal("Sorry, an error on server has occurred", "please, try again.", "error");
            console.log("error");
        }
    })
}
