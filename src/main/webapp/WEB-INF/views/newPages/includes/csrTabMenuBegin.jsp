<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="navbar-fixed-left">
    <div class="row">
        <%--<aside class="leftside col-lg-2 col-md-2 col-sm-2 col-xs-1">--%>
            <div class="collapse navbar-collapse" id="mobilkat" >
                <ul class="nav navbar-nav navbar-dikey">
                    <c:choose>
                        <c:when test="${param.page == 'UserInfo'}">
                            <li class="wet-asphalt active-tab"><a href="${pageContext.request.contextPath}/csr/getUserProfile">User profile</a></li>
                        </c:when>
                        <c:otherwise>
                            <li class="wet-asphalt"><a href="${pageContext.request.contextPath}/csr/getUserProfile">User profile</a></li>
                        </c:otherwise>
                    </c:choose>
                    <c:choose>
                        <c:when test="${param.page == 'UserTariffs'}">
                            <li class="wet-asphalt active-tab"><a href="${pageContext.request.contextPath}/csr/availableUserTariffs">User tariffs</a></li>
                        </c:when>
                        <c:otherwise>
                            <li class="wet-asphalt"><a href="${pageContext.request.contextPath}/csr/availableUserTariffs">User tariffs</a></li>
                        </c:otherwise>
                    </c:choose>
                    <c:choose>
                        <c:when test="${param.page == 'UserServices'}">
                            <li class="wet-asphalt active-tab"><a
                                    href="${pageContext.request.contextPath}/csr/orderService">User services</a></li>
                        </c:when>
                        <c:otherwise>
                            <li class="wet-asphalt"><a href="${pageContext.request.contextPath}/csr/orderService">User
                                services</a></li>
                        </c:otherwise>
                    </c:choose>
                    <c:choose>
                        <c:when test="${param.page == 'UserOrders'}">
                            <li class="wet-asphalt active-tab"><a href="${pageContext.request.contextPath}/csr/orders">Current
                                orders</a></li>
                        </c:when>
                        <c:otherwise>
                            <li class="wet-asphalt"><a href="${pageContext.request.contextPath}/csr/orders">Current
                                orders</a></li>
                        </c:otherwise>
                    </c:choose>
                    <c:choose>
                        <c:when test="${param.page == 'Operations'}">
                            <li class="wet-asphalt active-tab"><a href="${pageContext.request.contextPath}/csr/userHistory">Operations</a></li>
                        </c:when>
                        <c:otherwise>
                            <li class="wet-asphalt"><a href="${pageContext.request.contextPath}/csr/userHistory">Operations</a></li>
                        </c:otherwise>
                    </c:choose>
                    <c:choose>
                        <c:when test="${param.page == 'UserWriteComplaint'}">
                            <li class="wet-asphalt active-tab"><a href="${pageContext.request.contextPath}/csr/getCsrComplaint">Write complaint</a></li>
                        </c:when>
                        <c:otherwise>
                            <li class="wet-asphalt"><a href="${pageContext.request.contextPath}/csr/getCsrComplaint">Write complaint</a></li>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </div>
        </aside>
        <main class="col-lg-10 col-md-10 col-sm-10 col-xs-11">
        </main>
    </div>
</div>