var buttons1 = document.querySelectorAll('.toggleButton');
   buttons1.forEach((button) => {
    button.addEventListener('click', function(e){

        var card = e.target.closest('.card');
        var commentHidden = card.querySelector('.commentHidden');
        console.log(commentHidden);
                var maxHeight = '0px';

                if (commentHidden.style.height === maxHeight) {
                    commentHidden.style.height = 'auto';
                    button.innerText = '숨기기';
                } else {
                    commentHidden.style.height = maxHeight;
                    button.innerText = '펼치기';
                }
            });
        });