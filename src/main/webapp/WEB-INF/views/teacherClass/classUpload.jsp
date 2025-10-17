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
            <%@ include file="../teacherMypage/sidebar.jsp" %>
            <section class="content">
                <div class="class-upload">
                    <h2>CLASS</h2>

                    <form action="/teacherClass/courseUpload" method="post" enctype="multipart/form-data">
                        <!-- 클래스 이름 -->
                        <div class="field">
                            <label>클래스 이름</label>
                            <div class="field-content">
                                <input type="text" name="title" placeholder="클래스 이름을 입력하세요" required>
                            </div>
                        </div>
                        <!-- 언어 선택 -->
                        <div class="field">
                            <label>언어</label>
                            <div class="field-content">
                                <select name="language.id" required>
                                    <option value="">선택해주세요</option>
                                    <option value="1">영어</option>
                                    <option value="2">일본어</option>
                                    <option value="3">중국어</option>
                                </select>
                            </div>
                        </div>

                        <!-- 카테고리 선택 -->
                        <div class="field">
                            <label>카테고리</label>
                            <div class="field-content">
                                <select name="category.id" required>
                                    <option value="">선택해주세요</option>
                                    <option value="1">회화</option>
                                    <option value="2">문법</option>
                                    <option value="3">시험 대비</option>
                                    <option value="4">비즈니스</option>
                                </select>
                            </div>
                        </div>

                        <!-- 수강비 -->
                        <div class="field">
                            <label>수강비용</label>
                            <div class="field-content">
                                <input type="number" name="price" min="0" placeholder="금액을 입력하세요" required>
                            </div>
                        </div>

                        <!-- 접수 일정 -->
                        <div class="field">
                            <label>접수 기간</label>
                            <div class="field-content date-range">
                                <input type="date" name="applyStartedAt" required>
                                <span>~</span>
                                <input type="date" name="applyEndedAt" required>
                            </div>
                        </div>

                        <!-- 강의 시작/종료일 -->
                        <div class="field">
                            <label>클래스 운영 기간</label>
                            <div class="field-content date-range">
                                <input type="date" name="startedAt" required>
                                <span>~</span>
                                <input type="date" name="endedAt" required>
                            </div>
                        </div>

                        <!-- 강의 소개 -->
                        <div class="field">
                            <label>강의 소개</label>
                            <div class="field-content">
                                <textarea id="editor" name="content"></textarea>
                            </div>
                        </div>

                        <!-- 필수값 기본 지정 -->
                        <input type="hidden" name="status" value="true">
                        <input type="hidden" name="currentStudent" value="0">
                        <input type="hidden" name="lectureCount" value="1">
                        <input type="hidden" name="type" value="VOD">

                        <!-- 저장 버튼 -->
                        <div class="submit-wrap">
                            <button type="submit" class="submit-btn">저장하기</button>
                        </div>
                    </form>

                </div>
            </section>
        </section>
    </div>
</main>
<%@ include file="../common/footer.jsp" %>

<!-- CKEditor5 -->
<script src="https://cdn.ckeditor.com/ckeditor5/43.0.0/classic/ckeditor.js"></script>
<script src="/js/pages/teacher/ckeditor5.js"></script>
<script src="/js/pages/teacher/classVideo.js"></script>
</body>
</html>
