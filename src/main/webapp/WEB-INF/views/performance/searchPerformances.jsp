<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>공연 조회</title>
</head>
<body>

    <!-- 상단 PerFortival -->
    <h1 style="font-size: 2rem; font-weight: bold; cursor: pointer;" onclick="location.href='<%= request.getContextPath() %>/main'">
        PerFortival
    </h1>
    <hr>

    <h2>공연 조회</h2>

    <form action="<%= request.getContextPath() %>/performances/search" method="get">
        <input type="text" name="searchKeyword" placeholder="공연명 검색" />
        <input type="date" name="startDate" />
        <input type="date" name="endDate" />
        <button type="submit">검색</button>
    </form>

    <hr>

    <table border="1" width="100%">
        <thead>
            <tr>
                <th>공연명</th>
                <th>시작일</th>
                <th>종료일</th>
                <th>장소</th>
                <th>장르</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="performance" items="${searchResults}">
                <tr>
                    <td>
                        <a href="<%= request.getContextPath() %>/performances/detail?id=${performance.id}">
                            ${performance.title}
                        </a>
                    </td>
                    <td>${performance.startDate}</td>
                    <td>${performance.endDate}</td>
                    <td>${performance.location}</td>
                    <td>${performance.genre}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

</body>
</html>
