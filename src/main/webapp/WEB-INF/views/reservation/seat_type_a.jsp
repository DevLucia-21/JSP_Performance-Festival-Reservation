<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h1 style="font-size: 2rem; font-weight: bold; cursor: pointer;" onclick="location.href='<%= request.getContextPath() %>/main'">
    PerFortival
</h1>
<hr>

<h2>좌석 선택 (좌석A: 콘서트장)</h2>

<form action="${pageContext.request.contextPath}/reservation/step2" method="post">
    <input type="hidden" name="performanceId" value="${performance.id}" />
    <input type="hidden" name="date" value="${date}" />
    <input type="hidden" name="time" value="${time}" />
    <input type="hidden" name="seatId" id="selectedSeatInput" />
    <input type="hidden" name="seatPrice" id="seatPriceInput" />

    <!-- 1층 VIP석 -->
    <h3>1층 (VIP석)</h3>

    <!-- A, B 구역 -->
    <div class="seat-grid-center">
        <c:forEach var="zoneIndex" begin="0" end="1">
            <c:choose>
                <c:when test="${zoneIndex == 0}"><c:set var="zone" value="A"/></c:when>
                <c:when test="${zoneIndex == 1}"><c:set var="zone" value="B"/></c:when>
            </c:choose>
            <div class="zone-block">
                <c:forEach var="i" begin="1" end="4">
                    <c:set var="matchedSeat" value="${null}" />
                    <c:forEach var="s" items="${seatList}">
                        <c:if test="${s.seatType eq 'VIP' and s.zone eq zone and s.row eq 'V' and s.col eq i}">
                            <c:set var="matchedSeat" value="${s}" />
                        </c:if>
                    </c:forEach>
                    <button
                        class="seat 
                            <c:choose>
                                <c:when test="${empty matchedSeat or matchedSeat.reserved}">reserved</c:when>
                                <c:otherwise>vip</c:otherwise>
                            </c:choose>"
                        type="button"
                        <c:if test="${not empty matchedSeat}">value="${matchedSeat.seatId}"</c:if>
                        data-label="${zone}-V${i}"
                        data-price="${not empty matchedSeat ? matchedSeat.price : 0}"
                        <c:if test="${empty matchedSeat or matchedSeat.reserved}">disabled</c:if>>
                        ${zone}-V${i}
                    </button>
                </c:forEach>
            </div>
        </c:forEach>
    </div>

    <!-- C, D 구역 -->
    <div class="seat-grid-center">
        <c:forEach var="zoneIndex" begin="2" end="3">
            <c:choose>
                <c:when test="${zoneIndex == 2}"><c:set var="zone" value="C"/></c:when>
                <c:when test="${zoneIndex == 3}"><c:set var="zone" value="D"/></c:when>
            </c:choose>
            <div class="zone-block">
                <c:forEach var="i" begin="1" end="4">
                    <c:set var="matchedSeat" value="${null}" />
                    <c:forEach var="s" items="${seatList}">
                        <c:if test="${s.seatType eq 'VIP' and s.zone eq zone and s.row eq 'V' and s.col eq i}">
                            <c:set var="matchedSeat" value="${s}" />
                        </c:if>
                    </c:forEach>
                    <button
                        class="seat 
                            <c:choose>
                                <c:when test="${empty matchedSeat or matchedSeat.reserved}">reserved</c:when>
                                <c:otherwise>vip</c:otherwise>
                            </c:choose>"
                        type="button"
                        <c:if test="${not empty matchedSeat}">value="${matchedSeat.seatId}"</c:if>
                        data-label="${zone}-V${i}"
                        data-price="${not empty matchedSeat ? matchedSeat.price : 0}"
                        <c:if test="${empty matchedSeat or matchedSeat.reserved}">disabled</c:if>>
                        ${zone}-V${i}
                    </button>
                </c:forEach>
            </div>
        </c:forEach>
    </div>

    <!-- 2층 일반석 -->
    <h3>2층 (일반석)</h3>
    <div class="seat-grid-start">
        <c:forEach var="zoneIndex" begin="0" end="4">
            <c:choose>
                <c:when test="${zoneIndex == 0}"><c:set var="zone" value="I"/></c:when>
                <c:when test="${zoneIndex == 1}"><c:set var="zone" value="J"/></c:when>
                <c:when test="${zoneIndex == 2}"><c:set var="zone" value="K"/></c:when>
                <c:when test="${zoneIndex == 3}"><c:set var="zone" value="L"/></c:when>
                <c:when test="${zoneIndex == 4}"><c:set var="zone" value="M"/></c:when>
            </c:choose>
            <div class="zone-block">
                <c:forEach var="i" begin="1" end="4">
                    <c:set var="matchedSeat" value="${null}" />
                    <c:forEach var="s" items="${seatList}">
                        <c:if test="${s.seatType eq '일반석' and s.zone eq zone and s.row eq 'R' and s.col eq i}">
                            <c:set var="matchedSeat" value="${s}" />
                        </c:if>
                    </c:forEach>
                    <button
                        class="seat 
                            <c:choose>
                                <c:when test="${empty matchedSeat or matchedSeat.reserved}">reserved</c:when>
                                <c:otherwise>normal</c:otherwise>
                            </c:choose>"
                        type="button"
                        <c:if test="${not empty matchedSeat}">value="${matchedSeat.seatId}"</c:if>
                        data-label="${zone}-R${i}"
                        data-price="${not empty matchedSeat ? matchedSeat.price : 0}"
                        <c:if test="${empty matchedSeat or matchedSeat.reserved}">disabled</c:if>>
                        ${zone}-R${i}
                    </button>
                </c:forEach>
            </div>
        </c:forEach>
    </div>

    <!-- 선택 정보 -->
    <div style="margin-top: 1rem;">
        <p>선택한 좌석: <span id="selectedSeat">없음</span></p>
        <p>가격: <span id="seatPrice">0</span>원</p>
    </div>

    <div style="margin-top: 1rem;">
        <button type="submit">선택 완료</button>
    </div>
</form>

<style>
    .seat-grid-start {
        display: flex;
        gap: 20px;
        flex-wrap: wrap;
        margin-bottom: 40px;
        justify-content: flex-start;
    }

    .seat-grid-center {
        display: flex;
        gap: 20px;
        flex-wrap: wrap;
        margin-bottom: 20px;
        margin-left: 230px;
    }

    .zone-block {
        display: grid;
        grid-template-columns: repeat(2, auto);
        gap: 6px;
        background-color: #f5f5f5;
        padding: 12px;
        border-radius: 10px;
    }

    .seat {
        padding: 8px 12px;
        font-weight: bold;
        border: 1px solid #999;
        border-radius: 6px;
        cursor: pointer;
    }

    .vip {
        background-color: #A7C7E7;
    }

    .normal {
        background-color: #D3F4C3;
    }

    .reserved {
        background-color: #ccc;
        cursor: not-allowed;
    }
</style>

<script>
    document.addEventListener('DOMContentLoaded', () => {
        const seatButtons = document.querySelectorAll('.seat');
        const selectedSeatEl = document.getElementById('selectedSeat');
        const seatPriceEl = document.getElementById('seatPrice');
        const selectedSeatInput = document.getElementById('selectedSeatInput');
        const seatPriceInput = document.getElementById('seatPriceInput');

        seatButtons.forEach(btn => {
            btn.addEventListener('click', function () {
                if (btn.classList.contains('reserved')) return;
                const seatLabel = btn.getAttribute('data-label');
                const price = btn.getAttribute('data-price') || 0;
                selectedSeatEl.textContent = seatLabel;
                seatPriceEl.textContent = parseInt(price).toLocaleString();
                selectedSeatInput.value = btn.value;
                seatPriceInput.value = price;
            });
        });
    });
</script>
