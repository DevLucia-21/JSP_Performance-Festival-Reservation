<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="model.dto.MemberDTO" %>
<%
    MemberDTO member = (MemberDTO) session.getAttribute("loginUser");

    if (member == null) {
    		response.sendRedirect(request.getContextPath() + "/member/login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>마이페이지</title>
</head>
<body>
    <h2>마이페이지</h2>
    <p><strong>ID:</strong> <%= member.getId() %></p>
    <p><strong>이름:</strong> <%= member.getName() %></p>
    <p><strong>이메일:</strong> <%= member.getEmail() %></p>

    <hr>
    <a href="updateMember.jsp">회원 정보 수정</a> |
    <a href="withdraw.jsp">회원 탈퇴</a> |
    <a href="<%= request.getContextPath() %>/logout">로그아웃</a>
</body>
</html>
