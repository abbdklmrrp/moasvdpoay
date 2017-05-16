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
                        <li class="active"><a href="<%=request.getContextPath()%>/business/getProfile">Profile</a></li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="<%=request.getContextPath()%>/business/getProfile">Profile</a></li>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${param.pageName == 'Employees'}">
                        <li class="active"><a
                                href="<%=request.getContextPath()%>/business/getEmployeesPage">Employees</a></li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="<%=request.getContextPath()%>/business/getEmployeesPage">Employees</a></li>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${param.pageName == 'Orders'}">
                        <li class="active"><a href="<%=request.getContextPath()%>/business/orders">Orders</a></li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="<%=request.getContextPath()%>/business/orders">Orders</a></li>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${param.pageName == 'Tariffs'}">
                        <li class="active"><a href="<%=request.getContextPath()%>/business/tariffs">Tariffs</a></li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="<%=request.getContextPath()%>/business/tariffs">Tariffs</a></li>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${param.pageName == 'Services'}">
                        <li class="active"><a href="<%=request.getContextPath()%>/business/orderService">Services</a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="<%=request.getContextPath()%>/business/orderService">Services</a></li>
                    </c:otherwise>
                </c:choose>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">History<i class="icon-angle-down"></i></a>
                    <ul class="dropdown-menu">
                        <li><a href="${pageContext.request.contextPath}/business/operationsHistory">Operation history</a></li>
                        <li><a href="${pageContext.request.contextPath}/business/complaintHistory">Complaint history</a></li>
                    </ul>
                </li>
                <c:choose>
                    <c:when test="${param.pageName == 'WriteToSupport'}">
                        <li class="active"><a href="<%=request.getContextPath()%>/business/getComplaint">Write to
                            support</a></li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="<%=request.getContextPath()%>/business/getComplaint">Write to support</a></li>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${param.pageName == 'RegEmployee'}">
                        <li class="active"><a href="<%=request.getContextPath()%>/business/regEmployee">Employee
                            registration</a></li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="<%=request.getContextPath()%>/business/regEmployee">Employee registration</a></li>
                    </c:otherwise>
                </c:choose>
                <li><a href="<%=request.getContextPath()%>/doLogout">Logout</a></li>
            </ul>
        </div>
    </div>
</header>