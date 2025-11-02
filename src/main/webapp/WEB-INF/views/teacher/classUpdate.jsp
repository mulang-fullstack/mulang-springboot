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
    <link rel="stylesheet" href="/css/pages/teacher/class/classUpdate.css"/>
    <link href="https://cdn.jsdelivr.net/npm/summernote@0.8.20/dist/summernote-lite.min.css" rel="stylesheet">
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

                    <!--  action 수정 -->
                    <form action="/teacher/mypage/classes/update"
                          method="post"
                          enctype="multipart/form-data">

                        <!--  courseId 전달 -->
                        <input type="hidden" name="courseId" value="${course.id}"/>

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

                        <!-- 카테고리 선택 -->
                        <div class="field">
                            <label>카테고리</label>
                            <div class="field-content">
                                <select name="categoryId" required>
                                    <option value="">선택해주세요</option>
                                    <option value="1" ${course.category eq '기초' ? 'selected' : ''}>기초</option>
                                    <option value="2" ${course.category eq '문법' ? 'selected' : ''}>문법</option>
                                    <option value="3" ${course.category eq '어휘' ? 'selected' : ''}>어휘</option>
                                    <option value="4" ${course.category eq '회화' ? 'selected' : ''}>회화</option>
                                </select>
                            </div>
                        </div>

                        <!-- 언어 선택 -->
                        <div class="field">
                            <label>언어</label>
                            <div class="field-content">
                                <select name="languageId" required>
                                    <option value="">선택해주세요</option>
                                    <option value="1" ${course.language eq '영어' ? 'selected' : ''}>영어</option>
                                    <option value="2" ${course.language eq '중국어' ? 'selected' : ''}>중국어</option>
                                    <option value="3" ${course.language eq '일본어' ? 'selected' : ''}>일본어</option>
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

                        <!--  VOD 챕터 수정 -->
                        <div class="video-list">
                            <c:forEach var="lecture" items="${course.lectures}" varStatus="status">
                                <div class="video-item" data-index="${status.index}">
                                    <!--  기존 강의 식별용 -->
                                    <input type="hidden" name="lectures[${status.index}].id" value="${lecture.id}">

                                    <!-- 챕터 제목 -->
                                    <input type="text"
                                           name="lectures[${status.index}].title"
                                           value="${lecture.title}"
                                           placeholder="챕터 제목"
                                           required
                                           class="chapter-input">

                                    <!-- 챕터 소개 -->
                                    <input type="text"
                                           name="lectures[${status.index}].content"
                                           value="${lecture.content}"
                                           placeholder="챕터 소개"
                                           required
                                           class="chapter-input">

                                    <!-- 파일 업로드 -->
                                    <div class="custom-file">
                                        <label for="video_${status.index}" class="file-label">
                                            <span class="file-button">파일 선택</span>
                                            <span class="file-name">
                                                <c:out value="${lecture.originalName}" default="선택된 파일 없음"/>
                                            </span>
                                        </label>
                                        <input type="file"
                                               id="video_${status.index}"
                                               name="lectures[${status.index}].video"
                                               accept="video/mp4,video/webm,video/mov"
                                               class="video-input">
                                    </div>

                                    <!-- 버튼 -->
                                    <div class="video-btn-wrap">
                                        <button type="button" class="add-video-btn">＋</button>
                                        <button type="button" class="remove-video-btn">－</button>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>

                        <!--  삭제 ID 전송용 hidden -->
                        <div id="deletedLectureContainer"></div>

                        <!-- 강의 소개 -->
                        <div class="field">
                            <label>강의 소개</label>
                            <div class="field-content">
                                <textarea id="summernote" name="content">${course.content}</textarea>
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
<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/summernote@0.8.20/dist/summernote-lite.min.js"></script>
<script src="/js/pages/teacher/common/summernote-init.js?v=20251023"></script>
<script src="/js/pages/teacher/class/classVideoUpdate.js"></script>
<script src="/js/pages/teacher/class/classThumbnailPreview.js"></script>
<script src="/js/pages/teacher/class/classFormValidator.js"></script>
<script src="/js/pages/teacher/class/classSubmitLock.js"></script>

</body>
</html>
