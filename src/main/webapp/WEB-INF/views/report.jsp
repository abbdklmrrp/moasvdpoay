<%--
  Created by IntelliJ IDEA.
  User: Aleksandr
  Date: 20.04.17
  Time: 20:43
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Report</title>
    <jsp:include page="newPages/includes/head.jsp"/>
</head>
<body>
<form id="formWithRegionsAndDates" action="<%=request.getContextPath()%>/report/download" method="get">
    <label>Choose region:
        <select id="sel1" name="region" required>
            <c:forEach var="region" items="${regions}">
                <option value="${region.id}">${region.name}</option>
            </c:forEach>
        </select>
    </label>
    <br/>
    <label>Date Begin:
        <input id="beginDate" type="date" name="beginDate" onchange="fillData()"/>
    </label>
    <br/>
    <label>Date End:
        <input id="endDate" type="date" name="endDate" onchange="fillData()"/>
    </label>
    <br/>
</form>
<button id="btnDownloadReport" disabled="disabled">Download report</button>
<button id="btnShowReport" disabled="disabled">Show report</button>
<br/>
<span id="err" style="color: red"></span>
<div class="center" id="line_top_x" style="width: 900px; height: 500px"></div>
<div id="table_div"></div>
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
<script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/v/dt/dt-1.10.15/datatables.min.js"></script>
<script type="text/javascript" src="<c:url value="/resources/js/report.js"/>"></script>
</body>
</html>