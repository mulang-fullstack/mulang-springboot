<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="icon" href="/img/favicon.svg" type="image/png">

    <link rel="stylesheet" href="/css/global.css"/>
    <link rel="stylesheet" href="/css/pages/mypage/review/qnawrite.css"/>

    <title>리뷰 작성 | Mulang?</title>
</head>
<body>

<%@include file="../../common/header.jsp" %>

<main>
    <div class="contents">
        <section class="mypage">
            <%@include file="../sidebar.jsp" %>

            <section class="content">
                <h2>리뷰 작성</h2>

                <form action="/student/qnawrite" method="post" class="review-form" id="reviewForm" novalidate>

                    <!-- 강좌 선택 -->
                    <div class="form-field">
                        <label for="courseId">강좌선택</label>
                        <select id="courseId" name="courseId" required>
                            <option value="">강좌를 선택하세요</option>
                            <c:forEach var="course" items="${mycourseDTO}">
                                <option value="${course.courseId}">${course.courseTitle}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <!-- 제목 추가 -->
                    <div class="form-field">
                        <label for="title">제목</label>
                        <input type="text" id="title" name="title"
                               placeholder="제목을 입력하세요"
                               maxlength="100"
                               required>
                    </div>

                    <!-- 내용 -->
                    <div class="form-field">
                        <label for="content">내용</label>
                        <textarea id="content" name="content" rows="8" placeholder="질문하실 내용을 작성해주세요." required></textarea>
                    </div>

                    <!-- 버튼 -->
                    <div class="form-buttons">
                        <button type="submit" class="submit-btn">등록하기</button>
                        <a href="/student/qna" class="cancel-btn">취소</a>
                    </div>
                </form>
            </section>
        </section>
    </div>
</main>

<%@include file="../../common/footer.jsp" %>

<script src="/js/pages/mypage/qnawrite.js"></script>

</body>
</html>
