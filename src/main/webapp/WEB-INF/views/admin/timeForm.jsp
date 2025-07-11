<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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

<h2>공연 시간 등록</h2>

<form method="post" action="${pageContext.request.contextPath}/admin/times">
    <label>공연 선택:</label>
    <select name="performanceId" required>
        <c:forEach var="performance" items="${performanceList}">
            <option value="${performance.id}">${performance.title}</option>
        </c:forEach>
    </select>

    <hr>

    <div id="time-container">
        <div class="time-item">
            시간: <input type="time" name="time" required>
        </div>
    </div>
		<br>
    <button type="button" onclick="addTime()">+ 시간 추가</button>
    <br><br>
    <button type="submit">등록</button>
    <br><br>
				<button type="button" onclick="location.href='<%= request.getContextPath() %>/admin/performances'">이전 페이지로</button>
</form>

<script>
    function addTime() {
        const container = document.getElementById("time-container");
        const item = document.createElement("div");
        item.className = "time-item";
        item.innerHTML = `
            시간: <input type="time" name="time" required>
            <button type="button" onclick="this.parentNode.remove()">삭제</button>
        `;
        container.appendChild(item);
    }
</script>

</body>
</html>
