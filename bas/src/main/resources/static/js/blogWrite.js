function toggleMembershipSection(show) {
    const section = document.querySelector('.collection-membership-choose-select');
    section.style.display = show ? 'block' : 'none';
}

function toggleMembership(membershipType) {
    const buttons = document.querySelectorAll('.collection-membership-choose-box button');

    buttons.forEach(button => {
        button.classList.remove('selected');
    });

    const selectedButton = document.querySelector(`.collection-membership-choose-box button:nth-child(${membershipType === 'allPublic' ? 1 : 2})`);
    selectedButton.classList.add('selected');

    // 섹션을 표시하거나 숨기기
    toggleMembershipSection(membershipType === 'paidPublic');
}

function toggleGrade(grade) {
        const gradeButtons = document.querySelectorAll('.collection-membership-choose-select-box button');

        gradeButtons.forEach(button => {
            button.classList.remove('selected');
        });

        const selectedGradeButton = Array.from(gradeButtons).find(button => containsText(button.querySelector('span'), grade));

        if (selectedGradeButton) {
            selectedGradeButton.classList.add('selected');
        }
    }

document.getElementById('blogForm').addEventListener('submit', function (event) {
    const selectedMembershipType = document.querySelector('.collection-membership-choose-box button.selected span').innerText;
    const selectedGrade = document.querySelector('.collection-membership-choose-select-box button.selected span').innerText;

    console.log('폼 제출됨!', selectedMembershipType, selectedGrade);

    // 폼을 실제로 서버로 제출하는 로직을 여기에 추가하세요.
    // 예: event.preventDefault(); 와 AJAX 요청 등
});
// 대체된 containsText 함수
function containsText(element, text) {
    return element.textContent.toUpperCase().includes(text.toUpperCase());
}