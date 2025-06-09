<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>아이디 찾기 결과</title>
</head>
<body>

    <!-- 상단 PerFortival -->
    <h1 style="font-size: 2rem; font-weight: bold; cursor: pointer;"
        onclick="location.href='<%= request.getContextPath() %>/main'">
        PerFortival
    </h1>
    <hr>

    <h2>아이디 찾기 결과</h2>

    <c:choose>
        <c:when test="${not empty foundId}">
            <p>회원님의 아이디는 <strong>${foundId}</strong> 입니다.</p>
        </c:when>
        <c:otherwise>
            <p style="color:red;">${error}</p>
        </c:otherwise>
    </c:choose>

    <br>
    <button onclick="location.href='<%= request.getContextPath() %>/member/login'">로그인 하러 가기</button>
    <button onclick="location.href='<%= request.getContextPath() %>/member/findId.jsp'">다시 찾기</button>

</body>
</html>
