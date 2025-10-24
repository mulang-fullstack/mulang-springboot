<%@ page contentType="text/html;charset=utf-8" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="icon" href="img/favicon.svg" type="image/png">
    <link rel="stylesheet" href="/css/global.css"/>
    <link rel="stylesheet" href="/css/pages/course/course-detail.css"/>
    <script src="/js/pages/course/courseDetailTab.js"></script>
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
            <h2>리뷰</h2>

            <!-- 정렬 버튼 -->
            <div class="review-sort">
                <button id="sort-rating" class="active">별점순</button>
                <button id="sort-latest">최신순</button>
            </div>

            <!-- 리뷰 컨테이너 -->
            <div id="review-container"></div>

            <!-- 페이지네이션 -->
            <section class="pagination">
                <button class="prev"><img src="/img/icon/page-left.svg" alt="왼쪽 아이콘"></button>
                <span id="page-numbers"></span>
                <button class="next"><img src="/img/icon/page-right.svg" alt="오른쪽 아이콘"></button>
            </section>
        </div>

        <script>
            console.log("detail.id:", "${detail.id}");
            // ✅ courseId 안전하게 가져오기
            // 문자열로 감싸서 스프링 Long 변환 가능
            const courseId = "${detail.id}";

            let currentPage = 0;
            let sort = 'rating'; // 기본 정렬: 별점순

            // 리뷰 로딩
            function loadReviews(page = 0) {
                currentPage = page;

                fetch(`/courseDetail/reviews?courseId=${courseId}&page=${page}&size=2&sort=${sort}`)
                    .then(res => res.json())
                    .then(data => {
                        const container = document.getElementById('review-container');
                        container.innerHTML = '';

                        const reviews = data.content;

                        if (reviews.length === 0) {
                            container.innerHTML = '<p>등록된 리뷰가 없습니다.</p>';
                            return;
                        }

                        reviews.forEach(review => {
                            const starsHtml = [1,2,3,4,5].map(i =>
                                `<img src="/img/icon/star-${i <= review.rating ? 'full' : 'empty'}.svg" alt="별">`
                            ).join('');

                            const item = document.createElement('div');
                            item.classList.add('review-item');
                            item.innerHTML = `
                        <img src="/img/icon/review-mulang.svg" alt="머랭 캐릭터" class="review-profile-img">
                        <div class="review-profile-border"></div>
                        <div class="review-name">${review.studentName}</div>
                        <div class="rating">
                            <span class="stars">${starsHtml}</span>
                            <span class="review-score-text">${review.rating}</span>
                        </div>
                        <div class="review-content-wrapper">
                            <div class="review-content">${review.content}</div>
                        </div>
                    `;
                            container.appendChild(item);
                        });

                        renderPagination(data.totalPages, page);
                    });
            }

            // 페이지네이션 렌더링
            function renderPagination(totalPages, current) {
                const pageNumbers = document.getElementById('page-numbers');
                pageNumbers.innerHTML = '';
                for (let i = 0; i < totalPages; i++) {
                    const span = document.createElement('span');
                    span.textContent = i + 1;
                    span.classList.toggle('current', i === current);
                    span.addEventListener('click', () => loadReviews(i));
                    pageNumbers.appendChild(span);
                }
            }

            // prev / next 버튼
            document.querySelector('.prev').addEventListener('click', () => {
                if (currentPage > 0) loadReviews(currentPage - 1);
            });
            document.querySelector('.next').addEventListener('click', () => {
                loadReviews(currentPage + 1);
            });

            // 정렬 버튼
            document.getElementById('sort-rating').addEventListener('click', () => {
                sort = 'rating';
                document.getElementById('sort-rating').classList.add('active');
                document.getElementById('sort-latest').classList.remove('active');
                loadReviews(0);
            });
            document.getElementById('sort-latest').addEventListener('click', () => {
                sort = 'latest';
                document.getElementById('sort-latest').classList.add('active');
                document.getElementById('sort-rating').classList.remove('active');
                loadReviews(0);
            });

            // 초기 로드
            loadReviews(0);
        </script>
    </div>
</main>
<%@include file="../common/footer.jsp" %>
</body>
</html>
