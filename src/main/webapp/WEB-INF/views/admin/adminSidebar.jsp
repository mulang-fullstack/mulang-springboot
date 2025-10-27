<%@ page contentType="text/html;charset=utf-8" %>

<link rel="stylesheet" href="/css/pages/admin/adminSidebar.css"/>
<script src="/js/pages/admin/dashboard/activeMenu.js"></script>
<aside  data-active-menu="${activeMenu}" data-active-submenu="${activeSubmenu}">
    <div class="logo-area">
        <img src="/img/logo-white.png" alt="어램 로고" class="logo">
    </div>

    <nav class="menu">
        <ul>
            <li class="menu-section" data-menu="dashboard">
                <div><img src="/img/icon/dashboard.png"> 대시보드</div>
                <ul class="submenu">
                    <li><a href="/admin/dashboard/visitor" data-submenu="visitor">방문자 현황</a></li>
                    <li><a href="/admin/dashboard/sales" data-submenu="sales">매출 현황</a></li>
                </ul>
            </li>

            <li class="menu-section" data-menu="user">
                <div><img src="/img/icon/admin-user.png"> 사용자 관리</div>
                <ul class="submenu">
                    <li><a href="/admin/user" data-submenu="user">회원 조회</a></li>
                    <li><a href="/admin/user/log" data-submenu="userLog">사용자 로그</a></li>
                </ul>
            </li>

            <li class="menu-section" data-menu="content">
                <div><img src="/img/icon/calendar-edit.png"> 콘텐츠 관리</div>
                <ul class="submenu">
                    <li><a href="/admin/content/course" data-submenu="course">강좌 조회</a></li>
                    <li><a href="/admin/content/pendingCourse" data-submenu="pendingCourse">강좌 신청</a></li>
                </ul>
            </li>

            <li class="menu-section" data-menu="payment">
                <div><img src="/img/icon/admin-card.png"> 결제 관리</div>
                <ul class="submenu">
                    <li><a href="/admin/payment" data-submenu="payment">결제 내역</a></li>
                    <li><a href="/admin/payment/refund" data-submenu="refund">환불 신청</a></li>
                </ul>
            </li>

            <li class="menu-section" data-menu="system">
                <div><img src="/img/icon/admin-setting.png"> 시스템 관리</div>
                <ul class="submenu">
                    <li><a href="/admin/system/notice" data-submenu="notice">공지사항</a></li>
                    <li><a href="/admin/system/inquiry" data-submenu="inquiry">1대1 문의</a></li>
                </ul>
            </li>
        </ul>
    </nav>
</aside>
