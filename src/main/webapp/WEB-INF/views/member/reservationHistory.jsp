<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page import="com.perfortival.member.dto.MemberDTO" %>
<%
    MemberDTO member = (MemberDTO) session.getAttribute("loginUser");
    if (member == null) {
        response.sendRedirect(request.getContextPath() + "/member/login");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>예매 내역</title>
</head>
<body>

<h1 style="font-size: 2rem; font-weight: bold; cursor: pointer;"
    onclick="location.href='<%= request.getContextPath() %>/main'">
    PerFortival
</h1>
<hr>

<h2>예매 내역 확인</h2>

<c:choose>
    <c:when test="${empty reservationHistory}">
        <p>예매 내역이 없습니다.</p>
    </c:when>
    <c:otherwise>
        <form id="cancelForm" action="<%= request.getContextPath() %>/member/reservationHistory/cancel" method="post">
        		<input type="hidden" name="reservationId" id="cancelReservationId" />
        <table border="1" cellpadding="8" cellspacing="0">
            <thead>
                <tr>
                    <th>공연명</th>
                    <th>공연일</th>
                    <th>공연시간</th>
                    <th>좌석</th>
                    <th>좌석등급</th>
                    <th>수량</th>
                    <th>일수</th>
                    <th>결제상태</th>
                    <th>취소</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="res" items="${reservationHistory}">
                    <tr>
                        <td>${res.performanceTitle}</td>
                        <td>${res.reservationDate}</td>
                        <td>${res.reservationTime}</td>

                        <!-- ✅ 좌석명 출력 -->
                        <td>
												    <c:choose>
												        <c:when test="${res.seat == null}">
												            자유석
												        </c:when>
												        <c:otherwise>
												            ${res.seat.seatLabel}
												        </c:otherwise>
												    </c:choose>
												</td>

                        <td>
												    <c:choose>
												        <c:when test="${res.seat == null}">
												            자유석
												        </c:when>
												        <c:otherwise>
												            ${res.seat.seatType}
												        </c:otherwise>
												    </c:choose>
												</td>

                        <td><c:out value="${res.quantity}" default="-" /></td>
                        <td>
												    <c:choose>
												        <c:when test="${empty res.seat}">
												            <c:choose>
												                <c:when test="${res.days == 1}">1일권</c:when>
												                <c:when test="${res.days == 2}">2일권</c:when>
												                <c:when test="${res.days == 3}">3일권</c:when>
												                <c:otherwise>${res.days}일</c:otherwise>
												            </c:choose>
												        </c:when>
												        <c:otherwise>
												            -
												        </c:otherwise>
												    </c:choose>
												</td>
                        <td>${res.paymentStatus}</td>
                        <td>
                            <c:if test="${res.paymentStatus eq '결제완료'}">
                                <button type="button" onclick="confirmCancel(${res.id})">
																    취소
																</button>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        </form>
    </c:otherwise>
</c:choose>

<br>
<button onclick="location.href='<%= request.getContextPath() %>/member/mypage'">마이페이지로 돌아가기</button>

</body>
</html>

<script>
    function confirmCancel(reservationId) {
        const confirmed = confirm("예매를 취소하시겠습니까?");
        if (confirmed) {
            document.getElementById('cancelReservationId').value = reservationId;
            document.getElementById('cancelForm').submit();
        }
    }
</script>
