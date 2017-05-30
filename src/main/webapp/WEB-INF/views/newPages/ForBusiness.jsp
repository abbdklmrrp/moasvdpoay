<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="includes/head.jsp">
        <jsp:param name="tittle" value="For business "/>
    </jsp:include>
</head>
<body>
<jsp:include page="includes/headers/aboutHeader.jsp">
    <jsp:param name="pageName" value="business"/>
</jsp:include>
<section class="container">
    <br>
    <br>
    <div class="col-md-1"></div>
    <div class="col-md-10">
        <div class="col-md-12" style="text-indent: 20px">
            <div class="col-md-6">
                <div class="col-md-4" style="text-indent:0; padding-top: 10px">
                    <img src="<c:url value="/resources/photos/Network-big.png"/>" style="width: 100%">
                </div>
                <div class="col-md-8" style="text-indent:0">
                    <h4>We're the network you can trust</h4>
                    As well as providing increased 4G coverage indoors and out, we also have our own nationwide fibre
                    network. So we have the power to control the quality of our signal and to resolve network issues
                    fast.
                </div>
            </div>
            <div class="col-md-6">
                <div class="col-md-4" style="text-indent:0; padding-top: 10px">
                    <img src="<c:url value="/resources/photos/Partner-big.png"/>" style="width: 100%">
                </div>
                <div class="col-md-8" style="text-indent:0">
                    <h4>We're a transformation partner</h4>
                    As a total communications provider, we can help you be Ready for the new world. Our unique portfolio
                    across fixed, mobile and cloud services is designed to deliver all your communications needs in an
                    effective and holistic way, and transform the way you do business.
                </div>
            </div>
            <div class="col-md-6">
                <div class="col-md-4" style="text-indent:0; padding-top: 10px">
                    <img src="<c:url value="/resources/photos/Businesses-big.png"/>" style="width: 100%">
                </div>
                <div class="col-md-8" style="text-indent:0">
                    <h4>We help businesses get ready for the future</h4>
                    With us you can measure how prepared your business is for today –
                    and tomorrow. Our simple, interactive online assessment can help you gauge your organisation’s
                    strengths and weaknesses, compare your score with other organisations like yours, and put you on the
                    path towards unlocking its full potential.
                </div>
            </div>
            <div class="col-md-6">
                <div class="col-md-4" style="text-indent:0; padding-top: 10px">
                    <img src="<c:url value="/resources/photos/Pioneer-big.png"/>" style="width: 100%">
                </div>
                <div class="col-md-8" style="text-indent:0">
                    <h4>We pioneer Better Ways of Working</h4>
                    Using our tried and tested methodology, we’ll look at your use of people, space, processes and
                    technology. From this, we’ll deliver a tailored workplace solution to help you work more flexibly,
                    increase productivity and realise more efficiencies.
                </div>
            </div>
        </div>
    </div>
</section>
<br>
<br>
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
                    <th class="col-xs-1" data-grid-header="type_id" data-grid-header-sortable="true">
                        <div class="pull-right order-by">
                            <a class="glyphicon glyphicon-chevron-up" href="javascript:"
                               data-grid-header-sortable-up="up"></a>
                            <a class="glyphicon glyphicon-chevron-down" href="javascript:"
                               data-grid-header-sortable-down="down"></a>
                        </div>
                        Type
                    </th>
                    <%--<th class="col-xs-2" data-grid-header="duration_in_days" data-grid-header-sortable="true">--%>
                        <%--<div class="pull-right order-by">--%>
                            <%--<a class="glyphicon glyphicon-chevron-up" href="javascript:"--%>
                               <%--data-grid-header-sortable-up="up"></a>--%>
                            <%--<a class="glyphicon glyphicon-chevron-down" href="javascript:"--%>
                               <%--data-grid-header-sortable-down="down"></a>--%>
                        <%--</div>--%>
                        <%--Duration(in days)--%>
                    <%--</th>--%>
                    <th class="col-xs-1" data-grid-header="base_price">
                        Price($)
                    </th>
                    <th class="col-xs-1" data-grid-header="action">
                        Action
                    </th>
                </tr>
                </thead>
                <div data-grid="message"></div>
                <tbody>
                <tr data-grid="row">
                    <td data-cell="name"></td>
                    <td data-cell="type_id"></td>
                    <%--<td data-cell="duration_in_days"></td>--%>
                    <td data-cell="base_price"></td>
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
<jsp:include page="includes/footer.jsp"/>
</body>
<script src="${pageContext.request.contextPath}/resources/js/grid/ElementListener.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/grid/RemoteDataSource.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/grid/BooGrid.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/priceInfo.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/pricesPagination.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/viewPrices.js"></script>
<script>
    $().BooGrid({
        id: 'productsIds',
        ds: new RemoteDataSource({url: '${pageContext.request.contextPath}/productForBusiness.json'}),
        listeners: [
            new ElementListener($('#progressId'))
        ],
        renderers: {
            "base_price": function (pv, wv, grid) {
                return wv.base_price;
            },
            "action": function (pv, wv, grid) {
                return $('<input type="button" class="btn btn-success"  value="Details" >').click(function () {
                        swal(wv.description);
                    }
                );
            }
        }
    });
</script>
</html>
