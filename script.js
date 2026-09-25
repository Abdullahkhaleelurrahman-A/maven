document.addEventListener("DOMContentLoaded", function () {

    function togglePassword(checkbox) {
        const password = document.getElementById("password");
        password.type = checkbox.checked ? "text" : "password";
    }

    // Make togglePassword available to the HTML onclick
    window.togglePassword = togglePassword;

    const loginForm = document.getElementById("loginForm");

    loginForm.addEventListener("submit", function (event) {
        event.preventDefault();

        const username = document.getElementById("username").value.trim();
        const password = document.getElementById("password").value;

        // 1. Username validation
        if (username === "") {
            Swal.fire({
                icon: "warning",
                title: "Username Required",
                text: "Please enter your Registration Number.",
                confirmButtonText: "OK",
                confirmButtonColor: "#002060"
            });
            return;
        }

        // 2. Password validation
        if (password === "") {
            Swal.fire({
                icon: "warning",
                title: "Password Required",
                text: "Please enter your VTOP Password.",
                confirmButtonText: "OK",
                confirmButtonColor: "#002060"
            });
            return;
        }

        // 3. Login validation
        if (username === "username" && password === "password") {

            Swal.fire({
                icon: "success",
                title: "Sign In Successful",
                text: "Welcome to VIT Vellore Course Registration.",
                confirmButtonText: "Continue",
                confirmButtonColor: "#002060",
                allowOutsideClick: false
            }).then(function () {
                window.location.href = "instruction.html";
            });

        } else {

            Swal.fire({
                icon: "error",
                title: "Sign In Failed",
                text: "The username or password you entered is incorrect. Please try again.",
                confirmButtonText: "Try Again",
                confirmButtonColor: "#002060"
            });
        }
    });
});