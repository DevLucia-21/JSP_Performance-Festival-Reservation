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
    <title>회원 정보 수정</title>
</head>
<body>

    <!-- 상단 PerFortival -->
    <h1 style="font-size: 2rem; font-weight: bold; cursor: pointer;" 
        onclick="location.href='<%= request.getContextPath() %>/main'">
        PerFortival
    </h1>
    <hr>
    
    <h2>회원 정보 수정</h2>
    <form action="<%= request.getContextPath() %>/member/update" method="post">
        아이디: <%= member.getId() %> (수정 불가)<br><br>
        
        이름: <input type="text" name="name" value="<%= member.getName() %>" required><br><br>
        
        비밀번호: <input type="password" name="pw" minlength="8" required><br><br>
        
        이메일: <input type="email" name="email" value="<%= member.getEmail() %>" required><br><br>
        
        주소: <input type="text" name="address" value="<%= member.getAddress() %>" required><br><br>
        
        <input type="submit" value="수정 완료">
    </form>
</body>
</html>
