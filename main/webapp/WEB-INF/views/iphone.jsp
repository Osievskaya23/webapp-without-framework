<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: valentina
  Date: 23.02.19
  Time: 20:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>iPhone</title>
</head>
<body>
    <h1>iPhone product</h1>
    <p>Description: <c:out value="${product.description}"/></p>
    <p>Price: <c:out value="${product.price}"/></p>
</body>
</html>
