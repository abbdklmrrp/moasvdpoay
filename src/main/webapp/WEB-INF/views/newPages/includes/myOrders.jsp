<div class="container">
    <div class="row">
        <div class="col-xs-12">
        <h1 style="text-align: center">My orders</h1>
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
                        Orders
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
                        <th class="col-xs-2" data-grid-header="product_name" data-grid-header-sortable="true">
                            <div class="pull-right order-by">
                                <a class="glyphicon glyphicon-chevron-up" href="javascript:"
                                   data-grid-header-sortable-up="up"></a>
                                <a class="glyphicon glyphicon-chevron-down" href="javascript:"
                                   data-grid-header-sortable-down="down"></a>
                            </div>
                            Name
                        </th>
                        <th class="col-xs-2" data-grid-header="product_type" data-grid-header-sortable="true">
                            <div class="pull-right order-by">
                                <a class="glyphicon glyphicon-chevron-up" href="javascript:"
                                   data-grid-header-sortable-up="up"></a>
                                <a class="glyphicon glyphicon-chevron-down" href="javascript:"
                                   data-grid-header-sortable-down="down"></a>
                            </div>
                            Type
                        </th>
                        <th class="col-xs-2" data-grid-header="customer_type" data-grid-header-sortable="true">
                            <div class="pull-right order-by">
                                <a class="glyphicon glyphicon-chevron-up" href="javascript:"
                                   data-grid-header-sortable-up="up"></a>
                                <a class="glyphicon glyphicon-chevron-down" href="javascript:"
                                   data-grid-header-sortable-down="down"></a>
                            </div>
                            Customer type
                        </th>
                        <th class="col-xs-2" data-grid-header="operation_date" data-grid-header-sortable="true">
                            <div class="pull-right order-by">
                                <a class="glyphicon glyphicon-chevron-up" href="javascript:"
                                   data-grid-header-sortable-up="up"></a>
                                <a class="glyphicon glyphicon-chevron-down" href="javascript:"
                                   data-grid-header-sortable-down="down"></a>
                            </div>
                            Ordering date
                        </th>
                        <th class="col-xs-2" data-grid-header="place" data-grid-header-sortable="true">
                            <div class="pull-right order-by">
                                <a class="glyphicon glyphicon-chevron-up" href="javascript:"
                                   data-grid-header-sortable-up="up"></a>
                                <a class="glyphicon glyphicon-chevron-down" href="javascript:"
                                   data-grid-header-sortable-down="down"></a>
                            </div>
                            Region
                        </th>
                        <th class="col-xs-1" data-grid-header="action">
                            Action
                        </th>
                    </tr>
                    </thead>
                    <div data-grid="message"></div>
                    <tbody>
                    <tr data-grid="row">
                        <td data-cell="product_name"></td>
                        <td data-cell="product_type"></td>
                        <td data-cell="customer_type"></td>
                        <td data-cell="operation_date"></td>
                        <td data-cell="place"></td>
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
    </div>
</div>
</div>
<jsp:include page="../includes/footer.jsp"/>
<script src="${pageContext.request.contextPath}/resources/js/grid/ElementListener.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/grid/RemoteDataSource.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/grid/BooGrid.js"></script>
<script>
    $().BooGrid({
        id: 'productsIds',
        ds: new RemoteDataSource({url: '${pageContext.request.contextPath}/csr/getMyOrders.json'}),
        listeners: [
            new ElementListener($('#progressId'))
        ],
        renderers: {
            "action": function (pv, wv, grid) {
                return $('<input type="button" class="btn btn-primary" value="Process">').click(function () {
                        location.href = '${pageContext.request.contextPath}/csr/getOrderPage?id=' + wv.order_id
                    }
                );
            }
        }
    })
</script>
