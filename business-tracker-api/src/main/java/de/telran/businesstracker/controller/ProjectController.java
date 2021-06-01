package de.telran.businesstracker.controller;

import de.telran.businesstracker.data.Project;
import de.telran.businesstracker.dto.ProjectDto;
import de.telran.businesstracker.service.ProjectService;
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
@RequestMapping("/api/projects")
@Transactional
public class ProjectController {

    ProjectService projectService;

    @PostMapping("")
    public ResponseEntity<Project> createProject(@RequestBody @Valid ProjectDto projectDto) throws URISyntaxException {
        Project result = projectService.add(projectDto.name, projectDto.userId);
        return ResponseEntity
                .created(new URI("/api/projects/" + result.getId()))
                .body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable(value = "id", required = false)
                                                 @RequestBody @Valid ProjectDto projectDto) throws HttpClientErrorException.BadRequest {
        Project project = projectService.getById(projectDto.id);

        Project result = projectService.edit(project, projectDto.name, projectDto.userId);

        return ResponseEntity
                .ok()
                .body(result);
    }

    @GetMapping("")
    public List<Project> getAllProjects() {
        return projectService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> getProject(@PathVariable Long id) {
        Project project = projectService.getById(id);
        return ResponseEntity.ok().body(project);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectService.removeById(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}
