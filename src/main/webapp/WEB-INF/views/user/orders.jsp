<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: Yuliya Pedash
  Date: 07.05.2017
  Time: 14:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<body>
<%--<h1>Orders</h1>--%>
<%--<table border="1" class="table table-striped table-hover">--%>
    <%--<tr>--%>
        <%--<td>Name</td>--%>
        <%--<td>Type</td>--%>
        <%--<td>Description</td>--%>
        <%--<td>Date end</td>--%>
        <%--<td>Status</td>--%>
        <%--<td>Action</td>--%>
    <%--</tr>--%>
    <%--<c:forEach var="orderRow" items="${ordersRows}">--%>
        <%--<tr>--%>
            <%--<td>${orderRow.name}</td>--%>
            <%--<td>${orderRow.productType.name}</td>--%>
            <%--<td>${orderRow.description}</td>--%>
            <%--<td><fmt:formatDate value="${orderRow.endDate.time}" type='date' pattern="dd-MM-yyyy"/></td>--%>
            <%--<td>${orderRow.operationStatus.name}</td>--%>
            <%--<c:choose>--%>
                <%--<c:when test="${orderRow.operationStatus.name == 'Active'}">--%>
                    <%--<td><input type="button" class="btn btn-warning"--%>
                               <%--class="toggle-form-btn" value="Suspend"></td>--%>
                <%--</c:when>--%>
                <%--<c:when test="${orderRow.operationStatus.name == 'Suspended'}">--%>
                    <%--<td><input type="button" class="btn btn-success"--%>
                               <%--onclick="activateOrderAfterSuspend(${orderRow.orderId})" value="Suspend"></td>--%>
                <%--</c:when>--%>
                <%--<c:otherwise>--%>
                    <%--<td>-</td>--%>
                <%--</c:otherwise>--%>
            <%--</c:choose>--%>
        <%--</tr>--%>
    <%--</c:forEach>--%>
<%--</table>--%>

<%--<form id="formWithDates" class="form-horizontal"   >--%>
    <%--<br/>--%>
    <%--<label class="control-label">Date Begin:--%>
        <%--<input id="beginDate" type="date" name="beginDate"/>--%>
    <%--</label>--%>
    <%--<br/>--%>
    <%--<label class="control-label">Date End:--%>
        <%--<input id="endDate" type="date" name="endDate"/>--%>
    <%--</label>--%>
    <%--<input type="button" class="btn btn-warning suspendButton"--%>
           <%--onclick="suspendOrder()" value="Suspend">--%>
    <%--<br/>--%>
<%--</form>--%>
<script type="text/javascript" src="<c:url value="${pageContext.request.contextPath}/resources/js/suspendOrder.js"/>"></script>
</body>
</html>
