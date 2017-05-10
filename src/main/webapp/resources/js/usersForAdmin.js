/**
 * Created by Petro on 09.05.2017.
 */
function activateUser(userId) {
    $.ajax({
        url: 'activateUser',
        data: {userId: userId},
        type: "POST",
        dataType: 'text',
        //  async: false,
        success: function (resultMsg) {
            if (resultMsg === '"success"') {
                swal({
                    title: "This  was activated.",
                    type: "success"
                });
                var $statusElement = $('#' + userId);
                $statusElement.empty();
                var $newStatus = $('<input type="button" class="btn btn-danger"  value="Ban" onclick="deactivateUser('+userId+')">');
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
    // event.preventDefault();
}
function deactivateUser(userId) {
    swal({
            title: "Are you sure you want to deactivate this user?",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "DD6B55",
            confirmButtonText: "Yes, deactivate it",
            closeOnConfirm: false
        },
        function () {
            $.ajax({
                url: 'deactivateUser',
                data: {userId: userId},
                type: "POST",
                dataType: 'text',
                success: function (resultMsg) {
                    if (resultMsg === '"success"') {
                        swal({
                            title: "This  was deactivated.",
                            type: "success"
                        });
                        var $statusElement = $('#' + userId);
                        $statusElement.empty();
                        var $newStatus = $('<input type="button" class="btn btn-success"  value="Activate" onclick="activateUser('+userId+')">');
                        // $newStatus.addEventListener('click', function(){
                        //     alert(serviceId);
                        //     activateService(serviceId);
                        // });
                        // $newStatus.onclick = function () {
                        //     activateService(serviceId);
                        // }
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