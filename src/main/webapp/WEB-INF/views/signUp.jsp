<%--
  Created by IntelliJ IDEA.
  User: Petro
  Date: 17.04.2017
  Time: 18:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sign up</title>
</head>
<body>
<div align="center">
    <form action="signUpServlet" method="post">
        <table border="0" width="35%" align="center">
            <tr>
                <td> First Name</td>
                <td><input type="text" name="firstName" value="First"></td>
            </tr>
            <tr>
                <td> Last Name</td>
                <td><input type="text" name="lastName" value="Last"></td>
            </tr>
            <tr>
                <td> Email</td>
                <td><input type="text" name="email" value="Email"></td>
            </tr>
            <tr>
                <td> Password</td>
                <td><input type="text" name="password" value="Password"></td>
            </tr>
            <br>
        </table>
        <input type="submit" value="Sign up!">
    </form>
</div>
</body>
</html>
