$(document).ready(function () {
    loadBlogHomeContent();
});

function loadBlogHomeContent() {
    loadContent('/blogHome');
}

function loadBlogCollectionContent() {
    loadContent('/blogCollection/'+blogId);
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