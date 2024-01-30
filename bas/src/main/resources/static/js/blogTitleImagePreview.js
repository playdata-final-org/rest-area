 $(document).ready(function() {
        $('#coverImageInput').on('change', function(e) {
            var file = e.target.files[0];
            var reader = new FileReader();

            reader.onload = function(e) {
                $('#blogMainPreview').attr('src', e.target.result);
            };

            reader.readAsDataURL(file);
        });
    });