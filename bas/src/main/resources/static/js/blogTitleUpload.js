$(document).ready(function() {
    $('#coverImageInput').on('change', function(e) {
        var file = e.target.files[0];
        var formData = new FormData();
        formData.append('coverImage', file);
         var url = '/blog/' + blogId + '/upload';

        $.ajax({
            url: url,
            type: 'POST',
            data: formData,
            processData: false,
            contentType: false,
            success: function(response) {
                console.log('파일이 서버로 성공적으로 업로드되었습니다.');
            },
            error: function(xhr, status, error) {
                console.error('파일 업로드 중 오류가 발생했습니다:', error);
            }
        });

    });
});

