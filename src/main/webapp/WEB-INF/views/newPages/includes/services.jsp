<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container">
    <div class="row">
        <div class="col-md-2"></div>
        <div class="col-md-8">
            <h1 style="text-align: center">Services Catalog</h1>
            <br>
            <c:choose>
                <c:when test="${not empty msg}">
                    <h3 style="text-align: center">${msg}</h3>
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
                                <td colspan="5"><strong> ${categoriesProducts.key}</strong></td>
                            </tr>
                            <c:forEach var="productRow" items="${categoriesProducts.value}">
                                <tr>
                                    <td>${productRow.product.name}</td>
                                    <td>${productRow.product.description}</td>
                                    <td>${productRow.product.durationInDays}</td>
                                    <td>${productRow.price.price}</td>
                                    <c:choose><c:when test="${empty productRow.status}">
                                        <td id="${productRow.product.id}">
                                            <input type="button" class="btn btn-success"
                                                   onclick="activateService(${productRow.product.id})" value="Activate"></td>
                                    </c:when>
                                        <c:when test="${ productRow.status== 'Active'}">
                                            <td id="${productRow.product.id}">
                                                <input type="button" class="btn btn-danger"
                                                       onclick="deactivateService(${productRow.product.id})" value="Deactivate"></td>
                                        </c:when>
                                        <c:otherwise>
                                            <td>${productRow.status}</td>
                                        </c:otherwise>
                                    </c:choose>
                                </tr>
                            </c:forEach>
                        </c:forEach>
                    </table>
                </c:otherwise>
            </c:choose>
        </div>
        <div class="col-md-2"></div>
    </div>
</div>