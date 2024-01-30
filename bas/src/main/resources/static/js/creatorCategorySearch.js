$(document).ready(function() {
    $('.categoryItem').on('click', function() {
        var category = $(this).text();

        var url = "/blogs/category";
        var data = JSON.stringify({ category: category });

        $.ajax({
            type: "POST",
            url: url,
            contentType: "application/json",
            data: data,
            success: function(response) {
                displayBlogs(response);

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

    if (response.length === 0) {
        blogList.append('<p>찾으시는 크리에이터가 없습니다.</p>');
    } else {
        $.each(response, function(index, blog) {
            var blogId = blog.blogId;

            var imageUrl = blog.imageUrl;

            var nickName = blog.nickName;

            var blogAbout = blog.blogAbout;

            var boosterCount = blog.boosterCount;

            var collectionCount = blog.collectionCount;

            var blogHTML = '<section class="creator-search-list">' +
                '<div class="creator-search-info">' +
                '<a href="/blog/' + blogId + '">' +
                '<div class="creator-blog-img" style="width: 90px; height: 90px; border-radius: 50%; overflow: hidden;">' +
                '<img src="' + imageUrl + '" alt="Profile Image" style="object-fit: cover; border-radius: 3px; width: 100%; height: 100%;">' +
                '</div>' +
                '<div>' +
                '<h5>' + nickName + '</h5>' +
                '<span>' + blogAbout + '</span>' +
                '<span>게시글: ' + boosterCount + '</span>' +
                '<span>컬렉션 수: ' + collectionCount + '</span>' +
                '</div>' +
                '</a>' +
                '</div>' +
                '</section>';
            blogList.append(blogHTML);
        });
    }
}