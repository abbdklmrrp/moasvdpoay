<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page session="false" %>
<html>
<head>
    <jsp:include page="../includes/head.jsp">
        <jsp:param name="tittle" value="Products"/>
    </jsp:include>
</head>
<body>
<jsp:include page="../includes/headers/adminHeader.jsp"/>
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
                            <a class="glyphicon glyphicon-chevron-up" href="javascript:"
                               data-grid-header-sortable-up="up"></a>
                            <a class="glyphicon glyphicon-chevron-down" href="javascript:"
                               data-grid-header-sortable-down="down"></a>
                        </div>
                        Name
                    </th>
                    <th class="col-xs-2" data-grid-header="categoriesID" data-grid-header-sortable="true">
                        <div class="pull-right order-by">
                            <a class="glyphicon glyphicon-chevron-up" href="javascript:"
                               data-grid-header-sortable-up="up"></a>
                            <a class="glyphicon glyphicon-chevron-down" href="javascript:"
                               data-grid-header-sortable-down="down"></a>
                        </div>
                        Category
                    </th>
                    <th class="col-xs-2" data-grid-header="selectto" data-grid-header-sortable="true">
                        <div class="pull-right order-by">
                            <a class="glyphicon glyphicon-chevron-up" href="javascript:"
                               data-grid-header-sortable-up="up"></a>
                            <a class="glyphicon glyphicon-chevron-down" href="javascript:"
                               data-grid-header-sortable-down="down"></a>
                        </div>
                        Service
                    </th>

                </tr>
                </thead>
                <tbody>
                <%--<select name="categoriesID" id="categoriesID" multiple>--%>
                <c:forEach var="category" items="${productCategories}">

                    <%--</select>--%>
                    <tr data-grid="row">
                        <td data-cell="name">${category.categoryName}</td>
                        <td data-cell="categoriesID"><input type="checkbox" name="categoriesID"
                                                            value="${category.id}">check
                        </td>
                        <td data-cell="selectto"><select name="selectto" id="selectto"></select></td>
                    </tr>
                </c:forEach>
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
<script src="${contextPath}/resources/js/grid/ElementListener.js"></script>
<script src="${contextPath}/resources/js/grid/RemoteDataSource.js"></script>
<script src="${contextPath}/resources/js/grid/BooGrid.js"></script>
<script type="text/javascript" src="http://www.google.com/jsapi"></script>
<script type="text/javascript" src="http://www.google.com/jsapi"></script>
<script type="text/javascript">
    google.load("jquery", "1.4.4");
</script>
<script src="<c:url value="/resources/js/categoryService.js"/>"></script>
<script>
    $().BooGrid({
        id: 'productsIds',
        ds: new RemoteDataSource({url: '${contextPath}/admin/all.json'}),
        listeners: [
            new ElementListener($('#progressId'))
        ],
        renderers: {
            "action": function (pv, wv, grid) {
                return $('<input type="button" class="btn btn-success"  value="Edit" >').click(function () {
                        location.href = '${contextPath}/admin/getDetailsProduct?id=' + wv.id
                    }
                );
            }
        }
    })
</script>
</body>
</html>

<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>--%>
<%--&lt;%&ndash;--%>
<%--Created by IntelliJ IDEA.--%>
<%--User: Anna--%>
<%--Date: 24.04.2017--%>
<%--Time: 9:12--%>
<%--&ndash;%&gt;--%>
<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>
<%--<html>--%>
<%--<head>--%>
<%--<title>IDENTIFY TARIFF</title>--%>
<%--<link href="<c:url value="/resources/css/basic.css"/>" rel="stylesheet"/>--%>
<%--<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/meyer-reset/2.0/reset.min.css">--%>
<%--<script type="text/javascript" src="http://www.google.com/jsapi"></script>--%>
<%--<script type="text/javascript">--%>
<%--google.load("jquery", "1.4.4");--%>
<%--</script>--%>
<%--<script src="<c:url value="/resources/js/categoryService.js"/>"></script>--%>
<%--</head>--%>
<%--<body>--%>
<%--<form action="/admin/fillTariff" method="post">--%>
<%--<div class="login-form">--%>
<%--<h1>SELECT SERVICES</h1>--%>
<%--&lt;%&ndash;<div class="form-group ">&ndash;%&gt;--%>
<%--&lt;%&ndash;<select name="tariffId" class="form-control" id="tariffs">&ndash;%&gt;--%>
<%--&lt;%&ndash;<c:forEach var="tariff" items="${tariffs}">&ndash;%&gt;--%>
<%--&lt;%&ndash;<option value="${tariff.id}">${tariff.name}</option>&ndash;%&gt;--%>
<%--&lt;%&ndash;</c:forEach>&ndash;%&gt;--%>
<%--&lt;%&ndash;</select>&ndash;%&gt;--%>
<%--&lt;%&ndash;</div>&ndash;%&gt;--%>

<%--<c:if test="${not empty error}">--%>
<%--<span style="float:right ; color: #10CE88;">${error}</span>--%>
<%--</c:if>--%>

<%--<fieldset>--%>
<%--<h6>All categories</h6>--%>
<%--<select name="categoriesID" id="categoriesID" multiple>--%>
<%--<c:forEach var="category" items="${productCategories}">--%>
<%--<option value="${category.categoryName}">${category.categoryName}</option>--%>
<%--</c:forEach>--%>
<%--</select>--%>

<%--<h6>Selected services</h6>--%>
<%--<select name="selectto" id="selectto" multiple="multiple">--%>
<%--</select>--%>

<%--<a href="JavaScript:void(0);" id="btn-add">Add&raquo;</a>--%>
<%--<h6>Selected services</h6>--%>
<%--<select name="selectedService" id="selectedService" multiple="multiple" required>--%>
<%--</select>--%>
<%--<a href="JavaScript:void(0);" id="btn-remove">&laquo; Remove</a>--%>

<%--<button type="submit" class="log-btn" id="submit">Save</button>--%>

<%--</fieldset>--%>

<%--</div>--%>
<%--</form>--%>
<%--</body>--%>
<%--</html>--%>