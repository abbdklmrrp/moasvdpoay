/**
 * @author Anna Rysakova
 */
$(function () {
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
});
function selectedCategory(a) {
    document.getElementById("newCategory").style.display = 'none';
    document.getElementById("newCategoryDesc").style.display = 'none';
    var label = a.value;
    if (label == '') {
        var d = document.getElementById("newCategory");
        var c = document.getElementById("newCategoryDesc");
        d.style.display = 'block';
        c.style.display = 'block';
    } else {
        document.getElementById("newCategory").style.display = 'none';
        document.getElementById("newCategoryDesc").style.display = 'none';
        $("#newCategory-inpt").val("");
        $("#newCategoryDesc-inpt").val("");
    }
}
