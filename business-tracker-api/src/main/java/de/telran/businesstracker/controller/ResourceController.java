package de.telran.businesstracker.controller;

import de.telran.businesstracker.controller.dto.ResourceDto;
import de.telran.businesstracker.mapper.ResourceMapper;
import de.telran.businesstracker.model.Resource;
import de.telran.businesstracker.service.ResourceService;
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
@RequestMapping("/api/resources")
public class ResourceController {

    private final ResourceService resourceService;
    private final ResourceMapper resourceMapper;

    public ResourceController(ResourceService resourceService, ResourceMapper resourceMapper) {
        this.resourceService = resourceService;
        this.resourceMapper = resourceMapper;
    }

    @PostMapping("")
    public ResponseEntity<ResourceDto> createResource(@RequestBody @Valid ResourceDto resourceDto) throws URISyntaxException {
        Resource resource = resourceService.add(resourceDto.name, resourceDto.hours, resourceDto.cost, resourceDto.taskId);
        resourceDto.id = resource.getId();
        return ResponseEntity
                .created(new URI("/api/resource/" + resource.getId()))
                .body(resourceDto);
    }

    @PutMapping("")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateResource(@RequestBody @Valid ResourceDto resourceDto) throws HttpClientErrorException.BadRequest {
        resourceService.edit(resourceDto.id, resourceDto.name, resourceDto.hours, resourceDto.cost);
    }

    @GetMapping("roadmap/{id}")
    public List<ResourceDto> getAllResourcesByRoadMap(@PathVariable long id) {
        return resourceService.getAllByRoadMapId(id)
                .stream()
                .map(resourceMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("task/{id}")
    public List<ResourceDto> getAllResourcesByTask(@PathVariable long id) {
        return resourceService.getAllByTaskId(id)
                .stream()
                .map(resourceMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResourceDto getResource(@PathVariable Long id) {
        Resource resource = resourceService.getById(id);
        return resourceMapper.toDto(resource);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteResource(@PathVariable Long id) {
        resourceService.removeById(id);
    }
}
