package de.telran.businesstracker.service;

import de.telran.businesstracker.model.*;
import de.telran.businesstracker.repositories.MilestoneRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class KpiServiceTest {

    @Mock
    MilestoneRepository repository;

    @InjectMocks
    KpiService kpiService;

    @Test
    public void testAdd_success() {
        User user = new User(2L);
        Project project = new Project(4L, "Great project", user);
        Roadmap roadmap = new Roadmap(3L, "Roadmap", LocalDate.now(), project);
        Milestone milestone = new Milestone(1L, "Milestone", LocalDate.now(), LocalDate.now().plusDays(10), roadmap, new ArrayList<>());
        when(repository.findById(milestone.getId())).thenReturn(Optional.of(milestone));

        kpiService.add(milestone.getId(), "some kpi name");

        verify(repository, times(1)).save(any());
        verify(repository, times(1)).save(argThat(savedMilestone -> savedMilestone.getKpis().size() == 1));
    }

    @Test
    public void testAdd_roadmapDoesNotExist_EntityNotFoundException() {
        User user = new User(2L);
        Project project = new Project(4L, "Great project", user);
        Roadmap roadmap = new Roadmap(3L, "Roadmap", LocalDate.now(), project);

        Milestone milestone = new Milestone(1L, "Milestone", LocalDate.now(), LocalDate.now().plusDays(10), roadmap, new ArrayList<>());

        Exception exception = assertThrows(EntityNotFoundException.class, () ->
                kpiService.add(milestone.getId(), "some kpi name"));

        verify(repository, times(1)).findById(any());
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

        when(repository.findById(milestone1.getId())).thenReturn(Optional.of(milestone1));
        when(repository.findAllByRoadmapProject(project)).thenReturn(Arrays.asList(milestone1, milestone2));

        List<String> kpis = kpiService.getAllKpiByProject(milestone1.getId());

        verify(repository, times(1)).findById(milestone1.getId());
        verify(repository, times(1)).findAllByRoadmapProject(project);

        assertTrue(kpis.contains("kpi_01_01"));
        assertTrue(kpis.contains("kpi_01_02"));
        assertTrue(kpis.contains("kpi_01_03"));
        assertTrue(kpis.contains("kpi_01_04"));
        assertTrue(kpis.contains("kpi_02_01"));
        assertTrue(kpis.contains("kpi_02_01"));
    }

    @Test
    public void testGetAllKpisByProject_oneRoadMapWithTwoMileStones_sixElementFound() {
        User user = new User(2L);
        Project project = new Project(4L, "Great project", user);
        Roadmap roadmap1 = new Roadmap(3L, "Roadmap_01", LocalDate.now(), project);
        Milestone milestone1 = new Milestone(1L, "Milestone_01", LocalDate.now(), LocalDate.now().plusDays(10), roadmap1, new ArrayList<>());
        milestone1.addKpi("kpi_01_01");
        milestone1.addKpi("kpi_01_02");
        milestone1.addKpi("kpi_01_03");
        milestone1.addKpi("kpi_01_04");

        Milestone milestone2 = new Milestone(7L, "Milestone_02", LocalDate.now(), LocalDate.now().plusDays(10), roadmap1, new ArrayList<>());
        milestone2.addKpi("kpi_02_01");
        milestone2.addKpi("kpi_02_02");

        when(repository.findById(milestone1.getId())).thenReturn(Optional.of(milestone1));
        when(repository.findAllByRoadmapProject(project)).thenReturn(Arrays.asList(milestone1, milestone2));

        List<String> kpis = kpiService.getAllKpiByProject(milestone1.getId());

        verify(repository, times(1)).findById(milestone1.getId());
        verify(repository, times(1)).findAllByRoadmapProject(project);

        assertTrue(kpis.contains("kpi_01_01"));
        assertTrue(kpis.contains("kpi_01_02"));
        assertTrue(kpis.contains("kpi_01_03"));
        assertTrue(kpis.contains("kpi_01_04"));
        assertTrue(kpis.contains("kpi_02_01"));
        assertTrue(kpis.contains("kpi_02_01"));
    }

    @Test
    public void testGetAllKpisByProject_oneRoadMapWithTwoMileStones_noKpiExist_emptySet() {
        User user = new User(2L);
        Project project = new Project(4L, "Great project", user);
        Roadmap roadmap1 = new Roadmap(3L, "Roadmap_01", LocalDate.now(), project);
        Milestone milestone1 = new Milestone(1L, "Milestone_01", LocalDate.now(), LocalDate.now().plusDays(10), roadmap1, new ArrayList<>());
        Milestone milestone2 = new Milestone(7L, "Milestone_02", LocalDate.now(), LocalDate.now().plusDays(10), roadmap1, new ArrayList<>());

        when(repository.findById(milestone1.getId())).thenReturn(Optional.of(milestone1));
        when(repository.findAllByRoadmapProject(project)).thenReturn(Arrays.asList(milestone1, milestone2));

        List<String> kpis = kpiService.getAllKpiByProject(milestone1.getId());

        verify(repository, times(1)).findById(milestone1.getId());
        verify(repository, times(1)).findAllByRoadmapProject(project);
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

        when(repository.findById(milestone.getId())).thenReturn(Optional.of(milestone));
        List<String> kpis = kpiService.getAllKpiByMileStone(milestone.getId());

        verify(repository, times(1)).findById(milestone.getId());
        assertEquals(4, kpis.size());
    }

    @Test
    public void testGetAllKpiByBMileStone_emptyList() {
        User user = new User(2L);
        Project project = new Project(4L, "Great project", user);
        Roadmap roadmap = new Roadmap(3L, "Roadmap_01", LocalDate.now(), project);
        Milestone milestone = new Milestone(1L, "Milestone_01", LocalDate.now(), LocalDate.now().plusDays(10), roadmap, new ArrayList<>());

        when(repository.findById(milestone.getId())).thenReturn(Optional.of(milestone));
        List<String> kpis = kpiService.getAllKpiByMileStone(milestone.getId());

        verify(repository, times(1)).findById(milestone.getId());
        assertEquals(0, kpis.size());
    }


    @Test
    public void testGetAllKpiByRoadMap_oneProjectOneRoadMapsTreeMileStoneOnEachRoadMap_fourElementFound() {
        User user = new User(2L);
        Project project = new Project(4L, "Great project", user);
        Roadmap roadmap = new Roadmap(3L, "Roadmap_01", LocalDate.now(), project);

        Milestone milestone1 = new Milestone(1L, "Milestone_01", LocalDate.now(), LocalDate.now().plusDays(10), roadmap, new ArrayList<>());
        milestone1.addKpi("kpi_01_01");

        Milestone milestone2 = new Milestone(9L, "Milestone_01", LocalDate.now(), LocalDate.now().plusDays(10), roadmap, new ArrayList<>());
        milestone2.addKpi("kpi_01_01");
        milestone2.addKpi("kpi_01_02");

        Milestone milestone3 = new Milestone(8L, "Milestone_01", LocalDate.now(), LocalDate.now().plusDays(10), roadmap, new ArrayList<>());
        milestone3.addKpi("kpi_01_01");


        when(repository.findById(milestone1.getId())).thenReturn(Optional.of(milestone1));
        when(repository.findAllByRoadmap(roadmap)).thenReturn(Arrays.asList(milestone1, milestone2, milestone3));

        List<String> kpis = kpiService.getAllKpiByRoadMap(milestone1.getId());

        verify(repository, times(1)).findById(milestone1.getId());
        verify(repository, times(1)).findAllByRoadmap(roadmap);

        assertEquals(4, kpis.size());
    }


    @Test
    public void testGetAllKpiByRoadMap_emptyList() {
        User user = new User(2L);
        Project project = new Project(4L, "Great project", user);
        Roadmap roadmap = new Roadmap(3L, "Roadmap_01", LocalDate.now(), project);
        Milestone milestone = new Milestone(1L, "Milestone_01", LocalDate.now(), LocalDate.now().plusDays(10), roadmap, new ArrayList<>());

        when(repository.findById(milestone.getId())).thenReturn(Optional.of(milestone));
        List<String> kpis = kpiService.getAllKpiByMileStone(milestone.getId());

        verify(repository, times(1)).findById(milestone.getId());
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

        when(repository.findById(milestone.getId())).thenReturn(Optional.of(milestone));

        kpiService.removeKpi(milestone.getId(), "kpi_01_01");

        verify(repository, times(1)).findById(milestone.getId());
        verify(repository, times(1)).save(argThat(
                argument ->
                        argument.getId().equals(milestoneAfterRemovingKpi.getId()) &&
                                argument.getKpis().size() == milestoneAfterRemovingKpi.getKpis().size() &&
                                argument.getName().equals(milestoneAfterRemovingKpi.getName()) &&
                                !argument.getKpis().contains("kpi_01_01")
        ));
    }
}
