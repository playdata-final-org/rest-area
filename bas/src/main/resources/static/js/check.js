var contextPath = "";
function checkDuplicateUsername() {

    var username = document.getElementById('username').value;

    var duplicateUsernameMessage = document.getElementById('duplicateUsernameMessage');

    $.post(contextPath + '/signup/checkDuplicateUsername', { username: username }, function (response) {

        duplicateUsernameMessage.textContent = response;
    });
}

function checkDuplicateNickName() {

    var nickName = document.getElementById('nickName').value;

    var duplicateNickNameMessage = document.getElementById('duplicateNickNameMessage');

    $.post(contextPath + '/signup/checkDuplicateNickName', { nickName: nickName }, function (response) {

        duplicateNickNameMessage.textContent = response;
    });
}

function checkDuplicatePassword() {
        var password = document.getElementById('oldPassword').value;
        var duplicatePasswordMessage = document.getElementById('duplicatePasswordMessage');

        $.post(contextPath + '/signup/checkDuplicatePassword', { password: password }, function (response) {
            duplicatePasswordMessage.textContent = response;
        });
    }

 function validatePasswords() {
        var Password = document.getElementById('Password').value;
        var confirmNewPassword = document.getElementById('confirmNewPassword').value;

        var PasswordError = document.getElementById('PasswordError');
        var confirmNewPasswordError = document.getElementById('confirmNewPasswordError');

        if (Password !== confirmNewPassword) {
            PasswordError.textContent = "비밀번호가 일치하지 않습니다.";
            confirmNewPasswordError.textContent = "비밀번호가 일치하지 않습니다.";
        } else {
            PasswordError.textContent = "비밀번호가 일치합니다.";
            confirmNewPasswordError.textContent = "비밀번호가 일치합니다.";
        }
    }