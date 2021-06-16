package de.telran.businesstracker.service;

import de.telran.businesstracker.model.Milestone;
import de.telran.businesstracker.repositories.MilestoneRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class KpiService {

    private final String MILESTONE_NOT_FOUND = "Error! This milestone doesn't exist in our DB";
    private final MilestoneRepository milestoneRepository;

    public KpiService(MilestoneRepository milestoneRepository) {
        this.milestoneRepository = milestoneRepository;
    }

    public void add(long mileStoneId, String kpi) {
        Milestone milestone = findById(mileStoneId);
        milestone.addKpi(kpi);
        milestoneRepository.save(milestone);
    }

    public List<String> getAllKpiByRoadMap(long mileStoneId) {
        Milestone milestone = findById(mileStoneId);
        List<Milestone> milestoneListByRoadMap = milestoneRepository.findAllByRoadmap(milestone.getRoadmap());
        return milestoneListByRoadMap
                .stream()
                .flatMap(ms -> ms.getKpis().stream())
                .collect(Collectors.toList());
    }

    public Set<String> getAllKpiByMileStone(long mileStoneId) {
        return findById(mileStoneId)
                .getKpis();
    }

    public List<String> getAllKpiByProject(long mileStoneId) {
        Milestone milestone = findById(mileStoneId);
        List<Milestone> milestoneListByProject = milestoneRepository
                .findAllByRoadmapProject(milestone.getRoadmap().getProject());
        return milestoneListByProject
                .stream()
                .flatMap(ms -> ms.getKpis().stream())
                .collect(Collectors.toList());
    }

    public void removeKpi(long mileStoneId, String kpi) {
        Milestone milestone = findById(mileStoneId);
        milestone.removeKpi(kpi);
        milestoneRepository.save(milestone);
    }

    public Milestone findById(long mileStoneId) {
        return milestoneRepository.findById(mileStoneId)
                .orElseThrow(() -> new EntityNotFoundException(MILESTONE_NOT_FOUND));
    }
}
