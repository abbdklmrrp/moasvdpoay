// $(document).ready(function () {
//     $('#add').click(function () {
//         var sss=document.getElementById("allServices");
//         var str = '<li>';
//         // str+= '<label>ФИО</label><input type="text" value=""/> ';
//         str += '<label>ФИО</label> <select  class="form-control">' +
//             ' <c:forEach var="category" items="sss">' +
//             '<c:forEach var="categoryKey" items="${category.value}">' +
//             ' <option value="${categoryKey.id}">${categoryKey.name}</option>' +
//             ' </c:forEach>' +
//             '  </c:forEach>' +
//             '  </select>';
//         str += '<input type="button" value="Удалить" class="remove"/>';
//         str += '</li>';
//         $('#sites').append(str);
//     });
//     $('.remove').live('click', function () {
//         $(this).parent('li').remove();
//     });
// });

$(document).ready(function () {
    var currentService;

    $('#categoriesID').change(function () {
        currentService = this.value;

        // get
        $.ajax({
            url: "/services/tariffs/" + this.value + ".json", success: function (result) {
                $('#selectto').empty();

                for (var i = 0; i < result.length; i++) {
                    $('<option/>').val(result[i].id).attr('id', result[i].id).html(result[i].name).appendTo('#selectto');
                }
            }
        });
    });
    $('#btn-add').click(function () {
        $('#selectto option:selected').each(function () {
            $('#selectedService').append("<option value='" + $(this).val() + "' selected>" + $(this).text() + "</option>");
            $('#selectto').empty();
        });
    });
    $('#btn-remove').click(function () {
        $('#selectedService option:selected').each(function () {
            $('#selectto').append("<option value='" + $(this).val() + "'>" + $(this).text() + "</option>");
            $(this).remove();
        });
    });

});