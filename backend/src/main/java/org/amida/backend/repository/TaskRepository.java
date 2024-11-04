package org.amida.backend.repository;

import org.amida.backend.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findAllByOwnerId(Long userId);

    Task findByOwnerIdAndId(Long userId, Long taskId);

    Optional<Task> findByTitle(String title);
}
