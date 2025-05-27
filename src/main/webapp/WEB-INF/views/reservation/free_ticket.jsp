<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 

<!-- 상단 PerFortival -->
<h1 style="font-size: 2rem; font-weight: bold; cursor: pointer;" onclick="location.href='<%= request.getContextPath() %>/main'">
    PerFortival
</h1>
<hr>

<h2>자유석 예매</h2>

<form action="${pageContext.request.contextPath}/payment" method="post">
    <table>
        <tr>
            <td><label for="quantity">예매 수량:</label></td>
            <td>
                <select name="quantity" id="quantity" required>
                    <c:forEach var="i" begin="1" end="10">
                        <option value="${i}">${i}장</option>
                    </c:forEach>
                </select>
            </td>
        </tr>

        <tr>
            <td><label for="days">이용 일수:</label></td>
            <td>
                <select name="days" id="days" required>
                    <c:forEach var="i" begin="1" end="${maxDays}">
                        <c:choose>
                            <c:when test="${i == 1}">
                                <option value="1">1일권 - <fmt:formatNumber value="${performance.basePrice}" type="number" />원</option>
                            </c:when>
                            <c:when test="${i == 2}">
                                <option value="2">2일권 - <fmt:formatNumber value="${performance.basePrice * 2 * 0.9}" type="number" />원 (10% 할인)</option>
                            </c:when>
                            <c:when test="${i == 3}">
                                <option value="3">3일권 - <fmt:formatNumber value="${performance.basePrice * 3 * 0.85}" type="number" />원 (15% 할인)</option>
                            </c:when>
                        </c:choose>
                    </c:forEach>
                </select>
            </td>
        </tr>
    </table>

    <!-- 선택 정보 요약 -->
    <div style="margin-top: 1rem;">
        <p>선택 수량: <span id="selectedQty">1</span>장</p>
        <p>선택 일수: <span id="selectedDays">1일권</span></p>
    </div>

    <!-- 🔽 공연 ID 같이 넘겨야 함 -->
    <input type="hidden" name="performanceId" value="${performance.id}" />
    <input type="hidden" name="basePrice" value="${performance.basePrice}" />

    <br><br>
    <div style="margin-top: 1rem;">
        <button type="submit">선택 완료</button>
    </div>
</form>

<!-- 가격 표시용 JS -->
<script>
    const qtyEl = document.getElementById("quantity");
    const dayEl = document.getElementById("days");
    const qSpan = document.getElementById("selectedQty");
    const dSpan = document.getElementById("selectedDays");

    qtyEl.addEventListener("change", () => {
        qSpan.textContent = qtyEl.value;
    });
    dayEl.addEventListener("change", () => {
        const selected = dayEl.options[dayEl.selectedIndex].text;
        dSpan.textContent = selected.split(" -")[0];
    });
</script>
