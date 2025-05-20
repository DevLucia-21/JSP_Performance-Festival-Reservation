<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%-- 예매 타입에 따라 분기 --%>
<c:choose>
    <c:when test="${performance.reservationType eq '좌석A'}">
        <jsp:include page="seat_type_a.jsp" />
    </c:when>

    <c:when test="${performance.reservationType eq '좌석B'}">
        <jsp:include page="seat_type_b.jsp" />
    </c:when>

    <c:when test="${performance.reservationType eq '혼합'}">
        <jsp:include page="seat_type_mixed.jsp" />
    </c:when>

    <c:when test="${performance.reservationType eq '자유석'}">
        <jsp:include page="free_ticket.jsp" />
    </c:when>

    <c:otherwise>
        <p>예매 방식이 올바르지 않습니다.</p>
    </c:otherwise>
</c:choose>
