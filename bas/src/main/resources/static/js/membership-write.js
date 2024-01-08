$(document).ready(function () {
    var currentPage = 1;
    var totalPages = $('.blogWriteMembership-box').length;

    // 숨김 처리
    $('.blogWriteMembership-box').not('[data-page="1"]').hide();

    $('#nextPage').on('click', function () {
        if (currentPage < totalPages) {
            currentPage++;
            updateView();
        }
    });

    $('#prevPage').on('click', function () {
        if (currentPage > 1) {
            currentPage--;
            updateView();
        }
    });

    $('#clearForm').on('click', function () {
        $('#blogForm')[0].reset();
    });

    function updateView() {
        $('.blogWriteMembership-box').hide();
        $('.blogWriteMembership-box[data-page="' + currentPage + '"]').show();

        // Update button state based on current page
        $('#prevPage').prop('disabled', currentPage === 1);
        $('#nextPage').prop('disabled', currentPage === totalPages);
    }
});