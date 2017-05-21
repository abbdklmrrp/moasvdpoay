function viewProductPriceInfo(productId) {
    $.ajax({
        url: 'viewProductPriceInfo',
        data: {productId: productId},
        type: "POST",
        dataType: 'json',
        success: function (resultMsg) {
            var length = resultMsg.length;
            var info = resultMsg.substring(0, length - 1);
            swal({
                title: "Info",
                text: info
            })
        },
        error: function () {
            swal("Sorry, an error on server has occurred", "please, try again.", "error");
            console.log("error");
        }
    })
}
