<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link href="<c:url value="/resources/css/servicesCatalog.css"/>" rel="stylesheet"/>

<div class="container">
    <div class="row">
        <h1 style="text-align: center">Services Catalog</h1>
        <div class="col-md-2">
            <%--<ul class="nav navbar-nav cat-dropdown">--%>
            <%--<li class="dropdown cat-dropdown ">--%>
            <%--<a href="#" class="dropdown-toggle" data-toggle="dropdown"> Categories </a>--%>
            <%--<ul class="dropdown-menu cat-dropdown-menu">--%>
            <%--<li><a href="<%=request.getContextPath()%>/${userRole}/orderService">All Categories</a></li>--%>
            <%--<li class="divider"></li>--%>
            <%--<c:forEach var="productCategory" items="${productsCategories}">--%>

            <%--<li id="${productCategory.categoryName}">--%>
            <%--<a href="<%=request.getContextPath()%>/${userRole}/orderService?category=${productCategory.categoryName}">${productCategory.categoryName}</a>--%>
            <%--</li>--%>
            <%--<li class="divider"></li>--%>
            <%--</c:forEach>--%>

            <%--</ul>--%>
            <%--</li>--%>
            <%--</ul>--%>
            <h2>${categoryName}</h2>
            <div class="dropdown">
                <button class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown">Categories
                    <span class="caret"></span></button>
                <ul class="dropdown-menu">
                    <li id="category"><a href="<%=request.getContextPath()%>/${userRole}/orderService">All
                        Categories</a></li>
                    <li class="divider"></li>
                    <c:forEach var="productCategory" items="${productsCategories}">
                        <li id="category${productCategory.id}">
                            <a href="<%=request.getContextPath()%>/${userRole}/orderService?categoryId=${productCategory.id}">${productCategory.categoryName}</a>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </div>
        <div class="col-md-10">
            <br>
            <c:choose>
                <c:when test="${not empty msg}">
                    <h3 style="text-align: center">${msg}</h3>
                </c:when>
                <c:otherwise>
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
                                    <th class="col-xs-2" data-grid-header="base_price"
                                        data-grid-header-sortable="true">
                                        <div class="pull-right order-by">
                                            <a class="glyphicon glyphicon-chevron-up" href="javascript:"
                                               data-grid-header-sortable-up="up"></a>
                                            <a class="glyphicon glyphicon-chevron-down" href="javascript:"
                                               data-grid-header-sortable-down="down"></a>
                                        </div>
                                        Price $
                                    </th>
                                    <th class="col-xs-2" data-grid-header="category_id"
                                        data-grid-header-sortable="true">
                                        <div class="pull-right order-by">
                                            <a class="glyphicon glyphicon-chevron-up" href="javascript:"
                                               data-grid-header-sortable-up="up"></a>
                                            <a class="glyphicon glyphicon-chevron-down" href="javascript:"
                                               data-grid-header-sortable-down="down"></a>
                                        </div>
                                        Category
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
                                    <td data-cell="base_price"></td>
                                    <td data-cell="category_id"></td>
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
                    <%--<h3></h3>--%>
                    <%--<table border="1" class="table table-striped table-hover" id="tableServiceCatalog">--%>
                    <%--<c:if test="${not empty resultMsg}">--%>
                    <%--<h3>${resultMsg}</h3>--%>
                    <%--</c:if>--%>
                    <%--<tr>--%>
                    <%--<th>Name</th>--%>
                    <%--<th>Description</th>--%>
                    <%--<th>Duration(days)</th>--%>
                    <%--<th>Price</th>--%>
                    <%--<th>Status</th>--%>
                    <%--</tr>--%>
                    <%--<c:forEach var="categoriesProducts" items="${categoriesProducts}">--%>
                    <%--<tr>--%>
                    <%--<td colspan="5"><strong> ${categoriesProducts.key}</strong></td>--%>
                    <%--</tr>--%>
                    <%--<c:forEach var="productRow" items="${categoriesProducts.value}">--%>
                    <%--<tr>--%>
                    <%--<td>${productRow.product.name}</td>--%>
                    <%--<td>${productRow.product.description}</td>--%>
                    <%--<td>${productRow.product.durationInDays}</td>--%>
                    <%--<td>${productRow.price.price}</td>--%>
                    <%--<c:choose><c:when test="${empty productRow.status}">--%>
                    <%--<td id="${productRow.product.id}">--%>
                    <%--<input type="button" class="btn btn-success"--%>
                    <%--onclick="activateService(${productRow.product.id})" value="Activate">--%>
                    <%--</td>--%>
                    <%--</c:when>--%>
                    <%--<c:when test="${ productRow.status== 'Active'}">--%>
                    <%--<td id="${productRow.product.id}">--%>
                    <%--<input type="button" class="btn btn-danger"--%>
                    <%--onclick="deactivateService(${productRow.product.id})"--%>
                    <%--value="Deactivate"></td>--%>
                    <%--</c:when>--%>
                    <%--<c:otherwise>--%>
                    <%--<td>${productRow.status}</td>--%>
                    <%--</c:otherwise>--%>
                    <%--</c:choose>--%>
                    <%--</tr>--%>
                    <%--</c:forEach>--%>
                    <%--</c:forEach>--%>
                    <%--</table>--%>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>
<jsp:include page="../includes/footer.jsp"/>
<script type="text/javascript" src="<c:url value="${contextPath}/resources/js/orderService.js"/>"></script>
<script type="text/javascript" src="<c:url value="${contextPath}/resources/js/grid/BooGrid.js"/>"></script>
<script type="text/javascript" src="<c:url value="${contextPath}/resources/js/bootstrap/bootstrap.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="${contextPath}/resources/js/grid/ElementListener.js"/>"></script>
<script type="text/javascript" src="<c:url value="${contextPath}/resources/js/grid/RemoteDataSource.js"/>"></script>

<script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>

<script>
    $().BooGrid({
        id: 'productsIds',
        ds: new RemoteDataSource({url: '${pageContext.request.contextPath}/${userRole}/Services.json'}),
        listeners: [
            new ElementListener($('#progressId'))
        ],
        renderers: {
            "action": function (pv, wv, grid) {
                if (wv.status == 'In Tariff') {
                    return $('In Tariff')
                }
                if (!wv.status) {
                    return $('<div id=service' + wv.id + '><input type="button" class="btn btn-success btn-block"  value="Activate" onclick="activateService(' + wv.id + ')"></div>');
                }
                else {
                    return $('<div id=service' + wv.id + '><input type="button" class="btn btn-danger btn-block"  value="Deactivate" onclick="deactivateService(' + wv.id + ')"></div>');
                }
            },
            "name": function (pv, wv, grid) {
                return $('<a href=<%=request.getContextPath()%>/${userRole}/product?productId=' + wv.id + '>' + wv.name + '</a>')
            }
        }

    })
</script>
