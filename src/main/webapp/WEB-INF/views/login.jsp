<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
</head>

<body bgcolor="#f0ffff">
<div align="center">
    <form method="POST" action="<%=request.getContextPath()%>/doLogin">

        <table cellpadding="5" border="3" bgcolor="#f0e68c">
            <tr>
                <td><label> Username </label></td>
                <td><input type='text' name='username' value=''></td>
            </tr>
            <tr>
                <td><label> Password </label></td>
                <td><input type='password' name='password'/></td>
            </tr>
        </table>

        <c:if test="${not empty error}">
            ${error}
        </c:if>

        <%-- <input name="_spring_security_remember_me"--%>
        <%--type="checkbox"/> <label for="remember_me">Remember me</label>--%>
        <br>
        <input type="submit" value="Login">

    </form>
</div>
</body>
</html>