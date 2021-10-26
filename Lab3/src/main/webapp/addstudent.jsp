<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Add student</title>
</head>
<body>
<h2><%= "Add student" %></h2>
<br>
<form name="inputForm" method="post" action="addstudent">
  Name: <input type="text" name="studentName"/> <br/>
  <br>
  Courses:
  <br>
  <c:forEach items="${exams}" var="exam">
    <input type="checkbox" name="exams" value=${exam.getId()}> ${exam.getName()}<BR>
  </c:forEach>
  <br>
  <br>
  <input type="submit" value="Submit" />
</form>
</body>
</html>
