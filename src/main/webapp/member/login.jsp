<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>로그인</title>
</head>
<body>
    <h2>Login</h2>
    <form action="login" method="post">
        아이디: <input type="text" name="id"><br><br>
        비밀번호: <input type="password" name="pw"><br><br>
        <input type="submit" value="로그인">
    </form>
    
    <%
        String error = request.getParameter("error");
        if ("1".equals(error)) {
    %>
        <p style="color: red;">로그인에 실패했습니다. 아이디 또는 비밀번호를 확인해주세요.</p>
    <%
        }
    %>
    
</body>
</html>
