<%@ page contentType="text/html;charset=utf-8" %>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/swiper@11/swiper-bundle.min.css"/>
<link rel="stylesheet" href="/css/common/banner.css"/>
<section class="hero-slider swiper">
    <div class="swiper-wrapper">

        <div class="swiper-slide">
            <a href="#">
                <img src="/img/banner/banner-chinese.png" alt="중국어 배너">
            </a>
        </div>

        <div class="swiper-slide">
            <a href="#">
                <img src="/img/banner/banner-english.png" alt="영어 배너">
            </a>
        </div>

<%--        <div class="swiper-slide">--%>
<%--            <a href="#">--%>
<%--                <img src="/img/banner/banner-english.png" alt="일본어 배너">--%>
<%--            </a>--%>
<%--        </div>--%>
    </div>

    <!-- 네비게이션 버튼 -->
    <div class="swiper-button-next"></div>
    <div class="swiper-button-prev"></div>

    <!-- 페이지네이션 점 -->
    <div class="swiper-pagination"></div>
</section>
<script src="https://cdn.jsdelivr.net/npm/swiper@11/swiper-bundle.min.js"></script>
<script>
    new Swiper('.hero-slider', {
        loop: true,
        autoplay: { delay: 3500, disableOnInteraction: false },
        pagination: { el: '.swiper-pagination', clickable: true },
        navigation: {
            nextEl: '.swiper-button-next',
            prevEl: '.swiper-button-prev'
        }
    });
</script>