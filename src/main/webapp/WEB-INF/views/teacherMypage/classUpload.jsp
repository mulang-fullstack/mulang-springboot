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

                    <form action="/teacher/mypage/classes" method="post" enctype="multipart/form-data">

                        <div class="field">
                            <label>클래스 이름</label>
                            <div class="field-content">
                                <input type="text" name="title" placeholder="클래스 이름을 입력하세요" required>
                            </div>
                        </div>

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

                        <div class="field">
                            <label>수강비용</label>
                            <div class="field-content">
                                <input type="number" name="price" min="0" placeholder="금액을 입력하세요" required>
                            </div>
                        </div>

                        <div class="field">
                            <label>클래스 운영 기간</label>
                            <div class="field-content date-range">
                                <input type="date" name="startedAt" required>
                                <span>~</span>
                                <input type="date" name="endedAt" required>
                            </div>
                        </div>

                        <div class="field">
                            <label>썸네일 이미지</label>
                            <div class="field-content">
                                <input type="file" name="thumbnailFile"
                                       accept="image/png, image/jpeg, image/webp"
                                       required>
                                <p class="hint">※ 대표 이미지로 사용됩니다. 10MB 이하, JPG/PNG 권장</p>
                            </div>
                        </div>

                        <div class="field vod-section">
                            <label>챕터 업로드</label>
                            <div class="field-content">
                                <div class="video-list">
                                    <div class="video-item">
                                        <input type="text" name="lectureTitle[]" placeholder="챕터 제목을 입력하세요" class="chapter-input" required>
                                        <input type="file" name="lectureVideo[]" accept="video/mp4,video/webm,video/mov" class="video-input" required>
                                        <div class="video-btn-wrap">
                                            <button type="button" class="add-video-btn">＋</button>
                                            <button type="button" class="remove-video-btn">－</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="field">
                            <label>강의 소개</label>
                            <div class="field-content">
                                <textarea id="editor" name="content"></textarea>
                            </div>
                        </div>

                        <!-- 기본값 설정 -->
                        <input type="hidden" name="status" value="true">
                        <input type="hidden" name="currentStudent" value="0">
                        <input type="hidden" name="lectureCount" value="1">
                        <input type="hidden" name="type" value="VOD">

                        <!-- 로그인된 강사 ID -->
                        <input type="hidden" name="teacher.id" value="${teacher.id}">

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
<script src="https://cdn.ckeditor.com/ckeditor5/43.0.0/classic/ckeditor.js"></script>
<script src="/js/pages/teacher/ckeditor5.js"></script>
<script src="/js/pages/teacher/classVideo.js"></script>
</body>
</html>
