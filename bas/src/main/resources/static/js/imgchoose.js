function imageChoose(input) {
    var previewImage = document.getElementById('imagePreview');

    if (input.files && input.files[0]) {
        var reader = new FileReader();

        reader.onload = function (e) {
            previewImage.src = e.target.result;
        };

        reader.readAsDataURL(input.files[0]);
    } else {

        previewImage.src = 'assets/ai.png';
    }

}