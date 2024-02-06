IMP.init("imp41664175");

const handleButtonClick = async (amount) => {
    console.log("Money:", amount);
    const pointsToCharge = amount;

    IMP.request_pay({
        pg: "kakaopay",
        pay_method: "card",
        amount: amount,
        name: "포인트",
        merchant_uid: "merchant_" + new Date().getTime(),
    }, function(response) {
        console.log(response);
        if (response.success) {
            var msg = '결제가 완료되었습니다.';
            msg += '고유ID : ' + response.imp_uid;
            msg += '상점 거래ID : ' + response.merchant_uid;
            msg += '결제 금액 : ' + response.paid_amount;
            msg += '카드 승인번호 : ' + response.apply_num;

            chargePoints(pointsToCharge);

        } else {
            var msg = '결제에 실패하였습니다.';
            msg += '에러내용 : ' + response.error_msg;
            alert(msg);

        }
    });
};

function chargePoints(pointsToCharge) {
    $.ajax({
        type: "GET",
        url: "/point",
        data: {
            "amount": pointsToCharge
        },
        success: function(response) {
            console.log("포인트 충전 성공:", response);
            var pointAmountSpan = $("#pointAmount");
            pointAmountSpan.text(response);
        },
        error: function(error) {
            console.error("포인트 충전 실패:", error);
        }
    });
}