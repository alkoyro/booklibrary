<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  User: Alexey.Koyro
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Test param</title>
</head>
<body>
<form action="doWork" method="get">
    <input type="text" name="login"/>
    <input type="text" name="pass">
    <input type="submit" value="Submit">
</form>
</body>
</html>