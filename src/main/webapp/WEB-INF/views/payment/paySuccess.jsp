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
    <script src="https://js.tosspayments.com/v2/standard"></script>
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

                    <!-- 토스페이먼츠 위젯 -->
                    <div id="payment-method"></div>
                    <div id="agreement"></div>
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

                        <div class="price-row">
                            <span class="price-label">부가세 (10%)</span>
                            <span class="price-value">
                                <fmt:formatNumber value="${course.price * 0.1}" pattern="#,###"/>원
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
                            <fmt:formatNumber value="${course.price * 1.1}" pattern="#,###"/>원
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
    <c:set var="currentUserId" value="${userPrincipal.userId}" />
    <c:set var="currentUserName" value="${userPrincipal.username}" />
    <c:set var="currentUserEmail" value="${userPrincipal.email}" />
</sec:authorize>

<script>
    document.addEventListener("DOMContentLoaded", async () => {
        const button = document.getElementById("payment-button");
        const coupon = document.getElementById("coupon-box-input");
        const discountRow = document.getElementById("discount-row");

        // 강좌 정보
        const courseId = ${course.id};
        const courseTitle = "${course.title}";
        const coursePrice = ${course.price};
        const totalAmount = Math.floor(coursePrice * 1.1); // 부가세 포함

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

        // ------ 1. 결제 준비 API 호출 ------
        let orderId = null;
        try {
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
            orderId = prepareData.orderId;
            console.log('결제 준비 완료:', orderId);

        } catch (error) {
            console.error('결제 준비 실패:', error);
            alert('결제 준비 중 오류가 발생했습니다. 다시 시도해주세요.');
            window.location.href = '/courses/' + courseId;
            return;
        }

        // ------ 2. 결제위젯 초기화 ------
        const clientKey = "${tossClientKey}";
        const tossPayments = TossPayments(clientKey);

        // 사용자별 고객 키 생성
        const customerKey = "CUSTOMER_" + userId;
        const widgets = tossPayments.widgets({ customerKey });

        // ------ 3. 주문 금액 설정 ------
        await widgets.setAmount({
            currency: "KRW",
            value: totalAmount
        });

        // ------ 4. 위젯 렌더링 ------
        await Promise.all([
            widgets.renderPaymentMethods({
                selector: "#payment-method",
                variantKey: "DEFAULT"
            }),
            widgets.renderAgreement({
                selector: "#agreement",
                variantKey: "AGREEMENT"
            }),
        ]);

        // ------ 5. 쿠폰 적용 (선택사항) ------
        if (coupon) {
            coupon.addEventListener("change", async () => {
                const newAmount = coupon.checked ? totalAmount - 5000 : totalAmount;
                await widgets.setAmount({ currency: "KRW", value: newAmount });

                // UI 업데이트
                document.getElementById("total-amount").textContent =
                    newAmount.toLocaleString("ko-KR") + "원";

                // 할인 행 표시/숨김
                discountRow.style.display = coupon.checked ? "flex" : "none";
            });
        }

        // ------ 6. 결제 요청 ------
        button.addEventListener("click", async () => {
            if (!orderId) {
                alert('주문 정보가 없습니다. 페이지를 새로고침 해주세요.');
                return;
            }

            try {
                await widgets.requestPayment({
                    orderId: orderId,
                    orderName: courseTitle,
                    successUrl: window.location.origin + "/payments/success",
                    failUrl: window.location.origin + "/payments/fail",
                    customerName: userName,
                    customerEmail: userEmail
                });
            } catch (error) {
                console.error("결제 오류:", error);
                alert("결제 중 오류가 발생했습니다. 다시 시도해주세요.");
            }
        });
    });
</script>
</body>
</html>
