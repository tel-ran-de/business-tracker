package de.telran.businesstracker.controller;

import de.telran.businesstracker.controller.dto.RoadmapDto;
import de.telran.businesstracker.mapper.RoadmapMapper;
import de.telran.businesstracker.model.Roadmap;
import de.telran.businesstracker.service.RoadmapService;
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
@RequestMapping("/api/roadmaps")
public class RoadmapController {

    private final RoadmapService roadmapService;
    private final RoadmapMapper roadmapMapper;

    public RoadmapController(RoadmapService roadmapService, RoadmapMapper roadmapMapper) {
        this.roadmapService = roadmapService;
        this.roadmapMapper = roadmapMapper;
    }

    @PostMapping("")
    public ResponseEntity<RoadmapDto> createRoadmap(@RequestBody @Valid RoadmapDto roadmapDto) throws URISyntaxException {
        Roadmap roadmap = roadmapService.add(roadmapDto.name, roadmapDto.startDate, roadmapDto.projectId);
        roadmapDto.id = roadmap.getId();
        return ResponseEntity
                .created(new URI("/api/roadmaps/" + roadmap.getId()))
                .body(roadmapDto);
    }

    @PutMapping("")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateRoadmap(@RequestBody @Valid RoadmapDto roadmapDto) throws HttpClientErrorException.BadRequest {
        roadmapService.edit(roadmapDto.id, roadmapDto.name, roadmapDto.startDate);
    }

    @GetMapping("project/{id}")
    public List<RoadmapDto> getAllRoadmapsByProjectId(@PathVariable long id) {
        return roadmapService.getAllByProjectId(id)
                .stream()
                .map(roadmapMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public RoadmapDto getRoadmap(@PathVariable Long id) {
        Roadmap roadmap = roadmapService.getById(id);
        return roadmapMapper.toDto(roadmap);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRoadmap(@PathVariable Long id) {
        roadmapService.removeById(id);
    }
}
