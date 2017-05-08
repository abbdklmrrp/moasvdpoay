function Selected(a) {
    var label = a.value;
    if (label == "Service") {
        var d = document.getElementById("Block1");
        d.style.display = 'block';
    } else {
        document.getElementById("Block1").style.display = 'none';
    }

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
                for (var i = 0; i < listServices.length; i++) {
                    listServices += "- " + listServices[i].name + "<br>";
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
    function activateTariff(tariffId) {
        $.ajax({
            url: 'activateTariff',
            data: {tariffId: tariffId},
            type: "POST",
            success: function (status) {
                if (status == "success") {
                    swal("Success", "Tariff was activated!", "success");
                } else {
                    swal("Can`t activate", "Please try again later.");
                }
            },
            error: function (res) {
                console.log("error");
            }
        });
        event.preventDefault();
    }

    /**
     * Method deactivates tariff with id from params for current user.
     *
     * @param tariffId id of tariff.
     */
    function deactivateTariff(tariffId) {
        $.ajax({
            url: 'deactivateTariff',
            data: {tariffId: tariffId},
            type: "POST",
            success: function (status) {
                if (status == "success") {
                    swal("Success", "Tariff was deactivated!", "success");
                } else {
                    swal("Can`t deactivate", "Please try again later.");
                }
            },
            error: function (res) {
                console.log("error");
            }
        });
        event.preventDefault();
    }

}