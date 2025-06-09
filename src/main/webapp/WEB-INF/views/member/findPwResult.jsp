<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>비밀번호 찾기 결과</title>
</head>
<body>

<!-- 상단 PerFortival -->
<h1 style="font-size: 2rem; font-weight: bold; cursor: pointer;"
    onclick="location.href='<%= request.getContextPath() %>/main'">
    PerFortival
</h1>
<hr>

<h2>비밀번호 찾기 결과</h2>

<% String foundPw = (String) request.getAttribute("foundPw"); %>
<% String error = (String) request.getAttribute("error"); %>

<% if (foundPw != null) { %>
    <p><strong>찾은 비밀번호:</strong> <%= foundPw %></p>
    <a href="<%= request.getContextPath() %>/member/login">로그인 페이지로 이동</a>
<% } else if (error != null) { %>
    <p style="color: red;"><%= error %></p>
    <a href="<%= request.getContextPath() %>/member/findPwPage">다시 시도하기</a>
<% } %>

</body>
</html>
