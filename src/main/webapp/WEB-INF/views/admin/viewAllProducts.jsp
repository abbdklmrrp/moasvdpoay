<%--
  Created by IntelliJ IDEA.
  User: Anna
  Date: 01.05.2017
  Time: 9:12
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page session="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Products</title>
    <link href="${pageContext.request.contextPath}/resources/css/basic.css" rel="stylesheet"/>
    <spring:url value="/resources/js/jquery-1.12.1.min.js"
                var="jqueryJs"/>
    <script src="${jqueryJs}"></script>
    <script src="${datatable}"></script>
    <script type="text/javascript" src="http://www.google.com/jsapi"></script>
    <script type="text/javascript">
        google.load("jquery", "1.4.4");
    </script>
    <!-- Bootstrap core CSS -->
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap/bootstrap.min.css" rel="stylesheet">
    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap/ie10-viewport-bug-workaround.css" rel="stylesheet">

    <style>
        table td {
            height: 40px;
        }
    </style>

</head>

<body>

<div class="grid-progress-bar-placeholder">
    <div class="progress grid-progress-bar" style="display: none;" id="progressId">
        <div class="progress-bar progress-bar-striped active" role="progressbar" aria-valuenow="100" aria-valuemin="0" aria-valuemax="100" style="width: 100%">
            <span class="sr-only">in progress</span>
        </div>
    </div>
</div>

<div class="panel panel-default" id="productsIds">
    <div class="panel-heading">
        <div class="row">
            <div class="col-md-10" data-grid="title">
                Title
            </div>
            <div class="col-md-2" style="text-align:right;">
                <a href="javascript:" data-grid="pager-refresh">
                    Refresh
                </a>
            </div>
        </div>
    </div>
    <div class="panel-body">
        <div class="row" style="margin: 20px 0;">
            <div class="col-md-2">
                <select class="form-control" data-grid="pager-length">
                    <option value="10">10</option>
                    <option value="50">50</option>
                    <option value="100">100</option>
                    <option value="200">200</option>
                </select>
            </div>
            <div class="col-md-6">
                <nav>
                    <ul class="pagination" style="margin:0px !important;" data-grid="pager">
                        <li data-grid="pager-prev">
                            <a href="javascript:" aria-label="Previous">
                                <span aria-hidden="true">&laquo;</span>
                            </a>
                        </li>
                        <li data-grid="pager-item"><a href="javascript:">1</a></li>
                        <li data-grid="pager-next">
                            <a href="javascript:" aria-label="Next">
                                <span aria-hidden="true">&raquo;</span>
                            </a>
                        </li>
                    </ul>
                </nav>
            </div>
            <div class="col-md-4">
                <form class="form-inline" data-grid="search">
                    <div class="form-group">
                        <input type="text" class="form-control" placeholder="Query">
                    </div>
                    <button type="button" class="btn btn-default">Search</button>
                </form>
            </div>
        </div>
        <table class="table table-striped table-bordered table-hover" data-grid="grid">
            <thead>
            <tr>
                <th class="col-xs-2" data-grid-header="name" data-grid-header-sortable="true">
                    <div class="pull-right order-by">
                        <a class="glyphicon glyphicon-chevron-up" href="javascript:" data-grid-header-sortable-up="up"></a>
                        <a class="glyphicon glyphicon-chevron-down" href="javascript:" data-grid-header-sortable-down="down"></a>
                    </div>
                    Name
                </th>
                <th class="col-xs-2" data-grid-header="description" data-grid-header-sortable="true">
                    <div class="pull-right order-by">
                        <a class="glyphicon glyphicon-chevron-up" href="javascript:" data-grid-header-sortable-up="up"></a>
                        <a class="glyphicon glyphicon-chevron-down" href="javascript:" data-grid-header-sortable-down="down"></a>
                    </div>
                    Description
                </th>
                <th class="col-xs-2" data-grid-header="duration" data-grid-header-sortable="true">
                    <div class="pull-right order-by">
                        <a class="glyphicon glyphicon-chevron-up" href="javascript:" data-grid-header-sortable-up="up"></a>
                        <a class="glyphicon glyphicon-chevron-down" href="javascript:" data-grid-header-sortable-down="down"></a>
                    </div>
                    Duration
                </th>
                <th class="col-xs-2" data-grid-header="base_price" data-grid-header-sortable="true">
                    <div class="pull-right order-by">
                        <a class="glyphicon glyphicon-chevron-up" href="javascript:" data-grid-header-sortable-up="up"></a>
                        <a class="glyphicon glyphicon-chevron-down" href="javascript:" data-grid-header-sortable-down="down"></a>
                    </div>
                    Price
                </th>
                <th class="col-xs-2" data-grid-header="type_id" data-grid-header-sortable="true">
                    <div class="pull-right order-by">
                        <a class="glyphicon glyphicon-chevron-up" href="javascript:" data-grid-header-sortable-up="up"></a>
                        <a class="glyphicon glyphicon-chevron-down" href="javascript:" data-grid-header-sortable-down="down"></a>
                    </div>
                    Type
                </th>
                <th class="col-xs-2" data-grid-header="action">
                    Action
                </th>
                <%--<th class="col-xs-6" data-grid-header="status">--%>
                <%--Status--%>
                <%--</th>--%>
            </tr>
            </thead>
            <tbody>
            <tr data-grid="row">
                <td data-cell="name"></td>
                <td data-cell="description"></td>
                <td data-cell="duration"></td>
                <td data-cell="base_price"></td>
                <td data-cell="type_id"></td>
                <td data-cell="action"></td>

            </tr>
            </tbody>
        </table>
        <div class="row" style="margin: 20px 0;">
            <div class="col-md-2">
                <select class="form-control" data-grid="pager-length">
                    <option value="10">10</option>
                    <option value="50">50</option>
                    <option value="100">100</option>
                    <option value="200">200</option>
                </select>
            </div>
            <div class="col-md-10">
                <nav>
                    <ul class="pagination" style="margin:0px !important;" data-grid="pager">
                        <li data-grid="pager-prev">
                            <a href="javascript:" aria-label="Previous">
                                <span aria-hidden="true">&laquo;</span>
                            </a>
                        </li>
                        <li data-grid="pager-item"><a href="javascript:">1</a></li>
                        <li data-grid="pager-next">
                            <a href="javascript:" aria-label="Next">
                                <span aria-hidden="true">&raquo;</span>
                            </a>
                        </li>
                    </ul>
                </nav>
            </div>
        </div>
    </div>
</div>
</body>
</html>




<!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
<!--[if lt IE 9]><script src="${pageContext.request.contextPath}/resources/js/bootstrap/ie8-responsive-file-warning.js"></script><![endif]-->
<script src="${pageContext.request.contextPath}/resources/js/bootstrap/ie-emulation-modes-warning.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/jquery-1.12.1.min.js"></script>

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
<script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
<![endif]-->

<script src="${pageContext.request.contextPath}/resources/js/bootstrap/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap/ie10-viewport-bug-workaround.js"></script>

<script src="${pageContext.request.contextPath}/resources/js/grid/ElementListener.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/grid/RemoteDataSource.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/grid/BooGrid.js"></script>
<script>
    $().BooGrid({
        id: 'productsIds',
        ds: new RemoteDataSource({url: '${pageContext.request.contextPath}/products/all.json'}),
        listeners: [
            new ElementListener($('#progressId'))
        ],
        renderers: {
            "action": function (pv, wv, grid) {
                return $('<span>Click me</span>').click( function(){
                        location.href='http://localhost:8023/admin/getAllProducts?id=' + wv.id
                    }
                );
            }
        }
    })
</script>

