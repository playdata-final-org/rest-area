function handleFileChoose(input) {
    var filenames = Array.from(input.files).map(file => file.name).join(', ');
    updateFilenameDisplay(filenames);
}

function updateFilenameDisplay(filenames) {
    var filenameDisplay = document.getElementById("filenameDisplay");
    filenameDisplay.textContent = '선택된 파일: ' + filenames;
}

document.getElementById("file").addEventListener("change", function () {
    handleFileChoose(this);
});