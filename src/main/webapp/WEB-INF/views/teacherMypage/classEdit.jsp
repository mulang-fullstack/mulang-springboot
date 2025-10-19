<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="icon" href="/img/favicon.svg" type="image/png">

    <link rel="stylesheet" href="/css/global.css"/>
    <link rel="stylesheet" href="/css/pages/teacher/classEdit.css"/>

    <title>클래스 관리 | Mulang?</title>
</head>
<body>
<%@include file="../common/header.jsp" %>

<main>
    <div class="contents">
        <section class="dashboard">
            <%@ include file="sidebar.jsp" %>

            <section class="content">
                <!-- 버튼 -->
                <div class="title-wrap">
                    <h2>클래스 관리</h2>
                    <a href="/teacher/mypage/classes/new" class="create-class-btn">
                        <span class="plus">+</span> 클래스 만들기
                    </a>
                </div>

                <!-- 클래스 테이블 -->
                <div class="class-table">
                    <div class="table-header">
                        <span>썸네일</span>
                        <span>클래스명</span>
                        <span>운영방식</span>
                        <span>운영상태</span>
                        <span>등록일</span>
                    </div>

                    <div class="table-body">
                        <div class="table-row">
                            <div class="thumb">
                                <img src="https://placehold.co/160x90" alt="클래스 썸네일">
                            </div>
                            <div class="title">왕초보 기초회화</div>
                            <div class="type">VOD</div>
                            <div class="status"><span class="tag gray">임시 저장</span></div>
                            <div class="date">
                                2025-10-02
                                <div class="menu-wrap">
                                    <button class="menu-btn">⋯</button>
                                    <div class="menu-dropdown">
                                        <button class="menu-item edit-btn" data-id="1">수정</button>
                                        <button class="menu-item delete-btn" data-id="1">삭제</button>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="table-row">
                            <div class="thumb">
                                <img src="https://placehold.co/160x90" alt="클래스 썸네일">
                            </div>
                            <div class="title">미남이 될 수 있는 이유가</div>
                            <div class="type">VOD</div>
                            <div class="status"><span class="tag gray">임시 저장</span></div>
                            <div class="date">
                                2025-10-01
                                <div class="menu-wrap">
                                    <button class="menu-btn">⋯</button>
                                    <div class="menu-dropdown">
                                        <button class="menu-item edit-btn" data-id="${lecture.lectureId}">수정</button>
                                        <button class="menu-item delete-btn" data-id="${lecture.lectureId}">삭제</button>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="table-row">
                            <div class="thumb">
                                <img src="https://placehold.co/160x90" alt="클래스 썸네일">
                            </div>
                            <div class="title">윤서짱짱맨의 기나긴 여행</div>
                            <div class="type">VOD</div>
                            <div class="status"><span class="tag gray">임시 저장</span></div>
                            <div class="date">
                                2025-10-01
                                <div class="menu-wrap">
                                    <button class="menu-btn">⋯</button>
                                    <div class="menu-dropdown">
                                        <button class="menu-item edit-btn" data-id="${lecture.lectureId}">수정</button>
                                        <button class="menu-item delete-btn" data-id="${lecture.lectureId}">삭제</button>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="table-row">
                            <div class="thumb">
                                <img src="https://placehold.co/160x90" alt="클래스 썸네일">
                            </div>
                            <div class="title">윤서의 귀여운척 특강</div>
                            <div class="type">온/오프라인</div>
                            <div class="status"><span class="tag gray">임시 저장</span></div>
                            <div class="date">
                                2025-10-01
                                <div class="menu-wrap">
                                    <button class="menu-btn">⋯</button>
                                    <div class="menu-dropdown">
                                        <button class="menu-item edit-btn" data-id="1">수정</button>
                                        <button class="menu-item delete-btn" data-id="1">삭제</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </section>
    </div>
</main>

<%@include file="../common/footer.jsp" %>
<script src="/js/pages/teacher/classEdit.js"></script>
</body>
</html>
