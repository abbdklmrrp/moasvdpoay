<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="../includes/head.jsp">
        <jsp:param name="tittle" value="User Info"/>
    </jsp:include>
</head>
<body>
<jsp:include page="../includes/headers/pmgHeader.jsp">
    <jsp:param name="pageName" value="User Info"/>
</jsp:include>
<div class="container" style="margin-bottom: 30px; width:60%; max-width: 400px;">
    <div class="login-form">
        <h1 style="text-align: center">Personal information</h1>
        <br>
        <div class="form-group form-group-lg">
            <label class="col-sm-2 control-label">Name</label>
            <div class="col-sm-10">
                <input style="border-bottom-width: 0;" type="text" class="form-control disabled" name="name"
                       id="name" value=${user.name} readonly><br>
            </div>
        </div>
        <div class="form-group form-group-lg">
            <label class="col-sm-2 control-label">Surname</label><br>
            <div class="col-sm-10">
                <input type="text" class="form-control" name="surname" id="surname"
                       value=${user.surname} readonly><br>
            </div>
        </div>
        <div class="form-group form-group-lg">
            <label class="col-sm-2 control-label">Email</label><br>
            <div class="col-sm-10">
                <input type="email" class="form-control" name="email" id="email"
                       value=${user.email} readonly><br>
            </div>
        </div>
        <div class="form-group form-group-lg">
            <label class="col-sm-2 control-label">Phone</label><br>
            <div class="col-sm-10">
                <input type="text" class="form-control" name="phone" id="phone" value="${user.phone}" readonly><br>
            </div>
        </div>
        <div class="form-group form-group-lg">
            <label class="col-sm-2 control-label">Address</label><br>
            <div class="col-sm-10">
                <input type="text" class="form-control" name="address" id="address" value="${user.address}" readonly><br>
            </div>
        </div>
    </div>
</div>
<br>
<div class="container">
    <div class="grid-progress-bar-placeholder">
        <div class="progress grid-progress-bar" style="display: none;" id="progressId">
            <div class="progress-bar progress-bar-striped active" role="progressbar" aria-valuenow="100"
                 aria-valuemin="0" aria-valuemax="100" style="width: 100%">
                <span class="sr-only">in progress</span>
            </div>
        </div>
    </div>

    <div class="panel panel-default" id="productsIds">
        <div class="panel-heading">
            <div class="row">
                <div class="col-md-10" data-grid="title">
                    Products
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
                        <option value="5">5</option>
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
                            <a class="glyphicon glyphicon-chevron-up" href="javascript:"
                               data-grid-header-sortable-up="up"></a>
                            <a class="glyphicon glyphicon-chevron-down" href="javascript:"
                               data-grid-header-sortable-down="down"></a>
                        </div>
                        Name
                    </th>
                    <th class="col-xs-2" data-grid-header="description" data-grid-header-sortable="true">
                        <div class="pull-right order-by">
                            <a class="glyphicon glyphicon-chevron-up" href="javascript:"
                               data-grid-header-sortable-up="up"></a>
                            <a class="glyphicon glyphicon-chevron-down" href="javascript:"
                               data-grid-header-sortable-down="down"></a>
                        </div>
                        Description
                    </th>
                    <th class="col-xs-2" data-grid-header="type_id" data-grid-header-sortable="true">
                        <div class="pull-right order-by">
                            <a class="glyphicon glyphicon-chevron-up" href="javascript:"
                               data-grid-header-sortable-up="up"></a>
                            <a class="glyphicon glyphicon-chevron-down" href="javascript:"
                               data-grid-header-sortable-down="down"></a>
                        </div>
                        Product type
                    </th>
                    <th class="col-xs-2" data-grid-header="current_status_id" data-grid-header-sortable="true">
                        <div class="pull-right order-by">
                            <a class="glyphicon glyphicon-chevron-up" href="javascript:"
                               data-grid-header-sortable-up="up"></a>
                            <a class="glyphicon glyphicon-chevron-down" href="javascript:"
                               data-grid-header-sortable-down="down"></a>
                        </div>
                        Status
                    </th>
                    <th class="col-xs-2" data-grid-header="history">
                        History
                    </th>
                </tr>
                </thead>
                <tbody>
                <tr data-grid="row">
                    <td data-cell="name"></td>
                    <td data-cell="description"></td>
                    <td data-cell="type_id"></td>
                    <td data-cell="current_status_id"></td>
                    <td data-cell="history"></td>

                </tr>
                </tbody>
            </table>
            <div class="row" style="margin: 20px 0;">
                <div class="col-md-2">
                    <select class="form-control" data-grid="pager-length">
                        <option value="10">10</option>
                        <option value="5">5</option>
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
</div>
<div class="container" >
    <h3 id="header-operations" class="hide" style="text-align: center">Operations</h3>
    <div class="row">
        <div class="table-responsive">
            <table class="table table-bordered hide" id="tbl-operations">
                <thead>
                <tr>
                    <th>Date</th>
                    <th>Status</th>
                    <th>Operation</th>
                </tr>
                </thead>
                <tbody id="myTable">
                </tbody>
            </table>
        </div>
        <div class="col-md-12 text-center">
            <ul class="pagination" id="myPager"></ul>
        </div>
    </div>
</div>
<jsp:include page="../includes/footer.jsp"/>
</body>
</html>
<%--<script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>--%>
<%--<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>--%>
<%--<![endif]-->--%>

<%--<script src="${contextPath}/resources/js/bootstrap/bootstrap.min.js"></script>--%>
<%--<script src="${contextPath}/resources/js/bootstrap/ie10-viewport-bug-workaround.js"></script>--%>

<script src="${contextPath}/resources/js/grid/ElementListener.js"></script>
<script src="${contextPath}/resources/js/grid/RemoteDataSource.js"></script>
<script src="${contextPath}/resources/js/grid/BooGrid.js"></script>
<script type="text/javascript" src="<c:url value="/resources/js/operationHistoryPagination.js"/>"></script>

<script>
    $().BooGrid({
        id: 'productsIds',
        ds: new RemoteDataSource({url: '${contextPath}/pmg/getOrders.json'}),
        listeners: [
            new ElementListener($('#progressId'))
        ],
        renderers: {
            "history": function (pv, wv, grid) {
                return $('<input type="button" class="btn btn-success"  value="View" onclick="showHistory('+wv.orderId+')">');
            }
        }
    });
    function showHistory(orderId) {
        $('#myTable').empty();
        $('#myPager').empty();
        $('#myTable').pageMe({
            pagerSelector: '#myPager',
            showPrevNext: true,
            hidePageNumbers: false,
            perPage: 5
        }, 'getHistory',orderId);

    };

</script>
