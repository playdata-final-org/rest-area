   $(document).ready(function() {
        $('.plan-about button').click(function() {
            loadMembershipContent();
        });
    });
    function loadMembershipContent() {
        loadContent('/blogMembership/'+blogId);
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