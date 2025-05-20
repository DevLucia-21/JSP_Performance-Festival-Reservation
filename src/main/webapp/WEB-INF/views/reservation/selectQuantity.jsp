<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>자유석 예매 - 수량 선택</title>
</head>
<body>

    <!-- 상단 PerFortival 로고 -->
    <h1 style="font-size: 2rem; font-weight: bold; cursor: pointer;" onclick="location.href='<%= request.getContextPath() %>/main'">
        PerFortival
    </h1>
    <hr>

    <h2>자유석 예매 - 수량 및 기간 선택</h2>

    <form method="post" action="${pageContext.request.contextPath}/reservation/step1">

        <input type="hidden" name="performanceId" value="${performanceId}" />
        <input type="hidden" name="date" value="${date}" />
        <input type="hidden" name="time" value="${time}" />

        <label>수량:</label>
        <input type="number" name="quantity" min="1" required />

        <label>일수 (며칠간 관람):</label>
        <input type="number" name="days" min="1" required />

        <br><br>
        <button type="submit">예매 계속하기</button>
    </form>

    <br>
    <button onclick="history.back()">뒤로가기</button>

</body>
</html>
