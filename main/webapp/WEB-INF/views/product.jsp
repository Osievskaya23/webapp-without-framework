<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: valentina
  Date: 24.02.19
  Time: 9:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Product</title>
</head>
<body>
    <h1><c:out value="${product.productName}"/></h1>
    <p>Description: <c:out value="${product.description}"/></p>
    <p>Price: <c:out value="${product.price}"/></p>
</body>
</html>
