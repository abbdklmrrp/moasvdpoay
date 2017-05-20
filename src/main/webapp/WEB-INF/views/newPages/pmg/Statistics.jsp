<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="../includes/head.jsp">
        <jsp:param name="tittle" value="Statistics"/>
    </jsp:include>
    <link href="<c:url value="/resources/css/sweet-alert.css"/>" rel="stylesheet"/>
</head>
<body>
<jsp:include page="../includes/headers/pmgHeader.jsp">
    <jsp:param name="pageName" value="Statistics"/>
</jsp:include>
<jsp:include page="../includes/report.jsp"/>
<jsp:include page="../includes/footer.jsp"/>
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/v/dt/dt-1.10.15/datatables.min.js"></script>
<script type="text/javascript" src="<c:url value="/resources/js/sweet-alert.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/js/report.js"/>"></script>
<script>
    $(document).ready(function () {
        $('#btnShowReport').click(function () {
            if (checkDate()) {
                drawChartAndTable('getComplaintsReport', 'Complaints');
            }
        });
        $('#btnDownloadReport').click(function () {
            if (checkDate()) {
                $('#formWithRegionsAndDates').submit();
            }
        });

    });
</script>
</body>
</html>

