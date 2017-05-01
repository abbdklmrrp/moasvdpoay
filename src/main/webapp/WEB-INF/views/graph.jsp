<%--
  Created by IntelliJ IDEA.
  User: Aleksandr
  Date: 20.04.17
  Time: 20:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <link href="<c:url value="/resources/css/webGraph.css"/>" rel="stylesheet"/>
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/v/dt/dt-1.10.15/datatables.min.css"/>
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
    <script type="text/javascript" src="https://cdn.datatables.net/v/dt/dt-1.10.15/datatables.min.js"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/drawReport.js"/>"></script>
</head>
<body>
<form id="formWithRegionsAndDates">
    <label>Choose region:
        <select id="sel1" name="region">
            <c:forEach var="region" items="${regions}">
                <option value="${region.id}">${region.name}</option>
            </c:forEach>
        </select>
    </label>
    <br/>
    <label>Date Begin:
        <input type="date" name="beginDate"/>
    </label>
    <br/>
    <label>Date End:
        <input type="date" name="endDate"/>
    </label>
    <br/>
</form>
<button id="b1" onclick="drawChart()">Show</button>
<span id="err" style="color: red"></span>
<div class="center" id="line_top_x" style="width: 900px; height: 500px"></div>
<div id="table_div"></div>
<%--<table id="table_id" class="display">--%>
    <%--<thead>--%>
    <%--<tr>--%>
        <%--<th>Date</th>--%>
        <%--<th>Orders</th>--%>
        <%--<th>Complaints</th>--%>
    <%--</tr>--%>
    <%--</thead>--%>
<%--</table>--%>
</body>
</html>