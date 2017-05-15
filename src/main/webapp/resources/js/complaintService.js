function saveComplaint() {
    var productId=$("#products").val();
    var description=$("#description").val();
    $.ajax({
        url: 'writeComplaint',
        data: {productId:productId,description:description},
        type: "POST",
        dataType: 'text',
        success: function (resultMsg) {
            if (resultMsg === '"success"') {
                swal({
                    title: "The complaint was successfully sent.",
                    type: "success"
                });
                $('#description').val("");
            }
            else {
                swal("Sorry, an error occurred!", "Please, try again", "error");
            }
        },
        error: function () {
            swal("Sorry, an error on server has occurred", "please, try again.", "error");
            console.log("error");
        }
    })
}
