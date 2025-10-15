<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="icon" href="img/favicon.svg" type="image/png">
    <link rel="stylesheet" href="css/global.css"/>
    <link rel="stylesheet" href="css/pages/lecture/lecture-detail.css"/>
    <title>Mulang?</title>
</head>
<body>
<%@include file="../common/header.jsp" %>
<main>
    <div class="contents">
        <div class="leture-detail-contents">
            <%@include file="lectureInfo.jsp" %>
            <div class="lecture-tabs">
                <a href="lectureDetail.do"><div class="tab active">강의소개</div></a>
                <a href="lectureCurriculum.do"><div class="tab">커리큘럼</div></a>
                <a href="lectureReview.do"><div class="tab">리뷰</div></a>
            </div>
            <div class="lecture-content">
                <h2>왕초보 기초회화</h2>
                <p>
                    틀에 박힌 한국식 영어는 이제 그만! 유학파 및 원어민 강사가 알려주는 신비한 뉘앙스의 세계 - 찐 원어민 회화 패키지!<br />
                    어렵게만 느껴지는 스몰톡! 자신감이 필요하다면?<br />
                    필수 Expressions & Vocabulary를 자연스럽게 적용한 대화문을 온전히 내 것으로!<br />
                    현실감 최고인 Role-Play 대화문 속 유용한 Expressions를 반복 연습해보세요.<br /><br />
                    [강의 구성]<br />
                    - Warm up Quiz : 가장 중요한 한 문장을 소개하며 유추해보는 워밍업<br />
                    - Today’s Expression : 주요 패턴과 예시 문장 학습<br />
                    - Role Play, Conversation 등
                </p>
            </div>
        </div>
    </div>
</main>
<%@include file="../common/footer.jsp" %>
</body>
</html>