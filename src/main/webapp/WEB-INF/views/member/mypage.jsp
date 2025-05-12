<%@ page contentType="text/html; charset=UTF-8" %>
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
    <title>마이페이지</title>
</head>
<body>

    <!-- 상단 PerFortival -->
    <h1 style="font-size: 2rem; font-weight: bold; cursor: pointer;" onclick="location.href='<%= request.getContextPath() %>/main'">
        PerFortival
    </h1>
    <hr>
    
    <h2>마이페이지</h2>
    <p><strong>ID:</strong> <%= member.getId() %></p>
    <p><strong>이름:</strong> <%= member.getName() %></p>
    <p><strong>이메일:</strong> <%= member.getEmail() %></p>

    <hr>
    <a href="<%= request.getContextPath() %>/member/update">회원 정보 수정</a> |
    <a href="<%= request.getContextPath() %>/member/withdraw">회원 탈퇴</a> |
    <a href="<%= request.getContextPath() %>/member/logout">로그아웃</a>
</body>
</html>
