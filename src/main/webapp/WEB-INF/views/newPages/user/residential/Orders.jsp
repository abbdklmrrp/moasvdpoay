<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="../../includes/head.jsp">
        <jsp:param name="tittle" value="Orders"/>
    </jsp:include>
</head>
<body>
<jsp:include page="../../includes/headers/residentialHeader.jsp"/>
<div class="container">
    <h1>Orders</h1>
    <table border="1" class="table table-striped table-hover">
        <tr>
            <td>Name</td>
            <td>Type</td>
            <td>Description</td>
            <td>Date end</td>
            <td>Status</td>
            <td>Action</td>
        </tr>
        <c:forEach var="orderRow" items="${ordersRows}">
            <tr>
                <td>${orderRow.name}</td>
                <td>${orderRow.productType.name}</td>
                <td>${orderRow.description}</td>
                <td><fmt:formatDate value="${orderRow.endDate.time}" type='date' pattern="dd-MM-yyyy"/></td>
                <td>${orderRow.operationStatus.name}</td>
                <c:choose>
                    <c:when test="${orderRow.operationStatus.name == 'Active'}">
                        <td id="action(${orderRow.orderId})">
                            <input type="button" class="btn btn-warning toggle-form-btn"
                                   onclick="toggleFormFunc(${orderRow.orderId})" value="Suspend"></td>
                    </c:when>
                    <c:when test="${orderRow.operationStatus.name == 'Suspended'}">
                        <td><input type="button" class="btn btn-success"
                                   onclick="activateOrderAfterSuspend(${orderRow.orderId})" value="Activate"></td>
                    </c:when>
                    <c:otherwise>
                        <td>-</td>
                    </c:otherwise>
                </c:choose>
            </tr>
        </c:forEach>
    </table>

    <form id="formWithDates" class="form-horizontal">
        <br/>
        <label class="control-label">Date Begin:
            <input id="beginDate" name="beginDate"  type="date" />
        </label>
        <br/>
        <label class="control-label">Date End:
            <input id="endDate"  name="endDate" type="date" />
        </label>
        <input type="hidden" id="orderId" name="orderId" >
        <input type="submit" class="btn btn-warning suspendButton"
               value="Suspend">
        <%--<input type="button" class="btn btn-warning suspendButton"--%>
               <%--onclick="suspendOrder()" value="Suspend">--%>
        <br/>
    </form>

</div>
<jsp:include page="../../includes/footer.jsp"/>
<script type="text/javascript" src="<c:url value="${contextPath}/resources/js/suspendOrder.js"/>"></script>
<link href="<c:url value="/resources/css/sweet-alert.css"/>" rel="stylesheet">
<script type="text/javascript" src="<c:url value="${contextPath}/resources/js/sweet-alert.min.js"/>"></script>
</body>
</html>
