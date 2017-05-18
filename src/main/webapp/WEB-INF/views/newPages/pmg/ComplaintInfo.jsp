<%--
  Created by IntelliJ IDEA.
  User: Aleksandr Revniuk
  Date: 13.05.17
  Time: 22:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <jsp:include page="../includes/head.jsp">
        <jsp:param name="tittle" value="All complaints"/>
    </jsp:include>
    <link href="<c:url value="/resources/css/sweet-alert.css"/>" rel="stylesheet"/>
</head>
<body>
<jsp:include page="../includes/headers/pmgHeader.jsp">
    <jsp:param name="pageName" value="Complaints"/>
</jsp:include>
<div class="container" style="margin-bottom: 30px; width:60%; max-width: 300px;">
    <h1 style="text-align: center">Complaint info</h1>
    <div class="row">
        <label class="col-sm-5 control-label" for="date">Creating date</label>
        <div class="col-sm-7" id="date"><p class="text-info"><fmt:formatDate value="${complaint.creatingDate.time}"
                                                                             type='date'
                                                                             pattern="dd MMMM yyyy"/></p></div>
    </div>
    <div class="row">
        <label class="col-sm-5 control-label" for="description">Description</label>
        <div class="col-sm-7" id="description"><p class="text-info">${complaint.description}</p></div>
    </div>
    <div class="row">
        <label class="col-sm-5 control-label" for="status">Status</label>
        <div class="col-sm-7" id="status"><p class="text-info">${complaint.status.getComplaintStatus()}</p></div>
    </div>
    <div class="row">
        <label class="col-sm-5 control-label" for="product-name">Product name</label>
        <div class="col-sm-7" id="product-name"><p class="text-info">${complaint.productName}</p></div>
    </div>
    <div class="row">
        <label class="col-sm-5 control-label" for="user-name">User name</label>
        <div class="col-sm-7" id="user-name"><p class="text-info">${complaint.userName}</p></div>
    </div>
    <div class="row">
        <label class="col-sm-5 control-label" for="user-surname">User surname</label>
        <div class="col-sm-7" id="user-surname"><p class="text-info">${complaint.userSurname}</p>
        </div>
    </div>
    <div class="row">
        <label class="col-sm-5 control-label" for="user-phone">User phone</label>
        <div class="col-sm-7" id="user-phone"><p class="text-info">${complaint.userPhone}</p></div>
    </div>

    <c:if test="${complaint.pmgId != currentUserId}">
        <div class="row">
            <div class="col-sm-3"></div>
            <a href="${pageContext.request.contextPath}/pmg/assignTo?id=${complaint.id}"
               class="col-sm-6 btn btn btn-primary">Assign to me</a>
            <div class="col-sm-3"></div>
        </div>
    </c:if>
    <c:if test="${not empty msg}">
        <span style="text-align: center" id="errorMessage" hidden disabled="true">${msg}</span>
    </c:if>
    <c:if test="${empty msg && complaint.pmgId == currentUserId}">
        <div class="row">
            <div class="col-sm-3"></div>
            <c:choose>
                <c:when test="${complaint.status == 'Send'}">
                    <a href="${pageContext.request.contextPath}/pmg/changeStatus?statusId=2&id=${complaint.id}"
                       class="col-sm-6 btn btn btn-primary">Process</a>
                </c:when>
                <c:when test="${complaint.status == 'InProcessing'}">
                    <a href="${pageContext.request.contextPath}/pmg/changeStatus?statusId=3&id=${complaint.id}"
                       class="col-sm-6 btn btn btn-success">Done</a>
                </c:when>
                <c:when test="${complaint.status == 'Processed'}">
                    <p>This complaint is processed!</p>
                </c:when>
            </c:choose>
            <div class="col-sm-3"></div>
        </div>
    </c:if>

</div>
<jsp:include page="../includes/footer.jsp"/>
<script type="text/javascript" src="<c:url value="/resources/js/sweet-alert.min.js"/>"></script>
<script>
    window.onload = function () {
        if (document.getElementById('errorMessage').innerHTML.trim() != '') {
            swal("Oops...", document.getElementById('errorMessage').innerHTML, "error");
        }
    };
</script>
</body>
</html>
