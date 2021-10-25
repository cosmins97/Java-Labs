<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add exam</title>
</head>
<body>
<h2><%= "Add exam" %></h2>
<br>
<form name="inputForm" method="post" action="addexam">
    Exam: <input type="text" name="examname"/> <br/>
    <br>

    Date: <input type="date" name="examdate"/> <br/>
    <br>

    Duration (h): <input type="number" name="examduration"/> <br/>
    <br>

    <input type="submit" value="Submit" />
</form>
</body>
</html>
