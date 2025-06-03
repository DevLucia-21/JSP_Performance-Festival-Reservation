<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h1 style="font-size: 2rem; font-weight: bold; cursor: pointer;" onclick="location.href='<%= request.getContextPath() %>/main'">
    PerFortival
</h1>
<hr>

<h2>댓글 수정</h2>
<form action="${pageContext.request.contextPath}/comment/edit" method="post">
    <input type="hidden" name="id" value="${comment.id}" />
    <textarea name="content" rows="4" cols="60">${comment.content}</textarea><br><br>
    <button type="submit">수정 완료</button>
    <button type="button" onclick="location.href='${pageContext.request.contextPath}/review/list';">취소</button>
</form>