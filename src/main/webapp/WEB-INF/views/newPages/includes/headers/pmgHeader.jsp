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
            </button>
        </div>
        <div class="collapse navbar-collapse ">
            <ul class="nav navbar-nav ">
                <c:choose>
                    <c:when test="${param.pageName == 'Profile'}">
                        <li class="active"><a href="${pageContext.request.contextPath}/pmg/getProfile">Profile</a></li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="${pageContext.request.contextPath}/pmg/getProfile">Profile</a></li>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${param.pageName == 'Users'}">
                        <li class="active"><a href="${pageContext.request.contextPath}/pmg/getUsersPage">Users</a></li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="${pageContext.request.contextPath}/pmg/getUsersPage">Users</a></li>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${param.pageName == 'Products'}">
                        <li class="active"><a href="${pageContext.request.contextPath}/pmg/getPmgProductsPage">Products</a></li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="${pageContext.request.contextPath}/pmg/getPmgProductsPage">Products</a></li>
                    </c:otherwise>
                </c:choose>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">Complaints<i class="icon-angle-down"></i></a>
                    <ul class="dropdown-menu">
                        <li><a href="">My complaints</a></li>
                        <li><a href="${pageContext.request.contextPath}/pmg/allComplaints">All complaints</a></li>
                    </ul>
                </li>
                <c:choose>
                    <c:when test="${param.pageName == 'Statistics'}">
                        <li class="active"><a href="${pageContext.request.contextPath}/pmg/statistics">Statistics</a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="${pageContext.request.contextPath}/pmg/statistics">Statistics</a></li>
                    </c:otherwise>
                </c:choose>
                <li><a href="<%=request.getContextPath()%>/doLogout">Logout</a></li>
            </ul>
        </div>
    </div>
</header>