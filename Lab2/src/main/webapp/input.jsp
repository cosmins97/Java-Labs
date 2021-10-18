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
                <c:choose>
                    <c:when test="${category eq selectedCategory}">
                        <option value=${category} selected> ${category} </option>
                    </c:when>
                    <c:otherwise>
                        <option value=${category}> ${category} </option>
                    </c:otherwise>
                </c:choose>

            </c:forEach>
        </select> <br/>
        <br>
        Key: <input type="text" name="key"/> <br/>
        <br>
        Value: <input type="text" name="value"/> <br/>
        <br>
        Captcha: ${captchaFirstNumber} + ${captchaSecondNumber} =
        <input type="number" name="captchaAnswer"/> <br/>
        <label>${captchaMessage}</label>
        <input type="submit" value="Submit" />
    </form>
</body>
</html>
