$(document).ready(function() {
    $("#sendEmailForm").submit(function(event) {
        event.preventDefault();

        var email = $("#email").val();

        $.ajax({
            type: "POST",
            url: "/sendEmail",
            data: {email: email},
            success: function(response) {
                $("#successMessage").text(response);
            },
            error: function(xhr, status, error) {
                var errorMessage = xhr.responseJSON.errorMessage;
                $("#errorMessage").text(errorMessage);
            }
        });
    });
});