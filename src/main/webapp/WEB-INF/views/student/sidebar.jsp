<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="ko">
<meta charset="UTF-8">
<link rel="stylesheet" href="/css/pages/mypage/sidebar.css">
<aside class="sidebar">
    <div class="profile">
        <div class="profile-img-wrap">
            <img src="${not empty user.photoUrl ? user.photoUrl : '/img/dummy/profile.jpg'}"
                 alt="프로필 이미지">
        </div>
        <h3 class="nickname">${user.nickname}</h3>
    </div>

    <ul class="menu">
        <li class="active"><a href="${pageContext.request.contextPath}/student/personal">프로필</a></li>
        <li><a href="${pageContext.request.contextPath}/student/save">찜</a></li>
        <li><a href="${pageContext.request.contextPath}/student/course">나의 학습방</a></li>
        <li><a href="${pageContext.request.contextPath}/student/pay">결제내역</a></li>
        <li><a href="${pageContext.request.contextPath}/student/review">리뷰</a></li>
    </ul>
</aside>
