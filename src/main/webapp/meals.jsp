<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
    <table>
        <tr>
            <td>Время</td>
            <td>Описание</td>
            <td>Каллории</td>
        </tr>
        <c:forEach items="${mealList}" var="meal">
            <tr <c:choose>
                        <c:when test="${meal.isExceed()}">
                            bgcolor="red"
                        </c:when>
                        <c:otherwise>
                            bgcolor="green">
                        </c:otherwise>
                    </c:choose>>

                <td>"${meal.getDateTime()}"</td>
                <td>"${meal.getDescription()}"</td>
                <td>"${meal.getCalories()}"</td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
