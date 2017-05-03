<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: Nikita
  Date: 03.05.2017
  Time: 14:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Profile</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/meyer-reset/2.0/reset.min.css">
    <link href="../../../resources/css/basic.css" rel="stylesheet"/>
    <script>
        function setEditable() {
            var size = document.getElementById('forma').elements.length;
            for (var i = 0; i < size; i++) {
                document.getElementById('forma').elements[i].removeAttribute("disabled");
            }
            document.getElementById('submitButton').removeAttribute("hidden");
            document.getElementById('editButton').setAttribute("hidden", "true");
        }
    </script>
</head>
<body>
<div class="login-form">
    <h1>Profile</h1>
    <div class="form-group "><br>
        <form:form method="post" modelAttribute="user" action="${pageContext.request.contextPath}/user/profile"
                   id="forma">
            <form:input path="id" type="hidden"/><br>
            <form:label path="name">Name</form:label>
            <form:input path="name" disabled="true"/><br>
            <form:label path="surname">Surname</form:label>
            <form:input path="surname" disabled="true"/><br>
            <form:label path="phone">Phone</form:label>
            <form:input path="phone" disabled="true"/><br>
            <form:label path="email">Email</form:label>
            <form:input path="email" disabled="true"/><br>
            <form:label path="address">Address</form:label>
            <form:input path="address" disabled="true"/><br>
            <input type="button" onclick="setEditable()" value="Edit" id="editButton"><br>
            <input type="submit" value="Save" hidden id="submitButton">
        </form:form>
    </div>
</div>
</body>
</html>
