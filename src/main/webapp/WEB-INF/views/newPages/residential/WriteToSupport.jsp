<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="../includes/head.jsp">
        <jsp:param name="tittle" value="Write to support"/>
    </jsp:include>
</head>
<body>
    <jsp:include page="../includes/headers/residentialHeader.jsp">
        <jsp:param name="pageName" value="WriteToSupport"/>
    </jsp:include>
    <jsp:include page="../includes/writeToSupport.jsp"/>
    <jsp:include page="../includes/footer.jsp"/>
    <script type="text/javascript" src="<c:url value="/resources/js/complaintService.js"/>"></script>
</body>
</html>

