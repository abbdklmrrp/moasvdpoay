<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="../includes/head.jsp">
        <jsp:param name="tittle" value="My complaints"/>
    </jsp:include>
</head>
<body>
<jsp:include page="../includes/headers/pmgHeader.jsp">
    <jsp:param name="pageName" value="Complaints"/>
</jsp:include>
<div class="container">
    <h3 id="header-all-complaints" class="hide" style="text-align: center">My complaints</h3>
    <h3 id="header-no-complaints" style="text-align: center">No complaints</h3>
    <div class="row">
        <div class="table-responsive">
            <table class="table table-bordered hide" id="tbl-all-complaints">
                <thead>
                <tr>
                    <th>Date</th>
                    <th>Time</th>
                    <th>Description</th>
                </tr>
                </thead>
                <tbody id="myTable">
                </tbody>
            </table>
        </div>
        <div class="col-md-12 text-center">
            <ul class="pagination" id="myPager"></ul>
        </div>
    </div>
</div>
<jsp:include page="../includes/footer.jsp"/>
<script type="text/javascript" src="<c:url value="/resources/js/complaintsPagination.js"/>"></script>
<script>
    $(document).ready(function () {

        $('#myTable').pageMe({
            pagerSelector: '#myPager',
            showPrevNext: true,
            hidePageNumbers: false,
            perPage: 10
        }, 'getDataByPMG');

    });
    function forwardTo(complaintId) {
        return location.href = '${pageContext.request.contextPath}/pmg/complaintInfo?id='+complaintId;
    }
</script>
</body>
</html>

