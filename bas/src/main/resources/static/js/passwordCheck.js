 function validateNewPassword() {
        var newPassword = document.getElementById('newPassword').value;
        var oldPassword = document.getElementById('oldPassword').value;

        var newPasswordError = document.getElementById('newPasswordError');

        if (newPassword === oldPassword) {
            newPasswordError.textContent = "기존 비밀번호와 일치합니다. 다른 비밀번호를 사용하세요.";
        } else {
            newPasswordError.textContent = "";
        }
    }

    function validatePasswords() {
        var newPassword = document.getElementById('newPassword').value;
        var confirmNewPassword = document.getElementById('confirmNewPassword').value;

        var newPasswordError = document.getElementById('newPasswordError');
        var confirmNewPasswordError = document.getElementById('confirmNewPasswordError');

        if (newPassword !== confirmNewPassword) {
            newPasswordError.textContent = "비밀번호가 일치하지 않습니다.";
            confirmNewPasswordError.textContent = "비밀번호가 일치하지 않습니다.";
            return false;
        } else {
            newPasswordError.textContent = "";
            confirmNewPasswordError.textContent = "";
        }
    }