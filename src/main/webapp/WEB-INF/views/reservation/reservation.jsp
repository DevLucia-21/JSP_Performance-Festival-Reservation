<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>공연 예매 설정</title>
    <style>
        .section { margin-bottom: 20px; }
    </style>
</head>
<body>

		<!-- 상단 PerFortival -->
    <h1 style="font-size: 2rem; font-weight: bold; cursor: pointer;" onclick="location.href='<%= request.getContextPath() %>/main'">
        PerFortival
    </h1>
    <hr>
    
    <h2>공연 예매 설정</h2>

    <form action="<%= request.getContextPath() %>/reservation" method="post">
        <!-- 공연 선택 -->
        <!-- 공연 선택 -->
        <div class="section">
            <label for="performance">공연 선택:</label>
            <select name="performanceId" id="performance" required>
                <option value="">공연을 선택하세요</option>
                <c:forEach var="performance" items="${performances}">
                    <option value="${performance.id}">${performance.title}</option>
                </c:forEach>
            </select>
        </div>

        <!-- 예매 방식 선택 -->
        <div class="section">
            <label>예매 방식:</label><br>
            <input type="radio" name="reservationType" value="좌석A" required> 좌석A (콘서트)<br>
            <input type="radio" name="reservationType" value="좌석B"> 좌석B (뮤지컬)<br>
            <input type="radio" name="reservationType" value="혼합"> 혼합 (지정석 + 스탠딩)<br>
            <input type="radio" name="reservationType" value="자유석"> 자유석
        </div>

        <!-- 좌석A 금액 설정 (VIP, 일반석) -->
        <div class="section" id="seatASection" style="display: none;">
            <h3>좌석A - 콘서트</h3>
            <label for="vipPriceA">VIP석:</label>
            <input type="number" name="vipPriceA" min="0" step="100"><br>
            <label for="generalPriceA">일반석:</label>
            <input type="number" name="generalPriceA" min="0" step="100"><br>
        </div>

        <!-- 좌석B 금액 설정 (VIP, R석, S석) -->
        <div class="section" id="seatBSection" style="display: none;">
            <h3>좌석B - 뮤지컬</h3>
            <label for="vipPriceB">VIP석:</label>
            <input type="number" name="vipPriceB" min="0" step="100"><br>
            <label for="rPrice">R석:</label>
            <input type="number" name="rPrice" min="0" step="100"><br>
            <label for="sPrice">S석:</label>
            <input type="number" name="sPrice" min="0" step="100"><br>
        </div>

        <!-- 혼합 금액 설정 (스탠딩, 좌석) -->
        <div class="section" id="mixedSection" style="display: none;">
            <h3>혼합 - 지정석 + 스탠딩</h3>
            <label for="standingPrice">스탠딩석:</label>
            <input type="number" name="standingPrice" min="0" step="100"><br>
            <label for="seatPrice">좌석:</label>
            <input type="number" name="seatPrice" min="0" step="100"><br>
        </div>

        <!-- 자유석 수량 및 가격 설정 -->
        <div class="section" id="freeSeatSection" style="display: none;">
            <h3>자유석 설정</h3>
            <label for="freeSeatQuantity">수량:</label>
            <input type="number" name="freeSeatQuantity" min="1" max="100"><br>
            <label for="freeSeatPrice">가격:</label>
            <input type="number" name="freeSeatPrice" min="0" step="100">
        </div>

        <br>
        <input type="submit" value="설정 완료">
    </form>

    <script>
        const sections = {
            '좌석A': 'seatASection',
            '좌석B': 'seatBSection',
            '혼합': 'mixedSection',
            '자유석': 'freeSeatSection'
        };

        document.querySelectorAll('input[name="reservationType"]').forEach(radio => {
            radio.addEventListener('change', function() {
                Object.values(sections).forEach(section => {
                    document.getElementById(section).style.display = 'none';
                });
                const selectedSection = sections[this.value];
                if (selectedSection) {
                    document.getElementById(selectedSection).style.display = 'block';
                }
            });
        });
    </script>
</body>
</html>
