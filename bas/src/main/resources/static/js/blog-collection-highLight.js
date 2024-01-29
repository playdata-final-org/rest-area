$(document).ready(function() {
        $('.plan-about button').click(function() {
            loadMembershipContent();
             highlightButton('blogMembership');
        });
    });

function handleButtonClick(buttonId, contentLoader) {
    highlightButton(buttonId);
    contentLoader();
}

function highlightButton(buttonId) {
    var highlightElement = document.getElementById('buttonHighlight');
    var button = document.getElementById(buttonId);
    var buttonRect = button.getBoundingClientRect();


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