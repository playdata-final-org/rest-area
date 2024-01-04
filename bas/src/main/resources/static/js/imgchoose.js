function imageChoose(input) {
    var previewImage = document.getElementById('imagePreview');

    // 선택한 파일이 있는 경우
    if (input.files && input.files[0]) {
        var reader = new FileReader();

        reader.onload = function (e) {
            previewImage.src = e.target.result;
        };

        reader.readAsDataURL(input.files[0]);
    } else {
        // 선택한 파일이 없는 경우 기본 이미지를 표시
        previewImage.src = 'assets/ai.png';
    }

}