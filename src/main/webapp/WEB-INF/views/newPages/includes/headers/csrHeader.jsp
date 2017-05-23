<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<header class="navbar navbar-inverse navbar-fixed-top wet-asphalt" role="banner">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
        </div>
        <div class="collapse navbar-collapse ">
            <ul class="nav navbar-nav ">
                <c:choose>
                    <c:when test="${param.pageName == 'Profile'}">
                        <li class="active"><a href="${pageContext.request.contextPath}/csr/getProfile">Profile</a></li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="${pageContext.request.contextPath}/csr/getProfile">Profile</a></li>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${param.pageName == 'Customers'}">
                        <li class="active"><a href="${pageContext.request.contextPath}/csr/getCustomers">Customers</a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="${pageContext.request.contextPath}/csr/getCustomers">Customers</a></li>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${param.pageName == 'Orders'}">
                        <li class="dropdown active">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">Orders<i
                                    class="icon-angle-down"></i></a>
                            <ul class="dropdown-menu">
                                <li><a href="${pageContext.request.contextPath}/csr/getAllOrdersPage">All orders</a>
                                </li>
                                <li><a href="${pageContext.request.contextPath}/csr/getMyOrdersPage">My orders</a></li>
                                <li><a href="${pageContext.request.contextPath}/csr/getHistoryOrdersPage">History</a>
                                </li>
                            </ul>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">Orders<i
                                    class="icon-angle-down"></i></a>
                            <ul class="dropdown-menu">
                                <li><a href="${pageContext.request.contextPath}/csr/getAllOrdersPage">All orders</a>
                                </li>
                                <li><a href="${pageContext.request.contextPath}/csr/getMyOrdersPage">My orders</a></li>
                                <li><a href="${pageContext.request.contextPath}/csr/getHistoryOrdersPage">History</a>
                                </li>
                            </ul>
                        </li>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${param.pageName == 'Users'}">
                        <li class="active"><a href="${pageContext.request.contextPath}/csr/getUsersPage">Users</a></li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="${pageContext.request.contextPath}/csr/getUsersPage">Users</a></li>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${param.pageName == 'Products'}">
                        <li class="active"><a
                                href="${pageContext.request.contextPath}/csr/getCsrProductsPage">Products</a></li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="${pageContext.request.contextPath}/csr/getCsrProductsPage">Products</a></li>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${param.pageName == 'Statistics'}">
                        <li class="active"><a href="${pageContext.request.contextPath}/csr/statistics">Statistics</a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="${pageContext.request.contextPath}/csr/statistics">Statistics</a></li>
                    </c:otherwise>
                </c:choose>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">Registration<i
                            class="icon-angle-down"></i></a>
                    <ul class="dropdown-menu">
                        <li><a href="${pageContext.request.contextPath}/csr/registrationFromCsr">New user</a></li>
                        <li><a href="${pageContext.request.contextPath}/csr/getCreateCustomer">New customer</a></li>
                    </ul>
                </li>
                <li><a href="<%=request.getContextPath()%>/doLogout">Logout</a></li>
            </ul>
        </div>
    </div>
</header>