<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="icon" href="img/favicon.svg" type="image/png">
    <link rel="stylesheet" href="css/global.css"/>
    <link rel="stylesheet" href="css/pages/course/course-detail.css"/>
    <title>Mulang?</title>
</head>
<body>
<%@include file="../common/header.jsp" %>
<main>
    <div class="contents">
        <div class="leture-detail-contents">
            <%@include file="courseInfo.jsp" %>
            <div class="course-tabs">
                <div class="tab" data-target="introduction">강의소개</div>
                <div class="tab" data-target="curriculum">커리큘럼</div>
                <div class="tab" data-target="review">리뷰</div>
            </div>
        </div>
        <div id="introduction">
            <%@include file="courseIntroduction.jsp" %>
        </div>
        <div id="curriculum">
            <%@include file="courseCurriculum.jsp" %>
        </div>
        <div id="review">
            <%@include file="courseReview.jsp" %>
        </div>
    </div>
</main>
<%@include file="../common/footer.jsp" %>
</body>
</html>
<script>
    document.addEventListener('DOMContentLoaded', () => {
        const tabs = document.querySelectorAll('.tab');
        const sections = document.querySelectorAll('#introduction, #curriculum, #review');
        const header = document.querySelector('header');
        const stickyDiv = document.querySelector('.leture-detail-contents');
        const headerHeight = header?.offsetHeight || 0;

        // 탭 클릭 시 스크롤 이동
        tabs.forEach(tab => {
            tab.addEventListener('click', () => {
                const targetId = tab.dataset.target;
                const target = document.getElementById(targetId);
                if (!target) return;

                // sticky div가 이미 상단에 붙어있다면 높이만큼 offset 추가
               // const stickyOffset = (window.scrollY >= stickyDiv.offsetTop) ? stickyDiv.offsetHeight : 0;

                const targetTop = target.offsetTop - headerHeight - stickyDiv.offsetHeight + 20;

                window.scrollTo({ top: targetTop, behavior: 'smooth' });
            });
        });

        // 스크롤 시 active 탭 업데이트
        window.addEventListener('scroll', () => {
            let current = '';
            sections.forEach(section => {
                const sectionTop = section.offsetTop - headerHeight - stickyDiv.offsetHeight - 0; // 여유 마진
                if (window.scrollY >= sectionTop) {
                    current = section.getAttribute('id');
                }
            });

            tabs.forEach(tab => {
                tab.classList.remove('active');
                if (tab.dataset.target === current) {
                    tab.classList.add('active');
                }
            });
        });
    });

</script>