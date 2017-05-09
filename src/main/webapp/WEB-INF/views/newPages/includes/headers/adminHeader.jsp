<header class="navbar navbar-inverse navbar-fixed-top wet-asphalt" role="banner">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
        </div>
        <div class="collapse navbar-collapse ">
            <ul class="nav navbar-nav ">
                <li><a href="${pageContext.request.contextPath}/admin/getProfile">Profile</a></li>
                <li><a href="${pageContext.request.contextPath}/admin/getUsersPage">Users</a></li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">Products<i class="icon-angle-down"></i></a>
                    <ul class="dropdown-menu">
                        <li><a href="${pageContext.request.contextPath}/admin/getAllProducts">All products</a></li>
                        <li><a href="${pageContext.request.contextPath}/admin/addTariff">New tariff</a></li>
                        <li><a href="${pageContext.request.contextPath}/admin/addService">New service</a></li>
                    </ul>
                </li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">Registration<i class="icon-angle-down"></i></a>
                    <ul class="dropdown-menu">
                        <li><a href="${pageContext.request.contextPath}/admin/registrationFromAdmin">New user</a></li>
                        <li><a href="#">New customer</a></li>
                    </ul>
                </li>
                <li><a href="#">Promotions</a></li>
                <li><a href="<%=request.getContextPath()%>/doLogout">Logout</a></li>
            </ul>
        </div>
    </div>
</header>