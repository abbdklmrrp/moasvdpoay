<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="../includes/head.jsp">
        <jsp:param name="tittle" value="Create service"/>
    </jsp:include>
</head>
<body>
<jsp:include page="../includes/headers/adminHeader.jsp"/>
<div class="container" style="width:60%; max-width: 600px;">
    <form method="POST" modelAttribute="product" action="<%=request.getContextPath()%>/admin/addService">
        <div class="login-form">
            <h1 style="text-align: center">Create service</h1>

            <%--<h6>Select product type</h6>--%>
            <%--<div class="form-group ">--%>
            <%--<select id="productType" name="productType" class="form-control" aria-required="true" onChange="Selected(this)">--%>
            <%--<c:forEach var="productType" items="${productTypes}">--%>
            <%--<option value="${productType}">${productType}</option>--%>
            <%--</c:forEach>--%>
            <%--</select>--%>
            <%--</div>--%>

            <c:if test="${not empty error}">
                <span style="float:right ; color: #10CE88;">${error}</span>
            </c:if>
            <c:if test="${not empty errorEmptyProduct}">
                <span style="float:right ; color: #10CE88;">${errorEmptyProduct}</span>
            </c:if>

            <div class="form-group row" id='categoryId'>
                <label class="col-sm-4 control-label">Select category</label>
                <div class="col-sm-8">
                    <select name="categoryId" class="form-control" required>
                        <c:forEach var="category" items="${productCategories}">
                            <option value="${category.id}">${category.name}</option>
                        </c:forEach>
                        <option value="">New category</option>
                    </select>
                </div>
            </div>
            <div class="form-group row" id="newCategory">
                <label class="col-sm-4 control-label">Enter new category</label>
                <div class="col-sm-8">
                    <input type="text" class="form-control" placeholder="New category"
                           name="newCategory" id="newCategory-inpt">
                    <i class="fa fa-user"></i>
                </div>
            </div>
            <div class="form-group row" id="newCategoryDesc">
                <label class="col-sm-4 control-label">Enter new category description</label>
                <div class="col-sm-8">
                    <input type="text" class="form-control"
                                             placeholder="New category description "
                                             name="newCategoryDesc" id="newCategoryDesc-inpt">
                    <i class="fa fa-user"></i>
                </div>
            </div>
            <div class="form-group row">
                <label class="col-sm-4 control-label">Enter name</label>
                <div class="col-sm-8">
                    <input type="text" class="form-control" placeholder="Name " id="Name"
                                             name="name" required>
                    <i class="fa fa-user"></i>
                </div>
            </div>
            <div class="form-group row">
                <label class="col-sm-4 control-label">Enter description</label>
                <div class="col-sm-8">
                    <input type="text" class="form-control" placeholder="Description "
                                             id="Description" name="description" required>
                    <i class="fa fa-user"></i>
                </div>
            </div>
            <div class="form-group row">
                <label class="col-sm-4 control-label">Customer type</label>
                <div class="col-sm-8">
                    <select name="customerTypeId" class="form-control" id="customerTypeId" required>
                    <option value="1">Legal</option>
                    <option value="2">Individual</option>
                </select></div>
            </div>
            <div class="form-group row">
                <label class="col-sm-4 control-label">Select duration in days</label>
                <div class="col-sm-8">
                    <select name="durationInDays" class="form-control" id="durationInDays" required>
                        <option value="365">365</option>
                    </select>
                </div>
            </div>
            <div class="form-group row">
                <label class="col-sm-4 control-label">Need processing by admin</label>
                <div class="col-sm-8">
                    <div class="row">
                        <input type="radio" name="needProcessing" class="col-sm-1" value="1">
                        <label class="col-sm-5 control-label">Yes</label>
                        <input type="radio" name="needProcessing" class="col-sm-1" value="0" checked>
                        <label class="col-sm-5 control-label">No</label>
                    </div>
                    <%--<select name="needProcessing" class="form-control" id="needProcessing">--%>
                    <%--<option value="1">Need processing</option>--%>
                    <%--<option value="0">Do not need processing</option>--%>
                    <%--</select>--%>
                </div>
            </div>

            <div class="form-group row">
                <label class="col-sm-4 control-label">Select status service</label>
                <div class="col-sm-8">
                    <div class="row">
                        <input type="radio" name="status" class="col-sm-1" value="1">
                        <label class="col-sm-5 control-label">Activate</label>
                        <input type="radio" name="status" class="col-sm-1" value="0" checked>
                        <label class="col-sm-5 control-label">Not active</label>
                    </div>
                    <%--<select name="status" class="form-control" id="Status">--%>
                        <%--<option value="1">Activate</option>--%>
                        <%--<option value="0">Not active</option>--%>
                    <%--</select>--%>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-4 col-xs-4"></div>
                <button type="submit" class="btn btn-primary col-sm-4 col-xs-4">Save</button>
                <div class="col-sm-4 col-xs-4"></div>
            </div>

        </div>
    </form>
</div>

<jsp:include page="../includes/footer.jsp"/>
<%--<script type="text/javascript" src="http://www.google.com/jsapi"></script>--%>
<%--<script type="text/javascript">--%>
<%--google.load("jquery", "1.4.4");--%>
<%--</script>--%>
<script src="<c:url value="/resources/js/newCategoryService.js"/>"></script>
</body>
</html>