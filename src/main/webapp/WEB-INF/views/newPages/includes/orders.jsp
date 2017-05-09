<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="container">
    <div class="row">
        <div class="col-md-2"></div>
        <div class="col-md-8">
            <h1 style="text-align: center">Orders</h1>
            <br>
            <table border="1" class="table table-striped table-hover" id="tableServiceCatalog">
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
                                <td><input type="button" class="btn btn-warning"
                                           onclick="suspendOrder(${orderRow.productId})" value="Suspend"></td>
                            </c:when>
                            <c:when test="${orderRow.operationStatus.name == 'Suspended'}">
                                <td><input type="button" class="btn btn-success"
                                           onclick="activateOrderAfterSuspend(${orderRow.productId})" value="Suspend"></td>
                            </c:when>
                            <c:otherwise>
                                <td>-</td>
                            </c:otherwise>
                        </c:choose>
                    </tr>
                </c:forEach>
            </table>
        </div>
        <div class="col-md-2"></div>
    </div>
</div>