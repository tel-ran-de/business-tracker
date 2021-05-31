package de.telran.businesstracker.controller;

import de.telran.businesstracker.data.Resource;
import de.telran.businesstracker.dto.ResourceDto;
import de.telran.businesstracker.service.ResourceService;
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
@RequestMapping("/api/resources")
@Transactional
public class ResourceController {

    ResourceService resourceService;

    @PostMapping("")
    public ResponseEntity<Resource> createResource(@RequestBody @Valid ResourceDto resourceDto) throws URISyntaxException {
        Resource result = resourceService.add(resourceDto.name, resourceDto.hours, resourceDto.cost, resourceDto.taskId);
        return ResponseEntity
                .created(new URI("/api/resource/" + result.getId()))
                .body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Resource> updateResource(@PathVariable(value = "id", required = false)
                                                    @RequestBody @Valid ResourceDto resourceDto) throws HttpClientErrorException.BadRequest {
        Resource resource = resourceService.getById(resourceDto.id);

        Resource result = resourceService.edit(resource, resourceDto.name, resourceDto.hours, resourceDto.cost, resourceDto.taskId);
        return ResponseEntity
                .ok()
                .body(result);
    }

    @GetMapping("")
    public List<Resource> getAllResources() {
        return resourceService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource> getResource(@PathVariable Long id) {
        Resource resource = resourceService.getById(id);
        return ResponseEntity.ok().body(resource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResource(@PathVariable Long id) {
        resourceService.removeById(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}
