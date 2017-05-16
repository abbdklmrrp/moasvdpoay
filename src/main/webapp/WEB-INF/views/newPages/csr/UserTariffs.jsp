<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../includes/head.jsp" %>
<html>
<head>
    <jsp:include page="../includes/head.jsp">
        <jsp:param name="tittle" value="User tariffs"/>
    </jsp:include>
</head>
<body>
    <jsp:include page="../includes/headers/csrHeader.jsp">
        <jsp:param name="pageName" value="UserTariffs"/>
    </jsp:include>
    <jsp:include page="../includes/tariffs.jsp"/>
    <jsp:include page="../includes/footer.jsp"/>
    <script type="text/javascript" src="<c:url value="/resources/js/tariffsPagination.js"/>"></script>
    <script>
        $(document).ready(function () {

            $('#myTable').pageMe({
                pagerSelector: '#myPager',
                showPrevNext: true,
                hidePageNumbers: false,
                perPage: 4
            }, 'userTariffs');
        });
    </script>
</body>
</html>
