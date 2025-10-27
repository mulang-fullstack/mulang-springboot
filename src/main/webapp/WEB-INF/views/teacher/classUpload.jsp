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
    <link href="https://cdn.jsdelivr.net/npm/summernote@0.8.20/dist/summernote-lite.min.css" rel="stylesheet">
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

                    <form action="/teacher/mypage/classes" method="post" enctype="multipart/form-data">

                        <div class="field">
                            <label>클래스 이름</label>
                            <div class="field-content">
                                <input type="text" name="title" placeholder="클래스 이름을 입력하세요" required>
                            </div>
                        </div>

                        <div class="field">
                            <label>클래스 부제목</label>
                            <div class="field-content">
                                <input type="text" name="subtitle" placeholder="부제목을 입력하세요" required>
                            </div>
                        </div>


                        <div class="field">
                            <label>카테고리</label>
                            <div class="field-content">
                                <select name="categoryId" required>
                                    <option value="">선택해주세요</option>
                                    <option value="1">회화</option>
                                    <option value="2">문법</option>
                                    <option value="3">시험 대비</option>
                                    <option value="4">비즈니스</option>
                                </select>
                            </div>
                        </div>

                        <div class="field">
                            <label>언어</label>
                            <div class="field-content">
                                <select name="languageId" required>
                                    <option value="">선택해주세요</option>
                                    <option value="1">영어</option>
                                    <option value="2">일본어</option>
                                    <option value="3">중국어</option>
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
                            <label>썸네일 이미지</label>
                            <div class="field-content">
                                <input type="file" name="thumbnailFile"
                                       accept="image/png, image/jpeg, image/webp"
                                       required>
                                <p class="hint">※ 대표 이미지로 사용됩니다. 10MB 이하, JPG/PNG 권장</p>
                            </div>
                        </div>

                        <div class="video-list">
                            <div class="video-item" data-index="0">
                                <input type="text" name="lectures[0].title" placeholder="챕터 제목" required class="chapter-input">
                                <input type="text" name="lectures[0].content" placeholder="챕터 소개" required class="chapter-input">

                                <div class="custom-file">
                                    <label for="video_0" class="file-label">
                                        <span class="file-button">파일 선택</span>
                                        <span class="file-name">선택된 파일 없음</span>
                                    </label>
                                    <input type="file"
                                           id="video_0"
                                           name="lectures[0].video"
                                           accept="video/mp4,video/webm,video/mov"
                                           class="video-input"
                                           required>
                                </div>

                                <div class="video-btn-wrap">
                                    <button type="button" class="add-video-btn">＋</button>
                                    <button type="button" class="remove-video-btn">－</button>
                                </div>
                            </div>
                        </div>

                        <div class="field">
                            <label>강의 소개</label>
                            <div class="field-content">
                                <textarea id="summernote" name="content"></textarea>
                            </div>
                        </div>

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

<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
<link href="https://cdn.jsdelivr.net/npm/summernote@0.8.20/dist/summernote-lite.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/summernote@0.8.20/dist/summernote-lite.min.js"></script>
<script src="/js/pages/teacher/common/summernote-init.js?v=20251023"></script>

<script src="/js/pages/teacher/class/classVideoUpload.js"></script>
</body>
</html>
