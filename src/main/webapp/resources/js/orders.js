/**
 * Created by Yuliya Pedash on 08.05.2017.
 */
$(document).ready(function () {
    $("#formWithDates").hide();
    $("#planned-tasks-info").hide();

    $("#toggle-planned-info").click(function () {
        $("#planned-tasks-info").toggle();
    });


    $("#formWithDates").submit(function (e) {
        e.preventDefault();
        var formData = {}
        $.each(this, function (i, v) {
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
    start.setHours(0, 0, 0, 0);
    end.setHours(0, 0, 0, 0);
    if ($('#beginDate').val() == '' || $('#endDate').val() == '') {
        swal("Please, enter both, begin and end date.");
        return false;
    }
    if (end.getTime() == start.getTime()) {
        swal({
            title: "Begin date of order's suspense should be earlier than date of end of order's suspense.",
            type: "error"
        });
        return false;
    }
    if (end.getTime() == start.getTime()) {
        swal({
            title: "Begin date of order's suspense should be different from date of end of order's suspense.",
            type: "error"
        });
        return false;
    }
    if (start.getTime() < currentDate.getTime()) {
        swal({
            title: "Begin date of order's suspense should not not be earlier than today .",
            type: "error"
        });
    }

    else {
        return true;
    }
}


function activateOrderAfterSuspend(orderId) {
    jQuery.ajax({
        url: 'activateAfterSuspend',
        data: {orderId: orderId},
        type: "POST",
        dataType: 'text',
        success: function (isSuccess) {
            if (isSuccess) {
                swal({
                    title: "Activation of order was successful",
                    type: "success"
                });
                var $actionContainerElement = $("#order" + orderId);
                $actionContainerElement.empty();
                var newSuspendAction = "<input type=\"button\" onclick=\"toggleFormFunc(" + orderId + ")\" value=\"Suspend\" class=\"btn btn-warning\">";
                $actionContainerElement.html(newSuspendAction);
            }
            else {
                swal("Activation of order failed", "Please, try again.",
                    "error")
            }
        },
        error: function () {
            swal("Sorry, an error on server has occurred", "please, try again.", "error");
            console.log("error");
        }
    });

}
function cancelPlannedTask(plannedTaskId) {
    swal({
            title: "Are you sure you want to cancel this Suspense?",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "DD6B55",
            confirmButtonText: "Yes, cancel it",
            closeOnConfirm: false
        },
        function () {
            $.ajax({
                url: 'cancelSuspense',
                data: {plannedTaskId: plannedTaskId},
                type: "POST",
                dataType: 'text',
                success: function (resultMsg) {
                    if (resultMsg === '"success"') {
                        swal({
                            title: "This Suspense  was cancelled.",
                            type: "success"
                        });
                        var $statusElement = $('#task' + plannedTaskId);
                        $statusElement.empty();
                    }
                    else {
                        swal("Sorry, an error occurred while cancelling this planned task!", "Please, try again later", "error");
                    }
                },

                error: function () {
                    swal("Sorry, an error on server has occurred", "please, try again later.", "error");
                }
            })

        }
    )
    ;
}
function deactivateOrder(orderId) {
    swal({
            title: "Are you sure you want to deactivate this order?",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "DD6B55",
            confirmButtonText: "Yes, deactivate it",
            closeOnConfirm: false
        },
        function () {
            $.ajax({
                url: 'deactivateOrder',
                data: {orderId: orderId},
                type: "POST",
                dataType: 'text',
                success: function (resultMsg) {
                    if (resultMsg === '"success"') {
                        swal({
                            title: "This order was deactivated.",
                            type: "success"
                        });
                        var $statusElement = $('#order' + orderId);
                        $statusElement.empty();
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
