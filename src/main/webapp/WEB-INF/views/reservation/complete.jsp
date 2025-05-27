<%@ page contentType="text/html; charset=UTF-8" %>

<h1 style="font-size: 2rem; font-weight: bold; cursor: pointer;" onclick="location.href='<%= request.getContextPath() %>/main'">
    PerFortival
</h1>
<hr>

<h1>예매 완료</h1>
<p>예매가 성공적으로 완료되었습니다!</p>
<a href="<%= request.getContextPath() %>/mypage">마이페이지로 이동</a>
