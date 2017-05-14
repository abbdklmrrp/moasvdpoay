<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <jsp:include page="../includes/head.jsp">
        <jsp:param name="tittle" value="Users"/>
    </jsp:include>
    <script type="text/javascript" src="http://www.google.com/jsapi"></script>
    <script type="text/javascript">
        google.load("jquery", "1.4.4");
    </script>
</head>
<body>
<jsp:include page="../includes/headers/adminHeader.jsp">
    <jsp:param name="pageName" value="Users"/>
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
                    Users
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
                    <th class="col-xs-2" data-grid-header="surname" data-grid-header-sortable="true">
                        <div class="pull-right order-by">
                            <a class="glyphicon glyphicon-chevron-up" href="javascript:"
                               data-grid-header-sortable-up="up"></a>
                            <a class="glyphicon glyphicon-chevron-down" href="javascript:"
                               data-grid-header-sortable-down="down"></a>
                        </div>
                        Surname
                    </th>
                    <th class="col-xs-2" data-grid-header="email" data-grid-header-sortable="true">
                        <div class="pull-right order-by">
                            <a class="glyphicon glyphicon-chevron-up" href="javascript:"
                               data-grid-header-sortable-up="up"></a>
                            <a class="glyphicon glyphicon-chevron-down" href="javascript:"
                               data-grid-header-sortable-down="down"></a>
                        </div>
                        Email
                    </th>
                    <th class="col-xs-2" data-grid-header="phone" data-grid-header-sortable="true">
                        <div class="pull-right order-by">
                            <a class="glyphicon glyphicon-chevron-up" href="javascript:"
                               data-grid-header-sortable-up="up"></a>
                            <a class="glyphicon glyphicon-chevron-down" href="javascript:"
                               data-grid-header-sortable-down="down"></a>
                        </div>
                        Phone
                    </th>
                    <th class="col-xs-2" data-grid-header="address" data-grid-header-sortable="true">
                        <div class="pull-right order-by">
                            <a class="glyphicon glyphicon-chevron-up" href="javascript:"
                               data-grid-header-sortable-up="up"></a>
                            <a class="glyphicon glyphicon-chevron-down" href="javascript:"
                               data-grid-header-sortable-down="down"></a>
                        </div>
                        Address
                    </th>
                    <th class="col-xs-2" data-grid-header="role_id" data-grid-header-sortable="true">
                        <div class="pull-right order-by">
                            <a class="glyphicon glyphicon-chevron-up" href="javascript:"
                               data-grid-header-sortable-up="up"></a>
                            <a class="glyphicon glyphicon-chevron-down" href="javascript:"
                               data-grid-header-sortable-down="down"></a>
                        </div>
                        Role
                    </th>
                    <th class="col-xs-2" data-grid-header="action">
                        Action
                    </th>
                </tr>
                </thead>
                <tbody>
                <tr data-grid="row">
                    <td data-cell="name"></td>
                    <td data-cell="surname"></td>
                    <td data-cell="email"></td>
                    <td data-cell="phone"></td>
                    <td data-cell="address"></td>
                    <td data-cell="role_id"></td>
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
</body>
</html>
<script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
<![endif]-->


<script src="${contextPath}/resources/js/grid/ElementListener.js"></script>
<script src="${contextPath}/resources/js/grid/RemoteDataSource.js"></script>
<script src="${contextPath}/resources/js/grid/BooGrid.js"></script>
<script src="${contextPath}/resources/js/usersForAdmin.js"></script>
<script>
    $().BooGrid({
        id: 'productsIds',
        ds: new RemoteDataSource({url: '${contextPath}/admin/getUsers.json'}),
        listeners: [
            new ElementListener($('#progressId'))
        ],
        renderers: {
            "action": function (pv, wv, grid) {
                if (wv.role_id !== 'Admin') {
                    if (wv.enable === 1)
                        return $('<div id=' + wv.id + '><input type="button" class="btn btn-danger"  value="Ban" onclick="deactivateUser(' + wv.id + ')"></div>');
                    else
                        return $('<div id=' + wv.id + '><input type="button" class="btn btn-success"  value="Activate" onclick="activateUser(' + wv.id + ')"></div>');
                }
            }
        }
    })
</script>
