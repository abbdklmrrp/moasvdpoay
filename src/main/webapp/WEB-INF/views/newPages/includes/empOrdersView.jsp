<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="container">
    <div class="row">
        <div class="col-md-1"></div>
        <div class="col-md-10">
            <h1 style="text-align: center">Orders</h1>
            <br>
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
                        </tr>
                        </thead>
                        <div data-grid="message"></div>
                        <tbody>
                        <tr data-grid="row">
                            <td data-cell="name"></td>
                            <td data-cell="action_date"></td>
                            <td data-cell="type_id"></td>
                            <td data-cell="operation_status"></td>
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
        </div>
    </div>
</div>
<jsp:include page="../includes/footer.jsp"/>

<script type="text/javascript" src="<c:url value="${contextPath}/resources/js/grid/BooGrid.js"/>"></script>
<script type="text/javascript"
        src="<c:url value="${contextPath}/resources/js/bootstrap/bootstrap.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="${contextPath}/resources/js/grid/ElementListener.js"/>"></script>
<script type="text/javascript" src="<c:url value="${contextPath}/resources/js/grid/RemoteDataSource.js"/>"></script>

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
            "name": function (pv, wv, grid) {
                return $('<a href=<%=request.getContextPath()%>/${userRole}/product?productId=' + wv.product_id + '>' + wv.name + '</a>')
            }
        }

    })
</script>