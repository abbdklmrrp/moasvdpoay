$(function () {
    //initially hide the textbox
    $("#price").hide();
    $('#customerTypeId').change(function () {
        var basePrice = document.getElementById('basePrice');
        if ($(this).find('option:selected').val() == "Business") {
            $("#price").show();
            basePrice.setAttribute("required", "required");
        } else {
            $("#price").hide();
            basePrice.removeAttribute("required");
        }
    });

});
function selectedCustomer(a) {
    document.getElementById("price").style.display = 'none';
    var label = a.value;
    if (label == 'Business') {
        var c = document.getElementById("price");
        c.style.display = 'block';
        $("#basePrice").val("100.00");
    } else {
        document.getElementById("price").style.display = 'none';
        $("#basePrice").val("");
    }
}
