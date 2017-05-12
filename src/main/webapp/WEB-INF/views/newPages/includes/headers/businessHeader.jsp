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
                <li><a href="<%=request.getContextPath()%>/business/getProfile">Profile</a></li>
                <li><a href="<%=request.getContextPath()%>/business/orders">Orders</a></li>
                <li><a href="<%=request.getContextPath()%>/business/tariffs">Tariffs</a></li>
                <li><a href="<%=request.getContextPath()%>/business/orderService">Services</a></li>
                <li><a href="<%=request.getContextPath()%>/business/getComplaint">Write to support</a></li>
                <li><a href="<%=request.getContextPath()%>/business/regEmployee">Registration</a></li>
                <li><a href="<%=request.getContextPath()%>/doLogout">Logout</a></li>
            </ul>
        </div>
    </div>
</header>