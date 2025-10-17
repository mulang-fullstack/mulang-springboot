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
    <link rel="stylesheet" href="/css/pages/mypage/realpay.css"/>

    <title>주문 요약 | Mulang</title>
</head>
<body>
<%@include file="../common/header.jsp" %>

<main>
    <div class="contents">
        <section class="dashboard">
            <%@ include file="sidebar.jsp" %>

            <section class="content">
                <div class="profile-main">
                    <h2>주문 요약</h2>

                    <section class="realpay-section">
                        <div class="order-summary">
                            <h3>주문 요약</h3>
                            <p class="item-name">왕초보 영어 회화</p>

                            <div class="item-row">
                                <span>1</span>
                                <span>$115</span>
                            </div>

                            <input type="text" placeholder="프로모션 코드를 입력하세요" class="promo-input">
                            <p class="promo-text">프로모션 코드가 있습니까?</p>

                            <div class="summary-row">
                                <span>세금</span>
                                <span>+$10.21</span>
                            </div>

                            <div class="total">
                                <span>세금 포함 합계</span>
                                <span>$125.21</span>
                            </div>
                        </div>

                        <div class="payment-form">
                            <form action="/order/submit" method="post">
                                <label>이름</label>
                                <input type="text" name="name" required>

                                <label>성</label>
                                <input type="text" name="lastname" required>

                                <label>이메일</label>
                                <input type="email" name="email" required>

                                <label>전화번호</label>
                                <input type="tel" name="phone" required>

                                <label>신용 카드 번호</label>
                                <input type="text" name="card" maxlength="19" placeholder="0000-0000-0000-0000" required>

                                <div class="card-info">
                                    <div>
                                        <label>만료 월</label>
                                        <select name="month">
                                            <option>01</option><option>02</option><option>03</option>
                                            <option>04</option><option>05</option><option>06</option>
                                            <option>07</option><option>08</option><option>09</option>
                                            <option>10</option><option>11</option><option>12</option>
                                        </select>
                                    </div>

                                    <div>
                                        <label>만료 연도</label>
                                        <select name="year">
                                            <option>2025</option><option>2026</option><option>2027</option>
                                            <option>2028</option><option>2029</option>
                                        </select>
                                    </div>

                                    <div>
                                        <label>보안 코드</label>
                                        <input type="text" name="cvv" maxlength="4" required>
                                    </div>
                                </div>

                                <label>상세 주소</label>
                                <input type="text" name="address" required>

                                <div class="address-row">
                                    <div>
                                        <label>시</label>
                                        <input type="text" name="city" required>
                                    </div>
                                    <div>
                                        <label>도</label>
                                        <input type="text" name="state" required>
                                    </div>
                                    <div>
                                        <label>우편번호</label>
                                        <input type="text" name="zip" required>
                                    </div>
                                </div>

                                <div class="submit-wrap">
                                    <button type="submit" class="submit-btn">주문 확인</button>
                                </div>
                            </form>
                        </div>
                    </section>
                </div>
            </section>
        </section>
    </div>
</main>

<%@include file="../common/footer.jsp" %>
</body>
</html>
