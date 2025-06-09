<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h1 style="font-size: 2rem; font-weight: bold; cursor: pointer;" onclick="location.href='<%= request.getContextPath() %>/main'">
    PerFortival
</h1>
<hr>

<h2>결제 완료!</h2>

<p><strong><fmt:formatNumber value="${totalPrice}" pattern="#,###" />원이 결제되었습니다.</strong></p>

<c:if test="${not empty selectedSeats}">
    <p>좌석 번호:
        <c:forEach var="seat" items="${selectedSeats}" varStatus="loop">
            ${seat.zone}-${seat.row}${seat.col}<c:if test="${!loop.last}">, </c:if>
        </c:forEach>
    </p>
</c:if>

<p>카드 번호: ${maskedCardNumber}</p>
<p>예매가 성공적으로 처리되었습니다.</p>

<br><br>
<a href="<%= request.getContextPath() %>/main">메인으로 돌아가기</a>
