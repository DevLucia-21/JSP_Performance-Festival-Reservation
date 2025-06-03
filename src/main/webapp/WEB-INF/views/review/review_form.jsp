<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>공연 후기 작성</title>
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

<h2>공연 후기 작성</h2>

<form action="${pageContext.request.contextPath}/review/write" method="post">
        <label>공연 선택:</label>
        <select name="performanceId" required>
            <c:forEach var="performance" items="${performanceList}">
                <option value="${performance.id}">${performance.title}</option>
            </c:forEach>
        </select><br><br>

        <label>후기 구분:</label>
        <select name="reviewType" required>
            <option value="전">공연 전</option>
            <option value="후">공연 후</option>
        </select><br><br>

        <label>별점:</label>
        <div id="star-rating">
            <span class="star" data-value="1">☆</span>
            <span class="star" data-value="2">☆</span>
            <span class="star" data-value="3">☆</span>
            <span class="star" data-value="4">☆</span>
            <span class="star" data-value="5">☆</span>
        </div>
        <input type="hidden" name="rating" id="rating" value="5" /><br><br>

        <label>제목:</label>
        <input type="text" name="title" required /><br><br>

        <label>내용:</label><br>
        <textarea name="content" rows="10" cols="60" required></textarea><br><br>

        <button type="submit">작성 완료</button>
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

        // 초기 상태: 5점으로 설정
        document.querySelectorAll('.star').forEach((s, i) => {
            s.classList.add('selected');
            s.textContent = '★';
        });
    </script>

</body>
</html>