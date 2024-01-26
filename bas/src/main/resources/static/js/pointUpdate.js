$(document).ready(function () {
    updatePoint();

    function updatePoint() {
        $.ajax({
            type: "GET",
            url: "/getUserPoint",
            success: function (response) {
                console.log("포인트 가져오기 성공:", response);

                var pointAmountSpan = $("#pointAmount");
                pointAmountSpan.text(response);
            },
            error: function (error) {
                console.error("포인트 가져오기 실패:", error);
            }
        });
    }
});