<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="../includes/head.jsp">
        <jsp:param name="tittle" value="Tariffs"/>
    </jsp:include>
</head>
<body>
    <jsp:include page="../includes/headers/residentialHeader.jsp">
        <jsp:param name="pageName" value="Tariffs"/>
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
            }, 'allTariffs');
        });
    </script>
</body>
</html>
