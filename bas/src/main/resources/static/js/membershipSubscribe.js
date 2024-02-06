  $(document).ready(function() {
        $(".joinButton").click(function() {
            if (confirm("진짜 결제하시겠습니까?")) {
                $("#membershipForm").submit();
            }
        });
    });