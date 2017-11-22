<%-- 
    Document   : forgot
    Created on : Nov 21, 2017, 1:28:34 PM
    Author     : 677571
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Forgot Password</title>
    </head>
    <body>
        <h1>Forgot your password</h1>
        <h2>Enter your email address and hit reset password to have a reset link sent to the email address linked to the account</h2>
        
        <form action="forgot" method="post">
            Email Address: <input type="text" name="email"></input>
            <input type="submit" name="submit" value="Reset Password">
        </form>
        <a href="login">Back<a/>
    </body>
</html>
