package de.telran.businesstracker.service;

import de.telran.businesstracker.model.Milestone;
import de.telran.businesstracker.model.Project;
import de.telran.businesstracker.model.Roadmap;
import de.telran.businesstracker.model.User;
import de.telran.businesstracker.repositories.MilestoneRepository;
import de.telran.businesstracker.repositories.ProjectRepository;
import de.telran.businesstracker.repositories.RoadmapRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class KpiServiceTest {

    @Mock
    MilestoneRepository milestoneRepository;
    @Mock
    ProjectRepository projectRepository;
    @Mock
    RoadmapRepository roadmapRepository;

    @InjectMocks
    KpiService kpiService;

    @Test
    public void testAdd_success() {
        User user = new User(2L);
        Project project = new Project(4L, "Great project", user);
        Roadmap roadmap = new Roadmap(3L, "Roadmap", LocalDate.now(), project);
        Milestone milestone = new Milestone(1L, "Milestone", LocalDate.now(), LocalDate.now().plusDays(10), roadmap, new ArrayList<>());
        when(milestoneRepository.findById(milestone.getId())).thenReturn(Optional.of(milestone));

        kpiService.add(milestone.getId(), "some kpi name");

        verify(milestoneRepository, times(1)).save(any());
        verify(milestoneRepository, times(1)).save(argThat(savedMilestone -> savedMilestone.getKpis().size() == 1));
    }

    @Test
    public void testAdd_roadmapDoesNotExist_EntityNotFoundException() {
        User user = new User(2L);
        Project project = new Project(4L, "Great project", user);
        Roadmap roadmap = new Roadmap(3L, "Roadmap", LocalDate.now(), project);

        Milestone milestone = new Milestone(1L, "Milestone", LocalDate.now(), LocalDate.now().plusDays(10), roadmap, new ArrayList<>());

        Exception exception = assertThrows(EntityNotFoundException.class, () ->
                kpiService.add(milestone.getId(), "some kpi name"));

        verify(milestoneRepository, times(1)).findById(any());
        assertEquals("Error! This milestone doesn't exist in our DB", exception.getMessage());
    }

    @Test
    public void testGetAllKpisByProject_twoRoadMapsWithEachOneMileStones_sixElementFound() {
        User user = new User(2L);
        Project project = new Project(4L, "Great project", user);
        Roadmap roadmap1 = new Roadmap(3L, "Roadmap_01", LocalDate.now(), project);
        Milestone milestone1 = new Milestone(1L, "Milestone_01", LocalDate.now(), LocalDate.now().plusDays(10), roadmap1, new ArrayList<>());
        milestone1.addKpi("kpi_01_01");
        milestone1.addKpi("kpi_01_02");
        milestone1.addKpi("kpi_01_03");
        milestone1.addKpi("kpi_01_04");

        Roadmap roadmap2 = new Roadmap(6L, "Roadmap_02", LocalDate.now(), project);
        Milestone milestone2 = new Milestone(7L, "Milestone_02", LocalDate.now(), LocalDate.now().plusDays(10), roadmap2, new ArrayList<>());
        milestone2.addKpi("kpi_02_01");
        milestone2.addKpi("kpi_02_02");

        when(projectRepository.findById(project.getId())).thenReturn(Optional.of(project));
        when(milestoneRepository.findAllByRoadmapProject(project)).thenReturn(Arrays.asList(milestone1, milestone2));

        List<String> kpis = kpiService.getAllKpiByProject(project.getId());

        verify(projectRepository, times(1)).findById(project.getId());
        verify(milestoneRepository, times(1)).findAllByRoadmapProject(project);

        assertTrue(kpis.contains("kpi_01_01"));
        assertTrue(kpis.contains("kpi_01_02"));
        assertTrue(kpis.contains("kpi_01_03"));
        assertTrue(kpis.contains("kpi_01_04"));
        assertTrue(kpis.contains("kpi_02_01"));
        assertTrue(kpis.contains("kpi_02_01"));
    }

    @Test
    public void testGetAllKpisByProject_oneRoadMapWithTreeMileStones_sevenElementFound() {
        User user = new User(2L);
        Project project = new Project(4L, "Great project", user);
        Roadmap roadmap = new Roadmap(3L, "Roadmap_01", LocalDate.now(), project);
        Milestone milestone1 = new Milestone(1L, "Milestone_01", LocalDate.now(), LocalDate.now().plusDays(10), roadmap, new ArrayList<>());
        milestone1.addKpi("kpi_01_01");
        milestone1.addKpi("kpi_01_02");
        milestone1.addKpi("kpi_01_03");
        milestone1.addKpi("kpi_01_04");

        Milestone milestone2 = new Milestone(7L, "Milestone_02", LocalDate.now(), LocalDate.now().plusDays(10), roadmap, new ArrayList<>());
        milestone2.addKpi("kpi_02_01");
        milestone2.addKpi("kpi_02_02");

        Milestone milestone3 = new Milestone(13L, "Milestone_03", LocalDate.now(), LocalDate.now().plusDays(10), roadmap, new ArrayList<>());
        milestone2.addKpi("kpi_03_01");

        when(projectRepository.findById(project.getId())).thenReturn(Optional.of(project));
        when(milestoneRepository.findAllByRoadmapProject(project)).thenReturn(Arrays.asList(milestone1, milestone2, milestone3));

        List<String> kpis = kpiService.getAllKpiByProject(project.getId());

        verify(projectRepository, times(1)).findById(project.getId());
        verify(milestoneRepository, times(1)).findAllByRoadmapProject(project);

        assertEquals(7, kpis.size());
        assertTrue(kpis.contains("kpi_01_01"));
        assertTrue(kpis.contains("kpi_01_02"));
        assertTrue(kpis.contains("kpi_01_03"));
        assertTrue(kpis.contains("kpi_01_04"));
        assertTrue(kpis.contains("kpi_02_01"));
        assertTrue(kpis.contains("kpi_02_01"));
        assertTrue(kpis.contains("kpi_03_01"));
    }

    @Test
    public void testGetAllKpisByProject_oneRoadMapWithTwoMileStones_noKpiExist_emptyList() {
        User user = new User(2L);
        Project project = new Project(4L, "Great project", user);
        Roadmap roadmap1 = new Roadmap(3L, "Roadmap_01", LocalDate.now(), project);
        Milestone milestone1 = new Milestone(1L, "Milestone_01", LocalDate.now(), LocalDate.now().plusDays(10), roadmap1, new ArrayList<>());
        Milestone milestone2 = new Milestone(7L, "Milestone_02", LocalDate.now(), LocalDate.now().plusDays(10), roadmap1, new ArrayList<>());

        when(projectRepository.findById(project.getId())).thenReturn(Optional.of(project));
        when(milestoneRepository.findAllByRoadmapProject(project)).thenReturn(Arrays.asList(milestone1, milestone2));

        List<String> kpis = kpiService.getAllKpiByProject(project.getId());

        verify(projectRepository, times(1)).findById(project.getId());
        verify(milestoneRepository, times(1)).findAllByRoadmapProject(project);
        assertEquals(0, kpis.size());
    }

    @Test
    public void testGetAllKpiByMileStone_fourElementFound() {
        User user = new User(2L);
        Project project = new Project(4L, "Great project", user);
        Roadmap roadmap = new Roadmap(3L, "Roadmap_01", LocalDate.now(), project);
        Milestone milestone = new Milestone(1L, "Milestone_01", LocalDate.now(), LocalDate.now().plusDays(10), roadmap, new ArrayList<>());
        milestone.addKpi("kpi_01_01");
        milestone.addKpi("kpi_01_02");
        milestone.addKpi("kpi_01_03");
        milestone.addKpi("kpi_01_04");

        when(milestoneRepository.findById(milestone.getId())).thenReturn(Optional.of(milestone));
        List<String> kpis = kpiService.getAllKpiByMileStone(milestone.getId());

        verify(milestoneRepository, times(1)).findById(milestone.getId());
        assertEquals(4, kpis.size());
    }

    @Test
    public void testGetAllKpiByBMileStone_emptyList() {
        User user = new User(2L);
        Project project = new Project(4L, "Great project", user);
        Roadmap roadmap = new Roadmap(3L, "Roadmap_01", LocalDate.now(), project);
        Milestone milestone = new Milestone(1L, "Milestone_01", LocalDate.now(), LocalDate.now().plusDays(10), roadmap, new ArrayList<>());

        when(milestoneRepository.findById(milestone.getId())).thenReturn(Optional.of(milestone));
        List<String> kpis = kpiService.getAllKpiByMileStone(milestone.getId());

        verify(milestoneRepository, times(1)).findById(milestone.getId());
        assertEquals(0, kpis.size());
    }


    @Test
    public void testGetAllKpiByRoadMap_oneProjectOneRoadMapsTreeMileStoneOnEachRoadMap_fourElementFound() {
        User user = new User(2L);
        Project project = new Project(4L, "Great project", user);
        Roadmap roadmap = new Roadmap(3L, "Roadmap_01", LocalDate.now(), project);

        Milestone milestone1 = new Milestone(1L, "Milestone_01", LocalDate.now(), LocalDate.now().plusDays(10), roadmap, new ArrayList<>());
        milestone1.addKpi("kpi_01_01");

        Milestone milestone2 = new Milestone(9L, "Milestone_02", LocalDate.now(), LocalDate.now().plusDays(10), roadmap, new ArrayList<>());
        milestone2.addKpi("kpi_01_01");
        milestone2.addKpi("kpi_01_02");

        Milestone milestone3 = new Milestone(8L, "Milestone_03", LocalDate.now(), LocalDate.now().plusDays(10), roadmap, new ArrayList<>());
        milestone3.addKpi("kpi_01_01");

        when(roadmapRepository.findById(roadmap.getId())).thenReturn(Optional.of(roadmap));
        when(milestoneRepository.findAllByRoadmap(roadmap)).thenReturn(Arrays.asList(milestone1, milestone2, milestone3));

        List<String> kpis = kpiService.getAllKpiByRoadMap(roadmap.getId());

        verify(roadmapRepository, times(1)).findById(roadmap.getId());
        verify(milestoneRepository, times(1)).findAllByRoadmap(roadmap);

        assertEquals(4, kpis.size());
    }


    @Test
    public void testGetAllKpiByRoadMap_emptyList() {
        User user = new User(2L);
        Project project = new Project(4L, "Great project", user);
        Roadmap roadmap = new Roadmap(3L, "Roadmap_01", LocalDate.now(), project);

        when(roadmapRepository.findById(roadmap.getId())).thenReturn(Optional.of(roadmap));
        List<String> kpis = kpiService.getAllKpiByRoadMap(roadmap.getId());

        verify(roadmapRepository, times(1)).findById(roadmap.getId());
        assertEquals(0, kpis.size());
    }

    @Test
    public void testRemoveKpi_kpiRemoved() {
        User user = new User(2L);
        Project project = new Project(4L, "Great project", user);
        Roadmap roadmap = new Roadmap(3L, "Roadmap_01", LocalDate.now(), project);
        Milestone milestone = new Milestone(1L, "Milestone_01", LocalDate.now(), LocalDate.now().plusDays(10), roadmap, new ArrayList<>());
        milestone.addKpi("kpi_01_01");
        milestone.addKpi("kpi_01_02");

        Milestone milestoneAfterRemovingKpi = new Milestone(1L, "Milestone_01", LocalDate.now(), LocalDate.now().plusDays(10), roadmap, new ArrayList<>());
        milestoneAfterRemovingKpi.addKpi("kpi_01_02");

        when(milestoneRepository.findById(milestone.getId())).thenReturn(Optional.of(milestone));

        kpiService.removeKpi(milestone.getId(), "kpi_01_01");

        verify(milestoneRepository, times(1)).findById(milestone.getId());
        verify(milestoneRepository, times(1)).save(argThat(
                argument ->
                        argument.getId().equals(milestoneAfterRemovingKpi.getId()) &&
                                argument.getKpis().size() == milestoneAfterRemovingKpi.getKpis().size() &&
                                argument.getName().equals(milestoneAfterRemovingKpi.getName()) &&
                                !argument.getKpis().contains("kpi_01_01")
        ));
    }

    @Test
    public void testRemoveKpi_kpiNotExist_EntityNotFoundException() {
        User user = new User(2L);
        Project project = new Project(4L, "Great project", user);
        Roadmap roadmap = new Roadmap(3L, "Roadmap_01", LocalDate.now(), project);
        Milestone milestone = new Milestone(1L, "Milestone_01", LocalDate.now(), LocalDate.now().plusDays(10), roadmap, new ArrayList<>());

        when(milestoneRepository.findById(milestone.getId())).thenReturn(Optional.of(milestone));

        Exception exception = assertThrows(EntityNotFoundException.class, () ->
                kpiService.removeKpi(milestone.getId(), "kpi_01_01"));

        verify(milestoneRepository, times(1)).findById(milestone.getId());
        assertEquals("Error! This kpi doesn't exist in our DB", exception.getMessage());
    }

    @Test
    void testProjectById_projectDoesNotExist_EntityNotFoundException() {
        Exception exception = assertThrows(EntityNotFoundException.class, () -> kpiService.getProjectById(1));

        verify(projectRepository, times(1)).findById(1L);
        assertEquals("Error! This project doesn't exist in our DB", exception.getMessage());
    }

    @Test
    void testGetRoadMapById_roadMapNotExist_EntityNotFoundException() {
        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> kpiService.getRoadMapById(1));

        verify(roadmapRepository, times(1)).findById(1L);
        assertEquals("Error! This roadmap doesn't exist in our DB", exception.getMessage());

    }
}
