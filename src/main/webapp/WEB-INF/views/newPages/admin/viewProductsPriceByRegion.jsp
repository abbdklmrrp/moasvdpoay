<%--
  Created by Anna Rysakova
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page session="false" %>
<html>
<head>
    <jsp:include page="../includes/head.jsp">
        <jsp:param name="tittle" value="Product price in regions"/>
    </jsp:include>
    <script type="text/javascript">
        google.load("jquery", "1.4.4");
    </script>
</head>
<body>
<jsp:include page="../includes/headers/adminHeader.jsp">
    <jsp:param name="pageName" value="ProductsPriceInRegions"/>
</jsp:include>
<div class="col-xs-2">
    <div class="collapse navbar-collapse" id="mobilkat">
        <ul class="nav navbar-nav navbar-dikey">
            <c:choose>
                <c:when test="${param.page == 'ProductInfo'}">
                    <li class="wet-asphalt active-tab">
                        <a href="${pageContext.request.contextPath}/admin/getProducts">All products</a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li class="wet-asphalt">
                        <a href="${pageContext.request.contextPath}/admin/getProducts">All products</a>
                    </li>
                </c:otherwise>
            </c:choose>
        </ul>
    </div>
</div>
<div class="col-xs-1"></div>
<div class="col-xs-6">
    <h1 style="text-align: center">Product prices by region</h1>

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
                    <div class="col-md-6" data-grid="title">
                        Product price by region ${placeName}
                    </div>
                    <div class="col-md-6" style="text-align:right;">
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
                        <th class="col-xs-2" data-grid-header="productName" data-grid-header-sortable="true">
                            <div class="pull-right order-by">
                                <a class="glyphicon glyphicon-chevron-up" href="javascript:"
                                   data-grid-header-sortable-up="up"></a>
                                <a class="glyphicon glyphicon-chevron-down" href="javascript:"
                                   data-grid-header-sortable-down="down"></a>
                            </div>
                            Name
                        </th>
                        <th class="col-xs-2" data-grid-header="priceProduct" data-grid-header-sortable="true">
                            <div class="pull-right order-by">
                                <a class="glyphicon glyphicon-chevron-up" href="javascript:"
                                   data-grid-header-sortable-up="up"></a>
                                <a class="glyphicon glyphicon-chevron-down" href="javascript:"
                                   data-grid-header-sortable-down="down"></a>
                            </div>
                            Price
                        </th>
                        <th class="col-xs-2" data-grid-header="productType" data-grid-header-sortable="true">
                            <div class="pull-right order-by">
                                <a class="glyphicon glyphicon-chevron-up" href="javascript:"
                                   data-grid-header-sortable-up="up"></a>
                                <a class="glyphicon glyphicon-chevron-down" href="javascript:"
                                   data-grid-header-sortable-down="down"></a>
                            </div>
                            Type
                        </th>
                        <th class="col-xs-1" data-grid-header="productStatus" data-grid-header-sortable="true">
                            <div class="pull-right order-by">
                                <a class="glyphicon glyphicon-chevron-up" href="javascript:"
                                   data-grid-header-sortable-up="up"></a>
                                <a class="glyphicon glyphicon-chevron-down" href="javascript:"
                                   data-grid-header-sortable-down="down"></a>
                            </div>
                            Status
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
                        <td data-cell="priceProduct"></td>
                        <td data-cell="productType"></td>
                        <td data-cell="productStatus"></td>
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
<jsp:include page="../includes/footer.jsp"/>
<script src="${pageContext.request.contextPath}/resources/js/grid/ElementListener.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/grid/RemoteDataSource.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/grid/BooGrid.js"></script>
<script>
    $().BooGrid({
        id: 'productsIds',
        ds: new RemoteDataSource({url: '${pageContext.request.contextPath}/admin/getDetailsPriceByPlace/${id}.json'}),
        listeners: [
            new ElementListener($('#progressId'))
        ], renderers: {
            "action": function (pv, wv, grid) {
                return $('<input type="button" class="btn btn-success"  value="Details" >').click(function () {
                    location.href = '${pageContext.request.contextPath}/admin/getDetailsProduct?id=' + wv.productId;
                    }
                );
            }
        }
    });
</script>
</body>
</html>
