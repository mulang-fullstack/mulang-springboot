<%@ page contentType="text/html;charset=utf-8" %>

<link rel="stylesheet" href="/css/pages/admin/adminSidebar.css"/>
<script src="/js/pages/admin/dashboard/adminSidebar.js"></script>
<script src="/js/pages/admin/dashboard/activeMenu.js"></script>
<aside>
    <div class="logo-area">
        <img src="/img/logo-white.png" alt="어램 로고" class="logo">
    </div>

    <nav class="menu">
        <ul>
            <li class="menu-section" data-menu="dashboard">
                <div><img src="/img/icon/dashboard.png"> 대시보드</div>
                <ul class="submenu">
                    <li><a href="/admin/visitor" data-submenu="visitor">방문자 현황</a></li>
                    <li><a href="/admin/sales" data-submenu="sales">매출 현황</a></li>
                </ul>
            </li>

            <li class="menu-section" data-menu="userManage">
                <div><img src="/img/icon/admin-user.png"> 사용자 관리</div>
                <ul class="submenu">
                    <li><a href="#">회원 관리</a></li>
                    <li><a href="#">강사 관리</a></li>
                    <li><a href="#">사용자 로그 관리</a></li>
                </ul>
            </li>

            <li class="menu-section">
                <div><img src="/img/icon/calendar-edit.png"> 콘텐츠 관리</div>
                <ul class="submenu">
                    <li><a href="#">강좌 관리</a></li>
                    <li><a href="#">리뷰 관리</a></li>
                    <li><a href="#">Q&A 관리</a></li>
                </ul>
            </li>

            <li class="menu-section">
                <div><img src="/img/icon/admin-card.png"> 결제 관리</div>
                <ul class="submenu">
                    <li><a href="#">결제 관리</a></li>
                    <li><a href="#">환불 관리</a></li>
                </ul>
            </li>

            <li class="menu-section">
                <div><img src="/img/icon/admin-setting.png"> 시스템 관리</div>
                <ul class="submenu">
                    <li><a href="#">공지사항 관리</a></li>
                    <li><a href="#">1대1 문의</a></li>
                    <li><a href="#">관리자 리스트</a></li>
                    <li><a href="#">관리자 로그 관리</a></li>
                </ul>
            </li>
        </ul>
    </nav>
</aside>
