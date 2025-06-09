<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h1 style="font-size: 2rem; font-weight: bold; cursor: pointer;" onclick="location.href='<%= request.getContextPath() %>/main'">
    PerFortival
</h1>
<hr>

<h2>좌석 선택 (혼합 공연장)</h2>

<form action="${pageContext.request.contextPath}/reservation/step2" method="post">
    <input type="hidden" name="performanceId" value="${performance.id}" />
    <input type="hidden" name="date" value="${date}" />
    <input type="hidden" name="time" value="${time}" />
    <input type="hidden" name="quantity" value="${quantity}" />
		<input type="hidden" name="seatIds" id="selectedSeatInput" />
		<input type="hidden" name="seatPrices" id="seatPriceInput" />
		<div id="dynamicSeatInputs"></div>

    <!-- 1층 스탠딩 -->
		<h3>1층 (스탠딩)</h3>
		<div class="seat-grid">
		    <c:forEach var="zoneIndex" begin="0" end="1">
		        <c:choose>
		            <c:when test="${zoneIndex == 0}"><c:set var="zone" value="A"/></c:when>
		            <c:when test="${zoneIndex == 1}"><c:set var="zone" value="B"/></c:when>
		        </c:choose>
		        <div class="zone-block">
		            <c:forEach var="i" begin="1" end="4">
		                <c:set var="matchedSeat" value="${null}" />
		                <c:forEach var="s" items="${seatList}">
		                    <c:if test="${s.seatType eq '스탠딩' and s.zone eq zone and s.row eq 'T' and s.col eq i}">
		                        <c:set var="matchedSeat" value="${s}" />
		                    </c:if>
		                </c:forEach>
		                <button
		                    class="seat 
		                        <c:choose>
		                            <c:when test="${empty matchedSeat or matchedSeat.reserved}">reserved</c:when>
		                            <c:otherwise>standing</c:otherwise>
		                        </c:choose>"
		                    type="button"
		                    <c:if test="${not empty matchedSeat}">value="${matchedSeat.seatId}"</c:if>
		                    data-label="${zone}-T${i}"
		                    data-price="${not empty matchedSeat ? matchedSeat.price : 0}"
		                    <c:if test="${empty matchedSeat or matchedSeat.reserved}">disabled</c:if>>
		                    ${zone}-T${i}
		                </button>
		            </c:forEach>
		        </div>
		    </c:forEach>
		</div>
		
		<div class="seat-grid">
		    <c:forEach var="zoneIndex" begin="2" end="3">
		        <c:choose>
		            <c:when test="${zoneIndex == 2}"><c:set var="zone" value="C"/></c:when>
		            <c:when test="${zoneIndex == 3}"><c:set var="zone" value="D"/></c:when>
		        </c:choose>
		        <div class="zone-block">
		            <c:forEach var="i" begin="1" end="4">
		                <c:set var="matchedSeat" value="${null}" />
		                <c:forEach var="s" items="${seatList}">
		                    <c:if test="${s.seatType eq '스탠딩' and s.zone eq zone and s.row eq 'T' and s.col eq i}">
		                        <c:set var="matchedSeat" value="${s}" />
		                    </c:if>
		                </c:forEach>
		                <button
		                    class="seat 
		                        <c:choose>
		                            <c:when test="${empty matchedSeat or matchedSeat.reserved}">reserved</c:when>
		                            <c:otherwise>standing</c:otherwise>
		                        </c:choose>"
		                    type="button"
		                    <c:if test="${not empty matchedSeat}">value="${matchedSeat.seatId}"</c:if>
		                    data-label="${zone}-T${i}"
		                    data-price="${not empty matchedSeat ? matchedSeat.price : 0}"
		                    <c:if test="${empty matchedSeat or matchedSeat.reserved}">disabled</c:if>>
		                    ${zone}-T${i}
		                </button>
		            </c:forEach>
		        </div>
		    </c:forEach>
		</div>

    <!-- 2층 좌석 -->
    <h3 style="margin-top: 30px;">2층 (좌석)</h3>
    <div class="seat-grid">
        <c:forEach var="zoneIndex" begin="0" end="1">
				    <c:choose>
				        <c:when test="${zoneIndex == 0}"><c:set var="zone" value="I"/></c:when>
				        <c:when test="${zoneIndex == 1}"><c:set var="zone" value="J"/></c:when>
				    </c:choose>
				    <div class="zone-block">
				        <c:forEach var="i" begin="1" end="6">
				            <c:set var="matchedSeat" value="${null}" />
				            <c:forEach var="s" items="${seatList}">
				                <c:if test="${s.seatType eq '좌석' and s.zone eq zone and s.row eq 'R' and s.col eq i}">
				                    <c:set var="matchedSeat" value="${s}" />
				                </c:if>
				            </c:forEach>
				            <button
				                class="seat ${empty matchedSeat || matchedSeat.reserved ? 'reserved' : 'normal'}"
				                type="button"
				                <c:if test="${not empty matchedSeat}">value="${matchedSeat.seatId}"</c:if>
				                data-label="${zone}-R${i}"
				                data-price="${not empty matchedSeat ? matchedSeat.price : 0}"
				                <c:if test="${empty matchedSeat || matchedSeat.reserved}">disabled</c:if>>
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
    .seat-grid {
        display: flex;
        gap: 20px;
        flex-wrap: wrap;
        margin-bottom: 20px;
        justify-content: flex-start;
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

    .standing {
        background-color: #FFE5D0;
    }

    .normal {
        background-color: #D3F4C3;
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
    const dynamicSeatInputs = document.getElementById('dynamicSeatInputs');

    const maxQuantity = parseInt("${quantity}");
    let selectedSeats = [];

    seatButtons.forEach(btn => {
        btn.addEventListener('click', function () {
            if (btn.classList.contains('reserved')) return;

            const seatId = btn.value;
            const seatLabel = btn.getAttribute('data-label');
            const price = btn.getAttribute('data-price') || 0;

            const existingIndex = selectedSeats.findIndex(s => s.id === seatId);
            if (existingIndex !== -1) {
                selectedSeats.splice(existingIndex, 1);
                btn.classList.remove('selected-seat');
            } else {
                if (selectedSeats.length >= maxQuantity) {
                		alert(`선택한 수량에 맞게 좌석을 선택하세요.`);
                    return;
                }
                selectedSeats.push({ id: seatId, label: seatLabel, price: price });
                btn.classList.add('selected-seat');
            }

            if (selectedSeats.length === 0) {
                selectedSeatEl.textContent = "없음";
                seatPriceEl.textContent = "0";
                selectedSeatInput.value = "";
                seatPriceInput.value = "";
            } else {
                const labels = selectedSeats.map(s => s.label).join(', ');
                const total = selectedSeats.reduce((sum, s) => sum + parseInt(s.price), 0);

                selectedSeatEl.textContent = labels;
                seatPriceEl.textContent = total.toLocaleString();
                selectedSeatInput.value = selectedSeats.map(s => s.id).join(',');
                seatPriceInput.value = selectedSeats.map(s => s.price).join(',');

                // 동적 input 추가
                dynamicSeatInputs.innerHTML = "";
                selectedSeats.forEach(s => {
                    const input = document.createElement("input");
                    input.type = "hidden";
                    input.name = "seatId";
                    input.value = s.id;
                    dynamicSeatInputs.appendChild(input);
                });
            }
        });
    });
});
</script>

