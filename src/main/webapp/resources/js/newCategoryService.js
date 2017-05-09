$(function () {
    //initially hide the textbox
    $("#newCategory").hide();
    $("#newCategoryDesc").hide();
    $('#categoryId').change(function () {
        var newCategory = document.getElementById('newCategory-inpt');
        var newCategoryDesc = document.getElementById('newCategoryDesc-inpt');
        if ($(this).find('option:selected').val() == "") {
            $("#newCategory").show();
            $("#newCategoryDesc").show();
            newCategory.setAttribute("required", "required");
            newCategoryDesc.setAttribute("required", "required");
        } else {
            $("#newCategory").hide();
            $("#newCategoryDesc").hide();
            newCategory.removeAttribute("required");
            newCategoryDesc.removeAttribute("required");
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
function SelectedCategory(a) {
    document.getElementById("newCategory").style.display = 'none';
    document.getElementById("newCategoryDesc").style.display = 'none';
    var label = a.value;
    if (label == "") {
        var d = document.getElementById("newCategory");
        var c = document.getElementById("newCategoryDesc");
        d.style.display = 'block';
        c.style.display = 'block';
    } else {
        document.getElementById("newCategory").style.display = 'none';
        document.getElementById("newCategoryDesc").style.display = 'none';
        document.getElementById("newCategoryID").value="";
        document.getElementById("newCategoryDescID").value="";
    }
}
