<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>회원가입</title>
</head>
<body>
    <h2>회원가입</h2>
    <form action="signup" method="post">
        아이디: <input type="text" name="id"><br><br>
        비밀번호: <input type="password" name="pw" minlength="8" required><br><br>
        이름: <input type="text" name="name"><br><br>
        이메일: <input type="email" name="email" required><br><br>
        <input type="submit" value="가입">
    </form>
</body>
</html>
