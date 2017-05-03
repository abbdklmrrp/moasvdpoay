$(function () {
    //initially hide the textbox
    $("#newCategory").hide();
    $("#newCategoryDesc").hide();
    $('#categoryId').change(function () {
        if ($(this).find('option:selected').val() == "") {
            $("#newCategory").show();
            $("#newCategoryDesc").show();
        } else {
            $("#newCategory").hide();
            $("#newCategoryDesc").hide();
        }
    });
    $("#newCategory").keyup(function (ev) {
        var othersOption = $('#categoryId').find('option:selected');
        if (othersOption.val() == "") {
            ev.preventDefault();
            //change the selected drop down text
            $(othersOption).html($("#newCategory").val());
        }
    });
});
