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
    <link rel="stylesheet" href="css/pages/course/course-list.css"/>
    <title>Mulang?</title>
</head>
<body>
<%@include file="../common/header.jsp" %>
<main class="main">
    <div class="contents">
        <div class="course-list-contents">
            <section class="course-category">
                <h1>영어</h1>
                <div class="course-tabs">
                    <div class="tab">기초</div>
                    <div class="tab">문법</div>
                    <div class="tab">어휘</div>
                    <div class="tab">독해</div>
                    <div class="tab">비즈니스</div>
                    <div class="tab">CNN</div>
                </div>
            </section>

            <section class="course-sort">
                <div class="search-box">
                    <!-- 검색박스 내부 요소들 -->
                    <div class="search-input">
                        <input type="text"><button><img src="/img/icon/search.svg" alt="검색 아이콘"></button>
                    </div>
                    <div class="search-icon"></div>
                </div>
                <div class="sort-options">
                    <a href="#"><div class="sort-item active">별점순</div></a>
                    <a href="#"><div class="sort-item">리뷰순</div></a>
                    <a href="#"><div class="sort-item">최신순</div></a>
                </div>
            </section>

            <section class="course-list">
                <div class="course-card">
                    <a href="courseDetail">
                    <img src="https://placehold.co/250x180" alt="course"></a>
                    <div class="course-list-info">
                        <h2><a href="courseDetail">왕초보 기초회화</a></h2>
                        <p class="subtitle">틀에 박힌 한국식 영어는 이제 그만! - 찐 원어민 기초 회화!</p>
                        <p class="teacher">Sean Pablo</p>
                        <div class="rating">
                            <span class="score">4.7</span>
                            <span class="stars">
                                <img src="/img/icon/star-full.svg" alt="별">
                                <img src="/img/icon/star-full.svg" alt="별">
                                <img src="/img/icon/star-full.svg" alt="별">
                                <img src="/img/icon/star-full.svg" alt="별">
                                <img src="/img/icon/star-half.svg" alt="별 반개">
                            </span>
                            <span class="review-count">(1000)</span>
                        </div>
                    </div>
                    <div class="heart-purchase-wrap">
                        <button class="heart-icon"><img src="/img/icon/heart-full.svg" alt="찜 아이콘"></button>
                        <div class="course-purchase">
                            <span class="price">155,000원</span>
                            <button class="purchase-btn">결제하기</button>
                        </div>
                    </div>
                </div>

                <div class="course-card">
                    <a href="courseDetail">
                        <img src="https://placehold.co/250x180" alt="course"></a>
                    <div class="course-list-info">
                        <h2>하루 딱 10분! 신박한 영문법 토크쇼</h2>
                        <p class="subtitle">워라벨 시대에 적합한 짧고 굵은 강의!</p>
                        <p class="teacher">Sean Pablo</p>
                        <div class="rating">
                            <span class="score">4.0</span>
                            <span class="stars">
                                <img src="/img/icon/star-full.svg" alt="별">
                                <img src="/img/icon/star-full.svg" alt="별">
                                <img src="/img/icon/star-full.svg" alt="별">
                                <img src="/img/icon/star-full.svg" alt="별">
                                <img src="/img/icon/star-empty.svg" alt="별 반개">
                            </span>
                            <span class="review-count">(1000)</span>
                        </div>
                    </div>
                    <div class="heart-purchase-wrap">
                        <div class="heart-icon">
                            <img src="/img/icon/heart-empty.svg" alt="찜 아이콘">
                        </div>
                        <div class="course-purchase">
                            <span class="price">155,000원</span>
                            <button class="purchase-btn">결제하기</button>
                        </div>
                    </div>
                </div>
                <div class="course-card">
                    <a href="courseDetail">
                        <img src="https://placehold.co/250x180" alt="course"></a>
                    <div class="course-list-info">
                        <h2>Basic Grammar in Use</h2>
                        <p class="subtitle">영어의 기본기를 잡아주는 쉬운 문법강의! (이론 강의)</p>
                        <p class="teacher">Sean Pablo</p>
                        <div class="rating">
                            <span class="score">4.8</span>
                            <span class="stars">
                                <img src="/img/icon/star-full.svg" alt="별">
                                <img src="/img/icon/star-full.svg" alt="별">
                                <img src="/img/icon/star-full.svg" alt="별">
                                <img src="/img/icon/star-full.svg" alt="별">
                                <img src="/img/icon/star-full.svg" alt="별">
                            </span>
                            <span class="review-count">(1000)</span>
                        </div>
                    </div>
                    <div class="heart-purchase-wrap">
                        <div class="heart-icon">
                            <img src="/img/icon/heart-empty.svg" alt="찜 아이콘">
                        </div>
                        <div class="course-purchase">
                            <span class="price">120,000원</span>
                            <button class="purchase-btn">결제하기</button>
                        </div>
                    </div>
                </div>

                <div class="course-card">
                    <a href="courseDetail">
                        <img src="https://placehold.co/250x180" alt="course"></a>
                    <div class="course-list-info">
                        <h2>Business Negotiation</h2>
                        <p class="subtitle">한권으로 끝내는 협상 테이블의 바이블 강의</p>
                        <p class="teacher">Sean Pablo</p>
                        <div class="rating">
                            <span class="score">4.8</span>
                            <span class="stars">
                                <img src="/img/icon/star-full.svg" alt="별">
                                <img src="/img/icon/star-full.svg" alt="별">
                                <img src="/img/icon/star-full.svg" alt="별">
                                <img src="/img/icon/star-full.svg" alt="별">
                                <img src="/img/icon/star-full.svg" alt="별">
                            </span>
                            <span class="review-count">(1000)</span>
                        </div>
                    </div>
                    <div class="heart-purchase-wrap">
                        <div class="heart-icon">
                            <img src="/img/icon/heart-empty.svg" alt="찜 아이콘">
                        </div>
                        <div class="course-purchase">
                            <span class="price">80,000원</span>
                            <button class="purchase-btn">결제하기</button>
                        </div>
                    </div>
                </div>
            </section>

            <!-- 페이지네이션 -->
            <section class="pagination">
                <button class="prev"><img src="/img/icon/page-left.svg" alt="왼쪽 아이콘"></button>
                <span class="page-numbers">
                    <span class="current">1</span><span>2</span><span>3</span><span>4</span><span>5</span>
                </span>
                <button class="next"><img src="/img/icon/page-right.svg" alt="오른쪽 아이콘"></button>
            </section>
        </div>
    </div>
</main>
<%@include file="../common/footer.jsp" %>
</body>
</html>