<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="../includes/head.jsp">
        <jsp:param name="tittle" value="Operations history"/>
    </jsp:include>
</head>
<body>
    <jsp:include page="../includes/headers/residentialHeader.jsp">
        <jsp:param name="pageName" value="ComplaintHistory"/>
    </jsp:include>
    <jsp:include page="../includes/complaintHistory.jsp"/>
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
        ds: new RemoteDataSource({url: '${pageContext.request.contextPath}/residential/getComplaintHistory.json'}),
        listeners: [
            new ElementListener($('#progressId'))
        ],
        renderers: {
            "creatingDate": function (pv, wv, grid) {
                var dateFormat = {
                    year: 'numeric',
                    month: 'long',
                    day: 'numeric'
                };
                return new Date(wv.creatingDate).toLocaleString("en-US", dateFormat).toString();
            }
        }
    })
</script>
