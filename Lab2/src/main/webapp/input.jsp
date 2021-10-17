<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Lab2 - Input</title>
</head>
<body>
    <h2>Input</h2>
    <form name="inputForm" method="post" action="input">
        Category:
        <select name="category">
            <option hidden disabled selected value> -- select an option -- </option>
            <c:forEach items="${categories}" var="category">
                <option value=${category}>${category}</option>
            </c:forEach>
        </select> <br/>
        <br>
        Key: <input type="text" name="key"/> <br/>
        <br>
        Value: <input type="text" name="value"/> <br/>
        <br>
        <input type="submit" value="Submit" />
    </form>
</body>
</html>
