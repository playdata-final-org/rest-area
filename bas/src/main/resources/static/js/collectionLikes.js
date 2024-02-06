var button3 = document.querySelectorAll('.mainToggleButton');

button3.forEach((button) => {
    button.addEventListener('click', function(e) {
        var card = e.target.closest('.card');
        var collectionId = card.querySelector('.collection').getAttribute('data-collection-class');

        var xhr = new XMLHttpRequest();
        xhr.open('GET', '/likeCount/' + collectionId, true);

        xhr.onreadystatechange = function() {
            if (xhr.readyState === XMLHttpRequest.DONE) {
                if (xhr.status === 200) {
                    var like = JSON.parse(xhr.responseText);
                    let like_btn = card.querySelector('.likeCount');
                    like_btn.innerText = like?.count;
                    // 추후 수정 필요.
                    if(like.is_liked){
                        like_btn.style.color ="red";
                        like_btn.classList.add('active');   // 임시용
                    }
                } else {
                    console.error('좋아요 수 요청 실패');
                }
            }
        };

        xhr.send();
    });
});