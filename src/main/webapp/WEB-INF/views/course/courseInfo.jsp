<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
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
                <!--
                <div><span class="label">신청기간</span> <span></span></div>
                <div><span class="label">수강기간</span> <span></span></div>
                -->
                <div><span class="label">강의 수</span> <span>${detail.lectureCount}강</span></div>
            </div>
            <div class="course-purchase">
                <button class="heart-icon"><img src="/img/icon/heart-full.svg" alt="찜 아이콘"></button>
                <span class="price"><fmt:formatNumber value="${detail.price}" type="number" groupingUsed="true"/>원</span>
                <form action="/" method="get">
                    <input type="hidden" name="courseId" value="${detail.id}">
                    <a href="/payment/${detail.id}"><button class="purchase-btn">결제하기</button></a>
                </form>
            </div>
        </div>
    </div>
</div>
<script src="/js/pages/course/teacher.profile/openTeacherProfile.js"></script>
