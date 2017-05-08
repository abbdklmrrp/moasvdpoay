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
                <li><a href="">Profile</a></li>
                <li><a href="">Orders</a></li>
                <li><a href="<%=request.getContextPath()%>/user/business/tariffs">Tariffs</a></li>
                <li><a href="<%=request.getContextPath()%>/user/orderService">Services</a></li>
                <li><a href="${contextPath}/user/getComplaint">Write to support</a></li>
                <li><a href="">Registration</a></li>
                <li><a href="<%=request.getContextPath()%>/doLogout">Logout</a></li>
            </ul>
        </div>
    </div>
</header>