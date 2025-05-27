<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<h1 style="font-size: 2rem; font-weight: bold; cursor: pointer;" onclick="location.href='<%= request.getContextPath() %>/main'">
    PerFortival
</h1>
<hr>

<h2>결제 완료!</h2>

<p><strong><fmt:formatNumber value="${price}" pattern="#,###" />원이 결제되었습니다.</strong></p>
<p>카드 번호: ${maskedCardNumber}</p>
<p>예매가 성공적으로 처리되었습니다.</p>

<a href="<%= request.getContextPath() %>/main">메인으로 돌아가기</a>