<%@ page contentType="text/html; charset=UTF-8" %>


<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>공연 시간 등록</title>
</head>
<body>

<!-- 상단 PerFortival -->
<h1 style="font-size: 2rem; font-weight: bold; cursor: pointer;" onclick="location.href='<%= request.getContextPath() %>/main'">
    PerFortival
</h1>
<hr>

<head><title>성공</title></head>
<body>
    <h2>공연 예매 설정이 완료되었습니다.</h2>
    <a href="<%= request.getContextPath() %>/admin/performances">돌아가기</a>
</body>
</html>
