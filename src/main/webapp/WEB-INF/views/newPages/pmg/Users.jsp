<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="../includes/head.jsp">
        <jsp:param name="tittle" value="Users"/>
    </jsp:include>
    <script type="text/javascript">
        google.load("jquery", "1.12.1");
    </script>
</head>
<body>
    <jsp:include page="../includes/headers/pmgHeader.jsp">
        <jsp:param name="pageName" value="Users"/>
    </jsp:include>
    <jsp:include page="../includes/users.jsp"/>
    <jsp:include page="../includes/footer.jsp"/>
</body>
</html>


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
                        location.href='${pageContext.request.contextPath}/pmg/getDetails?id=' + wv.id
                    }
                );
            }
        }
    })
</script>
