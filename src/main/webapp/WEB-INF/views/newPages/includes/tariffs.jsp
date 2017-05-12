<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container">
    <div class="col-md-2"></div>
    <div class="col-md-8">
        <h1 style="text-align: center">Tariffs Catalog</h1>
        <br>
        <table border="1" class="table table-striped table-hover" id="tableServiceCatalog">
            <tr>
                <th>Name</th>
                <th>Description</th>
                <th>Duration(days)</th>
                <th>Price($)</th>
                <th>Services</th>
                <th>Status</th>
            </tr>
            <c:forEach var="tariff" items="${tariffs}">
                <tr>
                    <td>${tariff.name}</td>
                    <td>${tariff.description}</td>
                    <td>${tariff.durationInDays}</td>
                    <td>${tariff.basePrice}</td>
                    <td><input type="button" onclick="showServicesOfTariff(${tariff.id})" value="Show" class="btn btn-primary"></td>
                    <c:choose>
                        <c:when test="${ tariff.id == currentTariff.id}">
                            <td id="${tariff.id}"><input type="button" name="${tariff.id}" onclick="deactivateTariff(${tariff.id})" value="Deactivate" class="btn btn-danger"></td>
                        </c:when>
                        <c:otherwise>
                            <td id="${tariff.id}"><input type="button" name="${tariff.id}" onclick="activateTariff(${tariff.id}, ${currentTariff.id})" value="Activate" class="btn btn-success"></td>
                        </c:otherwise>
                    </c:choose>
                </tr>
            </c:forEach>
        </table>
    </div>
    <div class="col-md-2"></div>
</div>


<%--<div class="container">--%>
    <%--<div class="grid-progress-bar-placeholder">--%>
        <%--<div class="progress grid-progress-bar" style="display: none;" id="progressId">--%>
            <%--<div class="progress-bar progress-bar-striped active" role="progressbar" aria-valuenow="100"--%>
                 <%--aria-valuemin="0" aria-valuemax="100" style="width: 100%">--%>
                <%--<span class="sr-only">in progress</span>--%>
            <%--</div>--%>
        <%--</div>--%>
    <%--</div>--%>

    <%--<div class="panel panel-default" id="tariffsId">--%>
        <%--<div class="panel-body">--%>
            <%--<div class="row" style="margin: 20px 0;">--%>
                <%--<div class="col-md-2">--%>
                    <%--<select class="form-control" data-grid="pager-length">--%>
                        <%--<option value="10">10</option>--%>
                        <%--<option value="20">20</option>--%>
                        <%--<option value="50">50</option>--%>
                    <%--</select>--%>
                <%--</div>--%>
                <%--<div class="col-md-6">--%>
                    <%--<nav>--%>
                        <%--<ul class="pagination" style="margin:0px !important;" data-grid="pager">--%>
                            <%--<li data-grid="pager-prev">--%>
                                <%--<a href="javascript:" aria-label="Previous">--%>
                                    <%--<span aria-hidden="true">&laquo;</span>--%>
                                <%--</a>--%>
                            <%--</li>--%>
                            <%--<li data-grid="pager-item"><a href="javascript:">1</a></li>--%>
                            <%--<li data-grid="pager-next">--%>
                                <%--<a href="javascript:" aria-label="Next">--%>
                                    <%--<span aria-hidden="true">&raquo;</span>--%>
                                <%--</a>--%>
                            <%--</li>--%>
                        <%--</ul>--%>
                    <%--</nav>--%>
                <%--</div>--%>
                <%--<div class="col-md-4">--%>
                    <%--<form class="form-inline" data-grid="search">--%>
                        <%--<div class="form-group">--%>
                            <%--<input type="text" class="form-control" placeholder="Query">--%>
                        <%--</div>--%>
                        <%--<button type="button" class="btn btn-default">Search</button>--%>
                    <%--</form>--%>
                <%--</div>--%>
            <%--</div>--%>
            <%--<table class="table table-striped table-bordered table-hover" data-grid="grid">--%>
                <%--<thead>--%>
                <%--<tr>--%>
                    <%--<th class="col-xs-2" data-grid-header="name" data-grid-header-sortable="true">--%>
                        <%--<div class="pull-right order-by">--%>
                            <%--<a class="glyphicon glyphicon-chevron-up" href="javascript:"--%>
                               <%--data-grid-header-sortable-up="up"></a>--%>
                            <%--<a class="glyphicon glyphicon-chevron-down" href="javascript:"--%>
                               <%--data-grid-header-sortable-down="down"></a>--%>
                        <%--</div>--%>
                        <%--Name--%>
                    <%--</th>--%>
                    <%--<th class="col-xs-4" data-grid-header="description">--%>
                        <%--Description--%>
                    <%--</th>--%>
                    <%--<th class="col-xs-1" data-grid-header="duration">--%>
                        <%--Duration--%>
                    <%--</th>--%>
                    <%--<th class="col-xs-1" data-grid-header="price">--%>
                        <%--<div class="pull-right order-by">--%>
                            <%--<a class="glyphicon glyphicon-chevron-up" href="javascript:"--%>
                               <%--data-grid-header-sortable-up="up"></a>--%>
                            <%--<a class="glyphicon glyphicon-chevron-down" href="javascript:"--%>
                               <%--data-grid-header-sortable-down="down"></a>--%>
                        <%--</div>--%>
                        <%--Price($)--%>
                    <%--</th>--%>
                    <%--<th class="col-xs-2" data-grid-header="services">--%>
                        <%--Services--%>
                    <%--</th>--%>
                    <%--<th class="col-xs-2" data-grid-header="status">--%>
                        <%--Status--%>
                    <%--</th>--%>
                <%--</tr>--%>
                <%--</thead>--%>
                <%--<tbody>--%>
                <%--<tr data-grid="row">--%>
                    <%--<td data-cell="name"></td>--%>
                    <%--<td data-cell="description"></td>--%>
                    <%--<td data-cell="duration"></td>--%>
                    <%--<td data-cell="base_price"></td>--%>
                    <%--<td data-cell="services"></td>--%>
                    <%--<td data-cell="status"></td>--%>
                <%--</tr>--%>
                <%--</tbody>--%>
            <%--</table>--%>
            <%--<div class="row" style="margin: 20px 0;">--%>
                <%--<div class="col-md-2">--%>
                    <%--<select class="form-control" data-grid="pager-length">--%>
                        <%--<option value="10">10</option>--%>
                        <%--<option value="20">20</option>--%>
                        <%--<option value="50">50</option>--%>
                    <%--</select>--%>
                <%--</div>--%>
                <%--<div class="col-md-10">--%>
                    <%--<nav>--%>
                        <%--<ul class="pagination" style="margin:0px !important;" data-grid="pager">--%>
                            <%--<li data-grid="pager-prev">--%>
                                <%--<a href="javascript:" aria-label="Previous">--%>
                                    <%--<span aria-hidden="true">&laquo;</span>--%>
                                <%--</a>--%>
                            <%--</li>--%>
                            <%--<li data-grid="pager-item"><a href="javascript:">1</a></li>--%>
                            <%--<li data-grid="pager-next">--%>
                                <%--<a href="javascript:" aria-label="Next">--%>
                                    <%--<span aria-hidden="true">&raquo;</span>--%>
                                <%--</a>--%>
                            <%--</li>--%>
                        <%--</ul>--%>
                    <%--</nav>--%>
                <%--</div>--%>
            <%--</div>--%>
        <%--</div>--%>
    <%--</div>--%>
<%--</div>--%>
<%--<script src="${pageContext.request.contextPath}/resources/js/grid/ElementListener.js"></script>--%>
<%--<script src="${pageContext.request.contextPath}/resources/js/grid/RemoteDataSource.js"></script>--%>
<%--<script src="${pageContext.request.contextPath}/resources/js/grid/BooGrid.js"></script>--%>
<%--<script>--%>
    <%--$().BooGrid({--%>
        <%--id: 'tariffsId',--%>
        <%--ds: new RemoteDataSource({url: '${pageContext.request.contextPath}/csr/allCustomers.json'}),--%>
        <%--listeners: [--%>
            <%--new ElementListener($('#progressId'))--%>
        <%--],--%>
        <%--renderers: {--%>
            <%--"services": function (pv, wv, grid) {--%>
                <%--return $();--%>
            <%--},--%>
            <%--"status": function (pv, wv, grid) {--%>
                <%--return $();--%>
            <%--}--%>
        <%--}--%>
    <%--})--%>
<%--</script>--%>