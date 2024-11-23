package org.amida.backend.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.amida.backend.model.Task;
import org.amida.backend.model.User;
import org.amida.backend.request.TaskRequest;
import org.amida.backend.response.TaskResponse;
import org.amida.backend.service.TaskService;
import org.amida.backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/task")
@Slf4j
public class TaskController {

    private final TaskService taskService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(
            @RequestBody TaskRequest request,
            @AuthenticationPrincipal UserDetails userDetails) { // fix: pass the UserDetails parameter
        // along with the UserDetailsImpl

        String username = userDetails.getUsername();
        User user = userService.findUserByUsername(username);
        log.info("Request title {}", request.getTitle());
        TaskResponse response = taskService.createTask(user, request);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask(@AuthenticationPrincipal UserDetails userDetails,
                                                   @PathVariable("id") Long taskId,
                                                   TaskRequest request) {
        log.info("Request in Task Controller {}", request);
        String username = userDetails.getUsername();
        User user = userService.findUserByUsername(username);
        TaskResponse response = taskService.updateTask(user, taskId, request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TaskResponse> deleteTask(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails){
        String username = userDetails.getUsername();
        User user = userService.findUserByUsername(username); // можно вынести логику экстракта в отдельный прайват метод
        TaskResponse response = taskService.deleteTask(user, id);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks(@AuthenticationPrincipal UserDetails userDetails){
        String username = userDetails.getUsername();
        User user = userService.findUserByUsername(username);

        log.info(taskService.getAllTasksByUser(user).toString());

        return ResponseEntity.ok(taskService.getAllTasksByUser(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTask(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails){
        String username = userDetails.getUsername();
        User user = userService.findUserByUsername(username);

        return ResponseEntity.ok(taskService.getTaskByIdAndUser(user, id));
    }


}
