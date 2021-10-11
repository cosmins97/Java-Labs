<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Java Lab 1</title>
</head>
<body>
<h1><%= "Lab1" %>
</h1>
<form name="inputForm" method="post" action="lab1Servlet">
    Key: <input type="text" name="key"/> <br/>
    <br>

    Value: <input type="number" name="value"/> <br/>

    <p>Mock:</p>
    <input type="radio" id="mock_true" name="mock" value="True">
    <label for="mock_true">True</label><br>
    <input type="radio" id="mock_false" name="mock" value="False">
    <label for="mock_false">False</label><br>

    <p>Sync:</p>
    <input type="radio" id="sync_true" name="sync" value="True">
    <label for="sync_true">True</label><br>
    <input type="radio" id="sync_false" name="sync" value="False">
    <label for="sync_false">False</label><br>
    <br>

    <input type="submit" value="Submit" />
</form>

</body>
</html>