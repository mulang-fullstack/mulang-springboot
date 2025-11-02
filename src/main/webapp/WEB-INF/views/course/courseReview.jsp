<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<div class="course-riview">
    <div class="review-sort">
        <a href="/courseDetail?id=${courseId}&sortBy=rating&page=${param.sortBy != 'rating' ? 0 : currentPage}">
            <div class="sort-item ${sortBy == 'rating'? 'active':''}">별점순</div>
        </a>
        <a href="/courseDetail?id=${courseId}&sortBy=createdAt&page=${param.sortBy != 'createdAt' ? 0 : currentPage}">
            <div class="sort-item ${sortBy == 'createdAt'? 'active':''}">최신순</div>
        </a>
    </div>
    <div class="review-list">
        <c:forEach var="review" items="${review}">
            <div class="review-item">
            <img src="/img/icon/review-mulang.svg" alt="머랭 캐릭터" class="review-profile-img">
            <div class="review-profile-border"></div>
                <div class="review-name">${review.studentName}</div>
                <div class="rating">
                <span class="stars">
                    <c:forEach begin="1" end="5" var="i">
                        <img src="/img/icon/star-${i <= review.rating ? 'full' : 'empty'}.svg" alt="별">
                    </c:forEach>
                </span>
                    <span class="review-score-text">${review.rating}</span>
                </div>
                <div class="review-content-wrapper">
                    <div class="review-content long">${review.content}</div>
                    <div class="review-more">더보기</div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
<!-- 페이지네이션 -->
<section class="pagination">
    <c:if test="${currentPage > 0}">
        <a href="/courseDetail?id=${courseId}&page=${V-1}&sortBy=${sortBy}">
            <button class="prev"><img src="/img/icon/page-left.svg" alt="왼쪽 아이콘"></button>
        </a>
    </c:if>
    <span class="page-numbers">
                    <c:if test="${totalPages > 0}">
                        <c:forEach begin="0" end="${totalPages-1}" var="i">
                            <a href="/courseDetail?id=${courseId}&page=${i}&sortBy=${sortBy}">
                                <span class="${i == currentPage ? 'current' : ''}">${i+1}</span>
                            </a>
                        </c:forEach>
                    </c:if>
                </span>
    <c:if test="${currentPage < totalPages-1}">
        <a href="/courseDetail?id=${courseId}&page=${currentPage+1}&sortBy=${sortBy}">
            <button class="next"><img src="/img/icon/page-right.svg" alt="오른쪽 아이콘"></button>
        </a>
    </c:if>
</section>