<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- 상단 PerFortival -->
<h1 style="font-size: 2rem; font-weight: bold; cursor: pointer;" onclick="location.href='<%= request.getContextPath() %>/main'">
    PerFortival
</h1>
<hr>

<h2>전체 예매 로그</h2>
<hr>

<table border="1" cellpadding="8" cellspacing="0">
    <thead>
        <tr>
            <th>회원 ID</th>
            <th>공연명</th>
            <th>예매일</th>
            <th>시간</th>
            <th>좌석</th>
            <th>등급</th>
            <th>수량</th>
            <th>일수</th>
            <th>결제</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="log" items="${logs}">
            <tr>
                <td>${log.memberId}</td>
                <td>${log.performanceTitle}</td>
                <td>${log.reservationDate}</td>
                <td>${log.reservationTime}</td>
                <td>
                    <c:choose>
                        <c:when test="${log.seat == null}">
                            자유석
                        </c:when>
                        <c:otherwise>
                            ${log.seat.zone}-${log.seat.row}${log.seat.col}
                        </c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <c:choose>
                        <c:when test="${log.seat == null}">자유석</c:when>
                        <c:otherwise>${log.seat.seatType}</c:otherwise>
                    </c:choose>
                </td>
                <td><c:out value="${log.quantity}" default="-" /></td>
                <td><c:out value="${log.days}" default="-" /></td>
                <td>
                    <c:choose>
                        <c:when test="${log.paymentStatus eq '결제취소'}">
                            <span style="color:red;">취소됨</span>
                        </c:when>
                        <c:otherwise>예매됨</c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>

<br>
<button onclick="location.href='<%= request.getContextPath() %>/performances/search'">공연 목록으로</button>
