package de.telran.businesstracker.controller;

import de.telran.businesstracker.controller.dto.KpiDto;
import de.telran.businesstracker.service.KpiService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class KpiController {

    private final KpiService kpiService;

    public KpiController(KpiService kpiService) {
        this.kpiService = kpiService;
    }

    @PostMapping("/kpis/{id}")
    public void addKpi(@RequestBody KpiDto kpi, @PathVariable long id) {
        kpiService.add(id, kpi.kpi);
    }

    @GetMapping("/milestone/{id}/kpis")
    public List<KpiDto> getKpiByMileStone(@PathVariable long id) {
        return kpiService.getAllKpiByMileStone(id)
                .stream()
                .map(KpiDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/project/{id}/kpis")
    public List<KpiDto> getKpiByProject(@PathVariable long id) {
        return kpiService.getAllKpiByProject(id)
                .stream()
                .map(KpiDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/roadmap/{id}/kpis")
    public List<KpiDto> getKpiByRoadMap(@PathVariable long id) {
        return kpiService.getAllKpiByRoadMap(id)
                .stream()
                .map(KpiDto::new)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/milestone/{id}/kpi/{kpiId}")
    public void getKpiByRoadMap(@PathVariable long id, @PathVariable int kpiId) {
        kpiService.removeKpi(id, kpiId);
    }
}
