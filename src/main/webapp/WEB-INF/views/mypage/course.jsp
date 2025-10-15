
<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<meta charset="UTF-8">
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="icon" href="img/favicon.svg" type="image/png">

    <link rel="stylesheet" href="/css/global.css"/>
    <link rel="stylesheet" href="/css/pages/mypage/course.css"/>
    <title>나의 학습방</title>
</head>
<body>

<%@include file="../common/header.jsp" %>

<main class="main">
    <section class="dashboard">

        <%@include file="sidebar.jsp" %>

        <section class="content">
            <h2>나의 학습방</h2>
            <div class="class-table">
                <div class="table-header">
                    <span>담당강사</span>
                    <span>제목</span>
                    <span>총 강의 수</span>
                    <span>진행률</span>
                    <span>수강기간</span>
                </div>

                <div class="table-body">
                    <div class="table-row">
                        <div class="thumb"><img src="https://placehold.co/160x90" alt=""></div>
                        <div class="title">toeic</div>
                        <div class="type">35</div>
                        <div class="status"><span class="tag gray">70</span></div>
                        <div class="date">2025-10-02 ~ 2025-10-30</div>
                    </div>

                    <div class="table-row">
                        <div class="thumb"><img src="https://placehold.co/160x90" alt=""></div>
                        <div class="title">미남이 될 수 있는 이유가</div>
                        <div class="type">VOD</div>
                        <div class="status"><span class="tag gray">임시 저장</span></div>
                        <div class="date">2025-10-01</div>
                    </div>

                    <div class="table-row">
                        <div class="thumb"><img src="https://placehold.co/160x90" alt=""></div>
                        <div class="title">윤서짱짱맨의 기나긴 여행</div>
                        <div class="type">VOD</div>
                        <div class="status"><span class="tag gray">임시 저장</span></div>
                        <div class="date">2025-10-01</div>
                    </div>

                    <div class="table-row">
                        <div class="thumb"><img src="https://placehold.co/160x90" alt=""></div>
                        <div class="title">윤서의 귀여운척 특강</div>
                        <div class="type">온/오프라인</div>
                        <div class="status"><span class="tag gray">임시 저장</span></div>
                        <div class="date">2025-10-01</div>
                    </div>
                </div>
            </div>
        </section>
    </section>
</main>

<%@include file="../common/footer.jsp" %>

</body>
</html>
