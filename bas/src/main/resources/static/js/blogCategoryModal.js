document.addEventListener('DOMContentLoaded', function() {
    var categoryModalBtn = document.getElementById('categoryModalBtn');
    var categoryModal = document.getElementById('categoryModal');
    var categoryList = document.getElementById('categoryList');
    var categorySpan = document.getElementById('blogCategory');


    categoryModalBtn.addEventListener('click', function() {
        categoryModal.style.display = 'block';
    });

    categoryList.addEventListener('click', function(event) {
        if (event.target.classList.contains('categoryItem')) {
            var selectedCategoryName = event.target.textContent;
            categoryModal.style.display = 'none';
            sendCategoryToServer(selectedCategoryName);
        }
    });

    var closeButtons = document.querySelectorAll('.modalClose');
    closeButtons.forEach(function(button) {
        button.addEventListener('click', function() {
            categoryModal.style.display = 'none';
        });
    });

    function sendCategoryToServer(category) {
        var url = "/blog/" + blogId;
        var data = JSON.stringify({ category: category });
        console.log(url)
        console.log(data)
        $.ajax({
            type: "POST",
            url: url,
            contentType: "application/json",
            data: data,
            success: function(response) {
             categorySpan.textContent = category;
                console.log('성공');
            },
            error: function(xhr, status, error) {
                console.error('실패');
            }
        });
    }
});