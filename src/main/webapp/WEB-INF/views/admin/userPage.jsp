<%--
  Created by IntelliJ IDEA.
  User: Petro
  Date: 29.04.2017
  Time: 16:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>User's details</title>
    <link href="${contextPath}/resources/css/basic.css" rel="stylesheet"/>
</head>
<body>
<form id="details-form" action="${contextPath}/admin/save" method="post">
    <div class="login-form">
        <div class="form-group">
            <h6>Enter new name</h6>
            <div class="col-sm-10">
                <input type="text" class="form-control" name="name" id="name" value=${name}><br>
            </div>
        </div>

        <div class="form-group">
            <h6>Select duration in days</h6>
            <select name="durationInDays" class="form-control" id="durationInDays">
                <option value="365">365</option>
            </select>
        </div>
        <div class="form-group">
            <h6>Need processing by admin</h6>
            <select name="needProcessing" class="form-control" id="needProcessing">
                <option value="1">Need processing</option>
                <option value="0">Do not need processing</option>
            </select>
        </div>
        <div class="form-group">
            <div class="col-sm-10">
                <input type="text" class="form-control" name="description" id="description" value=${description}><br>
            </div>
        </div>
        <div class="form-group">
            <h6>Select status service</h6>
            <select name="status" class="form-control" id="Status">
                <option value="1">Activate</option>
                <option value="0">Not active</option>
            </select>
        </div>
        <button type="submit" class="log-btn">Save</button>
    </div>
</form>
</body>
</html>
