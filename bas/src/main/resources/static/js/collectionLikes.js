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
                    var likeCount = JSON.parse(xhr.responseText);
                    card.querySelector('.likeCount').innerText = likeCount;
                } else {
                    console.error('좋아요 수 요청 실패');
                }
            }
        };

        xhr.send();
    });
});