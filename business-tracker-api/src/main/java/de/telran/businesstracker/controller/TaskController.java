package de.telran.businesstracker.controller;

import de.telran.businesstracker.data.Task;
import de.telran.businesstracker.dto.TaskDto;
import de.telran.businesstracker.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@Transactional
public class TaskController {

    TaskService taskService;

    @PostMapping("")
    public ResponseEntity<Task> createTask(@RequestBody @Valid TaskDto taskDto) throws URISyntaxException {
        Task result = taskService.add(taskDto.name,taskDto.finished,taskDto.milestoneId, taskDto.memberId);
        return ResponseEntity
            .created(new URI("/api/tasks/" + result.getId()))
            .body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable(value = "id", required = false)
        @RequestBody @Valid TaskDto taskDto) throws HttpClientErrorException.BadRequest {
        Task task = taskService.getById(taskDto.id);

        Task result = taskService.edit(task, taskDto.name, taskDto.finished, taskDto.milestoneId, taskDto.memberId);

        return ResponseEntity
            .ok()
            .body(result);
    }

    @GetMapping("")
    public List<Task> getAllTasks() {
        return taskService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTask(@PathVariable Long id) {
        Task task = taskService.getById(id);
        return ResponseEntity.ok().body(task);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.removeById(id);
        return ResponseEntity
            .noContent()
            .build();
    }
}
