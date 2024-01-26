function submitForm() {
    var username = document.getElementById("username").value;
    var password = document.getElementById("password").value;

    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/signin", true);
    xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");

    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                var response = JSON.parse(xhr.responseText);
                if (response.success) {
                    showMessage("로그인 성공!");
                } else {
                    showMessage("아이디 또는 비밀번호가 잘못되었습니다.");
                }
            } else {
                showMessage("로그인 요청에 실패했습니다.");
            }
        }
    };

    var data = JSON.stringify({
        username: username,
        password: password
    });

    xhr.send(data);
}

function showMessage(message) {

    document.getElementById("message").innerText = message;
}