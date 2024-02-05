let buttons5 = document.querySelectorAll('.deleteMembership');
buttons5.forEach((button) => {
    button.addEventListener('click', function(e) {

        var card = e.target.closest('.historyCard');

        var boostHistoryId = card.querySelector('.boostHistoryId').getAttribute('data-user-class');

        var requestData = JSON.stringify({ "boostHistoryId": boostHistoryId });

        var xhr = new XMLHttpRequest();
        xhr.open('Post', '/secession', true);
        xhr.setRequestHeader('Content-Type', 'application/json');
        xhr.onreadystatechange = function() {
            if (xhr.readyState === XMLHttpRequest.DONE) {
                if (xhr.status === 200) {
                    var statusElement = card.querySelector('.status');
                    statusElement.innerText = '비열람중';
                    var statusBox = card.querySelector('.status_box');
                    statusBox.style.background = 'red';
                } else {
                    console.error('삭제 요청 실패');
                }
            }
        };
        xhr.send(requestData);
    });
});