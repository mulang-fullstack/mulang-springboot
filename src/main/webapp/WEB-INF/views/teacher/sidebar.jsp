<%@ page contentType="text/html;charset=utf-8" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="/css/pages/teacher/sidebar.css">
</head>
<body>

<aside class="sidebar">
    <div class="profile">
        <div class="profile-img-wrap">
            <img src="${teacher.photoUrl != null ? teacher.photoUrl : '/img/dummy/default-profile.jpg'}"
                 alt="프로필 이미지">
        </div>
        <div class="profile-info">
            <span class="nickname">${teacher.nickname}</span>
        </div>
    </div>

    <ul class="menu">
        <li><a href="/teacher/mypage/profile"><img src="/img/icon/profile.png" alt="">프로필</a></li>
        <li><a href="/teacher/mypage/classes/edit"><img src="/img/icon/classEdit.png" alt="">클래스관리</a></li>
        <li><a href="/teacher/mypage/classes/new"><img src="/img/icon/classUpload.png" alt="">클래스생성</a></li>
        <li><a href="/teacher/mypage/settlement"><img src="/img/icon/money.png" alt="">판매현황</a></li>
    </ul>
</aside>
<script src="/js/pages/teacher/common/sidebarLock.js"></script>
</body>
</html>
