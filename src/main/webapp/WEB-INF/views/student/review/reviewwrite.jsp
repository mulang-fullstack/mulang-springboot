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
    <link rel="stylesheet" href="/css/pages/mypage/review/reviewwrite.css"/>

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

                <form action="/student/reviewwrite" method="post" class="review-form">

                    <!-- 강좌 선택 -->
                    <div class="form-field">
                        <label for="title">강좌선택</label>
                        <select id="title" name="courseId" required>
                            <option value="">강좌를 선택하세요</option>
                            <c:forEach var="course" items="${mycourseDTO}">
                                <option value="${course.courseId}">${course.courseTitle}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <!-- 별점 -->
                    <div class="form-field">
                        <div class="rating">
                            <input type="radio" id="star-5" name="rating" value="5" required>
                            <label for="star-5">
                                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24"><path pathLength="360" d="M12,17.27L18.18,21L16.54,13.97L22,9.24L14.81,8.62L12,2L9.19,8.62L2,9.24L7.45,13.97L5.82,21L12,17.27Z"></path></svg>
                            </label>
                            <input type="radio" id="star-4" name="rating" value="4">
                            <label for="star-4">
                                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24"><path pathLength="360" d="M12,17.27L18.18,21L16.54,13.97L22,9.24L14.81,8.62L12,2L9.19,8.62L2,9.24L7.45,13.97L5.82,21L12,17.27Z"></path></svg>
                            </label>
                            <input type="radio" id="star-3" name="rating" value="3">
                            <label for="star-3">
                                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24"><path pathLength="360" d="M12,17.27L18.18,21L16.54,13.97L22,9.24L14.81,8.62L12,2L9.19,8.62L2,9.24L7.45,13.97L5.82,21L12,17.27Z"></path></svg>
                            </label>
                            <input type="radio" id="star-2" name="rating" value="2">
                            <label for="star-2">
                                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24"><path pathLength="360" d="M12,17.27L18.18,21L16.54,13.97L22,9.24L14.81,8.62L12,2L9.19,8.62L2,9.24L7.45,13.97L5.82,21L12,17.27Z"></path></svg>
                            </label>
                            <input type="radio" id="star-1" name="rating" value="1">
                            <label for="star-1">
                                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24"><path pathLength="360" d="M12,17.27L18.18,21L16.54,13.97L22,9.24L14.81,8.62L12,2L9.19,8.62L2,9.24L7.45,13.97L5.82,21L12,17.27Z"></path></svg>
                            </label>
                        </div>
                    </div>

                    <!-- 내용 -->
                    <div class="form-field">
                        <label for="content">내용</label>
                        <textarea id="content" name="content" rows="8" placeholder="수강 후기를 작성해주세요." required></textarea>
                    </div>

                    <!-- 버튼 -->
                    <div class="form-buttons">
                        <button type="submit" class="submit-btn">등록하기</button>
                        <a href="/student/review" class="cancel-btn">취소</a>
                    </div>
                </form>
            </section>
        </section>
    </div>
</main>

<%@include file="../../common/footer.jsp" %>
</body>
</html>
