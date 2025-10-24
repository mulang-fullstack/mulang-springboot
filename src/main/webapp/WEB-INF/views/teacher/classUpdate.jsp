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
    <title>클래스 수정 | Mulang?</title>
</head>
<body>
<%@ include file="../common/header.jsp" %>

<main>
    <div class="contents">
        <section class="dashboard">
            <%@ include file="sidebar.jsp" %>

            <section class="content">
                <div class="class-upload">
                    <h2>CLASS 수정</h2>

                    <form action="/teacher/mypage/classes/update/${course.id}"
                          method="post"
                          enctype="multipart/form-data">

                        <input type="hidden" name="id" value="${course.id}"/>

                        <!-- 클래스 이름 -->
                        <div class="field">
                            <label>클래스 이름</label>
                            <div class="field-content">
                                <input type="text" name="title" value="${course.title}" required>
                            </div>
                        </div>

                        <!-- 클래스 부제목 -->
                        <div class="field">
                            <label>클래스 부제목</label>
                            <div class="field-content">
                                <input type="text" name="subtitle" value="${course.subtitle}" required>
                            </div>
                        </div>

                        <!-- 언어 선택 -->
                        <div class="field">
                            <label>언어</label>
                            <div class="field-content">
                                <select name="languageId" required>
                                    <option value="">선택해주세요</option>
                                    <option value="1" ${course.language eq '영어' ? 'selected' : ''}>영어</option>
                                    <option value="2" ${course.language eq '일본어' ? 'selected' : ''}>일본어</option>
                                    <option value="3" ${course.language eq '중국어' ? 'selected' : ''}>중국어</option>
                                </select>
                            </div>
                        </div>

                        <!-- 카테고리 선택 -->
                        <div class="field">
                            <label>카테고리</label>
                            <div class="field-content">
                                <select name="categoryId" required>
                                    <option value="">선택해주세요</option>
                                    <option value="1" ${course.category eq '회화' ? 'selected' : ''}>회화</option>
                                    <option value="2" ${course.category eq '문법' ? 'selected' : ''}>문법</option>
                                    <option value="3" ${course.category eq '시험 대비' ? 'selected' : ''}>시험 대비</option>
                                    <option value="4" ${course.category eq '비즈니스' ? 'selected' : ''}>비즈니스</option>
                                </select>
                            </div>
                        </div>

                        <!-- 수강비용 -->
                        <div class="field">
                            <label>수강비용</label>
                            <div class="field-content">
                                <input type="number" name="price" value="${course.price}" min="0" required>
                            </div>
                        </div>

                        <!-- 썸네일 이미지 -->
                        <div class="field">
                            <label>썸네일 이미지</label>
                            <div class="field-content">
                                <input type="file" name="thumbnailFile"
                                       accept="image/png,image/jpeg,image/webp">
                                <c:if test="${not empty course.thumbnail}">
                                    <p>현재 이미지:</p>
                                    <img src="${course.thumbnail}" width="120">
                                </c:if>
                                <p class="hint">※ 대표 이미지로 사용됩니다. 10MB 이하, JPG/PNG 권장</p>
                            </div>
                        </div>

                        <!-- VOD 챕터 수정 (동적 추가 버튼 포함) -->
                        <div class="field vod-section">
                            <label>챕터 업로드</label>
                            <div class="field-content">
                                <div class="video-list">
                                    <c:forEach var="i" begin="0" end="${course.lectureCount - 1}">
                                        <div class="video-item" data-index="${i}">
                                            <input type="text" name="lectures[${i}].title" value="" placeholder="챕터 제목" class="chapter-input" required>
                                            <input type="text" name="lectures[${i}].content" value="" placeholder="챕터 소개" class="chapter-input" required>
                                            <input type="file" name="lectures[${i}].video" accept="video/mp4,video/webm,video/mov" class="video-input">
                                            <div class="video-btn-wrap">
                                                <button type="button" class="add-video-btn">＋</button>
                                                <button type="button" class="remove-video-btn">－</button>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </div>
                            </div>
                        </div>

                        <!-- 강의 소개 (CKEditor 동일 적용) -->
                        <div class="field">
                            <label>강의 소개</label>
                            <div class="field-content">
                                <textarea id="editor" name="content">${course.content}</textarea>
                            </div>
                        </div>

                        <input type="hidden" name="lectureCount" value="${course.lectureCount}">

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
<script src="https://cdn.ckeditor.com/ckeditor5/35.3.2/classic/ckeditor.js"></script>
<script src="/js/pages/teacher/ckeditor5.js?v=20251023"></script>
<script src="/js/pages/teacher/classVideo.js"></script>
</body>
</html>
