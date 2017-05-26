<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="../includes/head.jsp">
        <jsp:param name="tittle" value="Fill in tariff with service"/>
    </jsp:include>
    <link href="<c:url value="/resources/css/select.css" />" rel="stylesheet">
</head>
<body>
    <jsp:include page="../includes/headers/adminHeader.jsp">
        <jsp:param name="pageName" value="FillTariff"/>
    </jsp:include>
    <form action="${pageContext.request.contextPath}/admin/fillTariff?tariffId=${tariffId}" method="post">
        <div class="container">
            <div class="col-md-2"></div>
            <div class="col-md-8">
                <h1 style="text-align: center">Fill in tariff with services</h1>
                <br>
                <table border="1" class="table table-striped table-hover" id="allServicesWithCategory">
                    <tr>
                        <th>Category</th>
                        <th>Services</th>
                    </tr>
                    <c:forEach var="servcesByCategory" items="${allServicesWithCategory}">
                        <tr>
                            <td>${servcesByCategory.key}</td>
                            <td>
                                <select name="selectedService" id="soflow">
                                    <option value="">-</option>
                                    <c:forEach var="product" items="${servcesByCategory.value}">
                                        <option value="${product.id}">${product.name}</option>
                                    </c:forEach>
                                </select>
                            </td>

                        </tr>
                    </c:forEach>
                </table>
                <div class="row">
                    <div class="col-sm-4 col-xs-0"></div>
                    <button type="submit" class="btn btn-primary col-sm-4 col-xs-12">Save</button>
                    <div class="col-sm-4 col-xs-0"></div>
                </div>
            </div>
            <div class="col-md-2"></div>
        </div>
    </form>
    <jsp:include page="../includes/footer.jsp"/>
</body>
</html>