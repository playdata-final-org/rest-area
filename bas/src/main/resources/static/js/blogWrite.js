function toggleMembershipSection(show) {
    const section = document.querySelector('.collection-membership-choose-select');
    section.style.display = show ? 'block' : 'none';
}

function toggleMembership(membershipType) {
    const buttons = document.querySelectorAll('.collection-membership-choose-box button');

    buttons.forEach(button => {
        button.classList.remove('selected');
    });

    const selectedButton =
        document.querySelector(`.collection-membership-choose-box button[name="membershipType"][data-membership-type="${membershipType}"]`);
    selectedButton.classList.add('selected');

    toggleMembershipSection(membershipType === 'paidPublic');
}

function toggleGrade(tierId) {
    const gradeButtons = document.querySelectorAll('.collection-membership-choose-select-box button');

    gradeButtons.forEach(button => {
        button.classList.remove('selected');
    });

    const selectedGradeButton =
        document.querySelector(`.collection-membership-choose-select-box button[name="tierId"][data-tier-id="${tierId}"]`);

    if (selectedGradeButton) {
        selectedGradeButton.classList.add('selected');
    }
}

document.getElementById('blogForm').addEventListener('submit', function (event) {
    const selectedMembershipType =
        document.querySelector('.collection-membership-choose-box button[name="membershipType"].selected')
        .getAttribute('data-membership-type');

    const selectedGradeButton =
        document.querySelector('.collection-membership-choose-select-box button[name="tierId"].selected');

    const tierId = selectedGradeButton.getAttribute('data-tier-id');

    const membershipTypeInput = document.createElement('input');
    membershipTypeInput.type = 'hidden';
    membershipTypeInput.name = 'membershipType';
    membershipTypeInput.value = selectedMembershipType;
    event.target.appendChild(membershipTypeInput);

    const gradeInput = document.createElement('input');
    gradeInput.type = 'hidden';
    gradeInput.name = 'tierId';
    gradeInput.value = tierId;
    event.target.appendChild(gradeInput);
    console.log(tierId);

    return true;
});

function containsText(element, text) {
    return element.textContent.toUpperCase().includes(text.toUpperCase());
}