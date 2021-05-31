package de.telran.businesstracker.controller;

import de.telran.businesstracker.data.Roadmap;
import de.telran.businesstracker.dto.RoadmapDto;
import de.telran.businesstracker.service.RoadmapService;
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
@RequestMapping("/api/roadmaps")
@Transactional
public class RoadmapController {

    RoadmapService roadmapService;

    @PostMapping("")
    public ResponseEntity<Roadmap> createRoadmap(@RequestBody @Valid RoadmapDto roadmapDto) throws URISyntaxException {
        Roadmap result = roadmapService.add(roadmapDto.name, roadmapDto.startDate, roadmapDto.projectId);
        return ResponseEntity
                .created(new URI("/api/roadmaps/" + result.getId()))
                .body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Roadmap> updateRoadmap(@PathVariable(value = "id", required = false)
                                                 @RequestBody @Valid RoadmapDto roadmapDto) throws HttpClientErrorException.BadRequest {
        Roadmap roadmap = roadmapService.getById(roadmapDto.id);

        Roadmap result = roadmapService.edit(roadmap, roadmapDto.name, roadmapDto.startDate, roadmapDto.projectId);
        return ResponseEntity
                .ok()
                .body(result);
    }

    @GetMapping("")
    public List<Roadmap> getAllRoadmaps() {
        return roadmapService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Roadmap> getRoadmap(@PathVariable Long id) {
        Roadmap roadmap = roadmapService.getById(id);
        return ResponseEntity.ok().body(roadmap);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoadmap(@PathVariable Long id) {
        roadmapService.removeById(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}
