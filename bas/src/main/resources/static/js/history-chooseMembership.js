let buttons7 = document.querySelectorAll('.updateMembership');
buttons7.forEach((button) => {
    button.addEventListener('click', function (e) {
        var card = e.target.closest('.historyCard');
        var blogIdElement = card.querySelector('.blogId');
        var blogId = blogIdElement.getAttribute('data-user-class');
        if (blogId !== undefined) {
            loadMembershipContent(blogId);
        } else {
            console.error('blogId is undefined');
        }
    });
});

    function loadMembershipContent(blogId) {
        loadContent('/blogMembership/' + blogId);
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
