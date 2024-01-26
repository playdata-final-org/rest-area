document.addEventListener('DOMContentLoaded', function () {
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

    // 등급 버튼에 대한 클릭 이벤트 리스너 추가
    const gradeButtons = document.querySelectorAll('.collection-membership-choose-select-box button');

        gradeButtons.forEach(button => {
            button.addEventListener('click', function () {
                const grade = button.querySelector('span').innerText;
                toggleGrade(grade);
            });
        });

    // 초기에 섹션을 숨기기
    toggleMembershipSection(false);

    // 초기에 등급 버튼에 selected 클래스 적용
    const initialGradeButton = document.querySelector('.collection-membership-choose-select-box button:nth-child(1)');
        initialGradeButton.classList.add('selected');
});

