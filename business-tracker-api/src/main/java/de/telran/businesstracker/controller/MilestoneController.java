package de.telran.businesstracker.controller;

import de.telran.businesstracker.controller.dto.MilestoneDto;
import de.telran.businesstracker.mapper.MilestoneMapper;
import de.telran.businesstracker.model.Milestone;
import de.telran.businesstracker.service.MilestoneService;
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
@RequestMapping("/api/milestones")
public class MilestoneController {

    private final MilestoneService milestoneService;
    private final MilestoneMapper milestoneMapper;

    public MilestoneController(MilestoneService milestoneService, MilestoneMapper milestoneMapper) {
        this.milestoneService = milestoneService;
        this.milestoneMapper = milestoneMapper;
    }

    @PostMapping("")
    public ResponseEntity<MilestoneDto> createMilestone(@RequestBody @Valid MilestoneDto milestoneDto) throws URISyntaxException {
        Milestone milestone = milestoneService.add(milestoneDto.name, milestoneDto.startDate, milestoneDto.finishDate, milestoneDto.roadmapId);
        milestoneDto.id = milestone.getId();
        return ResponseEntity
                .created(new URI("/api/milestones/" + milestone.getId()))
                .body(milestoneDto);
    }

    @PutMapping("")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateMilestone(@RequestBody @Valid MilestoneDto milestoneDto) throws HttpClientErrorException.BadRequest {

        milestoneService.edit(milestoneDto.id, milestoneDto.name, milestoneDto.startDate, milestoneDto.finishDate);
    }

    @GetMapping("/project/{id}")
    public List<MilestoneDto> getAllMilestonesByProjectId(@PathVariable long id) {
        return milestoneService.getAllByProjectId(id)
                .stream()
                .map(milestoneMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/roadmap/{id}")
    public List<MilestoneDto> getAllMilestonesByRoadMapId(@PathVariable long id) {
        return milestoneService.getAllByRoadMapId(id)
                .stream()
                .map(milestoneMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public MilestoneDto getMilestone(@PathVariable Long id) {
        Milestone milestone = milestoneService.getById(id);
        return milestoneMapper.toDto(milestone);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMilestone(@PathVariable Long id) {
        milestoneService.removeById(id);
    }
}
