<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="../../includes/head.jsp">
        <jsp:param name="tittle" value="Services"/>
    </jsp:include>
</head>
<body>
<jsp:include page="../../includes/headers/residentialHeader.jsp"/>
<div class="container">
    <div class="row">
        <div class="col-md-2"></div>
        <div class="col-md-8">
            <h1 style="text-align: center">Services Catalog</h1>
            <c:choose>
            <c:when test="${not empty msg}">
                <h3>${msg}</h3>
            </c:when>
            <c:otherwise>
            <h3></h3>
            <table border="1" class="table table-striped table-hover" id="tableServiceCatalog">
                <c:if test="${not empty resultMsg}">
                    <h3>${resultMsg}</h3>
                </c:if>
                <tr>
                    <th>Name</th>
                    <th>Description</th>
                    <th>Duration(days)</th>
                    <th>Price</th>
                    <th>Status</th>
                </tr>
                <c:forEach var="categoriesProducts" items="${categoriesProducts}">
                    <tr>
                        <td colspan="5" style="text-align: center"><strong> ${categoriesProducts.key}</strong></td>
                    </tr>
                    <c:forEach var="productRow" items="${categoriesProducts.value}">
                        <tr>
                            <td>${productRow.product.name}</td>
                            <td>${productRow.product.description}</td>
                            <td>${productRow.product.durationInDays}</td>
                            <td>${productRow.price.price}</td>
                            <c:choose><c:when test="${empty productRow.status}">
                                <td class="success">
                                    <form method="POST" action="<%=request.getContextPath()%>/user/ordered" style="margin: 0px">
                                        <input type="hidden" value="${productRow.product.id}" name="productId">
                                        <input type="Submit" value="Activate" class="btn btn-primary">
                                    </form>
                                </td>
                            </c:when>
                                <c:when test="${ productRow.status== 'Active'}">
                                    <td class="danger">
                                        <form method="POST" action="<%=request.getContextPath()%>/user/deactivate" style="margin: 0px">
                                            <input type="hidden" value="${productRow.product.id}" name="productId">
                                            <input type="Submit" value="Deactivate" class="btn btn-primary">
                                        </form>
                                    </td>
                                </c:when>
                                <c:otherwise>
                                    <td>${productRow.status}</td>
                                </c:otherwise>
                            </c:choose>
                        </tr>
                    </c:forEach>
                </c:forEach>
            </table>
        </div>
        </c:otherwise>
        </c:choose>
        <div class="col-md-2"></div>
    </div>
</div>
<jsp:include page="../../includes/footer.jsp"/>
</body>
</html>