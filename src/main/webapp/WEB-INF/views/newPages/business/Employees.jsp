<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="../includes/head.jsp">
        <jsp:param name="tittle" value="Employees"/>
    </jsp:include>
    <script type="text/javascript" src="http://www.google.com/jsapi"></script>
    <script type="text/javascript">
        google.load("jquery", "1.4.4");
    </script>
</head>
<body>
    <jsp:include page="../includes/headers/businessHeader.jsp">
        <jsp:param name="pageName" value="Employees"/>
    </jsp:include>
    <jsp:include page="../includes/users.jsp">
        <jsp:param name="pageName" value="Employee"/>
    </jsp:include>
    <jsp:include page="../includes/footer.jsp"/>
</body>
</html>
<script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
<![endif]-->


<script src="${pageContext.request.contextPath}/resources/js/grid/ElementListener.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/grid/RemoteDataSource.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/grid/BooGrid.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/usersForAdmin.js"></script>
<script>
    $().BooGrid({
        id: 'productsIds',
        ds: new RemoteDataSource({url: '${pageContext.request.contextPath}/business/getEmployees.json'}),
        listeners: [
            new ElementListener($('#progressId'))
        ],
        renderers: {
            "action": function (pv, wv, grid) {
                if (wv.status === 'ENABLE')
                    return $('<div id=' + wv.id + '><input type="button" class="btn btn-danger"  value="Ban" onclick="deactivateUser(' + wv.id + ')"></div>');
                else if (wv.status === 'DISABLE') {
                    return $('<div id=' + wv.id + '><input type="button" class="btn btn-success"  value="Activate" onclick="activateUser(' + wv.id + ')"></div>');
                }
            }
        }
    })
</script>
