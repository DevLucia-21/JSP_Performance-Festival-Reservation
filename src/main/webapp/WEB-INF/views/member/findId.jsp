<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>아이디 찾기</title>
</head>
<body>

    <!-- 상단 PerFortival -->
    <h1 style="font-size: 2rem; font-weight: bold; cursor: pointer;"
        onclick="location.href='<%= request.getContextPath() %>/main'">
        PerFortival
    </h1>
    <hr>

    <h2>아이디 찾기</h2>

    <form action="<%= request.getContextPath() %>/member/findId" method="post">
        이름: <input type="text" name="name" required><br><br>
        이메일: <input type="email" name="email" required><br><br>
        <input type="submit" value="아이디 찾기">
    </form>

    <br>
    <button onclick="location.href='<%= request.getContextPath() %>/member/login'">로그인 페이지로</button>

</body>
</html>
