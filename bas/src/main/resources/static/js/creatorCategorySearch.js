$(document).ready(function() {
    $('.categoryItem').on('click', function(e) {

        let category = '';
        $(".categoryItem").css("background","");
        $(e.target).css("background","#db4b44");

        // TODO : 아래 코드 나중에 클래스화 시켜 변경할것
        var userInput = $('#creator-search-bar-titleInput').val().trim();
        if(!$(e.target).hasClass('active')){
            $(e.target).addClass("active");
            category = $(e.target).text();
        }else{
            $(".categoryItem").removeClass("active");
            $(".categoryItem").css("background","");

        }


        var url = "/blogs/search";

        let data = {};


        if (userInput !== '')
            data = { ...data, userInput: userInput };
        data = JSON.stringify({...data, category: category });

        $.ajax({
            type: "POST",
            url: url,
            contentType: "application/json",
            data: data,
            success: function(response) {
                displayBlogs(response);
                if (userInput === '' && category === ''){
                    $('#blogList').html('<p>검색어를 입력하세요.</p>');
                }
            },
            error: function(xhr, status, error) {
                console.error(error);
            }
        });
    });
});

function displayBlogs(response) {
    console.log(response);
    var blogList = $('#blogList');
    blogList.empty();

    if (!Array.isArray(response) || response.length === 0) {
        blogList.html('<p>찾으시는 크리에이터가 없습니다.</p>');
    } else {
        $.each(response, function(index, blog) {
            var blogId = blog.blogId;

            var imageUrl = blog.imageUrl;

            var nickName = blog.nickName;

            var blogAbout = blog.blogAbout;

            var boosterCount = blog.boosterCount;

            var collectionCount = blog.collectionCount;

             var category = blog.category;

            var blogHTML = '<section class="creator-search-list">' +
                '<div class="creator-search-info">' +
                '<a href="/blog/' + blogId + '">' +
                '<div class="creator-blog-img" style="width: 90px; height: 90px; border-radius: 50%; overflow: hidden;">' +
                '<img src="' + imageUrl + '" alt="Profile Image" style="object-fit: cover; border-radius: 3px; width: 100%; height: 100%;">' +
                '</div>' +
                '<div>' +
                '<h5>' + nickName + '</h5>' +
                '<span>' + category + '</span>' +
                '<span>' + blogAbout + '</span>' +
                '<span>회원 수: ' + boosterCount + '</span>' +
                '<span>컬렉션 수: ' + collectionCount + '</span>' +
                '</div>' +
                '</a>' +
                '</div>' +
                '</section>';
            blogList.append(blogHTML);
        });
    }
}