package de.telran.businesstracker.controller;

import de.telran.businesstracker.data.Milestone;
import de.telran.businesstracker.dto.MilestoneDto;
import de.telran.businesstracker.service.MilestoneService;
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
@RequestMapping("/api/milestones")
@Transactional
public class MilestoneController {

    MilestoneService milestoneService;

    @PostMapping("")
    public ResponseEntity<Milestone> createMilestone(@RequestBody @Valid MilestoneDto milestoneDto) throws URISyntaxException {
        Milestone result = milestoneService.add(milestoneDto.name, milestoneDto.startDate, milestoneDto.finishDate, milestoneDto.roadmapId);
        return ResponseEntity
            .created(new URI("/api/milestones/" + result.getId()))
            .body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Milestone> updateMilestone(@PathVariable(value = "id", required = false)
        @RequestBody @Valid MilestoneDto milestoneDto) throws HttpClientErrorException.BadRequest {
        Milestone milestone = milestoneService.getById(milestoneDto.id);

        Milestone result = milestoneService.edit(milestone, milestoneDto.name, milestoneDto.startDate, milestoneDto.finishDate, milestoneDto.roadmapId);

        return ResponseEntity
            .ok()
            .body(result);
    }

    @GetMapping("")
    public List<Milestone> getAllMilestones() {
        return milestoneService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Milestone> getMilestone(@PathVariable Long id) {
        Milestone milestone = milestoneService.getById(id);
        return ResponseEntity.ok().body(milestone);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMilestone(@PathVariable Long id) {
        milestoneService.removeById(id);
        return ResponseEntity
            .noContent()
            .build();
    }
}
