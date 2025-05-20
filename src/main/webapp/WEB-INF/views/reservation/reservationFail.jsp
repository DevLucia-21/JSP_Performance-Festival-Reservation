<%@ page contentType="text/html; charset=UTF-8" %>

<h2>예매에 실패했습니다 😢</h2>

<p>${errorMessage}</p>

<button onclick="history.back()">이전 페이지로</button>
<button onclick="location.href='<%= request.getContextPath() %>/main'">메인으로</button>
