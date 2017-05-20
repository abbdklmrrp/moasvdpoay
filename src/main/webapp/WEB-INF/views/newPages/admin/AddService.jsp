<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="../includes/head.jsp">
        <jsp:param name="tittle" value="Create service"/>
    </jsp:include>
    <link href="<c:url value="/resources/css/price.css" />" rel="stylesheet">
</head>
<body>
<jsp:include page="../includes/headers/adminHeader.jsp">
    <jsp:param name="pageName" value="AddService"/>
</jsp:include>
<div class="container" style="margin-bottom: 30px; width:60%; max-width: 600px;">
    <form method="POST" modelAttribute="product" action="<%=request.getContextPath()%>/admin/addService">
        <div class="login-form">
            <h1 style="text-align: center">Create service</h1>

            <c:if test="${not empty error}">
                <span style="float:right ; color: #10CE88;">${error}</span>
            </c:if>

            <div class="form-group row" id='categoryId'>
                <label class="col-sm-4 control-label">Select category</label>
                <div class="col-sm-8">
                    <select name="categoryId" class="form-control" onclick="SelectedCategory(this)" required>
                        <c:forEach var="category" items="${productCategories}">
                            <option value="${category.id}">${category.categoryName}</option>
                        </c:forEach>
                        <option value="">New category</option>
                    </select>
                </div>
            </div>
            <div class="form-group row" id="newCategory">
                <label class="col-sm-4 control-label">New category name</label>
                <div class="col-sm-8">
                    <input type="text" class="form-control" placeholder="New category"
                           name="categoryName" id="newCategory-inpt">
                    <i class="fa fa-user"></i>
                </div>
            </div>
            <div class="form-group row" id="newCategoryDesc">
                <label class="col-sm-4 control-label">New category description</label>
                <div class="col-sm-8">
                    <input type="text" class="form-control"
                           placeholder="New category description "
                           name="categoryDescription" id="newCategoryDesc-inpt">
                    <i class="fa fa-user"></i>
                </div>
            </div>
            <div class="form-group row">
                <label class="col-sm-4 control-label">Name</label>
                <div class="col-sm-8">
                    <input type="text" class="form-control" placeholder="Name " id="Name"
                           name="name" required>
                    <i class="fa fa-user"></i>
                </div>
            </div>
            <div class="form-group row">
                <label class="col-sm-4 control-label">Description</label>
                <div class="col-sm-8">
                    <input type="text" class="form-control" placeholder="Description "
                           id="Description" name="description" required>
                    <i class="fa fa-user"></i>
                </div>
            </div>
            <div class="form-group row">
                <label class="col-sm-4 control-label">Base price</label>
                <div class="col-sm-8">
                    <input type="number" class="currency" min="0.01" max="99999.99" value="100.00"
                           id="basePrice" name="basePrice" required>
                    <i class="fa fa-user"></i>
                </div>
            </div>
            <div class="form-group row">
                <label class="col-sm-4 control-label">Customer type</label>
                <div class="col-sm-8">
                    <select name="customerType" class="form-control" id="customerTypeId">
                        <option value="Residential">Residential</option>
                        <option value="Business">Business</option>
                    </select></div>
            </div>
            <div class="form-group row">
                <label class="col-sm-4 control-label">Duration in days</label>
                <div class="col-sm-8">
                    <input type="number" name="durationInDays" class="form-control" placeholder="365"
                           onchange="handleChange(this)">
                    <i class="fa fa-user"></i>
                </div>
            </div>
            <div class="form-group row">
                <label class="col-sm-4 control-label">Need processing</label>
                <div class="col-sm-8">
                    <div class="row">
                        <input type="radio" name="processingStrategy" class="col-sm-1" value="NeedProcessing">
                        <label class="col-sm-5 control-label">Yes</label>
                        <input type="radio" name="processingStrategy" class="col-sm-1" value="DoNotNeedProcessing"
                               checked>
                        <label class="col-sm-5 control-label">No</label>
                    </div>
                </div>
            </div>

            <div class="form-group row">
                <label class="col-sm-4 control-label">Select status service</label>
                <div class="col-sm-8">
                    <div class="row">
                        <input type="radio" name="status" class="col-sm-1" value="Available">
                        <label class="col-sm-5 control-label">Available</label>
                        <input type="radio" name="status" class="col-sm-1" value="NotAvailable" checked>
                        <label class="col-sm-5 control-label">Not Available</label>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-4 col-xs-0"></div>
                <button type="submit" class="btn btn-primary col-sm-4 col-xs-12">Save</button>
                <div class="col-sm-4 col-xs-0"></div>
            </div>

        </div>
    </form>
</div>

<jsp:include page="../includes/footer.jsp"/>
<script type="text/javascript" src="http://www.google.com/jsapi"></script>
<script type="text/javascript">
    google.load("jquery", "1.4.4");
</script>
<script>
    function handleChange(input) {
        if (input.value < 0) input.value = 0;
        if (input.value > 365) input.value = 365;
    }
</script>
<script>
    document.getElementById('basePrice').onkeypress = function (e) {
        if (this.value.indexOf(".") != '-1' || this.value.indexOf(",") != '-1') { // позволяет ввести или одну точку, или одну запятую
            return !(/[.,А-Яа-яA-Za-z-"+"]/.test(String.fromCharCode(e.charCode)));
        }
    }
</script>
<script src="<c:url value="/resources/js/newCategoryService.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/js/price.js"/>"></script>
</body>
</html>