/**
 * 결제 페이지 JavaScript
 * /js/pages/payment/payment.js
 */

document.addEventListener("DOMContentLoaded", async () => {
    // ==================== 초기화 ====================

    // 인증 확인
    if (!window.PAYMENT_DATA.isAuthenticated) {
        alert('로그인이 필요한 서비스입니다.');
        window.location.href = `/login?redirect=/payments/${window.PAYMENT_DATA.courseId}`;
        return;
    }

    // DOM 요소
    const paymentButton = document.getElementById("payment-button");
    const couponCheckbox = document.getElementById("coupon-box-input");
    const discountRow = document.getElementById("discount-row");
    const agreeCheckbox = document.getElementById("agree-all");
    const totalAmountElement = document.getElementById("total-amount");

    // 결제 데이터
    const { orderId, amount, courseId, courseTitle, userId, userName, userEmail } = window.PAYMENT_DATA;
    let totalAmount = amount;

    // 토스페이먼츠 클라이언트 초기화
    const tossPayments = TossPayments(window.TOSS_CLIENT_KEY);

    console.log('✅ 결제 페이지 초기화 완료', {
        orderId,
        courseId,
        amount,
        userId
    });

    // ==================== 결제 방법 선택 ====================

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

            console.log('📌 선택된 결제 방법:', selectedMethod, selectedType);
        });
    });

    // ==================== 쿠폰 적용 ====================

    if (couponCheckbox) {
        couponCheckbox.addEventListener("change", () => {
            const discountAmount = 5000;
            const newAmount = couponCheckbox.checked ? amount - discountAmount : amount;
            totalAmount = newAmount;

            // UI 업데이트
            totalAmountElement.textContent = newAmount.toLocaleString("ko-KR") + "원";
            discountRow.style.display = couponCheckbox.checked ? "flex" : "none";

            console.log('🎫 쿠폰 적용:', couponCheckbox.checked, '→ 금액:', newAmount);
        });
    }

    // ==================== 결제 요청 ====================

    paymentButton.addEventListener("click", async () => {
        try {
            // 1. 유효성 검사
            if (!validatePayment()) {
                return;
            }

            console.log('💳 결제 요청 시작...');

            // 2. 토스페이먼츠 결제 요청
            await requestTossPayment();

        } catch (error) {
            console.error('❌ 결제 오류:', error);
            handlePaymentError(error);
        }
    });

    // ==================== 함수 정의 ====================

    /**
     * 결제 유효성 검사
     */
    function validatePayment() {
        // 결제 방법 선택 확인
        if (!selectedMethod) {
            alert('결제 방법을 선택해주세요.');
            return false;
        }

        // 약관 동의 확인
        if (!agreeCheckbox.checked) {
            alert('결제 서비스 이용 약관에 동의해주세요.');
            agreeCheckbox.focus();
            return false;
        }

        return true;
    }

    /**
     * 토스페이먼츠 결제 요청
     */
    async function requestTossPayment() {
        console.log('📤 토스 결제 요청:', {
            type: selectedType,
            orderId: orderId,
            amount: totalAmount,
            orderName: courseTitle,
            customerName: userName,
            customerEmail: userEmail
        });

        await tossPayments.requestPayment(selectedType, {
            amount: totalAmount,
            orderId: orderId,
            orderName: courseTitle,
            customerName: userName,
            customerEmail: userEmail,
            successUrl: window.location.origin + "/payments/success",
            failUrl: window.location.origin + "/payments/fail",
        });
    }

    /**
     * 결제 오류 처리
     */
    function handlePaymentError(error) {
        if (error.code === 'USER_CANCEL') {
            alert("결제를 취소하셨습니다.");
        } else if (error.code === 'INVALID_PARAMETER') {
            alert("결제 정보가 올바르지 않습니다.");
        } else {
            alert("결제 중 오류가 발생했습니다: " + (error.message || '알 수 없는 오류'));
        }
    }
});