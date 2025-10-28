<jsp:directive.page contentType="text/html;charset=UTF-8" />
<div class="contents">
    <section class="dashboard">
        <section class="content">
            <div class="profile-main">
                <h2>강사 프로필</h2>

                <section class="profile-section">
                    <!-- 이름 -->
                    <div class="field">
                        <label>이름</label>
                        <div class="field-content">
                            <span class="text-value">${teacher.name}</span>
                        </div>
                    </div>
                    <!-- 이메일 -->
                    <div class="field">
                        <label>이메일</label>
                        <div class="field-content">
                            <span class="text-value">${teacher.email}</span>
                        </div>
                    </div>
                    <!-- 프로필 이미지 -->
                    <div class="field">
                        <label>프로필 이미지</label>
                        <div class="field-content">
                            <div class="profile-img-wrap">
                                <img id="profileImg"
                                     src="${teacher.photoUrl != null ? teacher.photoUrl : '/img/dummy/default-profile.png'}">
                            </div>
                        </div>
                    </div>

                    <!-- 닉네임 -->
                    <div class="field">
                        <label>닉네임</label>
                        <div class="field-content">
                            <span class="text-value">${teacher.nickname}</span>
                        </div>
                    </div>

                    <!-- 소개 -->
                    <div class="field">
                        <label>소개</label>
                        <div class="field-content">
                            <p class="text-area">${teacher.introduction}</p>
                        </div>
                    </div>

                    <!-- 경력 -->
                    <div class="field">
                        <label>경력</label>
                        <div class="field-content">
                            <p class="text-area">${teacher.career}</p>
                        </div>
                    </div>
                </section>
            </div>
        </section>
    </section>
</div>

