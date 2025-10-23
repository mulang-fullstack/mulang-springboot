<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!doctype html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="icon" href="/img/favicon.svg" type="image/png">

    <link rel="stylesheet" href="/css/global.css"/>
    <link rel="stylesheet" href="/css/pages/teacher/class/classUpload.css"/>
    <title>VOD 클래스 업로드 | Mulang?</title>
</head>
<body>
<%@ include file="../common/header.jsp" %>

<main>
    <div class="contents">
        <section class="dashboard">
            <%@ include file="sidebar.jsp" %>

            <section class="content">
                <div class="class-upload">
                    <h2>CLASS</h2>

                    <form action="/teacher/mypage/classes/update"
                          method="post"
                          enctype="multipart/form-data">

                        <input type="hidden" name="id" value="${course.id}"/>

                        <div class="field">
                            <label>클래스 이름</label>
                            <div class="field-content">
                                <input type="text" name="title" value="${course.title}" required>
                            </div>
                        </div>

                        <div class="field">
                            <label>클래스 부제목</label>
                            <div class="field-content">
                                <input type="text" name="subtitle" value="${course.subtitle}" required>
                            </div>
                        </div>

                        <div class="field">
                            <label>강의 소개</label>
                            <div class="field-content">
                                <textarea name="content" required>${course.content}</textarea>
                            </div>
                        </div>

                        <div class="field">
                            <label>수강비용</label>
                            <div class="field-content">
                                <input type="number" name="price" value="${course.price}" min="0" required>
                            </div>
                        </div>

                        <div class="field">
                            <label>썸네일 이미지</label>
                            <div class="field-content">
                                <input type="file" name="thumbnailFile" accept="image/png,image/jpeg,image/webp">
                                <c:if test="${not empty course.thumbnail}">
                                    <p>현재 이미지:</p>
                                    <img src="${course.thumbnail}" width="120">
                                </c:if>
                            </div>
                        </div>

                        <div class="submit-wrap">
                            <button type="submit" class="submit-btn">수정하기</button>
                        </div>
                    </form>

                </div>
            </section>
        </section>
    </div>
</main>

<%@ include file="../common/footer.jsp" %>
<script src="https://cdn.ckeditor.com/ckeditor5/43.0.0/classic/ckeditor.js"></script>
<script src="/js/pages/teacher/ckeditor5.js"></script>
<script src="/js/pages/teacher/classVideo.js"></script>
</body>
</html>
