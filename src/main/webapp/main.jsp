<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="model.dto.MemberDTO" %>
<%
    // 세션에서 로그인된 사용자 정보 가져오기
    MemberDTO member = (MemberDTO) session.getAttribute("loginUser");

    if (member == null) {
        // 로그인 안 되어 있으면 로그인 페이지로 리다이렉트
        response.sendRedirect(request.getContextPath() + "/member/login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Main Page</title>
</head>
<body>
    <h2>로그인 성공</h2>
    <p><strong>ID:</strong> <%= member.getId() %></p>
    <p><strong>Name:</strong> <%= member.getName() %></p>
    <p><strong>Email:</strong> <%= member.getEmail() %></p>

    <form action="<%= request.getContextPath() %>/logout" method="get">
    <input type="submit" value="로그아웃">
		</form>
		<br>
		<form action="member/mypage.jsp" method="get">
    <input type="submit" value="마이페이지">
		</form>
</body>
</html>
