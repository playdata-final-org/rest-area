var liked = false
var buttons2 = document.querySelectorAll('.likeButton');
buttons2.forEach((button) => {
console.log(button)
    button.addEventListener('click', function(e) {

        var card = e.target.closest('.card');
        var userId = card.querySelector('.userId').getAttribute('data-user-class');
        var collectionId = card.querySelector('.collection').getAttribute('data-collection-class');

        var likeRequest = {
            userId: userId,
            collectionId: collectionId
        };
        console.log(likeRequest)
        var requestData = JSON.stringify(likeRequest);
        console.log(requestData)
        let is_liked = e.currentTarget.querySelector('i').style.color === "red";
        if (is_liked) {
            var xhr = new XMLHttpRequest();
            e.target.style.color ="";
            xhr.open('DELETE', '/remove', true);
            xhr.setRequestHeader('Content-Type', 'application/json');
            xhr.onreadystatechange = function() {
                if (xhr.readyState === XMLHttpRequest.DONE) {
                    if (xhr.status === 200) {
                        var likeCount = parseInt(card.querySelector('.likeCount').innerText);
                        card.querySelector('.likeCount').innerText = likeCount - 1;
                        liked = false;
                    } else {
                        console.error('좋아요 취소 요청 실패');
                    }
                }
            };
            xhr.send(requestData);
        } else {
            e.target.style.color ="red";
            var xhr = new XMLHttpRequest();
            xhr.open('POST', '/add', true);
            xhr.setRequestHeader('Content-Type', 'application/json');
            xhr.onreadystatechange = function() {
                if (xhr.readyState === XMLHttpRequest.DONE) {
                    if (xhr.status === 200) {
                        var likeCount = parseInt(card.querySelector('.likeCount').innerText);
                        card.querySelector('.likeCount').innerText = likeCount + 1;
                        liked = true;
                    } else {
                        console.error('좋아요 요청 실패');
                    }
                }
            };
            xhr.send(requestData);
        }
    });

});
