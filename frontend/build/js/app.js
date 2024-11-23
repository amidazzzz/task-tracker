$(document).ready(function () {
    // Обработчик формы входа
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

    // Обработчик формы регистрации
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
                $("#message").text("Registration failed.");
            },
        });
    });

    // Функция загрузки задач
    function loadTasks() {
        const token = localStorage.getItem("token");

        $.ajax({
            url: "http://localhost:8080/api/v1/task",
            type: "GET",
            headers: {
                Authorization: `Bearer ${token}`,
            },
            success: function (tasks) {
                $("#taskList").empty();
                tasks.forEach(function (task) {
                    $("#taskList").append(`
                        <li>
                            ${task.name}
                            ${task.description}
                            ${"task"}
                            <button class="delete-task" data-id="${task.id}">Delete</button>
                            <button class="update-task" data-id="${task.id}">Update</button>
                        </li>
                    `);
                });
            },
            error: function () {
                $("#message").text("Failed to load tasks. Please try again.");
            },
        });
    }

    // Обработчик формы добавления задачи
    $("#createTaskForm").on("submit", function (event) {
            event.preventDefault();
            const token = localStorage.getItem("token");
            const taskData = {
                title: $("input[name='title']").val(),
                description: $("textarea[name='description']").val(),
                status: $("select[name='status']").val(),
            };

            $.ajax({
                url: "http://localhost:8080/api/v1/task",
                type: "POST",
                data: JSON.stringify(taskData),
                contentType: "application/json",
                headers: {
                    Authorization: `Bearer ${token}`,
                },
                success: function (response) {
                    $("#message").text("Task created successfully!");
                    loadTasks(); // Reload tasks to show the new one
                },
                error: function () {
                    $("#message").text("Failed to create task. Please try again.");
                },
            });
        });

    // Обработчик для удаления задачи
    $(document).on("click", ".delete-task", function () {
        const taskId = $(this).data("id");
        const token = localStorage.getItem("token");

        $.ajax({
            url: `http://localhost:8080/api/v1/task/${taskId}`,
            type: "DELETE",
            headers: {
                Authorization: `Bearer ${token}`,
            },
            success: function () {
                $("#message").text("Task deleted successfully!");
                loadTasks();  // Перезагрузка задач
            },
            error: function () {
                $("#message").text("Failed to delete task. Please try again.");
            },
        });
    });

    // Обработчик для обновления задачи
    $(document).on("click", ".update-task", function () {
        const taskId = $(this).data("id");
        const token = localStorage.getItem("token");

        // Получение нового имени задачи
        const newTaskName = prompt("Enter the new name for the task:");
        if (!newTaskName || newTaskName.trim() === "") {
            alert("Task name cannot be empty.");
            return;
        }

        // Получение нового описания задачи
        const newTaskDescription = prompt("Enter the new description for the task:");
        if (!newTaskDescription || newTaskDescription.trim() === "") {
            alert("Task description cannot be empty.");
            return;
        }

        // Формирование AJAX-запроса
        $.ajax({
            url: `http://localhost:8080/api/v1/task/${taskId}`,
            type: "PUT",
            headers: {
                Authorization: `Bearer ${token}`,
            },
            contentType: "application/json",
            data: JSON.stringify({
                title: newTaskName,
                description: newTaskDescription,
                status: "IN_PROGRESS" // Укажите значение статуса или сделайте его динамическим
            }),
            success: function () {
                $("#message").text("Task updated successfully!");
                loadTasks();
            },
            error: function () {
                $("#message").text("Failed to update task. Please try again.");
            },
        });
    });


    // Загрузка задач при наличии токена
    if (localStorage.getItem("token")) {
        loadTasks();
    }
});
