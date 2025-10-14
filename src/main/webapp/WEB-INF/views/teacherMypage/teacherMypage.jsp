
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
    <link rel="stylesheet" href="css/global.css"/>
    <link rel="stylesheet" href="css/pages/teacher/mypage.css"/>
    <title>Mulang?</title>
</head>
<body>

<%@include file="../common/header.jsp" %>

<main class="main">
    <section class="dashboard">

        <%@include file="sidebar.jsp" %>

        <section class="content">
            <h2>클래스 관리</h2>
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
                        <div class="thumb"><img src="https://placehold.co/160x90" alt=""></div>
                        <div class="title">나는 개똥벌레 친구가 없네</div>
                        <div class="type">VOD</div>
                        <div class="status"><span class="tag gray">임시 저장</span></div>
                        <div class="date">2025-10-02</div>
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
