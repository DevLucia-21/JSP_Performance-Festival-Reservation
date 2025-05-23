<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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


        </c:when>
        <c:otherwise>
            <p>공연 정보를 찾을 수 없습니다.</p>
        </c:otherwise>
    </c:choose>

    <br><br>
    <button onclick="location.href='<%= request.getContextPath() %>/performances/search'">목록으로 돌아가기</button>

</body>
</html>
