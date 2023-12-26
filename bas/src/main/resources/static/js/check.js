var contextPath = "";
function checkDuplicateUsername() {
    // 아이디 입력란에서 값을 가져옵니다.
    var username = document.getElementById('username').value;
    // 중복 메시지를 표시할 엘리먼트를 가져옵니다.
    var duplicateUsernameMessage = document.getElementById('duplicateUsernameMessage');

    // 서버로 아이디 중복을 체크하는 POST 요청을 보냅니다.
    $.post(contextPath + '/checkDuplicateUsername', { username: username }, function (response) {
        // 서버에서 받은 응답을 중복 메시지 엘리먼트에 표시합니다.
        duplicateUsernameMessage.textContent = response;
    });
}

function checkDuplicateEmail() {
    // 이메일 입력란에서 값을 가져옵니다.
    var email = document.getElementById('email').value;
    // 중복 메시지를 표시할 엘리먼트를 가져옵니다.
    var duplicateEmailMessage = document.getElementById('duplicateEmailMessage');

    // 서버로 이메일 중복을 체크하는 POST 요청을 보냅니다.
    $.post(contextPath + '/checkDuplicateEmail', { email: email }, function (response) {
        // 서버에서 받은 응답을 중복 메시지 엘리먼트에 표시합니다.
        duplicateEmailMessage.textContent = response;
    });
}