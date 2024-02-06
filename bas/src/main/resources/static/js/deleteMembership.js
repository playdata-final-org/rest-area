let buttons5 = document.querySelectorAll('.deleteMembership');
buttons5.forEach((button) => {
    button.addEventListener('click', function(e) {
        var card = e.target.closest('.historyCard');
        var expirationDate = card.querySelector('.expirationDate').textContent;
        var membership = card.querySelector('.membership').textContent;
        var confirmMessage = membership + " 멤버십에 탈퇴하시겠습니까? 탈퇴 후 복구는 하실수 없으며 " + expirationDate + " 까지 컬렉션 다운로드가 가능합니다.";

        if (confirm(confirmMessage)) {
            var boostHistoryId = card.querySelector('.boostHistoryId').getAttribute('data-user-class');
            var requestData = JSON.stringify({ "boostHistoryId": boostHistoryId });

            var xhr = new XMLHttpRequest();
            xhr.open('Post', '/secession', true);
            xhr.setRequestHeader('Content-Type', 'application/json');
            xhr.onreadystatechange = function() {
                if (xhr.readyState === XMLHttpRequest.DONE) {
                    if (xhr.status === 200) {
                        var statusElement = card.querySelector('.status');
                        statusElement.innerText = '탈퇴 예약';
                        var statusBox = card.querySelector('.status_box');
                        statusBox.style.background = 'red';
                        button.style.display = 'none'; // hide the delete button after successful reservation
                        alert(membership + " 멤버십이 탈퇴 예약 되었습니다.");
                    } else {
                        console.error('삭제 요청 실패');
                    }
                }
            };
            xhr.send(requestData);
        } else {
            alert("탈퇴 예약이 취소되었습니다.");
            return false;
        }
    });
})