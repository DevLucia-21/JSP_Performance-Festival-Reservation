<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<h1 style="font-size: 2rem; font-weight: bold; cursor: pointer;" onclick="location.href='<%= request.getContextPath() %>/main'">
    PerFortival
</h1>
<hr>

<h2>결제 정보 확인</h2>

<p>공연명: ${performance.title}</p>
<p>공연 날짜: ${date}</p>
<p>공연 시간: ${time}</p>
<p>좌석 번호: ${seatLabel}</p>
<p>가격: <fmt:formatNumber value="${price}" pattern="#,###" /> 원</p>

<form action="${pageContext.request.contextPath}/reservation/complete" method="post">
    <input type="hidden" name="performanceId" value="${performance.id}">
    <input type="hidden" name="date" value="${date}">
    <input type="hidden" name="time" value="${time}">
    <input type="hidden" name="seatId" value="${seatId}">
    <input type="hidden" name="price" value="${price}">
    
    <button type="submit">결제 및 예매 완료</button>
</form>
