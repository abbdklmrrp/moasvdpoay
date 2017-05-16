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
            </button>
        </div>
        <div class="collapse navbar-collapse ">
            <ul class="nav navbar-nav ">
                <c:choose>
                    <c:when test="${param.pageName == 'about'}">
                        <li class="active"><a href="<%=request.getContextPath()%>/">About</a></li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="<%=request.getContextPath()%>/">About</a></li>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${param.pageName == 'business'}">
                        <li class="active"><a
                                href="<%=request.getContextPath()%>/forBusiness">For business</a></li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="<%=request.getContextPath()%>/forBusiness">For business</a></li>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${param.pageName == 'customers'}">
                        <li class="active"><a href="<%=request.getContextPath()%>/forCustomers">For private
                            customers</a></li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="<%=request.getContextPath()%>/forCustomers">For private customers</a></li>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${param.pageName == 'login'}">
                        <li class="active"><a href="<%=request.getContextPath()%>/"login>Login</a></li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="<%=request.getContextPath()%>/login">Login</a></li>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${param.pageName == 'registration'}">
                        <li class="active"><a
                                href="<%=request.getContextPath()%>/registration">Registration</a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="<%=request.getContextPath()%>/registration">Registration</a></li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </div>
    </div>
</header>