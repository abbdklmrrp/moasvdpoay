/**
 * Created by Yuliya Pedash on 08.05.2017.
 */
$(document).ready(function () {

    $("#formWithDates").hide();

    // $(".toggle-form-btn").click(function () {
    //
    //     $("#formWithDates").toggle();
    //     $("#orderId").value =
    //
    // });
    $("#formWithDates").submit(function(e) {
        // reference to form object
        var form = this;
        // for stopping the default action of element
        e.preventDefault();
        // mapthat will hold form data
        var formData = {}
        //iterate over form elements
        $.each(this, function(i, v){
            var input = $(v);
            formData[input.attr("name")] = input.val();
            delete formData["undefined"];
        });
        console.log(formData);

        if (areDatesCorrect()) {
            jQuery.ajax({
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            url: 'suspend',
            type: "POST",
            dataType: "json",
           // data: jQuery("#formWithDates").serialize(),
            data: JSON.stringify(formData),
            success: function (resultMsg) {
                swal(resultMsg);
            },
            error: function () {
                swal("Sorry, an error on server has occurred", "please, try again.", "error");
                console.log("error");
            }
        });
        }
    });

});
function toggleFormFunc(orderId) {
    $("#formWithDates").toggle();
    $("#orderId").val(orderId);
}
function areDatesCorrect() {
    var start = new Date($('#beginDate').val());
    var end = new Date($('#endDate').val());
    var currentDate = new Date();
    currentDate.setHours(0, 0, 0, 0);
    start.setHours(0,0,0,0);
    if ($('#beginDate').val() == '' || $('#endDate').val() == '') {
        swal("Please, enter both, begin and end date.");
        return false;
    }
    if (end.getTime() < start.getTime()) {
        swal({
            title: "Begin date of order's suspense should be earlier than date of end of order's suspense.",
            type: "error"
        });
        return false;
    }
    if (start.getTime() <  currentDate.getTime()){
        swal({
            title: "Begin date of order's suspense should not not be earlier than today .",
            type: "error"
        });
    }

    else {
        return true;
    }
}

// function suspendOrderBtnClick(orderId){
//     $("#formWithDates").show();
//     $(".suspendButton").attr("id", orderId);
//
// }
function getDataFromWithDates() {
    var formData = {};
    formData["orderId"] = $()
}
// function suspendOrder() {
//     if (areDatesCorrect()) {
//         var formData = getDataFromWithDates();
//         jQuery.ajax({
//             headers: {
//                 'Accept': 'application/json',
//                 'Content-Type': 'application/json'
//             },
//             url: 'suspend',
//             type: "POST",
//             dataType: "json",
//            // data: jQuery("#formWithDates").serialize(),
//             data: JSON.stringify(formData),
//             success: function (resultMsg) {
//                 swal(resultMsg);
//             },
//             error: function () {
//                 swal("Sorry, an error on server has occurred", "please, try again.", "error");
//                 console.log("error");
//             }
//         });
//     }
//
// }
function activateOrderAfterSuspend(orderId) {
    if (areDatesCorrect()) {
        jQuery.ajax({
            url: 'activateAfterSuspend',
            data: {orderId: orderId},
            type: "POST",
            dataType: 'text',
            success: function (resultMsg) {
                if (resultMsg === '"success"') {
                    swal({
                        title: "Activation of order was successful",
                        type: "success"
                    })
                    var actionElement = $("action"+orderId);
                    actionElement.empty();
                    var $newActionElement = $('<input/>', {
                        type: 'button',
                        value: 'Suspend',
                        class: "btn btn-warning toggle-form-btn"
                    });
                    $newActionElement.onclick= new function () {
                        toggleFormFunc(orderId);
                    }
                }
                else {
                    swal("Activation of order failed", "Please, try again.",
                        "error")
                }
            },
            error: function () {
                swal("Sorry, an error on server has occurred", "please, try again.", "error");
                console.log("error");PRICES
            }
        });
    }
}