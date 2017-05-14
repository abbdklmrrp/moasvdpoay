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
<jsp:include page="../includes/users.jsp"/>
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
