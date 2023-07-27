// JavaScript to hide the alert message after 5 seconds
document.addEventListener('DOMContentLoaded', function() {
    var alertMessage = document.getElementById("alertMessage");
    var errorMessage = document.getElementById("errorMessage");

    if (alertMessage) {
        setTimeout(function() {
            alertMessage.style.opacity = 0;
            setTimeout(function() {
                alertMessage.style.display = "none";
            }, 500);
        }, 5000);
    }

    if (errorMessage) {
        setTimeout(function() {
            errorMessage.style.opacity = 0;
            setTimeout(function() {
                errorMessage.style.display = "none";
            }, 500);
        }, 5000);
    }
});
