<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="model.dto.MemberDTO" %>
<%
    MemberDTO member = (MemberDTO) session.getAttribute("loginUser");
    if (member == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>회원 정보 수정</title>
</head>
<body>
    <h2>회원 정보 수정</h2>
    <form action="<%= request.getContextPath() %>/member/update" method="post">
        아이디: <%= member.getId() %> (수정 불가)<br><br>
        이름: <input type="text" name="name" value="<%= member.getName() %>"><br><br>
        비밀번호: <input type="password" name="pw"  minlength="8" required value="<%= member.getPw() %>"><br><br>
        이메일: <input type="email" name="email" required value="<%= member.getEmail() %>"><br><br>
        <input type="submit" value="수정 완료">
    </form>
</body>
</html>
