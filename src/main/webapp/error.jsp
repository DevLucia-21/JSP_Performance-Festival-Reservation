<%@ page contentType="text/html; charset=UTF-8" isErrorPage="true" %>
<html>
<head>
    <title>오류 발생</title>
</head>
<body>
    <h1>⚠ 오류가 발생했습니다</h1>
    <p>죄송합니다. 요청 처리 중 문제가 발생했습니다.</p>

    <h3>오류 정보:</h3>
    <ul>
        <li><strong>에러 코드:</strong> <%= request.getAttribute("javax.servlet.error.status_code") %></li>
        <li><strong>요청 URL:</strong> <%= request.getAttribute("javax.servlet.error.request_uri") %></li>
        <li><strong>예외 정보:</strong> <%= request.getAttribute("javax.servlet.error.exception") %></li>
    </ul>

    <p><a href="<%= request.getContextPath() %>/main">메인으로 돌아가기</a></p>
</body>
</html>
