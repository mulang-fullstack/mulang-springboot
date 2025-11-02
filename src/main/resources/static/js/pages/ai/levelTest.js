/**
 * AI 레벨 테스트 팝업 JavaScript (30일 제한 적용)
 */
let testState = {
    testId: null,
    currentQuestion: 0,
    questions: [],
    userAnswers: [],
    language: '',
    level: 'A1'
};

/**
 * 페이지 로드 시 초기화
 */
document.addEventListener('DOMContentLoaded', function() {
    showLanguageSelection();
});

/**
 * 1단계: 언어 선택 화면
 */
function showLanguageSelection() {
    const wrapper = document.querySelector('.test-wrapper');
    wrapper.innerHTML = `
        <header class="test-header">
            <h1 class="test-title">AI 레벨테스트</h1>
            <div class="test-line"></div>
        </header>
        
        <article class="test-question-box">
            <p class="test-question-text">테스트할 언어를 선택하세요</p>
            <p class="test-subtext">AI가 맞춤형 문제를 생성합니다</p>
            <p class="test-subtext" style="color: var(--text-error); margin-top: 0.5rem;">
                ⚠️ 각 언어별로 한 달에 한 번만 테스트 가능합니다
            </p>
        </article>
        
        <div class="test-options" id="languageOptions">
            <button class="option-item" onclick="checkAndSelectLanguage('ENGLISH')">
                <strong>영어</strong><br>
                <small>English Level Test</small>
            </button>
            <button class="option-item" onclick="checkAndSelectLanguage('JAPANESE')">
                <strong>일본어</strong><br>
                <small>日本語レベルテスト</small>
            </button>
            <button class="option-item" onclick="checkAndSelectLanguage('CHINESE')">
                <strong>중국어</strong><br>
                <small>中文水平测试</small>
            </button>
        </div>
        
        <footer class="test-footer">
            <button class="footer-button" onclick="window.close()">취소</button>
        </footer>
    `;
}

/**
 * 2-1단계: 테스트 가능 여부 확인 (새로 추가!)
 */
async function checkAndSelectLanguage(language) {
    showLoading('테스트 가능 여부를 확인하고 있습니다...');

    try {
        // 테스트 가능 여부 확인
        const checkResponse = await fetch(`/ai/level/api/check-availability?language=${language}`);
        const checkResult = await checkResponse.json();

        if (!checkResult.success) {
            alert(checkResult.message || '오류가 발생했습니다.');
            showLanguageSelection();
            return;
        }

        const availability = checkResult.data;

        if (!availability.available) {
            // 테스트 불가능 - 안내 메시지 표시
            showRestrictionMessage(language, availability);
            return;
        }

        // 테스트 가능 - 문제 생성 진행
        selectLanguage(language);

    } catch (error) {
        console.error('Error:', error);
        alert('오류가 발생했습니다. 다시 시도해주세요.');
        showLanguageSelection();
    }
}

/**
 * 2-2단계: 제한 안내 메시지 표시
 */
function showRestrictionMessage(language, availability) {
    const languageNames = {
        'ENGLISH': '영어',
        'JAPANESE': '일본어',
        'CHINESE': '중국어'
    };

    const lastTestDate = new Date(availability.lastTestDate);
    const nextAvailableDate = new Date(availability.nextAvailableDate);

    const wrapper = document.querySelector('.test-wrapper');
    wrapper.innerHTML = `
        <header class="test-header">
            <h1 class="test-title">⏰ 테스트 제한</h1>
            <div class="test-line"></div>
        </header>
        
        <article class="test-question-box" style="padding: 3rem 2rem;">
            <div style="text-align: center; margin-bottom: 2rem;">
                <div style="
                    width: 80px;
                    height: 80px;
                    background: var(--bg-error);
                    border-radius: 50%;
                    display: flex;
                    align-items: center;
                    justify-content: center;
                    margin: 0 auto 1.5rem;
                    font-size: 3rem;
                ">
                    🚫
                </div>
                <h2 style="font-size: 1.5rem; color: var(--text-primary); margin-bottom: 1rem;">
                    ${languageNames[language]} 테스트를 진행할 수 없습니다
                </h2>
                <p style="font-size: 1.1rem; color: var(--text-secondary); line-height: 1.6;">
                    테스트는 한 달에 한 번만 가능합니다.
                </p>
            </div>
            
            <div style="background: var(--bg-secondary); padding: 2rem; border-radius: 1rem; text-align: left;">
                <div style="margin-bottom: 1.5rem;">
                    <p style="color: var(--text-secondary); font-size: 0.9rem; margin-bottom: 0.5rem;">
                        마지막 테스트 날짜
                    </p>
                    <p style="color: var(--text-primary); font-size: 1.2rem; font-weight: 700;">
                        ${formatDate(lastTestDate)}
                    </p>
                </div>
                
                <div style="margin-bottom: 1.5rem;">
                    <p style="color: var(--text-secondary); font-size: 0.9rem; margin-bottom: 0.5rem;">
                        다음 테스트 가능일
                    </p>
                    <p style="color: var(--text-point-main); font-size: 1.2rem; font-weight: 700;">
                        ${formatDate(nextAvailableDate)}
                    </p>
                </div>
                
                <div style="
                    background: var(--bg-point-sub);
                    padding: 1rem;
                    border-radius: 0.5rem;
                    text-align: center;
                ">
                    <p style="color: var(--text-point-main); font-size: 1.3rem; font-weight: 700;">
                        ${availability.daysRemaining}일 남음
                    </p>
                </div>
            </div>
            
            <div style="margin-top: 2rem; padding: 1rem; background: var(--bg-warning); border-radius: 0.5rem;">
                <p style="color: var(--text-secondary); font-size: 0.9rem; line-height: 1.6;">
                    💡 <strong>Tip:</strong> 다른 언어의 레벨 테스트는 가능합니다!<br>
                    새로운 언어에 도전해보세요.
                </p>
            </div>
        </article>
        
        <footer class="test-footer" style="gap: 1rem;">
            <button class="footer-button" onclick="showLanguageSelection()" style="flex: 1;">
                다른 언어 선택
            </button>
            <button class="footer-button" onclick="window.close()" style="flex: 1;">
                닫기
            </button>
        </footer>
    `;
}

/**
 * 날짜 포맷팅
 */
function formatDate(date) {
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    return `${year}년 ${month}월 ${day}일`;
}

/**
 * 3단계: 언어 선택 및 문제 생성
 */
async function selectLanguage(language) {
    testState.language = language;

    showLoading('AI가 문제를 생성하고 있습니다...');

    try {
        const response = await fetch('/ai/level/api/start', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                language: language,
                level: testState.level
            })
        });

        const result = await response.json();

        if (result.success) {
            testState.testId = result.data.testId;
            testState.questions = result.data.questions;
            testState.currentQuestion = 0;
            testState.userAnswers = new Array(result.data.totalQuestions).fill(null);

            showQuestion();
        } else {
            alert(result.message || '문제 생성에 실패했습니다.');
            showLanguageSelection();
        }
    } catch (error) {
        console.error('Error:', error);
        alert('오류가 발생했습니다. 다시 시도해주세요.');
        showLanguageSelection();
    }
}

/**
 * 4단계: 문제 표시
 */
function showQuestion() {
    const question = testState.questions[testState.currentQuestion];
    const questionNum = testState.currentQuestion + 1;
    const total = testState.questions.length;

    // 이전에 선택한 답변
    const selectedAnswer = testState.userAnswers[testState.currentQuestion];

    const wrapper = document.querySelector('.test-wrapper');
    wrapper.innerHTML = `
        <header class="test-header">
            <h1 class="test-title">AI 레벨테스트 (${questionNum} / ${total})</h1>
            <div class="test-line"></div>
        </header>
        
        <article class="test-question-box">
            <p class="test-question-text">${question.question}</p>
        </article>
        
        <div class="test-options" id="optionsContainer">
            ${question.choices.map((choice, index) => `
                <button 
                    class="option-item ${selectedAnswer === choice ? 'selected' : ''}" 
                    data-choice="${escapeHtml(choice)}"
                    onclick="selectAnswer(${index})">
                    ${index + 1}. ${choice}
                </button>
            `).join('')}
        </div>
        
        <footer class="test-footer">
            ${questionNum > 1 ? `
                <button class="footer-button" onclick="previousQuestion()" style="margin-right: 10px;">이전</button>
            ` : ''}
            <button class="footer-button next" onclick="nextQuestion()">
                ${questionNum === total ? '제출하기' : '다음 문제'}
            </button>
        </footer>
    `;
}

/**
 * 답변 선택 (수정됨 - index 기반)
 */
function selectAnswer(choiceIndex) {
    const question = testState.questions[testState.currentQuestion];
    const selectedChoice = question.choices[choiceIndex];

    // 답변 저장
    testState.userAnswers[testState.currentQuestion] = selectedChoice;

    // UI 업데이트 - 모든 버튼에서 selected 제거
    const buttons = document.querySelectorAll('.option-item');
    buttons.forEach(btn => {
        btn.classList.remove('selected');
        btn.style.background = '';
        btn.style.color = '';
    });

    // 선택한 버튼만 하이라이트
    buttons[choiceIndex].classList.add('selected');
    buttons[choiceIndex].style.background = 'var(--bg-point-main)';
    buttons[choiceIndex].style.color = 'var(--text-white)';
}

/**
 * 다음 문제
 */
function nextQuestion() {
    if (testState.userAnswers[testState.currentQuestion] === null) {
        alert('답변을 선택해주세요.');
        return;
    }

    if (testState.currentQuestion === testState.questions.length - 1) {
        // 마지막 문제 - 제출
        submitTest();
    } else {
        // 다음 문제로
        testState.currentQuestion++;
        showQuestion();
    }
}

/**
 * 이전 문제
 */
function previousQuestion() {
    if (testState.currentQuestion > 0) {
        testState.currentQuestion--;
        showQuestion();
    }
}

/**
 * 5단계: 답안 제출
 */
async function submitTest() {
    if (!confirm('테스트를 제출하시겠습니까?')) {
        return;
    }

    showLoading('채점 중입니다...');

    try {
        const answers = testState.userAnswers.map((answer, index) => ({
            questionNumber: index + 1,
            answer: answer
        }));

        const response = await fetch('/ai/level/api/submit', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                testId: testState.testId,
                answers: answers
            })
        });

        const result = await response.json();

        if (result.success) {
            showResult(result.data);
        } else {
            alert(result.message || '제출에 실패했습니다.');
        }
    } catch (error) {
        console.error('Error:', error);
        alert('오류가 발생했습니다. 다시 시도해주세요.');
    }
}

/**
 * 6단계: 결과 표시
 */
function showResult(data) {
    const wrapper = document.querySelector('.test-wrapper');

    const correctCount = data.correctAnswers;
    const totalCount = data.totalQuestions;
    const score = data.score;
    const resultLevel = data.resultLevelKorean;
    const languageKorean = data.languageKorean;

    wrapper.innerHTML = `
        <header class="test-header">
            <h1 class="test-title">테스트 결과</h1>
            <div class="test-line"></div>
        </header>
        
        <article class="test-question-box" style="padding: 3rem 2rem;">
            <div class="result-score">
                <h2 style="font-size: 3rem; color: var(--text-point-main); margin-bottom: 1rem;">
                    ${score}점
                </h2>
                <p style="font-size: 1.3rem; color: var(--text-secondary); margin-bottom: 2rem;">
                    ${correctCount} / ${totalCount} 문제 정답
                </p>
            </div>
            
            <div class="result-level" style="background: var(--bg-point-sub); padding: 1.5rem; border-radius: 1rem; margin-bottom: 2rem;">
                <h3 style="font-size: 1.5rem; color: var(--text-point-main); margin-bottom: 0.5rem;">
                    ${languageKorean} ${resultLevel} 수준
                </h3>
                <p style="color: var(--text-secondary); line-height: 1.6;">
                    ${data.resultDescription}
                </p>
            </div>
            
            <div class="result-feedback" style="background: var(--bg-secondary); padding: 1.5rem; border-radius: 1rem; text-align: left;">
                <h4 style="font-size: 1.1rem; color: var(--text-primary); margin-bottom: 1rem;">
                    📌 학습 가이드
                </h4>
                <p style="color: var(--text-secondary); line-height: 1.8; white-space: pre-line;">
                    ${data.feedback}
                </p>
            </div>
            
            <div style="margin-top: 1.5rem; padding: 1rem; background: var(--bg-warning); border-radius: 0.5rem; text-align: center;">
                <p style="color: var(--text-secondary); font-size: 0.9rem;">
                    ⏰ 다음 ${languageKorean} 테스트는 <strong>30일 후</strong> 가능합니다
                </p>
            </div>
        </article>
        
        <footer class="test-footer" style="gap: 1rem;">
            <button class="footer-button" onclick="showDetailedResults()" style="flex: 1;">
                상세 결과 보기
            </button>
            <button class="footer-button" onclick="window.close()" style="flex: 1;">
                닫기
            </button>
        </footer>
    `;

    // 상세 결과 데이터 저장
    window.detailedResults = data.questionResults;
}

/**
 * 상세 결과 보기
 */
function showDetailedResults() {
    const results = window.detailedResults;
    if (!results) return;

    const wrapper = document.querySelector('.test-wrapper');
    wrapper.innerHTML = `
        <header class="test-header">
            <h1 class="test-title">상세 결과</h1>
            <div class="test-line"></div>
        </header>
        
        <div style="width: 100%; max-height: 60vh; overflow-y: auto; padding: 1rem;">
            ${results.map((result, index) => `
                <div class="result-item" style="
                    background: var(--bg-white);
                    border: var(--border-default);
                    border-left: 4px solid ${result.isCorrect ? 'var(--text-success)' : 'var(--text-error)'};
                    border-radius: 0.5rem;
                    padding: 1.5rem;
                    margin-bottom: 1rem;
                ">
                    <p style="font-weight: 700; color: var(--text-primary); margin-bottom: 0.5rem;">
                        문제 ${result.questionNumber}. ${result.isCorrect ? '✅ 정답' : '❌ 오답'}
                    </p>
                    <p style="color: var(--text-secondary); margin-bottom: 1rem;">
                        ${result.question}
                    </p>
                    <div style="background: var(--bg-secondary); padding: 1rem; border-radius: 0.5rem;">
                        <p style="color: var(--text-secondary); margin-bottom: 0.5rem;">
                            <strong>내 답변:</strong> ${result.userAnswer || '(답변 안 함)'}
                        </p>
                        <p style="color: var(--text-success);">
                            <strong>정답:</strong> ${result.correctAnswer}
                        </p>
                    </div>
                </div>
            `).join('')}
        </div>
        
        <footer class="test-footer">
            <button class="footer-button" onclick="window.close()">닫기</button>
        </footer>
    `;
}

/**
 * 로딩 화면
 */
function showLoading(message) {
    const wrapper = document.querySelector('.test-wrapper');
    wrapper.innerHTML = `
        <div style="display: flex; flex-direction: column; align-items: center; justify-content: center; min-height: 50vh;">
            <div class="loading-spinner" style="
                border: 4px solid var(--bg-secondary);
                border-top: 4px solid var(--bg-point-main);
                border-radius: 50%;
                width: 50px;
                height: 50px;
                animation: spin 1s linear infinite;
                margin-bottom: 1rem;
            "></div>
            <p style="color: var(--text-secondary); font-size: 1.1rem;">${message}</p>
        </div>
    `;
}

/**
 * HTML 이스케이프
 */
function escapeHtml(text) {
    const div = document.createElement('div');
    div.textContent = text;
    return div.innerHTML;
}

// CSS 애니메이션 추가
const style = document.createElement('style');
style.textContent = `
    @keyframes spin {
        0% { transform: rotate(0deg); }
        100% { transform: rotate(360deg); }
    }
    
    .option-item.selected {
        background: var(--bg-point-main) !important;
        color: var(--text-white) !important;
        border-color: var(--bg-point-main) !important;
    }
    
    .option-item {
        transition: all 0.2s ease;
    }
    
    .option-item:hover {
        transform: translateY(-2px);
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    }
`;
document.head.appendChild(style);