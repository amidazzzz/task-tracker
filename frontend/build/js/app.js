$(document).ready(function () {
    // Login form submission
    $("#loginForm").on("submit", function (event) {
        event.preventDefault();
        const formData = {
            username: $("input[name='username']").val(),
            password: $("input[name='password']").val(),
        };

        $.ajax({
            url: "http://localhost:8080/api/v1/auth/login",
            type: "POST",
            data: JSON.stringify(formData),
            contentType: "application/json",
            success: function (response) {
                localStorage.setItem("token", response.token);
                $("#message").text("Logged in successfully!");
                loadTasks();
            },
            error: function () {
                $("#message").text("Login failed. Please try again.");
            },
        });
    });

    // Registration form submission
    $("#registerForm").on("submit", function (event) {
        event.preventDefault();
        const formData = {
            username: $("input[name='username']").val(),
            email: $("input[name='email']").val(),
            password: $("input[name='password']").val(),
        };

        $.ajax({
            url: "http://localhost:8080/api/v1/auth/register",
            type: "POST",
            data: JSON.stringify(formData),
            contentType: "application/json",
            success: function () {
                $("#message").text("Registration successful. You can log in now.");
            },
            error: function () {
                console.log(response.message);
                $("#message").text("Registration failed.");
            },
        });
    });

    // Function to load tasks
    function loadTasks() {
        const token = localStorage.getItem("token");

        $.ajax({
            url: "http://localhost:8080/api/v1/task",
            type: "GET",
            headers: {
                Authorization: `Bearer ${token}`,  // Обратите внимание на шаблонную строку здесь
            },
            success: function (tasks) {
                $("#taskList").empty();
                tasks.forEach(function (task) {
                    $("#taskList").append(`<li>${task.name}</li>`);  // Исправлен синтаксис
                });
            },
            error: function () {
                $("#message").text("Failed to load tasks. Please try again.");
            },
        });
    }

    // Load tasks if user is already logged in
    if (localStorage.getItem("token")) {
        loadTasks();
    }
});
