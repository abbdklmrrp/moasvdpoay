/**
 * @author Yuliya Pedash
 * @since 08.05.2017.
 */
$(document).ready(function () {
    $("#planned-tasks-info").hide();
    $("#toggle-planned-info").click(function () {
        $("#planned-tasks-info").toggle();
    });

    $("#suspend-form-submit").click(function () {
        $("#formWithDates").submit();
    });

    $("#formWithDates").submit(function (e) {
        e.preventDefault();
        var formData = {}
        $.each(this, function (i, v) {
            var input = $(v);
            formData[input.attr("name")] = input.val();
            delete formData["undefined"];
        });

        if (areDatesCorrect()) {
            $("#suspendModal").modal('hide')
            jQuery.ajax({
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                url: 'suspend',
                type: "POST",
                dataType: 'text',
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
    $("#suspendModal").modal({backdrop: true});
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
                    title: "Resuming was successful",
                    type: "success"
                });
                var $actionContainerElement = $("#order" + orderId);
                $actionContainerElement.empty();
                var newSuspendAction = "<div id=order" + orderId + "><input type=\"button\" onclick=\"toggleFormFunc(" + orderId + ")\" value=\"Suspend\" class=\"btn btn-warning btn-block\" style=\"margin:5px 5px 5px 5px\"><input type=\"button\" onclick=\"deactivateOrder(" + orderId + ")\" value=\"Deactivate\" class=\"btn btn-danger btn-block\" style=\"margin:5px 5px 5px 5px\"></div>";
                $actionContainerElement.html(newSuspendAction);
            }
            else {
                swal("Resuming failed", "Please, try again.",
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
            title: "Are you sure you want to cancel this suspense?",
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
                    if (resultMsg == '"success"') {
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
                    if (resultMsg == '"success"') {
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
    );
}
