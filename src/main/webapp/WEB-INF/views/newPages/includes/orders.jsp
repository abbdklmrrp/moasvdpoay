<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="container">
    <div class="row">
        <div class="col-md-2"></div>
        <div class="col-md-8">
            <h1 style="text-align: center">Orders</h1>
            <br>
            <c:choose>
                <c:when test="${empty ordersRows}">
                    <h3 style="text-align: center">There are no orders now!</h3>
                </c:when>
                <c:otherwise>
                <table border="1" class="table table-striped table-hover">
                    <tr>
                        <td>Name</td>
                        <td>Type</td>
                        <td>Date end</td>
                        <td>Status</td>
                        <td>Action</td>
                    </tr>
                    <c:forEach var="orderRow" items="${ordersRows}">
                        <tr>
                            <td><a href="#" id="showDescriptionOfProduct">${orderRow.name}</a></td>
                            <td>${orderRow.productType.name}</td>
                            <td><fmt:formatDate value="${orderRow.endDate.time}" type='date' pattern="dd-MM-yyyy"/></td>
                            <td>${orderRow.operationStatus.name}</td>

                                    <td id="action${orderRow.orderId}">
                                        <c:choose>
                                        <c:when test="${orderRow.operationStatus.name == 'Active'}">
                                        <input type="button" class="btn btn-warning"
                                               onclick="toggleFormFunc(${orderRow.orderId})" value="Suspend"></td>
                                </c:when>
                                <c:when test="${orderRow.operationStatus.name == 'Suspended'}">
                                 <input type="button" class="btn btn-success"
                                               onclick="activateOrderAfterSuspend(${orderRow.orderId})" value="Activate">
                                </c:when>
                                <c:otherwise>
                                  -
                                </c:otherwise>
                            </c:choose>
                        </td>
                        </tr>
                    </c:forEach>
                </table>
                </c:otherwise>
            </c:choose>
        </div>
        <div class="col-md-2"></div>
    </div>
    <div class="row">
        <form id="formWithDates" class="form-horizontal">
            <br/>
            <label class="control-label">Date Begin:
                <input id="beginDate" name="beginDate" type="date"/>
            </label>
            <br/>
            <label class="control-label">Date End:
                <input id="endDate" name="endDate" type="date"/>
            </label>
            <input type="hidden" id="orderId" name="orderId">
            <input type="submit" class="btn btn-warning suspendButton"
                   value="Suspend">
            <%--<input type="button" class="btn btn-warning suspendButton"--%>
            <%--onclick="suspendOrder()" value="Suspend">--%>
            <br/>
        </form>
    </div>
</div>
<link href="<c:url value="/resources/css/sweet-alert.css"/>" rel="stylesheet">
<script type="text/javascript" src="<c:url value="${contextPath}/resources/js/sweet-alert.min.js"/>"></script>