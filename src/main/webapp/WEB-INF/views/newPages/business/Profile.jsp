<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="../includes/head.jsp">
        <jsp:param name="tittle" value="Profile"/>
    </jsp:include>
</head>
<body>
    <jsp:include page="../includes/headers/businessHeader.jsp">
        <jsp:param name="pageName" value="Profile"/>
    </jsp:include>
    <jsp:include page="../includes/profile.jsp"/>
    <jsp:include page="../includes/footer.jsp"/>
    <script type="text/javascript"
            src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBhhghVMdW1rIbJCJupKdngdNk0k5JwaQE&libraries=places"></script>
</body>
</html>

