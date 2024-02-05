$(document).ready(function () {
    loadBlogHomeContent();
});

function loadBlogHomeContent() {
    loadContent('/myPage/' + userId);
}

function loadBlogCollectionContent() {
    loadContent('/pointHistory/' + userId);
}

function loadMembershipContent() {
    loadContent('/boostHistory/'+ userId);
}

function loadBlogAboutContent() {
    loadContent('/boostDetailPage/'+userId);
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