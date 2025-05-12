<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h1>관리자 - 공연 관리</h1>

<!-- 상단 PerFortival -->
<h1 style="font-size: 2rem; font-weight: bold; cursor: pointer;" onclick="location.href='<%= request.getContextPath() %>/main'">
    PerFortival
</h1>
<hr>
    
<!-- 검색 폼 -->
<form action="<%= request.getContextPath() %>/admin/performances" method="get">
    <input type="text" name="searchKeyword" placeholder="검색어 입력" />
    <input type="date" name="startDate" />
    <input type="date" name="endDate" />
    <button type="submit">검색</button>
</form>

<!-- 검색 결과가 있을 경우 출력 -->
<c:choose>
    <c:when test="${not empty searchResults}">
        <form action="<%= request.getContextPath() %>/admin/performances" method="post">
            <table border="1" style="width: 100%; margin-top: 10px;">
                <thead>
                    <tr>
                        <th>선택</th>
                        <th>공연명</th>
                        <th>기간</th>
                        <th>장소</th>
                        <th>장르</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="performance" items="${searchResults}">
                        <tr>
                            <td>
                                <input type="checkbox" name="selectedPerformances" value="${performance.id}" />
                            </td>
                            <td>${performance.title}</td>
                            <td>${performance.startDate} ~ ${performance.endDate}</td>
                            <td>${performance.location}</td>
                            <td>${performance.genre}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <button type="submit" style="margin-top: 10px;">선택한 공연 저장</button>
        </form>
    </c:when>

    <c:otherwise>
        <p style="margin-top: 20px;">검색 결과가 없습니다.</p>
    </c:otherwise>
</c:choose>
