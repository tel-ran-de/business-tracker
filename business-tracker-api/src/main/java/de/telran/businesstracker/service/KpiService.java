package de.telran.businesstracker.service;

import de.telran.businesstracker.model.Milestone;
import de.telran.businesstracker.model.Project;
import de.telran.businesstracker.model.Roadmap;
import de.telran.businesstracker.repositories.MilestoneRepository;
import de.telran.businesstracker.repositories.ProjectRepository;
import de.telran.businesstracker.repositories.RoadmapRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class KpiService {

    private final String KPI_NOT_FOUND = "Error! This kpi doesn't exist in our DB";
    private final String MILESTONE_NOT_FOUND = "Error! This milestone doesn't exist in our DB";
    private final String PROJECT_DOES_NOT_EXIST = "Error! This project doesn't exist in our DB";
    private final String ROADMAP_DOES_NOT_EXIST = "Error! This roadmap doesn't exist in our DB";

    private final MilestoneRepository milestoneRepository;
    private final ProjectRepository projectRepository;
    private final RoadmapRepository roadmapRepository;

    public KpiService(MilestoneRepository milestoneRepository,
                      ProjectRepository projectRepository,
                      RoadmapRepository roadmapRepository) {
        this.milestoneRepository = milestoneRepository;
        this.projectRepository = projectRepository;
        this.roadmapRepository = roadmapRepository;
    }

    public void add(long mileStoneId, String kpi) {
        Milestone milestone = getMileStoneById(mileStoneId);
        milestone.addKpi(kpi);
        milestoneRepository.save(milestone);
    }

    public List<String> getAllKpiByRoadMap(long roadMapId) {
        Roadmap roadmap = getRoadMapById(roadMapId);
        List<Milestone> milestoneListByRoadMap = milestoneRepository.findAllByRoadmap(roadmap);
        return milestoneListByRoadMap
                .stream()
                .flatMap(ms -> ms.getKpis().stream())
                .collect(Collectors.toList());
    }

    public List<String> getAllKpiByMileStone(long mileStoneId) {
        return getMileStoneById(mileStoneId)
                .getKpis();
    }

    public List<String> getAllKpiByProject(long projectId) {
        Project project = getProjectById(projectId);
        List<Milestone> milestoneListByProject = milestoneRepository.findAllByRoadmapProject(project);
        return milestoneListByProject
                .stream()
                .flatMap(ms -> ms.getKpis().stream())
                .collect(Collectors.toList());
    }

    public void removeKpi(long mileStoneId, int kpiIndex) {
        Milestone milestone = getMileStoneById(mileStoneId);
        List<String> kpis = milestone.getKpis();

        try {
            kpis.remove(kpiIndex);
            milestoneRepository.save(milestone);
        } catch (IndexOutOfBoundsException e) {
            throw new EntityNotFoundException(KPI_NOT_FOUND);
        }
    }

    public Milestone getMileStoneById(long mileStoneId) {
        return milestoneRepository.findById(mileStoneId)
                .orElseThrow(() -> new EntityNotFoundException(MILESTONE_NOT_FOUND));
    }

    public Project getProjectById(long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(PROJECT_DOES_NOT_EXIST));
    }

    public Roadmap getRoadMapById(long id) {
        return roadmapRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ROADMAP_DOES_NOT_EXIST));
    }
}
