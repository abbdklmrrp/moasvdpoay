<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="../includes/head.jsp">
        <jsp:param name="tittle" value="Users"/>
    </jsp:include>
</head>
<body>
    <jsp:include page="../includes/headers/pmgHeader.jsp">
        <jsp:param name="pageName" value="Users"/>
    </jsp:include>
    <jsp:include page="../includes/users.jsp"/>
    <jsp:include page="../includes/footer.jsp"/>
</body>
</html>
<script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
<![endif]-->

<script src="${pageContext.request.contextPath}/resources/js/bootstrap/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap/ie10-viewport-bug-workaround.js"></script>

<script src="${pageContext.request.contextPath}/resources/js/grid/ElementListener.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/grid/RemoteDataSource.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/grid/BooGrid.js"></script>
<script>
    $().BooGrid({
        id: 'productsIds',
        ds: new RemoteDataSource({url: '${pageContext.request.contextPath}/pmg/getUsers.json'}),
        listeners: [
            new ElementListener($('#progressId'))
        ],
        renderers: {
            "action": function (pv, wv, grid) {
                return $('<input type="button" class="btn btn-success"  value="Details">').click( function(){
                        <%--location.href='${pageContext.request.contextPath}/pmg/getDetails?id=' + wv.id--%>
                    }
                );
            }
        }
    })
</script>
