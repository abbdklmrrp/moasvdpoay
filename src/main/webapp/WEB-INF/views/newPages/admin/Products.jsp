<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="../includes/head.jsp">
        <jsp:param name="tittle" value="Products"/>
    </jsp:include>
</head>
<body>
<jsp:include page="../includes/headers/adminHeader.jsp"/>
<div class="container">
    <table id="products" class="display" cellspacing="0" width="100%" style="overflow-x:auto">
        <thead>
        <tr>
            <th>Name</th>
            <th>Type</th>
            <th>Category</th>
            <th>Duration</th>
            <th>Need processing</th>
            <th>Description</th>
            <th>Status</th>
            <th>Action</th>
        </tr>
        </thead>
    </table>
</div>
<jsp:include page="../includes/footer.jsp"/>
<script type="text/javascript" src="https://cdn.datatables.net/v/dt/dt-1.10.15/datatables.min.js"></script>
<script src="${datatable}"></script>
<script type="text/javascript">
    $(document).ready(function () {
        var data = eval('${productList}');
        var table = $('#products').DataTable({
            "aaData": data,
            "aoColumns": [
                {"mData": "name"},
                {"mData": "productType"},
                {"mData": "categoryId"},
                {"mData": "durationInDays"},
//                {"mData": "customerTypeId"},
                {"mData": "needProcessing"},
                {"mData": "description"},
                {"mData": "status"},
                {
                    "mData": "id",
                    "bSortable": false,
                    "mRender": function (data) {
                        return '<a class="btn btn-info btn-sm" href=${contextPath}/admin/getDetailsProduct?id=' + data + '>' + "Edit" + '</a>';
                    }
                }
            ],
            "paging": true
        });
    });
</script>
</body>
</html>
