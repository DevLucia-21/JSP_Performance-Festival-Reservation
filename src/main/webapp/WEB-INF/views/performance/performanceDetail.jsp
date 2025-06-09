<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>공연 상세 정보</title>
</head>
<body>

    <!-- 상단 PerFortival -->
    <h1 style="font-size: 2rem; font-weight: bold; cursor: pointer;" onclick="location.href='<%= request.getContextPath() %>/main'">
        PerFortival
    </h1>
    <hr>

    <h2>공연 상세 정보</h2>

    <c:choose>
        <c:when test="${not empty performance}">
            <h3>${performance.title}</h3>
            
            <c:if test="${not empty performance.posterUrl}">
                <img src="${performance.posterUrl}" alt="${performance.title} 포스터" width="300px" />
            </c:if>
            
            <p><strong>장소:</strong> ${performance.location}</p>
            <p><strong>기간:</strong> ${performance.startDate} ~ ${performance.endDate}</p>
            <p><strong>장르:</strong> ${performance.genre}</p>
            
            <c:if test="${avgRating > 0}">
						    <p><strong>평균 별점: </strong>
						        <c:forEach var="i" begin="1" end="${fn:substringBefore(avgRating, '.')+0}">
						            ★
						        </c:forEach>
						        (${fn:substring(avgRating, 0, 3)} / 5.0)
						    </p>
						</c:if>
						<c:if test="${avgRating == 0}">
						    <p>아직 별점이 없습니다.</p>
						</c:if>

            <!-- 예매 영역 시작 -->
            <hr>
            <h3>예매하기</h3>

            <form method="post" action="${pageContext.request.contextPath}/reservation/step1">

                <!-- 날짜 선택: dateList 사용 -->
                <label>날짜:</label>
                <select name="date" required>
                    <c:forEach var="d" items="${dateList}">
                        <option value="${d}">${d}</option>
                    </c:forEach>
                </select>
                
                <!-- 시간 선택 -->
                <label>시간:</label>
                <select name="time" required>
                    <c:forEach var="t" items="${timeList}">
                        <option value="${t.time}">${t.time}</option>
                    </c:forEach>
                </select>
                
                <!-- 자유석이 아닐 경우에만 수량 선택 -->
                <c:if test="${performance.reservationType ne '자유석'}">
                    <label>수량:</label>
                    <select name="quantity" required>
                        <option value="1">1장</option>
                        <option value="2">2장</option>
                    </select>
                </c:if>
                
                <!-- 공연 ID 숨김 전달 -->
                <input type="hidden" name="performanceId" value="${performance.id}" />

                <br><br>
                <button type="submit">예매하기</button>
            </form>
            <!-- 예매 영역 끝 -->

        </c:when>
        <c:otherwise>
            <p>공연 정보를 찾을 수 없습니다.</p>
        </c:otherwise>
    </c:choose>

    <br><br>
    <button onclick="location.href='<%= request.getContextPath() %>/performances/search'">목록으로 돌아가기</button>

</body>
</html>