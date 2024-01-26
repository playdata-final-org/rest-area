function signUpValidatePasswords() {
            var Password = document.getElementById('password').value;
            var confirmPassword = document.getElementById('confirmPassword').value;

            var PasswordError = document.getElementById('PasswordError');
            var confirmPasswordError = document.getElementById('confirmPasswordError');

            if (Password !== confirmPassword) {
                PasswordError.textContent = "비밀번호가 일치하지 않습니다.";
                confirmPasswordError.textContent = "비밀번호가 일치하지 않습니다.";
            } else {
                PasswordError.textContent = "";
                confirmPasswordError.textContent = "";
            }
}