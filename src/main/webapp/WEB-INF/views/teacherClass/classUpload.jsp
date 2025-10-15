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
    <link rel="stylesheet" href="/css/pages/teacher/class/classUpload.css"/>

    <title>VOD 클래스 업로드</title>
</head>
<body>

<%@include file="../common/header.jsp" %>

<main class="main">
    <section class="dashboard">
        <section class="content">
            <div class="class-upload">
                <h2>VOD 강의 업로드</h2>

                <!-- 클래스 이름 -->
                <div class="field">
                    <label>클래스 이름</label>
                    <div class="field-content">
                        <input type="text" name="title" placeholder="클래스 이름을 입력하세요" required/>
                    </div>
                </div>

                <!-- 언어 선택 -->
                <div class="field">
                    <label>언어</label>
                    <div class="field-content">
                        <select name="language_id" required>
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
                        <select name="category_id" required>
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
                    <label>수강비 (₩)</label>
                    <div class="field-content">
                        <input type="number" name="price" min="0" placeholder="금액을 입력하세요" required/>
                    </div>
                </div>

                <!-- 강의 일정 -->
                <div class="field">
                    <label>강의 일정</label>
                    <div class="field-content date-range">
                        <input type="date" name="apply_started_at" required/>
                        <span>~</span>
                        <input type="date" name="apply_ended_at" required/>
                    </div>
                </div>

                <!-- 썸네일 업로드 -->
                <div class="field">
                    <label>썸네일</label>
                    <div class="field-content">
                        <input type="file" name="thumbnail" accept="image/png,image/jpeg" required/>
                        <p class="hint">10MB 이하 jpg, jpeg, png 파일만 업로드 가능합니다.</p>
                    </div>
                </div>

                <!-- 영상 업로드 -->
                <div class="field">
                    <label>강의 영상</label>
                    <div class="field-content">
                        <input type="file" name="videoFile" accept="video/mp4,video/webm,video/mov" required/>
                        <p class="hint">10GB 이하의 mp4, webm, mov 형식만 업로드 가능합니다.</p>
                    </div>
                </div>

                <!-- 클래스 소개 (에디터) -->
                <div class="field">
                    <label>클래스 소개</label>
                    <div class="field-content">
                        <textarea id="editor" name="content"></textarea>
                    </div>
                </div>

                <!-- 저장 버튼 -->
                <div class="submit-wrap">
                    <button type="submit" class="submit-btn">저장하기</button>
                </div>
            </div>
        </section>
    </section>
</main>

<%@include file="../common/footer.jsp" %>

<!-- CKEditor5 CDN -->
<script src="https://cdn.ckeditor.com/ckeditor5/43.0.0/classic/ckeditor.js"></script>

<!-- 커스텀 에디터 설정 -->
<script src="/js/ckeditor5.js"></script>

</body>
</html>
