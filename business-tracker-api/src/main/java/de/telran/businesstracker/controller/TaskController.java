package de.telran.businesstracker.controller;

import de.telran.businesstracker.controller.dto.ResourceDto;
import de.telran.businesstracker.controller.dto.TaskDto;
import de.telran.businesstracker.controller.dto.TaskToAddDto;
import de.telran.businesstracker.mapper.TaskMapper;
import de.telran.businesstracker.model.Task;
import de.telran.businesstracker.service.ResourceService;
import de.telran.businesstracker.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;
    private final ResourceService resourceService;
    private final TaskMapper taskMapper;

    public TaskController(TaskService taskService, ResourceService resourceService, TaskMapper taskMapper) {
        this.taskService = taskService;
        this.resourceService = resourceService;
        this.taskMapper = taskMapper;
    }

    @PostMapping("")
    public ResponseEntity<TaskDto> createTask(@RequestBody @Valid TaskToAddDto taskDto) throws URISyntaxException {
        Task task = taskService.add(taskDto.name, taskDto.finished, taskDto.milestoneId, taskDto.memberId);

        for (ResourceDto resource : taskDto.resources) {
            resourceService.add(resource.name, resource.hours, resource.cost, task.getId());
        }

        return ResponseEntity
                .created(new URI("/api/tasks/" + task.getId()))
                .body(taskMapper.toDto(task));
    }

    @PutMapping("")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTask(@RequestBody @Valid TaskDto taskDto) throws HttpClientErrorException.BadRequest {
        taskService.edit(taskDto.id, taskDto.name, taskDto.finished);
    }

    @GetMapping("milestone/{id}")
    public List<TaskDto> getAllTasksByMileStoneId(@PathVariable Long id) {
        return taskService.getAllTasksByMileStoneId(id)
                .stream()
                .map(taskMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public TaskDto getTask(@PathVariable Long id) {
        Task task = taskService.getById(id);
        return taskMapper.toDto(task);
    }

    @GetMapping("/active")
    public List<TaskDto> getTasksBy() {
        return taskService.getByActiveParam(true)
                .stream()
                .map(taskMapper::toDto)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable Long id) {
        taskService.removeById(id);
    }
}
