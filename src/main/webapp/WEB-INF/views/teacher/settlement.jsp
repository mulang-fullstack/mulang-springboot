<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="icon" href="/img/favicon.svg" type="image/png">
    <link rel="stylesheet" href="/css/global.css">
    <link rel="stylesheet" href="/css/pages/teacher/settlement.css">
    <title>클래스 판매 현황 | Mulang?</title>
</head>
<body>
<%@ include file="../common/header.jsp" %>

<main>
    <div class="contents">
        <section class="dashboard">
            <%@ include file="sidebar.jsp" %>

            <section class="content">
                <h2>클래스 판매 현황</h2>

                <!-- 상단 요약 -->
                <div class="settlement-summary">
                    <div class="summary-box">
                        <h3>클래스 판매 금액</h3>
                        <p class="amount">
                            <fmt:formatNumber value="${totalSales}" type="currency"/>원
                            <span>(${salesPage.totalElements}건)</span>
                        </p>
                    </div>
                </div>

                <!-- 매출 테이블 -->
                <div class="settlement-table">
                    <div class="table-header">
                        <span>클래스명</span>
                        <span>클래스 매출</span>
                    </div>

                    <div class="table-body">
                        <c:choose>
                            <c:when test="${not empty salesPage.content}">
                                <c:forEach var="sale" items="${salesPage.content}">
                                    <div class="row">
                                        <span class="course-title">${sale.courseTitle}</span>
                                        <span class="course-amount">
                                            <fmt:formatNumber value="${sale.totalAmount}" type="currency"/>원
                                        </span>
                                    </div>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <div class="empty">데이터가 없습니다.</div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
                <div class="sales-pagination"
                     data-current="${salesPage.number}"
                     data-total="${salesPage.totalPages}">
                </div>
            </section>
        </section>
    </div>
</main>
<script src="/js/pages/teacher/class/settlementPagination.js"></script>
<script src="/js/pages/teacher/class/settlementController.js"></script>
<%@ include file="../common/footer.jsp" %>
</body>
</html>
