<%--
  Created by IntelliJ IDEA.
  User: Anna Rysakova
  Date: 9.05.2017
  Time: 9:28
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="../includes/head.jsp">
        <jsp:param name="tittle" value="Price in region"/>
    </jsp:include>
</head>
<body>
<jsp:include page="../includes/headers/adminHeader.jsp">
    <jsp:param name="pageName" value="UpdatePriceInRegion"/>
</jsp:include>
<%--<jsp:include page="../includes/footer.jsp"/>--%>

<div class="navbar-fixed-left">
    <div class="row">
        <aside class="leftside col-lg-2 col-md-2 col-sm-2 col-xs-1">
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
                    <c:choose>
                        <c:when test="${param.page == 'Products'}">
                            <li class="wet-asphalt active-tab">
                                <a href="${pageContext.request.contextPath}/admin/getDetailsProduct?id=${id}">Product
                                    info</a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="wet-asphalt">
                                <a href="${pageContext.request.contextPath}/admin/getDetailsProduct?id=${id}">Product
                                    info</a>
                            </li>
                        </c:otherwise>
                    </c:choose>

                    <c:if test="${productType eq 'Tariff'}">
                        <c:choose>
                            <c:when test="${param.page == 'UpdateServicesInTariff'}">
                                <li class="wet-asphalt active-tab">
                                    <a href="${pageContext.request.contextPath}/admin/updateServicesInTariff?id=${id}">Services
                                        in tariff</a>
                                </li>
                            </c:when>
                            <c:otherwise>
                                <li class="wet-asphalt">
                                    <a href="${pageContext.request.contextPath}/admin/updateServicesInTariff?id=${id}">Services
                                        in tariff</a>
                                </li>
                            </c:otherwise>
                        </c:choose>
                    </c:if>
                </ul>
            </div>
        </aside>
        <main class="col-lg-10 col-md-10 col-sm-10 col-xs-11">

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
                                <th class="col-xs-2" data-grid-header="placeName" data-grid-header-sortable="true">
                                    <div class="pull-right order-by">
                                        <a class="glyphicon glyphicon-chevron-up" href="javascript:"
                                           data-grid-header-sortable-up="up"></a>
                                        <a class="glyphicon glyphicon-chevron-down" href="javascript:"
                                           data-grid-header-sortable-down="down"></a>
                                    </div>
                                    Place
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
                                <th class="col-xs-1" data-grid-header="action">
                                    Action
                                </th>
                            </tr>
                            </thead>
                            <div data-grid="message"></div>
                            <tbody>
                            <tr data-grid="row">
                                <td data-cell="placeName"></td>
                                <td data-cell="priceProduct"></td>
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
            <jsp:include page="../includes/footer.jsp"/>
            <script src="${pageContext.request.contextPath}/resources/js/grid/ElementListener.js"></script>
            <script src="${pageContext.request.contextPath}/resources/js/grid/RemoteDataSource.js"></script>
            <script src="${pageContext.request.contextPath}/resources/js/grid/BooGrid.js"></script>
            <script src="${pageContext.request.contextPath}/resources/js/priceInfo.js"></script>
            <script>
                $().BooGrid({
                    id: 'productsIds',
                    ds: new RemoteDataSource({url: '${pageContext.request.contextPath}/admin/productsPriceInRegions/${id}.json'}),
                    listeners: [
                        new ElementListener($('#progressId'))
                    ], renderers: {
                        "action": function (pv, wv, grid) {
                            return $('<input type="button" class="btn btn-success"  value="Update" >').click(function () {
                                location.href = '${pageContext.request.contextPath}/admin/updateProductPrice?id=' +${id};
                                }
                            );
                        }
                    }
                })
            </script>
            <h2 style="text-align: center" id="errorMessage" hidden disabled="true">${msg}</h2>
        </main>
    </div>
</div>
