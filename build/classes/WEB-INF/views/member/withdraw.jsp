<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>회원 탈퇴</title>
</head>
<body>
    
    <!-- 상단 PerFortival -->
    <h1 style="font-size: 2rem; font-weight: bold; cursor: pointer;" onclick="location.href='<%= request.getContextPath() %>/main'">
        PerFortival
    </h1>
    <hr>
		
    <h2>회원 탈퇴</h2>
    <form action="<%= request.getContextPath() %>/member/withdraw" method="post">
        비밀번호 확인: <input type="password" name="pw" required><br><br>
        <input type="submit" value="탈퇴하기">
    </form>
</body>
</html>
