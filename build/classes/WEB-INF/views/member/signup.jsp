<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>회원가입</title>
</head>
<body>
    
    <!-- 상단 PerFortival -->
    <h1 style="font-size: 2rem; font-weight: bold; cursor: pointer;" onclick="location.href='<%= request.getContextPath() %>/main'">
        PerFortival
    </h1>
    <hr>
    
    <h2>회원가입</h2>
    <form action="<%= request.getContextPath() %>/member/signup" method="post">
        아이디: <input type="text" name="id" required><br><br>
        비밀번호: <input type="password" name="pw" minlength="8" required><br><br>
        이름: <input type="text" name="name" required><br><br>
        이메일: <input type="email" name="email" required><br><br>
        <input type="submit" value="가입">
    </form>
</body>
</html>
