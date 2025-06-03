<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%
    com.perfortival.member.dto.MemberDTO loginUser =
        (com.perfortival.member.dto.MemberDTO) session.getAttribute("loginUser");
%>

<h1 style="font-size: 2rem; font-weight: bold; cursor: pointer;" onclick="location.href='<%= request.getContextPath() %>/main'">
    PerFortival
</h1>
<hr>

<h2 style="display: inline;">공연 후기 게시판</h2>
<a href="${pageContext.request.contextPath}/review/write" style="float: right;">
    <button>후기 작성</button>
</a>
<br><br>

<!-- 필터/정렬 -->
<form method="get" action="${pageContext.request.contextPath}/review/list">
    <label>공연명:</label>
    <select name="performanceId">
        <option value="">전체</option>
        <c:forEach var="p" items="${performanceList}">
            <option value="${p.id}" <c:if test="${param.performanceId == p.id}">selected</c:if>>${p.title}</option>
        </c:forEach>
    </select>

    <label>후기 유형:</label>
    <select name="reviewType">
        <option value="">전체</option>
        <option value="전" <c:if test="${param.reviewType == '전'}">selected</c:if>>공연 전</option>
        <option value="후" <c:if test="${param.reviewType == '후'}">selected</c:if>>공연 후</option>
    </select>

    <label>정렬:</label>
    <select name="sort">
        <option value="latest" <c:if test="${param.sort == 'latest'}">selected</c:if>>최신순</option>
        <option value="likes" <c:if test="${param.sort == 'likes'}">selected</c:if>>추천순</option>
    </select>
    &nbsp;<button type="submit">필터 적용</button>
</form>
<hr>

<!-- 후기 목록 -->
<table border="1" cellpadding="8" cellspacing="0" width="100%">
    <thead>
        <tr>
            <th colspan="3" style="text-align: left;">공연 후기 목록</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="review" items="${reviewList}">
            <tr>
                <td colspan="3">
                    <b>[ 공연 ${review.reviewType} ]</b>
                    ${review.performanceTitle} ｜ ${review.memberId} ｜ ${review.createdAt}
                    
                    <c:if test="${review.isDeleted == 0}">
                        <!-- 좋아요 / 싫어요 -->
                        <form action="${pageContext.request.contextPath}/review/like" method="post" style="display:inline; margin-left: 10px;">
                            <input type="hidden" name="reviewId" value="${review.id}" />
                            <input type="hidden" name="isLike" value="true" />
                            <button type="submit">👍 ${review.likes}</button>
                        </form>
                        <form action="${pageContext.request.contextPath}/review/like" method="post" style="display:inline; margin-left: 5px;">
                            <input type="hidden" name="reviewId" value="${review.id}" />
                            <input type="hidden" name="isLike" value="false" />
                            <button type="submit">👎 ${review.dislikes}</button>
                        </form>

                        <!-- 본인 수정/삭제 -->
                        <c:if test="${loginUser != null and loginUser.id == review.memberId}">
                            <form action="${pageContext.request.contextPath}/review/edit" method="get" style="display:inline; margin-left: 10px;">
                                <input type="hidden" name="id" value="${review.id}" />
                                <button type="submit">수정</button>
                            </form>
                            <form action="${pageContext.request.contextPath}/review/delete" method="get" style="display:inline; margin-left: 5px;">
                                <input type="hidden" name="reviewId" value="${review.id}" />
                                <button type="submit" onclick="return confirm('후기를 삭제하시겠습니까?')">삭제</button>
                            </form>
                        </c:if>

                        <!-- 관리자 삭제 -->
                        <c:if test="${loginUser != null and loginUser.admin}">
                            <form action="${pageContext.request.contextPath}/admin/review/delete" method="post" style="display:inline; margin-left: 10px;">
                                <input type="hidden" name="id" value="${review.id}" />
                                <button type="submit" onclick="return confirm('관리자가 해당 후기를 삭제하시겠습니까?')">삭제</button>
                            </form>
                        </c:if>
                    </c:if>

                    <br>
                    <c:choose>
                        <c:when test="${review.isDeleted == 1}">
                            <br>
                            <i style="color: gray;">(관리자에 의해 삭제된 후기입니다)</i>
                        </c:when>
                        <c:otherwise>
                            <br>
                            ▶ ${fn:replace(review.content, '\\n', '<br/>')}
                        </c:otherwise>
                    </c:choose>

										<br>

                    <!-- 댓글 출력 -->
										<c:forEach var="comment" items="${commentMap[review.id]}">
										    <div style="margin-left: 30px; font-size: 0.9rem; line-height: 1.8;">
										        <c:choose>
										            <c:when test="${comment.isDeleted == 1}">
										                <br>
										                ㄴ <b>${comment.memberId}</b>: <i style="color:gray;">(관리자에 의해 삭제된 댓글입니다)</i>
										                <span style="color:gray;">(${comment.createdAt})</span>
										            </c:when>
										            <c:otherwise>
										            		<br>
										                ㄴ <b>${comment.memberId}</b>: ${comment.content}
										                <span style="color:gray;">(${comment.createdAt})</span>
										
										                &nbsp;&nbsp;
										                <c:if test="${loginUser != null and loginUser.id == comment.memberId}">
										                    <form action="${pageContext.request.contextPath}/comment/edit" method="get" style="display:inline;">
										                        <input type="hidden" name="id" value="${comment.id}" />
										                        <button type="submit" style="font-size: 0.8rem;">수정</button>
										                    </form>
										                    <form action="${pageContext.request.contextPath}/comment/delete" method="post" style="display:inline;">
										                        <input type="hidden" name="id" value="${comment.id}" />
										                        <button type="submit" onclick="return confirm('댓글을 삭제하시겠습니까?')" style="font-size: 0.8rem;">삭제</button>
										                    </form>
										                </c:if>
										
										                <c:if test="${loginUser != null and loginUser.admin}">
										                    <form action="${pageContext.request.contextPath}/admin/comment/delete" method="post" style="display:inline;">
										                        <input type="hidden" name="id" value="${comment.id}" />
										                        <button type="submit" onclick="return confirm('관리자가 댓글을 삭제하시겠습니까?')" style="font-size: 0.8rem;">삭제</button>
										                    </form>
										                </c:if>
										            </c:otherwise>
										        </c:choose>
										    </div>
										</c:forEach>
										
										<br>
										
                    <c:if test="${loginUser != null and review.isDeleted == 0}">
                        <form action="${pageContext.request.contextPath}/comment/write" method="post" style="margin-left: 30px; margin-top: 5px;">
                            <input type="hidden" name="reviewId" value="${review.id}" />
                            <textarea name="content" rows="2" cols="60" placeholder="댓글을 입력하세요" required></textarea><br>
                            <button type="submit">등록</button>
                        </form>
                    </c:if>
                </td>
            </tr>
        </c:forEach>

        <c:if test="${empty reviewList}">
            <tr><td colspan="3">작성된 후기가 없습니다.</td></tr>
        </c:if>
    </tbody>
</table>
