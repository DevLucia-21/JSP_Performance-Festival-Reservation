<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.perfortival.member.dto.MemberDTO" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Main Page</title>
    <style>
        .performance-card {
            border: 1px solid #ccc;
            padding: 16px;
            margin-bottom: 16px;
            display: inline-block;
            width: 200px;
            text-align: center;
        }

        .performance-card img {
            width: 100%;
            height: 150px;
            object-fit: cover;
            margin-bottom: 10px;
        }

        .performance-container {
            display: flex;
            flex-wrap: wrap;
            gap: 16px;
        }
    </style>
</head>
<body>

    <!-- 상단 PerFortival -->
    <h1 style="font-size: 2rem; font-weight: bold; cursor: pointer;" onclick="location.href='<%= request.getContextPath() %>/main'">
        PerFortival
    </h1>
    <hr>

    <%
        MemberDTO member = (MemberDTO) session.getAttribute("loginUser");
        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
    %>

    <%
        if (member != null) {
    %>
        <h3>환영합니다, <%= member.getName() %>님!</h3>

        <form action="<%= request.getContextPath() %>/member/logout" method="get" style="display: inline;">
            <input type="submit" value="로그아웃">
        </form>
        &nbsp;
        <form action="<%= request.getContextPath() %>/member/mypage" method="get" style="display: inline;">
            <input type="submit" value="마이페이지">
        </form>

    <%
           if (isAdmin != null && isAdmin) {
        %>
             <br><br>
           <hr>
           <form action="<%= request.getContextPath() %>/admin/performances" method="get" style="display: inline;">
               <input type="submit" value="공연 관리 페이지">
           </form>
           &nbsp;&nbsp;
           <form action="<%= request.getContextPath() %>/admin/reservationLogs" method="get" style="display: inline;">
						    <input type="submit" value="예매 내역 페이지">
						</form>
           
        <%
           }
        %>
        <br>

    <%
        } else {
    %>
        <h3>로그인되지 않았습니다.</h3>

        <div>
            <form action="<%= request.getContextPath() %>/member/login" method="get" style="display: inline;">
                <input type="submit" value="로그인">
            </form>
            &nbsp;
            <form action="<%= request.getContextPath() %>/member/signup" method="get" style="display: inline;">
                <input type="submit" value="회원가입">
            </form>
        </div>
    <%
        }
    %>
    <hr>
    <h2>주요 기능</h2>
        
    <div>
    <!-- 공연 조회 버튼 -->
	    <form action="<%= request.getContextPath() %>/performances/search" method="get" style="display: inline;">
	        <input type="submit" value="공연 조회">
	    </form>
	    &nbsp;
	    <form action="<%= request.getContextPath() %>/review/list" method="get" style="display: inline;">
	        <input type="submit" value="공연 후기 게시판">
       </form>
    </div>
    <hr>

    <h2>공연 목록</h2>

    <div class="performance-container">
        <c:forEach var="performance" items="${performances}">
            <div class="performance-card">
                <c:if test="${not empty performance.posterUrl}">
                    <img src="${performance.posterUrl}" alt="${performance.title} 포스터">
                </c:if>
                <h3>${performance.title}</h3>
                <p>시작일: ${performance.startDate}</p>
                <p>종료일: ${performance.endDate}</p>
                <p>장소: ${performance.location}</p>
                <p>장르: ${performance.genre}</p>
            </div>
        </c:forEach>
    </div>

</body>
</html>
