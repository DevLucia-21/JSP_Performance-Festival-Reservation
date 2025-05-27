<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- 상단 PerFortival -->
<h1 style="font-size: 2rem; font-weight: bold; cursor: pointer;" onclick="location.href='<%= request.getContextPath() %>/main'">
    PerFortival
</h1>
<hr>

<h2>관리자 - 공연 관리</h2>

<!-- 검색 폼 -->
<form action="<%= request.getContextPath() %>/admin/performances" method="get">
    <input type="text" name="searchKeyword" placeholder="검색어 입력" />
    <input type="date" name="startDate" />
    <input type="date" name="endDate" />
    <button type="submit">검색</button>
</form>

<hr>

<form action="<%= request.getContextPath() %>/admin/performances" method="post" style="margin-top: 10px;">
    <table border="1" style="width: 100%; margin-top: 20px;">
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
            <c:choose>
                <c:when test="${not empty searchResults}">
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
                </c:when>
                <c:otherwise>
                    <tr>
                        <td colspan="5">검색 결과가 없습니다.</td>
                    </tr>
                </c:otherwise>
            </c:choose>
        </tbody>
    </table>

    <br>
		<form action="<%= request.getContextPath() %>/admin/performances" method="post" style="margin-top: 10px;">
		    <button type="submit" name="action" value="save">선택한 공연 저장</button>
		</form>
		<hr>
		<!-- 예매 방식 관리 버튼 -->
		<form action="<%= request.getContextPath() %>/reservation" method="get" style="margin-top: 10px;">
		    <button type="submit">공연 예매 방식 관리</button>
		</form>
		<hr>
		<!-- 공연 시간 등록 버튼 -->
		<form action="${pageContext.request.contextPath}/admin/times" method="get" style="display:inline;">
		    <button type="submit">공연 시간 등록</button>
		</form>