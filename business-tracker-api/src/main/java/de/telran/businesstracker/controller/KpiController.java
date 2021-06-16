package de.telran.businesstracker.controller;

import de.telran.businesstracker.controller.dto.KpiDto;
import de.telran.businesstracker.service.KpiService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/kpi")
public class KpiController {

    private final KpiService kpiService;

    public KpiController(KpiService kpiService) {
        this.kpiService = kpiService;
    }

    @PostMapping("/{id}")
    public void addKpi(@PathVariable long id, @RequestBody KpiDto kpi) {
        kpiService.add(id, kpi.kpi);
    }

    @GetMapping("/{id}")
    public List<KpiDto> getKpiByMileStone(@PathVariable long id) {
        return kpiService.getAllKpiByMileStone(id)
                .stream()
                .map(KpiDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}/project")
    public List<KpiDto> getKpiByProject(@PathVariable long id) {
        return kpiService.getAllKpiByProject(id)
                .stream()
                .map(KpiDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}/road-map")
    public List<KpiDto> getKpiByRoadMap(@PathVariable long id) {
        return kpiService.getAllKpiByRoadMap(id)
                .stream()
                .map(KpiDto::new)
                .collect(Collectors.toList());
    }
}
