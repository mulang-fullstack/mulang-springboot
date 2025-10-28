<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta http-equiv="X-UA-Compatible" content="ie=edge" />
    <link rel="icon" href="/img/favicon.svg" type="image/png" />
    <link rel="stylesheet" href="/css/global.css" />
    <link rel="stylesheet" href="/css/pages/payment/payment.css" />
    <!-- 토스 페이먼츠 결제 SDK -->
    <script src="https://js.tosspayments.com/v1/payment"></script>
    <title>강의 결제 | Mulang</title>
</head>
<body>
<%@ include file="../common/header.jsp" %>

<main>
    <div class="payment-container">
        <h1 class="payment-title">강의 결제</h1>
        <p class="payment-subtitle">평생 소장 가능한 강의를 지금 만나보세요</p>

        <div class="payment-grid">
            <!-- 왼쪽: 강의 정보 + 결제 위젯 -->
            <div class="payment-main">
                <!-- 강의 헤더 -->
                <div class="course-header">
                    <div class="course-thumbnail">
                        <c:choose>
                            <c:when test="${not empty course.thumbnail}">
                                <img src="${course.thumbnail}" alt="${course.title}" style="width: 100%; height: 100%; object-fit: cover; border-radius: 16px;">
                            </c:when>
                            <c:otherwise>
                                ${course.language.name.substring(0, 1)}
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="course-info">
                        <h2 class="course-title">${course.title}</h2>
                        <p class="course-instructor">
                            강사:
                            <c:choose>
                                <c:when test="${not empty course.teacher}">
                                    ${course.teacher.user.username}
                                </c:when>
                                <c:otherwise>
                                    관리자
                                </c:otherwise>
                            </c:choose>
                        </p>
                        <div class="course-meta">
                            <div class="meta-item">
                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24"
                                     fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                    <rect x="2" y="7" width="20" height="15" rx="2" ry="2"></rect>
                                    <polyline points="17 2 12 7 7 2"></polyline>
                                </svg>
                                ${course.lectureCount}개 강의
                            </div>
                            <div class="meta-item">
                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24"
                                     fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                    <polygon points="12 2 15.09 8.26 22 9.27 17 14.14 18.18 21.02 12 17.77 5.82 21.02 7 14.14 2 9.27 8.91 8.26 12 2"></polygon>
                                </svg>
                                <c:choose>
                                    <c:when test="${not empty course.averageRating}">
                                        <fmt:formatNumber value="${course.averageRating}" pattern="#.#"/>점
                                    </c:when>
                                    <c:otherwise>
                                        평가 없음
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <c:if test="${not empty course.reviewCount and course.reviewCount > 0}">
                                <div class="meta-item">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24"
                                         fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                        <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"></path>
                                        <circle cx="9" cy="7" r="4"></circle>
                                        <path d="M23 21v-2a4 4 0 0 0-3-3.87"></path>
                                        <path d="M16 3.13a4 4 0 0 1 0 7.75"></path>
                                    </svg>
                                    <fmt:formatNumber value="${course.reviewCount}" pattern="#,###"/>명 수강
                                </div>
                            </c:if>
                        </div>
                    </div>
                </div>

                <!-- 결제 방법 섹션 -->
                <div class="payment-method-section">
                    <h3>
                        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24"
                             fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                            <rect x="1" y="4" width="22" height="16" rx="2" ry="2"></rect>
                            <line x1="1" y1="10" x2="23" y2="10"></line>
                        </svg>
                        결제 방법 선택
                    </h3>

                    <!-- 테스트 환경 알림 -->
                    <div class="test-notice">
                        ⚠️ 테스트 환경 - 실제로 결제되지 않습니다.
                    </div>

                    <!-- 커스텀 결제 방법 선택 UI -->
                    <div class="payment-methods-grid">
                        <!-- 일반결제 -->
                        <div class="payment-method-item" data-method="NORMAL" data-type="CARD">
                            <div class="method-content">
                                <div class="method-icon">💳</div>
                                <div class="method-name">일반결제</div>
                            </div>
                        </div>

                        <!-- 신용·체크카드 -->
                        <div class="payment-method-item" data-method="CARD" data-type="CARD">
                            <div class="method-content">
                                <div class="method-icon">💳</div>
                                <div class="method-name">신용·체크카드</div>
                            </div>
                        </div>

                        <!-- 토스페이 (추천 뱃지) -->
                        <div class="payment-method-item recommended" data-method="TOSSPAY" data-type="TOSSPAY">
                            <div class="recommended-badge">적립 혜택</div>
                            <div class="method-content">
                                <img src="/img/payment/tosspay-logo.png" alt="토스페이" class="method-logo" onerror="this.style.display='none'; this.nextElementSibling.style.display='block';">
                                <div class="method-icon" style="display:none;">🎯</div>
                                <div class="method-name">토스페이</div>
                            </div>
                        </div>

                        <!-- PAYCO -->
                        <div class="payment-method-item" data-method="PAYCO" data-type="PAYCO">
                            <div class="method-content">
                                <img src="/img/payment/payco-logo.png" alt="PAYCO" class="method-logo" onerror="this.style.display='none'; this.nextElementSibling.style.display='block';">
                                <div class="method-icon" style="display:none;">💰</div>
                                <div class="method-name">PAYCO</div>
                            </div>
                        </div>

                        <!-- 카카오페이 -->
                        <div class="payment-method-item" data-method="KAKAOPAY" data-type="KAKAOPAY">
                            <div class="method-content">
                                <div class="method-icon">💛</div>
                                <div class="method-name">카카오페이</div>
                            </div>
                        </div>

                        <!-- 네이버페이 -->
                        <div class="payment-method-item" data-method="NAVERPAY" data-type="NAVERPAY">
                            <div class="method-content">
                                <div class="method-icon">💚</div>
                                <div class="method-name">네이버페이</div>
                            </div>
                        </div>

                        <!-- 휴대폰 -->
                        <div class="payment-method-item" data-method="MOBILE" data-type="MOBILE_PHONE">
                            <div class="method-content">
                                <div class="method-icon">📱</div>
                                <div class="method-name">휴대폰</div>
                            </div>
                        </div>
                    </div>

                    <!-- 신용카드 무이자 할부 안내 -->
                    <div class="installment-info">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24"
                             fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                            <circle cx="12" cy="12" r="10"></circle>
                            <line x1="12" y1="16" x2="12" y2="12"></line>
                            <line x1="12" y1="8" x2="12.01" y2="8"></line>
                        </svg>
                        신용카드 최대 3개월 무이자 할부
                    </div>

                    <!-- 약관 동의 -->
                    <div class="payment-agreements">
                        <div class="agreement-item">
                            <input type="checkbox" id="agree-all" class="agreement-checkbox">
                            <label for="agree-all" class="agreement-label">
                                [필수] 결제 서비스 이용 약관, 개인정보 처리 동의
                            </label>
                        </div>
                        <div class="agreement-details">
                            신용카드 무이자 할부 안내 &gt;
                        </div>
                    </div>
                </div>
            </div>

            <!-- 오른쪽: 결제 정보 사이드바 -->
            <div class="payment-sidebar">
                <!-- 결제 정보 -->
                <div class="order-summary-card">
                    <h3>결제 정보</h3>

                    <div class="price-breakdown">
                        <div class="price-row">
                            <span class="price-label">강좌 금액</span>
                            <span class="price-value">
                                <fmt:formatNumber value="${course.price}" pattern="#,###"/>원
                            </span>
                        </div>

                        <div class="price-row discount-row" id="discount-row" style="display: none;">
                            <span class="price-label">쿠폰 할인</span>
                            <span class="price-value">-5,000원</span>
                        </div>
                    </div>

                    <div class="total-row">
                        <span class="total-label">최종 결제 금액</span>
                        <span class="total-amount" id="total-amount">
                            <fmt:formatNumber value="${course.price}" pattern="#,###"/>원
                        </span>
                    </div>
                </div>

                <!-- 쿠폰 적용 (선택사항) -->
                <div class="coupon-box" onclick="document.getElementById('coupon-box-input').click()" style="display: none;">
                    <input type="checkbox" id="coupon-box-input">
                    <label for="coupon-box-input">
                        <span class="coupon-badge">5,000원</span>
                        신규 회원 쿠폰 적용하기
                    </label>
                </div>

                <!-- 보안 정보 -->
                <div class="security-info">
                    <p>
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24"
                             fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                            <rect x="3" y="11" width="18" height="11" rx="2" ry="2"></rect>
                            <path d="M7 11V7a5 5 0 0 1 10 0v4"></path>
                        </svg>
                        안전한 결제가 보장됩니다
                    </p>
                </div>

                <!-- 결제하기 버튼 -->
                <button class="payment-button" id="payment-button">
                    결제하기
                </button>
            </div>
        </div>
    </div>
</main>

<%@ include file="../common/footer.jsp" %>

<!-- Spring Security 인증 정보를 JavaScript 변수로 전달 -->
<sec:authorize access="isAuthenticated()">
    <sec:authentication property="principal" var="userPrincipal" />
    <c:set var="currentUser" value="${userPrincipal.user}" />
    <c:set var="currentUserId" value="${currentUser.id}" />
    <c:set var="currentUserName" value="${currentUser.username}" />
    <c:set var="currentUserEmail" value="${currentUser.email}" />
</sec:authorize>

<script>
    document.addEventListener("DOMContentLoaded", async () => {
        const button = document.getElementById("payment-button");
        const coupon = document.getElementById("coupon-box-input");
        const discountRow = document.getElementById("discount-row");
        const agreeCheckbox = document.getElementById("agree-all");

        // 강좌 정보
        const courseId = ${course.id};
        const courseTitle = "${course.title}";
        const coursePrice = ${course.price};
        let totalAmount = coursePrice;

        // Spring Security에서 전달받은 사용자 정보
        <sec:authorize access="isAuthenticated()">
        const userId = ${currentUserId};
        const userName = "${currentUserName}";
        const userEmail = "${currentUserEmail}";
        </sec:authorize>

        <sec:authorize access="!isAuthenticated()">
        alert('로그인이 필요한 서비스입니다.');
        window.location.href = '/login?redirect=/payments/${course.id}';
        </sec:authorize>

        // ------ 1. 토스페이먼츠 클라이언트 초기화 ------
        const clientKey = "${clientKey}";
        const tossPayments = TossPayments(clientKey);

        // ------ 2. 결제 방법 선택 처리 ------
        let selectedMethod = null;
        let selectedType = 'CARD'; // 기본값
        const paymentMethodItems = document.querySelectorAll('.payment-method-item');

        paymentMethodItems.forEach(item => {
            item.addEventListener('click', () => {
                // 모든 아이템의 선택 상태 제거
                paymentMethodItems.forEach(el => el.classList.remove('selected'));
                // 클릭된 아이템 선택
                item.classList.add('selected');
                selectedMethod = item.dataset.method;
                selectedType = item.dataset.type;
                console.log('선택된 결제 방법:', selectedMethod, selectedType);
            });
        });

        // ------ 3. 쿠폰 적용 (선택사항) ------
        if (coupon) {
            coupon.addEventListener("change", () => {
                const newAmount = coupon.checked ? coursePrice - 5000 : coursePrice;
                totalAmount = newAmount;

                // UI 업데이트
                document.getElementById("total-amount").textContent =
                    newAmount.toLocaleString("ko-KR") + "원";

                // 할인 행 표시/숨김
                discountRow.style.display = coupon.checked ? "flex" : "none";
            });
        }

        // ------ 4. 결제 요청 ------
        button.addEventListener("click", async () => {
            // 결제 방법 선택 확인
            if (!selectedMethod) {
                alert('결제 방법을 선택해주세요.');
                return;
            }

            // 약관 동의 확인
            if (!agreeCheckbox.checked) {
                alert('결제 서비스 이용 약관에 동의해주세요.');
                agreeCheckbox.focus();
                return;
            }

            try {
                // 주문 ID 생성 (결제 준비 API 호출)
                const prepareResponse = await fetch('/payments/prepare?userId=' + userId, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({
                        courseId: courseId,
                        amount: totalAmount,
                        orderName: courseTitle
                    })
                });

                if (!prepareResponse.ok) {
                    throw new Error('결제 준비에 실패했습니다.');
                }

                const prepareData = await prepareResponse.json();
                const orderId = prepareData.orderId;

                // 토스페이먼츠 결제 요청
                await tossPayments.requestPayment(selectedType, {
                    amount: totalAmount,
                    orderId: orderId,
                    orderName: courseTitle,
                    customerName: userName,
                    customerEmail: userEmail,
                    successUrl: window.location.origin + "/payments/success",
                    failUrl: window.location.origin + "/payments/fail",
                });

            } catch (error) {
                console.error('결제 오류:', error);
                if (error.code === 'USER_CANCEL') {
                    alert("결제를 취소하셨습니다.");
                } else {
                    alert("결제 중 오류가 발생했습니다: " + (error.message || '알 수 없는 오류'));
                }
            }
        });
    });
</script>
</body>
</html>
