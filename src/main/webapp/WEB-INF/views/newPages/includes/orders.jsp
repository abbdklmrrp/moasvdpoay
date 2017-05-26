<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="container">
    <div class="row">
        <div class="col-xs-10">
            <h1 style="text-align: center">Orders</h1>
            <div class="grid-progress-bar-placeholder">
                <div class="progress grid-progress-bar" style="display: none;" id="progressId">
                    <div class="progress-bar progress-bar-striped active" role="progressbar" aria-valuenow="100"
                         aria-valuemin="0" aria-valuemax="100" style="width: 100%">
                        <span class="sr-only">in progress</span>
                    </div>
                </div>
            </div>

            <div class="panel panel-default" id="orders">
                <div class="panel-heading">
                    <div class="row">
                        <div class="col-md-10" data-grid="title">
                            Services
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
                        <div class="col-md-5">
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
                        <div class="col-md-5">
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
                            <th class="col-xs-2" data-grid-header="name"
                                data-grid-header-sortable="true">
                                <div class="pull-right order-by">
                                    <a class="glyphicon glyphicon-chevron-up" href="javascript:"
                                       data-grid-header-sortable-up="up"></a>
                                    <a class="glyphicon glyphicon-chevron-down" href="javascript:"
                                       data-grid-header-sortable-down="down"></a>
                                </div>
                                Name
                            </th>
                            <th class="col-xs-2" data-grid-header="action_date"
                                data-grid-header-sortable="true">
                                <div class="pull-right order-by">
                                    <a class="glyphicon glyphicon-chevron-up" href="javascript:"
                                       data-grid-header-sortable-up="up"></a>
                                    <a class="glyphicon glyphicon-chevron-down" href="javascript:"
                                       data-grid-header-sortable-down="down"></a>
                                </div>
                                End Date
                            </th>
                            <th class="col-xs-2" data-grid-header="type_id"
                                data-grid-header-sortable="true">
                                <div class="pull-right order-by">
                                    <a class="glyphicon glyphicon-chevron-up" href="javascript:"
                                       data-grid-header-sortable-up="up"></a>
                                    <a class="glyphicon glyphicon-chevron-down" href="javascript:"
                                       data-grid-header-sortable-down="down"></a>
                                </div>
                                Type
                            </th>
                            <th class="col-xs-2" data-grid-header="operation_status">
                                Operation Status
                            </th>
                            <th class="col-xs-2" data-grid-header="action">
                                Action
                            </th>
                        </tr>
                        </thead>
                        <div data-grid="message"></div>
                        <tbody>
                        <tr data-grid="row">
                            <td data-cell="name"></td>
                            <td data-cell="action_date"></td>
                            <td data-cell="type_id"></td>
                            <td data-cell="operation_status"></td>
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

        <%--<div class="row" style="{padding-bottom:50px; padding-top:50px;}" align="center">--%>
        <div id="suspendModal" class="modal fade" role="dialog">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">Suspend order</h4>
                    </div>
                    <div class="modal-body" align="center">
                        <form id="formWithDates" class="form-horizontal">
                            <br/>
                            <label class="control-label">Date Begin:
                                <input id="beginDate" name="beginDate" type="date"/>
                            </label>
                            <br/>
                            <label class="control-label">Date End:
                                <input id="endDate" name="endDate" type="date"/>
                            </label>
                            <br/>
                            <input type="hidden" id="orderId" name="orderId">
                            <%--<input type="submit" class="btn btn-warning"--%>
                            <%--value="Suspend">--%>

                            <br/>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button class="btn btn-warning" id="suspend-form-submit">Suspend</button>
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>
        <%--</div>--%>
        <div class="row" align="center">
            <button class="btn btn-middle btn-info" id="toggle-planned-info">Planned tasks for my orders</button>
        </div>
        <div class="row" id="planned-tasks-info">
            <div class="col-md-12">

                <div class="grid-progress-bar-placeholder">
                    <div class="progress grid-progress-bar" style="display: none;" id="progressId2">
                        <div class="progress-bar progress-bar-striped active" role="progressbar" aria-valuenow="100"
                             aria-valuemin="0" aria-valuemax="100" style="width: 100%">
                            <span class="sr-only">in progress</span>
                        </div>
                    </div>
                </div>
                <div class="panel panel-default" id="plannedTasks">
                    <div class="panel-heading">
                        <div class="row">
                            <div class="col-md-10" data-grid="title">
                                Planned tasks
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
                                    <option value="5">5</option>
                                    <option value="10">10</option>
                                    <option value="15">15</option>
                                </select>
                            </div>
                            <div class="col-md-5">
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
                        <table class="table table-striped table-bordered table-hover" data-grid="grid">
                            <thead>
                            <tr>
                                <th class="col-xs-2" data-grid-header="productName">
                                    Name
                                </th>
                                <th class="col-xs-2" data-grid-header="status">
                                    Future status
                                </th>
                                <th class="col-xs-2" data-grid-header="actionDate">
                                    Action Date
                                </th>
                                <th class="col-xs-2" data-grid-header="action">
                                    Action
                                </th>
                            </tr>
                            </thead>
                            <div data-grid="message"></div>
                            <tbody>
                            <tr data-grid="row">
                                <td data-cell="productName"></td>
                                <td data-cell="status"></td>
                                <td data-cell="actionDate"></td>
                                <td data-cell="action"></td>
                            </tr>
                            </tbody>
                        </table>
                        <div class="row" style="margin: 20px 0;">
                            <div class="col-md-2">
                                <select class="form-control" data-grid="pager-length">
                                    <option value="5">5</option>
                                    <option value="10">10</option>
                                    <option value="15">15</option>
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
        </div>
        </div>
    </div>
</div>

<jsp:include page="../includes/footer.jsp"/>
<link href="<c:url value="/resources/css/sweet-alert.css"/>" rel="stylesheet">
<script type="text/javascript"
        src="<c:url value="${contextPath}/resources/js/sweet-alert.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="${contextPath}/resources/js/grid/BooGrid.js"/>"></script>
<script type="text/javascript"
        src="<c:url value="${contextPath}/resources/js/bootstrap/bootstrap.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="${contextPath}/resources/js/grid/ElementListener.js"/>"></script>
<script type="text/javascript" src="<c:url value="${contextPath}/resources/js/grid/RemoteDataSource.js"/>"></script>
<script type="text/javascript" src="<c:url value="${contextPath}/resources/js/orders.js"/>"></script>

<script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>

<script>
    $().BooGrid({
        id: 'orders',
        ds: new RemoteDataSource({url: '${pageContext.request.contextPath}/${userRole}/getOrders.json'}),
        listeners: [
            new ElementListener($('#progressId'))
        ],
        renderers: {
            "action": function (pv, wv, grid) {
                if (wv.operation_status == "Suspended") {
                    return $('<div id=order' + wv.order_id + '><input type="button" class="btn btn-success btn-block"  style="margin:5px 5px 5px 5px" value="Activate" onclick="activateOrderAfterSuspend(' + wv.order_id + ')">' +
                        '<input type="button" class="btn btn-warning btn-block" style="margin:5px 5px 5px 5px"  value="Suspend" onclick="toggleFormFunc(' + wv.order_id + ')">' +
                        '<input type="button" class="btn btn-danger btn-block" style="margin:5px 5px 5px 5px" value="Deactivate" onclick="deactivateOrder(' + wv.order_id + ')"></div>');
                }
                if (wv.operation_status == "In Processing") {
                    return $('<div id=order' + wv.order_id + '><input type="button" class="btn btn-danger btn-block" style="margin:5px 5px 5px 5px" value="Deactivate" onclick="deactivateOrder(' + wv.order_id + ')"></div>');
                }
                else {
                    return $('<div id=order' + wv.order_id + '><input type="button" class="btn btn-warning btn-block" style="margin:5px 5px 5px 5px" value="Suspend" onclick="toggleFormFunc(' + wv.order_id + ')">' +
                        '<input type="button" class="btn btn-danger btn-block" style="margin:5px 5px 5px 5px" value="Deactivate" onclick="deactivateOrder(' + wv.order_id + ')"></div>');
                }
            },
            "name": function (pv, wv, grid) {
                return $('<a href=<%=request.getContextPath()%>/${userRole}/product?productId=' + wv.product_id + '>' + wv.name + '</a>')
            }
        }

    })
    $().BooGrid({
        id: 'plannedTasks',
        ds: new RemoteDataSource({url: '${pageContext.request.contextPath}/${userRole}/getPlannedTasks.json'}),
        listeners: [
            new ElementListener($('#progressId2'))
        ],
        renderers: {
            "action": function (pv, wv, grid) {
                if (wv.status == "Suspended") {
                    return $('<div id=task' + wv.id + '><input type="button" class="btn btn-danger btn-block" value="Cancel Suspense" onclick="cancelPlannedTask(' + wv.id + ')"></div>');
                }
                else {
                    return $('-');
                }
            }
        }
    })

</script>