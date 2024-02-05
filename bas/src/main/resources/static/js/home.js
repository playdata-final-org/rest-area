$(document).ready(function () {
    loadBlogCollectionContent();
});

function loadBlogCollectionContent() {
    loadContent('/blogCollection/' + blogId);
}
function loadBlogCollectionContent1(page) {
    console.log(page)
    var pageSize = 2;
    var pageNumber = page -1;
    loadContent('/blogCollection/' + blogId+'?page='+pageNumber+'&size='+pageSize);
}

function loadMembershipContent() {
    loadContent('/blogMembership/'+blogId);
}

function loadBlogAboutContent() {
    loadContent('/blogAbout/'+blogId);
}

function loadContent(page) {
    $.ajax({
        url: page,
        type: 'GET',
        success: function (data) {
            $('#blogContentContainer').html(data);
        },
        error: function () {
            alert('콘텐츠 로드에 실패ㅠㅠ.');
        }
    });
}