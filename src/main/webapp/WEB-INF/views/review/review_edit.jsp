<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>공연 후기 수정</title>
    <style>
        .star {
            font-size: 2rem;
            cursor: pointer;
            color: gray;
        }
        .star.selected {
            color: gold;
        }
    </style>
</head>
<body>

<h1 style="font-size: 2rem; font-weight: bold; cursor: pointer;" onclick="location.href='<%= request.getContextPath() %>/main'">
    PerFortival
</h1>
<hr>

<h2>공연 후기 수정</h2>

<form action="${pageContext.request.contextPath}/review/edit" method="post">
    <input type="hidden" name="id" value="${review.id}" />

    <label><b>공연 제목:</b></label>
    <span>${review.performanceTitle}</span><br><br>

    <label><b>후기 구분:</b></label>
    <span>${review.reviewType == '전' ? '공연 전' : '공연 후'}</span><br><br>

    <label><b>별점:</b></label>
    <div id="star-rating">
        <c:forEach var="i" begin="1" end="5">
            <span class="star <c:if test='${i <= review.rating}'>selected</c:if>'" data-value="${i}">
                <c:choose>
                    <c:when test="${i <= review.rating}">★</c:when>
                    <c:otherwise>☆</c:otherwise>
                </c:choose>
            </span>
        </c:forEach>
    </div>
    <input type="hidden" name="rating" id="rating" value="${review.rating}" /><br><br>

    <label><b>제목:</b></label>
    <input type="text" name="title" value="${review.title}" required /><br><br>

    <label><b>내용:</b></label><br>
    <textarea name="content" rows="10" cols="60" required>${review.content}</textarea><br><br>

    <button type="submit">수정 완료</button>
    <button type="button" onclick="location.href='${pageContext.request.contextPath}/review/list';">취소</button>
</form>

<script>
    const stars = document.querySelectorAll('.star');
    const ratingInput = document.getElementById('rating');

    stars.forEach(star => {
        star.addEventListener('click', () => {
            const value = parseInt(star.getAttribute('data-value'));
            ratingInput.value = value;

            stars.forEach((s, index) => {
                if (index < value) {
                    s.classList.add('selected');
                    s.textContent = '★';
                } else {
                    s.classList.remove('selected');
                    s.textContent = '☆';
                }
            });
        });
    });
</script>

</body>
</html>
