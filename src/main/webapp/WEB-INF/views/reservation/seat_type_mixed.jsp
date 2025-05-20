<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h3>좌석 선택 (혼합형: 스탠딩 + 지정석)</h3>

<!-- 1층 (스탠딩) -->
<h4>1층 (스탠딩 구역)</h4>
<div class="seat-zone">
    <c:forEach var="seat" items="${seatList}">
        <c:if test="${seat.floor == 1 && seat.seatType eq '스탠딩'}">
            <button 
                class="seat <c:choose>
                                <c:when test='${seat.isReserved}'>reserved</c:when>
                                <c:otherwise>standing</c:otherwise>
                             </c:choose>"
                name="selectedSeatId"
                value="${seat.seatId}"
                data-price="${seat.price}"
                <c:if test="${seat.isReserved}">disabled</c:if>>
                ${seat.zone}구역
            </button>
        </c:if>
    </c:forEach>
</div>

<!-- 2층 (지정석) -->
<h4>2층 (지정석)</h4>
<div class="seat-zone">
    <c:forEach var="seat" items="${seatList}">
        <c:if test="${seat.floor == 2 && seat.seatType eq '일반석'}">
            <button 
                class="seat <c:choose>
                                <c:when test='${seat.isReserved}'>reserved</c:when>
                                <c:otherwise>normal</c:otherwise>
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
    .standing { background-color: red; color: white; }
    .normal { background-color: blue; color: white; }
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
