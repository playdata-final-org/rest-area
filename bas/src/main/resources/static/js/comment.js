$(document).ready(function() {
    var commentButtons1 = document.querySelectorAll('.commentButton');

    commentButtons1.forEach((button) => {

        button.addEventListener("click" , function(e) {
            var card = e.target.closest('.card');
            var commentContent = card.querySelector('textarea[name="content"]').value;
            var userId = card.querySelector('.userId').getAttribute('data-user-class');
            var collectionId = card.querySelector('.collection').getAttribute('data-collection-class');
            var userComment = {
                content: commentContent,
                userId: userId,
                collectionId: collectionId
            };
            var requestData = JSON.stringify(userComment);

            $.ajax({
                url: '/saveComment',
                type: 'POST',
                contentType: "application/json",
                data: requestData,
                success: function(response) {
                    displayComment(response,button);
                    $('textarea[name="content"]').val('');
                    console.log(displayComment)
                },
                error: function(err) {
                    console.error('Error:', err);
                }
            });
        });
    });
});

function displayComment(response,button) {
    var imageUrl = response.profileImageUrl;
    console.log(imageUrl)
    var nickName = response.nickName;
    var content = response.content;
    var card = button.closest('.card');
    var comment_area = card.querySelector('.commentBox');
    console.log(comment_area);

    var blogHTML = '<section class="comment_box" style="margin: 20px; display: flex; flex-direction: row; padding: 10px;">' +
        '<div class="userProfileImg">' +
        '<div class="creator-blog-img" style="width: 90px; height: 90px; border-radius: 50%; overflow: hidden;">' +
        '<img id="imagePreview1" alt="Profile Image" src="' + imageUrl + '" style="object-fit: cover; border-radius: 3px; width: 100%; height: 100%;">' +
        '</div>' +
        '</div>' +
        '<div style="width:100%; display: flex; flex-direction: column; justify-content: space-around;">' +
        '<div style="width: 100%; display: flex; justify-content: space-between; align-items: center;">' +
        '<span>' + nickName + '</span>' +
        '</div>' +
        '<div style="width: 100%; display: flex; align-items: center;">' +
        '<span>' + content + '</span>' +
        '</div>' +
        '</div>' +
        '</section>';


    comment_area.innerHTML = blogHTML;
}