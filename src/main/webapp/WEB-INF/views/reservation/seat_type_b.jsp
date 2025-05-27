<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<h1 style="font-size: 2rem; font-weight: bold; cursor: pointer;" onclick="location.href='<%= request.getContextPath() %>/main'">
    PerFortival
</h1>
<hr>

<h2>좌석 선택 (좌석B: 뮤지컬/클래식 공연장)</h2>

<form action="${pageContext.request.contextPath}/payment" method="post">
    <input type="hidden" name="performanceId" value="${performance.id}" />
    <input type="hidden" name="date" value="${date}" />
    <input type="hidden" name="time" value="${time}" />
    <input type="hidden" name="selectedSeatId" id="selectedSeatInput" />
    <input type="hidden" name="seatPrice" id="seatPriceInput" />

    <!-- 1층 VIP석 -->
    <h3>1층 (VIP석)</h3>
    <div class="seat-grid-horizontal">
        <div class="row-block">
            <c:forEach var="i" begin="1" end="6">
                <c:set var="zone" value="A" />
                <c:set var="matchedSeat" value="${null}" />
                <c:forEach var="s" items="${seatList}">
                    <c:if test="${s.seatType eq 'VIP' and s.zone eq zone and s.row eq 'V' and s.col eq i}">
                        <c:set var="matchedSeat" value="${s}" />
                    </c:if>
                </c:forEach>
                <button class="seat ${empty matchedSeat ? 'reserved' : 'vip'}" type="button" value="${not empty matchedSeat ? matchedSeat.seatId : ''}" data-label="${zone}-V${i}" data-price="${not empty matchedSeat ? matchedSeat.price : 0}" <c:if test="${empty matchedSeat}">disabled</c:if>>${zone}-V${i}</button>
            </c:forEach>
        </div>
        <div class="row-block">
            <c:forEach var="i" begin="1" end="6">
                <c:set var="zone" value="B" />
                <c:set var="matchedSeat" value="${null}" />
                <c:forEach var="s" items="${seatList}">
                    <c:if test="${s.seatType eq 'VIP' and s.zone eq zone and s.row eq 'V' and s.col eq i}">
                        <c:set var="matchedSeat" value="${s}" />
                    </c:if>
                </c:forEach>
                <button class="seat ${empty matchedSeat ? 'reserved' : 'vip'}" type="button" value="${not empty matchedSeat ? matchedSeat.seatId : ''}" data-label="${zone}-V${i}" data-price="${not empty matchedSeat ? matchedSeat.price : 0}" <c:if test="${empty matchedSeat}">disabled</c:if>>${zone}-V${i}</button>
            </c:forEach>
        </div>
    </div>

    <!-- 1층 R석 -->
    <h3>1층 (R석)</h3>
    <div class="seat-grid-horizontal">
        <div class="row-block">
            <c:forEach var="i" begin="1" end="6">
                <c:set var="zone" value="I" />
                <c:set var="matchedSeat" value="${null}" />
                <c:forEach var="s" items="${seatList}">
                    <c:if test="${s.seatType eq 'R석' and s.zone eq zone and s.row eq 'R' and s.col eq i}">
                        <c:set var="matchedSeat" value="${s}" />
                    </c:if>
                </c:forEach>
                <button class="seat ${empty matchedSeat ? 'reserved' : 'rgrade'}" type="button" value="${not empty matchedSeat ? matchedSeat.seatId : ''}" data-label="${zone}-R${i}" data-price="${not empty matchedSeat ? matchedSeat.price : 0}" <c:if test="${empty matchedSeat}">disabled</c:if>>${zone}-R${i}</button>
            </c:forEach>
        </div>
        <div class="row-block">
            <c:forEach var="i" begin="1" end="6">
                <c:set var="zone" value="J" />
                <c:set var="matchedSeat" value="${null}" />
                <c:forEach var="s" items="${seatList}">
                    <c:if test="${s.seatType eq 'R석' and s.zone eq zone and s.row eq 'R' and s.col eq i}">
                        <c:set var="matchedSeat" value="${s}" />
                    </c:if>
                </c:forEach>
                <button class="seat ${empty matchedSeat ? 'reserved' : 'rgrade'}" type="button" value="${not empty matchedSeat ? matchedSeat.seatId : ''}" data-label="${zone}-R${i}" data-price="${not empty matchedSeat ? matchedSeat.price : 0}" <c:if test="${empty matchedSeat}">disabled</c:if>>${zone}-R${i}</button>
            </c:forEach>
        </div>
    </div>

    <!-- 2층 R석 -->
    <h3>2층 (R석)</h3>
    <div class="seat-grid-horizontal">
        <div class="row-block">
            <c:forEach var="i" begin="1" end="6">
                <c:set var="zone" value="K" />
                <c:set var="matchedSeat" value="${null}" />
                <c:forEach var="s" items="${seatList}">
                    <c:if test="${s.seatType eq 'R석' and s.zone eq zone and s.row eq 'R' and s.col eq i}">
                        <c:set var="matchedSeat" value="${s}" />
                    </c:if>
                </c:forEach>
                <button class="seat ${empty matchedSeat ? 'reserved' : 'rgrade'}" type="button" value="${not empty matchedSeat ? matchedSeat.seatId : ''}" data-label="${zone}-R${i}" data-price="${not empty matchedSeat ? matchedSeat.price : 0}" <c:if test="${empty matchedSeat}">disabled</c:if>>${zone}-R${i}</button>
            </c:forEach>
        </div>
    </div>

    <!-- 2층 S석 -->
    <h3>2층 (S석)</h3>
    <div class="seat-grid-horizontal">
        <div class="row-block">
            <c:forEach var="i" begin="1" end="6">
                <c:set var="zone" value="O" />
                <c:set var="matchedSeat" value="${null}" />
                <c:forEach var="s" items="${seatList}">
                    <c:if test="${s.seatType eq 'S석' and s.zone eq zone and s.row eq 'S' and s.col eq i}">
                        <c:set var="matchedSeat" value="${s}" />
                    </c:if>
                </c:forEach>
                <button class="seat ${empty matchedSeat ? 'reserved' : 'sgrade'}" type="button" value="${not empty matchedSeat ? matchedSeat.seatId : ''}" data-label="${zone}-S${i}" data-price="${not empty matchedSeat ? matchedSeat.price : 0}" <c:if test="${empty matchedSeat}">disabled</c:if>>${zone}-S${i}</button>
            </c:forEach>
        </div>
        <br><br>
        <div class="row-block">
            <c:forEach var="i" begin="1" end="6">
                <c:set var="zone" value="P" />
                <c:set var="matchedSeat" value="${null}" />
                <c:forEach var="s" items="${seatList}">
                    <c:if test="${s.seatType eq 'S석' and s.zone eq zone and s.row eq 'S' and s.col eq i}">
                        <c:set var="matchedSeat" value="${s}" />
                    </c:if>
                </c:forEach>
                <button class="seat ${empty matchedSeat ? 'reserved' : 'sgrade'}" type="button" value="${not empty matchedSeat ? matchedSeat.seatId : ''}" data-label="${zone}-S${i}" data-price="${not empty matchedSeat ? matchedSeat.price : 0}" <c:if test="${empty matchedSeat}">disabled</c:if>>${zone}-S${i}</button>
            </c:forEach>
        </div>
        <div class="row-block">
            <c:forEach var="i" begin="1" end="6">
                <c:set var="zone" value="Q" />
                <c:set var="matchedSeat" value="${null}" />
                <c:forEach var="s" items="${seatList}">
                    <c:if test="${s.seatType eq 'S석' and s.zone eq zone and s.row eq 'S' and s.col eq i}">
                        <c:set var="matchedSeat" value="${s}" />
                    </c:if>
                </c:forEach>
                <button class="seat ${empty matchedSeat ? 'reserved' : 'sgrade'}" type="button" value="${not empty matchedSeat ? matchedSeat.seatId : ''}" data-label="${zone}-S${i}" data-price="${not empty matchedSeat ? matchedSeat.price : 0}" <c:if test="${empty matchedSeat}">disabled</c:if>>${zone}-S${i}</button>
            </c:forEach>
        </div>
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
    .seat-grid-horizontal {
        display: flex;
        flex-direction: column;
        gap: 10px;
        margin-bottom: 40px;
    }

    .row-block {
        display: flex;
        gap: 10px;
    }

    .seat {
        padding: 8px 12px;
        font-weight: bold;
        font-size: 16px;
        border: 1px solid #999;
        border-radius: 6px;
        cursor: pointer;
    }

    .vip {
        background-color: #A7C7E7;
    }

    .rgrade {
        background-color: #D8B4F8;
    }

    .sgrade {
        background-color: #F9F3A9;
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

        seatButtons.forEach(btn => {
            btn.addEventListener('click', function () {
                if (btn.classList.contains('reserved')) return;
                const seatLabel = btn.getAttribute('data-label');
                const price = btn.getAttribute('data-price') || 0;
                const seatPriceInput = document.getElementById('seatPriceInput');
                selectedSeatEl.textContent = seatLabel;
                seatPriceEl.textContent = parseInt(price).toLocaleString();
                selectedSeatInput.value = btn.value;
                seatPriceInput.value = price;
            });
        });
    });
</script>