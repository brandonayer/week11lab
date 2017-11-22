<%-- 
    Document   : reset
    Created on : Nov 21, 2017, 1:54:09 PM
    Author     : 677571
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Reset Password</title>
    </head>
    <body>
        <h1>Enter a new password</h1>
        <form action="reset" method="post">
            New Password: <input type="password" name="newPassword"></input>
            <input type="hidden" name="uuid" value="${uuid}">
            <input type="submit" name="submit" value="reset password">
        </form>
        <a href="login"> back </a>
    </body>
</html>
