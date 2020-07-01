<%--
  Created by IntelliJ IDEA.
  User: wangguijie
  Date: 6/28/20
  Time: 00:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

    <c:if test="${!empty list}">
        <c:forEach items="${list}" var="admin">
            ${admin}<br/>
        </c:forEach>
    </c:if>

</body>
</html>
