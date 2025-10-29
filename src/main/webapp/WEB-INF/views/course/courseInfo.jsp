<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<link rel="stylesheet" href="/css/pages/course/course-detail-info.css"/>
<link rel="stylesheet" href="/css/pages/teacher/profile.css"/>
<link rel="stylesheet" href="/css/pages/course/course-detail-teacher-profile.css"/>
<div class="course-detail">
    <div class="course-name">
        <h1>${detail.title}</h1>
    </div>
    <div class="course-summary">
        <img src="${detail.thumbnail}" alt="강의 썸네일" class="course-thumb" />
        <!--<img src="https://placehold.co/347x250" alt="강의 썸네일" class="course-thumb" />-->
        <div class="course-info">
            <p class="course-desc">${detail.subtitle}</p>
            <div class="course-meta">
                <div><span class="label">강사</span> <span>${detail.teacherName}</span>
                <!--
                <a href="/courseDetail/teacherProfile?id=${detail.id}" target="_blank">
                    <img src="/img/icon/user-circle.svg" alt="강사 정보">
                </a>-->
                <button id="teacherBtn" data-course-id="${detail.id}">
                    <img src="/img/icon/user-circle.svg" alt="강사 정보">
                </button>
                </div>
                <div id="teacherModal" class="modal" style="display: none;">
                    <div class="modal-content">
                        <span class="close">&times;</span>
                        <div id="modalBody">
                            <!-- teacherProfile 내용-->
                        </div>
                    </div>
                </div>
                <div><span class="label">강의 수</span> <span>${detail.lectureCount}강</span></div>
                <div class="detailRating">
                    <span class="score">${detail.averageRating}</span>&nbsp&nbsp&nbsp
                    <span class="stars">
                        <c:forEach begin="1" end="5" var="i">
                            <img src="/img/icon/star-${i <= detail.averageRating ? 'full' : (i - 0.5 <= detail.averageRating ? 'half' : 'empty')}.svg" alt="별">
                        </c:forEach>
                    </span>
                    <span class="review-count">(${detail.reviewCount})</span>
                </div>
                <!--<div><span class="label">평균별점</span>${detail.averageRating}점</div>
                <div><span class="label">리뷰 수</span>${detail.reviewCount}개</div>-->
            </div>
        </div>
            <div class="course-purchase">
                <div class="heart-icon" data-course-id="${detail.id}">
                    <img src="${detail.favorited ? '/img/icon/heart-full.svg' : '/img/icon/heart-empty.svg'}" alt="찜 아이콘">
                </div>
                <span class="price"><fmt:formatNumber value="${detail.price}" type="number" groupingUsed="true"/>원</span>
                <a href="/payments/${detail.id}"><button class="purchase-btn">결제하기</button></a>
            </div>
    </div>
</div>
<script src="/js/pages/course/teacher.profile/openTeacherProfile.js"></script>
<script src="/js/pages/course/courseFavorite.js"></script>
