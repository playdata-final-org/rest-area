$(document).ready(function() {
    $('#creator-search-bar-titleInput').keydown(function(event) {
        if (event.keyCode === 13) {
            searchBlogs();
        }
    });

    $('#searchButton').click(function() {
            searchBlogs();
        });

function searchBlogs() {
        var category = $(".categoryItem.active").text();
        var userInput = $('#creator-search-bar-titleInput').val().trim();
        if (userInput !== '' || category.trim() !== "") {
            var url = "/blogs/search";
            var data = { userInput: userInput };


            if(category.trim() !== "")
                data = {...data,category : category}
            data = JSON.stringify(data);

            $.ajax({
                type: 'POST',
                url: url,
                contentType: "application/json",
                data: data,
                success: function(response) {
                    displayBlogs(response);
                    // TODO : 추후 로직 변경시 수정할것 (기존 검색어 유지로 변경한 상태)
                    // $('#creator-search-bar-titleInput').val('');
                    console.log(response);
                },
                error: function(err) {
                    console.error('Error:', err);
                }
            });
        } else {

            console.log('검색어를 입력하세요.');
            $('#blogList').html('<p>검색어를 입력하세요.</p>');
        }
    }

    function displayBlogs(response) {
        console.log(response);
        var blogList = $('#blogList');
        blogList.empty();

       if (!Array.isArray(response) || response.length === 0) {
            blogList.append('<p>찾으시는 크리에이터 정보가 없습니다.</p>');
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
});