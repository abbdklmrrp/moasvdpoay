<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page session="false" %>
<html>
<head>
    <jsp:include page="../includes/head.jsp">
        <jsp:param name="tittle" value="Products"/>
    </jsp:include>
    <script type="text/javascript">
        google.load("jquery", "1.4.4");
    </script>
    <style type="text/css">

        #back {
            background-color: rgba(0, 0, 0, 0.4);
            position: fixed;
            left: 0;
            right: 0;
            top: 0;
            bottom: 0;
            display: none;
            z-index: 1000;
        }

        #prices {
            background-color: white;
            font-family: 'Open Sans', sans-serif;
            width: 478px;
            padding: 17px;
            border-radius: 5px;
            text-align: center;
            position: fixed;
            left: 50%;
            top: 50%;
            margin-left: -256px;
            margin-top: -200px;
            overflow: hidden;
            display: none;
            z-index: 2000;
        }

        .close {
            margin-left: 364px;
            margin-top: 4px;
            cursor: pointer;
        }

    </style>
</head>
<body>
<jsp:include page="../includes/headers/adminHeader.jsp">
    <jsp:param name="pageName" value="Products"/>
</jsp:include>
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
                    All products
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
                    <th class="col-xs-1" data-grid-header="type_id" data-grid-header-sortable="true">
                        <div class="pull-right order-by">
                            <a class="glyphicon glyphicon-chevron-up" href="javascript:"
                               data-grid-header-sortable-up="up"></a>
                            <a class="glyphicon glyphicon-chevron-down" href="javascript:"
                               data-grid-header-sortable-down="down"></a>
                        </div>
                        Type
                    </th>
                    <th class="col-xs-2" data-grid-header="customer_type_id" data-grid-header-sortable="true">
                        <div class="pull-right order-by">
                            <a class="glyphicon glyphicon-chevron-up" href="javascript:"
                               data-grid-header-sortable-up="up"></a>
                            <a class="glyphicon glyphicon-chevron-down" href="javascript:"
                               data-grid-header-sortable-down="down"></a>
                        </div>
                        Customer type
                    </th>
                    <th class="col-xs-2" data-grid-header="duration" data-grid-header-sortable="true">
                        <div class="pull-right order-by">
                            <a class="glyphicon glyphicon-chevron-up" href="javascript:"
                               data-grid-header-sortable-up="up"></a>
                            <a class="glyphicon glyphicon-chevron-down" href="javascript:"
                               data-grid-header-sortable-down="down"></a>
                        </div>
                        Duration(in days)
                    </th>
                    <th class="col-xs-1" data-grid-header="base_price">
                        Price($)
                    </th>
                    <th class="col-xs-1" data-grid-header="status" data-grid-header-sortable="true">
                        <div class="pull-right order-by">
                            <a class="glyphicon glyphicon-chevron-up" href="javascript:"
                               data-grid-header-sortable-up="up"></a>
                            <a class="glyphicon glyphicon-chevron-down" href="javascript:"
                               data-grid-header-sortable-down="down"></a>
                        </div>
                        Status
                    </th>
                </tr>
                </thead>
                <div data-grid="message"></div>
                <tbody>
                <tr data-grid="row">
                    <td data-cell="name"></td>
                    <td data-cell="description"></td>
                    <td data-cell="type_id"></td>
                    <td data-cell="customer_type_id"></td>
                    <td data-cell="duration"></td>
                    <td data-cell="base_price"></td>
                    <td data-cell="status"></td>
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
<div id="back" style='display: none;'></div>
<div class="container" id="prices">
    <img class="close" onclick="show('none')" src="http://sergey-oganesyan.ru/wp-content/uploads/2014/01/close.png">
    <div id="nameProduct" align="left"></div>
    <br>
    <div class="row">
        <div class="table-responsive">
            <table class="table table-bordered hide" id="tbl-operations">
                <thead>
                <tr>
                    <th>Place</th>
                    <th>Price</th>
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
<script src="${pageContext.request.contextPath}/resources/js/grid/ElementListener.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/grid/RemoteDataSource.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/grid/BooGrid.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/priceInfo.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/pricesPagination.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/viewPrices.js"></script>
<script>
    $().BooGrid({
        id: 'productsIds',
        ds: new RemoteDataSource({url: '${pageContext.request.contextPath}/pmg/all.json'}),
        listeners: [
            new ElementListener($('#progressId'))
        ],
        renderers: {
            "base_price": function (pv, wv, grid) {
                if (wv.customer_type_id == 'Business') {
                    return wv.base_price;
                } else {
                    return $('<input type="button" class="btn btn-primary" value="View" onclick="viewPrices(' + wv.id + ')">');
                }
            }
        }
    });

    function show(state) {
        document.getElementById('prices').style.display = state;
        document.getElementById('back').style.display = state;
    }
</script>
</body>
</html>
