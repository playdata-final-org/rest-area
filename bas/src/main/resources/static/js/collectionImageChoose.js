var containerIds = ["imagePreviewContainer", "secondImagePreviewContainer", "thirdImagePreviewContainer"];
var currentContainerIndex = 0;

var deleteIcon = document.createElement("span");
deleteIcon.className = "delete-icon";
deleteIcon.innerHTML = "&times;";
deleteIcon.style.cursor = "pointer";

function handleImageChoose(input) {
    if (currentContainerIndex >= containerIds.length) {
        return;
    }
    var containerId = containerIds[currentContainerIndex];
    var container = document.getElementById(containerId);
    var imageAddContainer = document.querySelector(".imageAddContainer");

    var secondImageAddLabel = document.querySelector('.secondImageAddContainer label');
    var thirdImageAddContainer = document.querySelector('.thirdImageAddContainer');
    var thirdImageAddLabel = document.querySelector('.thirdImageAddContainer label');

    if (input.files && input.files.length > 0) {
        for (var i = 0; i < input.files.length; i++) {
            var reader = new FileReader();
            reader.onload = function (e) {
                var img = document.createElement("img");
                img.src = e.target.result;

                var previewContainer = document.createElement("div");
                previewContainer.className = "image-preview";
                previewContainer.appendChild(img);
                previewContainer.appendChild(deleteIcon.cloneNode(true));

                container.appendChild(previewContainer);
            };
            reader.readAsDataURL(input.files[i]);
        }

        container.style.display = "block";
        currentContainerIndex++;

        switch (currentContainerIndex) {
            case 1:
                hideElements(["#imageUploadButton label", "#imageUploadButton p"]);
                showElement(".secondImageAddContainer");
                break;
            case 2:
                hideElement(".secondImageAddContainer label");
                showElement(".thirdImageAddContainer");
                break;
            case 3:
                hideElement(".thirdImageAddContainer label");
                break;
        }

        container.onclick = function (event) {
            if (event.target.classList.contains("delete-icon")) {
                var previewContainer = event.target.parentNode;
                previewContainer.parentNode.removeChild(previewContainer);
                currentContainerIndex--;

                if (currentContainerIndex === 0) {
                    showElements(["#imageUploadButton label", "#imageUploadButton p"]);
                    hideElement(".secondImageAddContainer");
                    hideElement(".image-preview-container");
                } else if (currentContainerIndex === 1) {
                    showElement(".secondImageAddContainer label");
                    hideElement(".thirdImageAddContainer");
                } else if (currentContainerIndex === 2) {
                    showElement(".thirdImageAddContainer label");
                }
            }
        };
    }
}

function hideElement(selector) {
    document.querySelector(selector).style.display = "none";
}

function showElement(selector) {
    document.querySelector(selector).style.display = "block";
}

function hideElements(selectors) {
    selectors.forEach(function (selector) {
        hideElement(selector);
    });
}

function showElements(selectors) {
    selectors.forEach(function (selector) {
        showElement(selector);
    });
}