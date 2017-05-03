<%--
  Created by IntelliJ IDEA.
  User: Petro
  Date: 29.04.2017
  Time: 15:52
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page session="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Users</title>
    <link rel="stylesheet" type="text/css" href="//cdn.datatables.net/1.10.0/css/jquery.dataTables.css">
    <link href="${contextPath}/resources/css/basic.css" rel="stylesheet"/>
    <spring:url value="/resources/js/jquery-1.12.1.min.js"
                var="jqueryJs" />
    <script src="${jqueryJs}"></script>
    <%--<spring:url value="/resources/js/jquery.dataTables.js"--%>
                <%--var="datatable" />--%>
    <script type="text/javascript" src="https://cdn.datatables.net/v/dt/dt-1.10.15/datatables.min.js"></script>
    <script src="${datatable}"></script>
</head>

<body>
<table id="users" class="table table-striped table-bordered" cellspacing="0" width="100%" style="overflow-x:auto">
    <thead>
    <tr>
        <th>Name</th>
        <th>Surname</th>
        <th>Email</th>
        <th>Phone</th>
        <th>Address</th>
        <th>View</th>
    </tr>
    </thead>
</table>
</body>
</html>
<script type="text/javascript">
    $(document).ready(function(){
        var data =eval('${userList}');
        var table = $('#users').DataTable( {
            "aaData": data,
            "aoColumns": [
                { "mData": "name"},
                { "mData": "surname"},
                { "mData": "email"},
                { "mData": "phone"},
                { "mData": "address"},
                {
                    "mData": "id",
                    "bSortable": false,
                    "mRender": function(data,type,full) {
                        return '<a class="btn btn-info btn-sm" href=${contextPath}/csr/getDetails?id='+data+'>'+'Details'+'</a>';
//
                    }}
            ],
            "paging":true
        });
    } );
</script>
