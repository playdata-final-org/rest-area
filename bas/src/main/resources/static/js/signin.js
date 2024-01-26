function showSocialLogin() {
    var socialLoginContainer = document.getElementById('socialLoginContainer');
    var overlay = document.getElementById('overlay');
    socialLoginContainer.style.display = 'block';
    overlay.style.display = 'block';
}

function hideSocialLogin() {
    var socialLoginContainer = document.getElementById('socialLoginContainer');
    var overlay = document.getElementById('overlay');
    socialLoginContainer.style.display = 'none';
    overlay.style.display = 'none';
}

document.getElementById('showLoginOptions').addEventListener('click', showSocialLogin);

document.getElementById('overlay').addEventListener('click', hideSocialLogin);