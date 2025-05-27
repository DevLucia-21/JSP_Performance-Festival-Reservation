<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<h1 style="font-size: 2rem; font-weight: bold; cursor: pointer;" onclick="location.href='<%= request.getContextPath() %>/main'">
    PerFortival
</h1>
<hr>

<h3>결제 정보 확인</h3>

<p>공연명: ${performance.title}</p>
<p>공연 날짜: ${date}</p>
<p>공연 시간: ${time}</p>
<p>좌석 번호:
    <c:choose>
        <c:when test="${empty seatLabel}">자유석</c:when>
        <c:otherwise>${seatLabel}</c:otherwise>
    </c:choose>
</p>
<p>가격: <fmt:formatNumber value="${price}" pattern="#,###" /> 원</p>

<hr>

<h3>결제 정보 입력 (가상)</h3>

<form action="${pageContext.request.contextPath}/reservation/complete" method="post">
    <div style="margin-bottom: 10px;">
        <label>카드번호:</label>
        <input type="password" name="cardNumber" id="cardNumber" maxlength="19" required placeholder="1111-2222-3333-4444">
        <button type="button" onclick="toggleVisibility('cardNumber', this)">👁</button>
    </div>

    <div style="margin-bottom: 10px;">
        <label>유효기간:</label>
        <input type="text" name="expiry" id="expiry" maxlength="5" placeholder="MM/YY" required>
    </div>

    <div style="margin-bottom: 10px;">
        <label>CVC:</label>
        <input type="password" name="cvc" id="cvc" maxlength="3" placeholder="123" required>
        <button type="button" onclick="toggleVisibility('cvc', this)">👁</button>
    </div>

    <input type="hidden" name="performanceId" value="${performance.id}">
    <input type="hidden" name="date" value="${date}">
    <input type="hidden" name="time" value="${time}">
    <input type="hidden" name="days" value="${days}">
    <input type="hidden" name="seatId" value="${seatId}">
    <input type="hidden" name="price" value="${price}">
    
    <button type="submit">결제 및 예매 완료</button>
</form>

<script>
  // 카드번호 자동 하이픈
  document.getElementById("cardNumber").addEventListener("input", function(e) {
    let value = e.target.value.replace(/[^0-9]/g, '');
    if (value.length > 16) value = value.slice(0, 16);
    let formatted = value.match(/.{1,4}/g);
    e.target.value = formatted ? formatted.join('-') : '';
  });

  // 유효기간 자동 슬래시
  document.getElementById("expiry").addEventListener("input", function(e) {
    let value = e.target.value.replace(/[^0-9]/g, '');
    if (value.length > 4) value = value.slice(0, 4);
    e.target.value = value.length >= 3
      ? value.slice(0, 2) + '/' + value.slice(2)
      : value;
  });

  // CVC 숫자만 3자리 제한
  document.getElementById("cvc").addEventListener("input", function(e) {
    let value = e.target.value.replace(/[^0-9]/g, '');
    if (value.length > 3) value = value.slice(0, 3);
    e.target.value = value;
  });

  // 보기/숨기기 토글
  function toggleVisibility(fieldId, button) {
    const input = document.getElementById(fieldId);
    if (input.type === "password") {
      input.type = "text";
      button.textContent = "🔓";
    } else {
      input.type = "password";
      button.textContent = "🔒";
    }
  }
</script>