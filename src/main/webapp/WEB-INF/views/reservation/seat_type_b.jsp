<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h3>좌석 선택 (좌석B: 뮤지컬/클래식 공연장)</h3>

<!-- 1층 -->
<h4>1층 (VIP석 + R석)</h4>
<div class="seat-zone">
    <c:forEach var="seat" items="${seatList}">
        <c:if test="${seat.floor == 1}">
            <button 
                class="seat
                    <c:choose>
                        <c:when test="${seat.isReserved}">reserved</c:when>
                        <c:when test="${seat.seatType eq 'VIP'}">vip</c:when>
                        <c:otherwise>rgrade</c:otherwise>
                    </c:choose>"
                name="selectedSeatId"
                value="${seat.seatId}"
                data-price="${seat.price}"
                <c:if test="${seat.isReserved}">disabled</c:if>>
                ${seat.zone}-${seat.row}${seat.col}
            </button>
        </c:if>
    </c:forEach>
</div>

<!-- 2층 -->
<h4>2층 (R석 + S석)</h4>
<div class="seat-zone">
    <c:forEach var="seat" items="${seatList}">
        <c:if test="${seat.floor == 2}">
            <button 
                class="seat
                    <c:choose>
                        <c:when test="${seat.isReserved}">reserved</c:when>
                        <c:when test="${seat.seatType eq 'R석'}">rgrade</c:when>
                        <c:otherwise>sgrade</c:otherwise>
                    </c:choose>"
                name="selectedSeatId"
                value="${seat.seatId}"
                data-price="${seat.price}"
                <c:if test="${seat.isReserved}">disabled</c:if>>
                ${seat.zone}-${seat.row}${seat.col}
            </button>
        </c:if>
    </c:forEach>
</div>

<!-- 선택 정보 표시 -->
<div style="margin-top: 1rem;">
    <p>선택한 좌석: <span id="selectedSeat">없음</span></p>
    <p>가격: <span id="seatPrice">0</span>원</p>
</div>

<!-- 스타일 -->
<style>
    .seat-zone {
        display: flex;
        flex-wrap: wrap;
        gap: 5px;
        margin-bottom: 20px;
    }
    .seat {
        padding: 5px 10px;
        border-radius: 4px;
        cursor: pointer;
        border: none;
    }
    .vip { background-color: blue; color: white; }
    .rgrade { background-color: purple; color: white; }
    .sgrade { background-color: green; color: white; }
    .reserved { background-color: gray; color: white; cursor: not-allowed; }
</style>

<!-- 선택 좌석 표시 JS -->
<script>
    const seatButtons = document.querySelectorAll('.seat');
    const selectedSeatEl = document.getElementById('selectedSeat');
    const seatPriceEl = document.getElementById('seatPrice');

    seatButtons.forEach(btn => {
        btn.addEventListener('click', function (e) {
            e.preventDefault();
            if (btn.classList.contains('reserved')) return;

            const seatText = btn.textContent;
            const price = btn.getAttribute('data-price') || 0;

            selectedSeatEl.textContent = seatText;
            seatPriceEl.textContent = parseInt(price).toLocaleString();
        });
    });
</script>
