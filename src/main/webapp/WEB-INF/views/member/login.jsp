<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>로그인</title>
    <style>
        .error-message {
            color: red;
        }
    </style>
</head>
<body>

    <!-- 상단 PerFortival -->
    <h1 style="font-size: 2rem; font-weight: bold; cursor: pointer;" onclick="location.href='<%= request.getContextPath() %>/main'">
        PerFortival
    </h1>
    <hr>

    <h2>Login</h2>
    <form action="<%= request.getContextPath() %>/member/login" method="post">
        아이디: <input type="text" name="id"><br><br>
        비밀번호: <input type="password" name="pw"><br><br>
        <input type="submit" value="로그인">
    </form>
    
    <%
        String error = request.getParameter("error");
        if ("1".equals(error)) {
    %>
        <p class="error-message">로그인에 실패했습니다. 아이디 또는 비밀번호를 확인해주세요.</p>
    <%
        }
    %>
    
</body>
</html>
