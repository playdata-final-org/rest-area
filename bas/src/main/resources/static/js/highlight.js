window.addEventListener('load', function() {
    var homeButtonId = 'blogHome';
    highlightButton(homeButtonId);
});

function handleButtonClick(buttonId, contentLoader) {
    highlightButton(buttonId);
    contentLoader();
}

function highlightButton(buttonId) {
    var highlightElement = document.getElementById('buttonHighlight');
    var button = document.getElementById(buttonId);
    var buttonRect = button.getBoundingClientRect();

    // 수정된 부분
    highlightElement.style.position = 'relative';
    highlightElement.style.top = '1px';
    highlightElement.style.left = '0';
    highlightElement.style.marginLeft = buttonRect.left + 'px';
    highlightElement.style.width = buttonRect.width + 'px';
    highlightElement.style.height = '5px';
    highlightElement.style.backgroundColor = '#3498db';
}

window.addEventListener('resize', function() {
    var activeButtonId = document.querySelector('.bc button.active').id;
    highlightButton(activeButtonId);
});

window.addEventListener('scroll', function() {
    var activeButtonId = document.querySelector('.bc button.active').id;
    highlightButton(activeButtonId);
});