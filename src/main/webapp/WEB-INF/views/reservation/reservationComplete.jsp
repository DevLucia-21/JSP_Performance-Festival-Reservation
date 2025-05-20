<%@ page contentType="text/html; charset=UTF-8" %>

<h2>예매가 완료되었습니다! 🎉</h2>

<p>감사합니다. 선택하신 공연이 정상적으로 예매되었습니다.</p>

<button onclick="location.href='<%= request.getContextPath() %>/mypage'">마이페이지로 이동</button>
<button onclick="location.href='<%= request.getContextPath() %>/main'">메인으로</button>
