var buttons1 = document.querySelectorAll('.toggleButton');
buttons1.forEach((button) => {

    button.addEventListener('click', function(e){
        var card = e.target.closest('.card');
        var commentHidden = card.querySelector('.commentHidden');
        let isCommentHide = commentHidden.style.display === "none" || commentHidden.style.height === "0px";

        if(isCommentHide){
            var maxHeight = '0px';
            var collectionDiv = card.querySelector('.collection');
            var collectionId = collectionDiv.getAttribute('data-collection-class');
            console.log(collectionId);
            var requestData = {
                collectionId: collectionId
            };
            console.log(requestData)

            // 컬렉션 아이디를 객체 형태로 전달합니다.
            $.ajax({
                url: '/comments',
                type: 'POST',
                contentType: "application/json",
                data: JSON.stringify(requestData),
                success: function(response) {
                    displayComments(response, button);
                    console.log(displayComments)
                    commentHidden.style.height = 'auto';
                    commentHidden.style.display = "block";
                    button.innerText = '숨기기';

                },
                error: function(err) {
                    console.error('Error:', err);
                }
            });
        }else{
            commentHidden.style.display = "none";
            button.innerText = '펼치기';
        }

    });
});

function displayComments(response, button) {
    var commentHTML = '';
    response.forEach(function(comment) {
        var imageUrl = comment.profileImageUrl;
        var nickName = comment.nickName;
        var content = comment.content;

        commentHTML += `<section class="comment_box" style="margin: 20px; display: flex; flex-direction: row; padding: 10px;"> 
            <div class="userProfileImg"> 
            <div class="creator-blog-img" style="width: 90px; height: 90px; border-radius: 50%; overflow: hidden;"> 
            <img id="imagePreview1" alt="Profile Image" src="${imageUrl}" style="object-fit: cover; border-radius: 3px; width: 100%; height: 100%;"> 
            </div> 
            </div> 
            <div style="width:100%; display: flex; flex-direction: column; justify-content: space-around;"> 
            <div style="width: 100%; display: flex; justify-content: space-between; align-items: center;"> 
            <span>작성자 : ${nickName}</span> 
            </div> 
            <div style="width: 100%; display: flex; align-items: center;"> 
            <span>내용 : ${content}</span> 
            </div> 
            </div> 
            </section>`;
    });

    var card = button.closest('.card');
    var comment_area = card.querySelector('.commentBoxes');
    comment_area.innerHTML = commentHTML;
}