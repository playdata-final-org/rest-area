$(document).ready(function() {
    $(".joinButton").click(function(e) {

        var card = e.target.closest('.card');

        var membershipTitle = card.querySelector('.card-title').textContent;

        var membershipPrice = card.querySelector('.card-text').textContent;

        var confirmMessage = membershipTitle + " 멤버십에 가입하시겠습니까? " + membershipPrice + " 포인트 만큼 차감됩니다.";
            if (confirm(confirmMessage)) {
                    alert(membershipTitle + " 멤버십에 가입하셨습니다.");
                    $("#membershipForm").submit();
                } else {
                    alert("결제가 취소되었습니다.");
                    return false;
                }
    });
})

$(document).ready(function() {
    $(".updateJoinButton").click(function(e) {

        var card = e.target.closest('.card');

        var membershipTitle = card.querySelector('.card-title').textContent;
        var membershipPrice = card.querySelector('.card-text').textContent;
        var confirmMessage = membershipTitle + " 멤버십으로 업데이트하시겠습니까? " + membershipPrice + " 포인트 만큼 차감됩니다.";
            if (confirm(confirmMessage)) {
                    alert(membershipTitle + " 멤버십에 가입하셨습니다.");
                    $("#membershipForm").submit();
                } else {
                    alert("결제가 취소되었습니다.");
                    return false;
                }
       });
})