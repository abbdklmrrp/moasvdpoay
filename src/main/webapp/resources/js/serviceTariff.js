/**
 * Method shows in modal window all services of tariff.
 *
 * @param tariffId id of tariff
 */
function showServicesOfTariff(tariffId) {
    $.ajax({
        url: 'showServicesOfTariff',
        data: {tariffId: tariffId},
        type: "POST",
        success: function (listOfServices) {
            var listServices = "";
            for (var i = 0; i < listOfServices.length; i++) {
                listServices += (i+1) + ")" + listOfServices[i].name + " : " + listOfServices[i].description + "\n";
            }
            swal("Tariff consist of services:", listServices);
        },
        error: function (res) {
            console.log("error");
        }
    });
    event.preventDefault();
}

/**
 * Method activates tariff with id from params for current user.
 *
 * @param tariffId id of tariff.
 */
function activateTariff(tariffId, userId, currentTariffId) {
    swal({
            title: "Are you sure?",
            text: "This tariff will be activated!",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "Activate",
            closeOnConfirm: false
        },
        function () {
            $.ajax({
                url: 'activateTariff',
                data: {tariffId: tariffId, userId: userId},
                type: "POST",
                success: function (status) {
                    if (status == "success") {
                        swal("Success", "Tariff was activated!", "success");
                        var respContent = "<input type=\"button\" name=\"" + tariffId + "\" onclick=\"deactivateTariff(" + tariffId + ","+ userId +")\" value=\"Deactivate\" class=\"btn btn-danger\">";
                        $("#" + tariffId).html(respContent);
                        if (currentTariffId != "") {
                            var buttonOldTariff = "";
                            buttonOldTariff += "<input type=\"button\" name=\"" + currentTariffId + "\" onclick=\"activateTariff(" + currentTariffId + "," + userId + "," + tariffId + ")\" value=\"Activate\" class=\"btn btn-success\">";
                            $("#" + currentTariffId).html(buttonOldTariff);
                        }
                        var currentUserTariff = document.getElementsByClassName(tariffId);
                        $("#currentUserTariff").html("Current tariff: " + currentUserTariff[0].innerHTML);
                        updateMethodParamsInAllButtonsOfTariffPage(tariffId, userId);
                    } else {
                        swal("Can`t activate", "Please try again later.");
                    }
                },
                error: function (res) {
                    console.log("error");
                }
            });
            event.preventDefault();
        });
}

/**
 * Method deactivates tariff with id from params for current user.
 *
 * @param tariffId id of tariff.
 */
function deactivateTariff(tariffId, userId) {
    swal({
            title: "Are you sure?",
            text: "This tariff will be deactivated!",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "Deactivate",
            closeOnConfirm: false
        },
        function () {
            $.ajax({
                url: 'deactivateTariff',
                data: {tariffId: tariffId, userId: userId},
                type: "POST",
                success: function (status) {
                    if (status == "success") {
                        swal("Success", "Tariff was deactivated!", "success");
                        var respContent = "<input type=\"button\" onclick=\"activateTariff(" + tariffId + "," + userId + ")\" value=\"Activate\" class=\"btn btn-success\">";
                        $("#" + tariffId).html(respContent);
                        $("#currentUserTariff").html("No tariff now");
                    } else {
                        swal("Can`t deactivate", "Please try again later.");
                    }
                },
                error: function (res) {
                    console.log("error");
                }
            });
            event.preventDefault();
        });
}

/**
 * Method updates params in method 'activateTariff' in all buttons on the page with tariffs.
 *
 * @param tariffId new current tariff id.
 */
function updateMethodParamsInAllButtonsOfTariffPage(tariffId, userId) {
    var allButtons = document.getElementsByTagName('input');
    for (var i = 0; i < allButtons.length; i++) {
        var updatedButton = "";
        if (allButtons[i].value == 'Activate') {
            updatedButton += "<input type=\"button\" name=\"" + allButtons[i].name + "\" onclick=\"activateTariff(" + allButtons[i].name + "," + userId + "," + tariffId + ")\" value=\"Activate\" class=\"btn btn-success\">";
            $("#" + allButtons[i].name).html(updatedButton);
        }
    }
}