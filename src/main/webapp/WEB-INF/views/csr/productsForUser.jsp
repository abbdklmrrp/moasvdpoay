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
    <title>Unconnected products</title>
    <link rel="stylesheet" type="text/css" href="//cdn.datatables.net/1.10.0/css/jquery.dataTables.css">
    <link href="${pageContext.request.contextPath}/resources/css/basic.css" rel="stylesheet"/>
    <spring:url value="/resources/js/jquery-1.12.1.min.js"
                var="jqueryJs" />
    <script src="${jqueryJs}"></script>
    <spring:url value="/resources/js/jquery.dataTables.js"
                var="datatable" />
    <script src="${datatable}"></script>
    <spring:url value="/resources/css/common.css" var="common" />
    <link href="${common}" rel="stylesheet" />
</head>

<body>
<table id="products" class="display" cellspacing="0" width="100%" style="overflow-x:auto">
    <thead>
    <tr>
        <th>Name</th>
        <th>Description</th>
        <th>Duration</th>
        <th>View</th>
    </tr>
    </thead>
</table>
</body>
</html>
<script type="text/javascript">
    $(document).ready(function(){
        var data =eval('${productList}');
        var table = $('#products').DataTable( {
            "aaData": data,
            "aoColumns": [
                { "mData": "name"},
                { "mData": "description"},
                { "mData": "duration"},
                {
                    "mData": "id",
                    "bSortable": false,
                    "mRender": function(data) {
                        return '<a class="btn btn-info btn-sm" href=${pageContext.request.contextPath}/csr/getDetails?id='+data+'>' +"Connect"+'</a>',
                        '<a class="btn btn-info btn-sm" href=${pageContext.request.contextPath}/csr/getDetails?id='+data+'>' +"Deactive"+'</a>' ;
                    }}
            ],
            "paging":true
        });
    });
</script>
