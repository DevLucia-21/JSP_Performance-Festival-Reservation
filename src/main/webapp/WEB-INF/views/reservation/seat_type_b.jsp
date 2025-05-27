<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<h1 style="font-size: 2rem; font-weight: bold; cursor: pointer;" onclick="location.href='<%= request.getContextPath() %>/main'">
    PerFortival
</h1>
<hr>

<h2>좌석 선택 (좌석B: 뮤지컬/클래식 공연장)</h2>

<form action="${pageContext.request.contextPath}/reservation/step2" method="post">
    <input type="hidden" name="performanceId" value="${performance.id}" />
    <input type="hidden" name="date" value="${date}" />
    <input type="hidden" name="time" value="${time}" />
    <input type="hidden" name="seatId" id="selectedSeatInput" />
    <input type="hidden" name="seatPrice" id="seatPriceInput" />

    <!-- 1층 VIP석 -->
    <h3>1층 (VIP석)</h3>
    <div class="seat-grid-horizontal">
        <div class="row-block">
            <c:set var="zone" value="A" />
            <c:forEach var="i" begin="1" end="6">
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
        <div class="row-block">
            <c:set var="zone" value="B" />
            <c:forEach var="i" begin="1" end="6">
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
    </div>

    <!-- 1층 R석 -->
    <h3>1층 (R석)</h3>
    <div class="seat-grid-horizontal">
        <div class="row-block">
            <c:set var="zone" value="I" />
            <c:forEach var="i" begin="1" end="6">
                <c:set var="matchedSeat" value="${null}" />
                <c:forEach var="s" items="${seatList}">
                    <c:if test="${s.seatType eq 'R석' and s.zone eq zone and s.row eq 'R' and s.col eq i}">
                        <c:set var="matchedSeat" value="${s}" />
                    </c:if>
                </c:forEach>
                <button
                    class="seat 
                        <c:choose>
                            <c:when test="${empty matchedSeat or matchedSeat.reserved}">reserved</c:when>
                            <c:otherwise>rgrade</c:otherwise>
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
        <div class="row-block">
            <c:set var="zone" value="J" />
            <c:forEach var="i" begin="1" end="6">
                <c:set var="matchedSeat" value="${null}" />
                <c:forEach var="s" items="${seatList}">
                    <c:if test="${s.seatType eq 'R석' and s.zone eq zone and s.row eq 'R' and s.col eq i}">
                        <c:set var="matchedSeat" value="${s}" />
                    </c:if>
                </c:forEach>
                <button
                    class="seat 
                        <c:choose>
                            <c:when test="${empty matchedSeat or matchedSeat.reserved}">reserved</c:when>
                            <c:otherwise>rgrade</c:otherwise>
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
    </div>

    <!-- 2층 R석 -->
    <h3>2층 (R석)</h3>
    <div class="seat-grid-horizontal">
        <div class="row-block">
            <c:set var="zone" value="K" />
            <c:forEach var="i" begin="1" end="6">
                <c:set var="matchedSeat" value="${null}" />
                <c:forEach var="s" items="${seatList}">
                    <c:if test="${s.seatType eq 'R석' and s.zone eq zone and s.row eq 'R' and s.col eq i}">
                        <c:set var="matchedSeat" value="${s}" />
                    </c:if>
                </c:forEach>
                <button
                    class="seat 
                        <c:choose>
                            <c:when test="${empty matchedSeat or matchedSeat.reserved}">reserved</c:when>
                            <c:otherwise>rgrade</c:otherwise>
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
    </div>

    <!-- 2층 S석 -->
    <h3>2층 (S석)</h3>
    <div class="seat-grid-horizontal">
        <div class="row-block">
            <c:set var="zone" value="O" />
            <c:forEach var="i" begin="1" end="6">
                <c:set var="matchedSeat" value="${null}" />
                <c:forEach var="s" items="${seatList}">
                    <c:if test="${s.seatType eq 'S석' and s.zone eq zone and s.row eq 'S' and s.col eq i}">
                        <c:set var="matchedSeat" value="${s}" />
                    </c:if>
                </c:forEach>
                <button
                    class="seat ${empty matchedSeat or matchedSeat.reserved ? 'reserved' : 'sgrade'}"
                    type="button"
                    <c:if test="${not empty matchedSeat}">value="${matchedSeat.seatId}"</c:if>
                    data-label="${zone}-S${i}"
                    data-price="${not empty matchedSeat ? matchedSeat.price : 0}"
                    <c:if test="${empty matchedSeat or matchedSeat.reserved}">disabled</c:if>>
                    ${zone}-S${i}
                </button>
            </c:forEach>
        </div>
        <br>
        <div class="row-block">
            <c:set var="zone" value="P" />
            <c:forEach var="i" begin="1" end="6">
                <c:set var="matchedSeat" value="${null}" />
                <c:forEach var="s" items="${seatList}">
                    <c:if test="${s.seatType eq 'S석' and s.zone eq zone and s.row eq 'S' and s.col eq i}">
                        <c:set var="matchedSeat" value="${s}" />
                    </c:if>
                </c:forEach>
                <button
                    class="seat ${empty matchedSeat or matchedSeat.reserved ? 'reserved' : 'sgrade'}"
                    type="button"
                    <c:if test="${not empty matchedSeat}">value="${matchedSeat.seatId}"</c:if>
                    data-label="${zone}-S${i}"
                    data-price="${not empty matchedSeat ? matchedSeat.price : 0}"
                    <c:if test="${empty matchedSeat or matchedSeat.reserved}">disabled</c:if>>
                    ${zone}-S${i}
                </button>
            </c:forEach>
        </div>
        <div class="row-block">
            <c:set var="zone" value="Q" />
            <c:forEach var="i" begin="1" end="6">
                <c:set var="matchedSeat" value="${null}" />
                <c:forEach var="s" items="${seatList}">
                    <c:if test="${s.seatType eq 'S석' and s.zone eq zone and s.row eq 'S' and s.col eq i}">
                        <c:set var="matchedSeat" value="${s}" />
                    </c:if>
                </c:forEach>
                <button
                    class="seat ${empty matchedSeat or matchedSeat.reserved ? 'reserved' : 'sgrade'}"
                    type="button"
                    <c:if test="${not empty matchedSeat}">value="${matchedSeat.seatId}"</c:if>
                    data-label="${zone}-S${i}"
                    data-price="${not empty matchedSeat ? matchedSeat.price : 0}"
                    <c:if test="${empty matchedSeat or matchedSeat.reserved}">disabled</c:if>>
                    ${zone}-S${i}
                </button>
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
		    width: 64px; /* 고정 크기 */
		    height: 40px;
		    padding: 8px;
		    font-weight: bold;
		    font-size: 16px;
		    border: 1px solid #999;
		    border-radius: 6px;
		    cursor: pointer;
		    text-align: center;
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
    
    .selected-seat {
		    background-color: #e6d3b3 !important;
		    border: 3px solid #a67c52 !important;
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
		
		            // 기존 선택 해제
		            seatButtons.forEach(b => b.classList.remove('selected-seat'));
		
		            // 선택된 좌석 강조
		            btn.classList.add('selected-seat');
		
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