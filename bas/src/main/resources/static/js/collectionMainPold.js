var buttons0 = document.querySelectorAll('.mainToggleButton');
buttons0.forEach((button) => {
    button.addEventListener('click', function(e) {
        console.log(e.target.closest('.card'));
        var card = e.target.closest('.card');
        var altoBox = card.querySelector('.altoBox');
        var maxHeight = '150px';

        if (altoBox.style.height === maxHeight) {
            altoBox.style.height = 'auto';
            button.innerText = '숨기기';
        } else {
            altoBox.style.height = maxHeight;
            button.innerText = '펼치기';
        }
    });
});
