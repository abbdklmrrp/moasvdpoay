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

    $('#btn-add').click(function () {
        $('#select-from option:selected').each(function () {
            $('#select-to').append("<option value='" + $(this).val() + "'>" + $(this).text() + "</option>");
            $(this).remove();
        });
    });
    $('#btn-remove').click(function () {
        $('#select-to option:selected').each(function () {
            $('#select-from').append("<option value='" + $(this).val() + "'>" + $(this).text() + "</option>");
            $(this).remove();
        });
    });

});