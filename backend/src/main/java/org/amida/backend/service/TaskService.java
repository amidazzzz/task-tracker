package org.amida.backend.service;

import lombok.RequiredArgsConstructor;
import org.amida.backend.exception.TaskNotFoundException;
import org.amida.backend.model.Task;
import org.amida.backend.model.User;
import org.amida.backend.repository.TaskRepository;
import org.amida.backend.request.TaskRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public Task createTask(User user, TaskRequest request){
        return Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .status(request.getStatus())
                .owner(user)
                .build();
    }

    public Task updateTask(User user, Long taskId, TaskRequest request){
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

        return task;
    }

    public void deleteTask(User user, Long taskId) {
        Task task = taskRepository.findByOwnerIdAndId(user.getId(), taskId);
        if (task == null) {
            throw new TaskNotFoundException("Task not found");
        }

        taskRepository.delete(task);
    }

    public Task getTaskByIdAndUser(User user, Long taskId) {
        return taskRepository.findByOwnerIdAndId(user.getId(), taskId);
    }

    public List<Task> getAllTasksByUser(User user) {
        return taskRepository.findAllByOwnerId(user.getId());
    }

}
