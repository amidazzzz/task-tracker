package org.amida.backend.service;

import lombok.RequiredArgsConstructor;
import org.amida.backend.exception.TaskNotFoundException;
import org.amida.backend.model.Task;
import org.amida.backend.model.User;
import org.amida.backend.repository.TaskRepository;
import org.amida.backend.request.TaskRequest;
import org.amida.backend.response.TaskResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskResponse createTask(User user, TaskRequest request){
        Task task = Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .status(request.getStatus())
                .owner(user)
                .build();

        return TaskResponse.builder()
                .message("Task created successfully")
                .success(true)
                .build();
    }

    public TaskResponse updateTask(User user, Long taskId, TaskRequest request){
        Long userId = user.getId();
        Task task = taskRepository.findByOwnerIdAndId(userId, taskId);
        if (task == null){
            throw new TaskNotFoundException("Task not found");
        }

        task = Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .status(request.getStatus())
                .build();

        taskRepository.save(task);

        return TaskResponse.builder()
                .message("Task updated successfully")
                .success(true)
                .build();
    }

    public TaskResponse deleteTask(User user, Long taskId) {
        Task task = taskRepository.findByOwnerIdAndId(user.getId(), taskId);
        if (task == null) {
            throw new TaskNotFoundException("Task not found");
        }

        taskRepository.delete(task);

        return TaskResponse.builder()
                .message("Task deleted successfully")
                .success(true)
                .build();
    }

    public Task getTaskByIdAndUser(User user, Long taskId) {
        return taskRepository.findByOwnerIdAndId(user.getId(), taskId);
    }

    public List<Task> getAllTasksByUser(User user) {
        return taskRepository.findAllByOwnerId(user.getId());
    }

}
