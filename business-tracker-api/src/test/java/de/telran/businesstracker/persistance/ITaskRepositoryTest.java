package de.telran.businesstracker.persistance;

import de.telran.businesstracker.model.*;
import de.telran.businesstracker.repositories.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class ITaskRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TaskRepository taskRepository;

    @Test
    public void testFindAllByMileStone_oneMileStone_viveRecordsFound() {

        Roadmap roadmap = new Roadmap();
        Member member = new Member();
        Milestone milestone = new Milestone("Milestone", LocalDate.now(), LocalDate.now().plusDays(10), roadmap, new ArrayList<>());

        entityManager.persist(roadmap);
        entityManager.persist(member);
        entityManager.persist(milestone);

        List<Task> tasks = Arrays.asList(
                new Task("Task_01", false, true, "Document", milestone, member),
                new Task("Task_02", false, false, "Document", milestone, member),
                new Task("Task_03", false, true, "Document", milestone, member),
                new Task("Task_04", false, false, "Document", milestone, member),
                new Task("Task_05", false, false, "Document", milestone, member)
        );

        tasks.forEach(task -> entityManager.persist(task));

        entityManager.flush();
        entityManager.clear();

        List<Task> foundTasks = taskRepository.findAllByMilestone(milestone);
        assertEquals(5, foundTasks.size());

        assertEquals(tasks.get(0).getName(), foundTasks.get(0).getName());
        assertEquals(tasks.get(1).getName(), foundTasks.get(1).getName());
        assertEquals(tasks.get(2).getName(), foundTasks.get(2).getName());
        assertEquals(tasks.get(3).getName(), foundTasks.get(3).getName());
        assertEquals(tasks.get(4).getName(), foundTasks.get(4).getName());

        assertEquals(tasks.get(0).getMilestone().getName(), foundTasks.get(0).getMilestone().getName());
        assertEquals(tasks.get(1).getMilestone().getName(), foundTasks.get(1).getMilestone().getName());
        assertEquals(tasks.get(2).getMilestone().getName(), foundTasks.get(2).getMilestone().getName());
        assertEquals(tasks.get(3).getMilestone().getName(), foundTasks.get(3).getMilestone().getName());
        assertEquals(tasks.get(4).getMilestone().getName(), foundTasks.get(4).getMilestone().getName());

        assertEquals(tasks.get(0).isActive(), foundTasks.get(0).isActive());
        assertEquals(tasks.get(2).isActive(), foundTasks.get(2).isActive());
    }

    @Test
    public void testFindAllByMileStone_threeMileStone_viveRecordsFound() {

        Roadmap roadmap = new Roadmap();
        Member member = new Member();
        Milestone milestone = new Milestone("Milestone", LocalDate.now(), LocalDate.now().plusDays(10), roadmap, new ArrayList<>());
        Milestone milestone2 = new Milestone("Milestone_02", LocalDate.now(), LocalDate.now().plusDays(10), roadmap, new ArrayList<>());
        Milestone milestone3 = new Milestone("Milestone_03", LocalDate.now(), LocalDate.now().plusDays(10), roadmap, new ArrayList<>());

        entityManager.persist(roadmap);
        entityManager.persist(member);
        entityManager.persist(milestone);
        entityManager.persist(milestone2);
        entityManager.persist(milestone3);

        List<Task> tasks = Arrays.asList(
                new Task("Task_01", false, true, "Document", milestone, member),
                new Task("Task_02", false, false, "Document", milestone, member),
                new Task("Task_03", false, true, "Document", milestone, member),
                new Task("Task_04", false, false, "Document", milestone, member),
                new Task("Task_05", false, false, "Document", milestone, member),

                new Task("Task_23", false, true, "Document", milestone2, member),
                new Task("Task_24", false, false, "Document", milestone2, member),
                new Task("Task_25", false, false, "Document", milestone2, member),
                new Task("Task_35", false, false, "Document", milestone3, member)
        );

        tasks.forEach(task -> entityManager.persist(task));

        entityManager.flush();
        entityManager.clear();

        List<Task> foundTasks = taskRepository.findAllByMilestone(milestone);
        assertEquals(5, foundTasks.size());

        assertEquals(tasks.get(0).getName(), foundTasks.get(0).getName());
        assertEquals(tasks.get(1).getName(), foundTasks.get(1).getName());
        assertEquals(tasks.get(2).getName(), foundTasks.get(2).getName());
        assertEquals(tasks.get(3).getName(), foundTasks.get(3).getName());
        assertEquals(tasks.get(4).getName(), foundTasks.get(4).getName());

        assertEquals(tasks.get(0).getMilestone().getName(), foundTasks.get(0).getMilestone().getName());
        assertEquals(tasks.get(1).getMilestone().getName(), foundTasks.get(1).getMilestone().getName());
        assertEquals(tasks.get(2).getMilestone().getName(), foundTasks.get(2).getMilestone().getName());
        assertEquals(tasks.get(3).getMilestone().getName(), foundTasks.get(3).getMilestone().getName());
        assertEquals(tasks.get(4).getMilestone().getName(), foundTasks.get(4).getMilestone().getName());

        assertEquals(tasks.get(0).isActive(), foundTasks.get(0).isActive());
        assertEquals(tasks.get(2).isActive(), foundTasks.get(2).isActive());
    }

    @Test
    public void testFindByMileStone_twoMileStone_noElementsFound() {
        User user = new User();
        Project project = Project
                .builder()
                .user(user)
                .name("Some project name")
                .build();

        Roadmap roadmap = Roadmap.
                builder()
                .project(project)
                .name("RM_01")
                .startDate(LocalDate.now())
                .build();

        Member member = new Member();
        Milestone milestone = new Milestone("Milestone", LocalDate.now(), LocalDate.now().plusDays(10), roadmap, new ArrayList<>());
        Milestone milestone2 = new Milestone("Milestone_02", LocalDate.now(), LocalDate.now().plusDays(10), roadmap, new ArrayList<>());

        entityManager.persist(user);
        entityManager.persist(project);
        entityManager.persist(roadmap);

        entityManager.persist(member);

        entityManager.persist(milestone);
        entityManager.persist(milestone2);

        Task task = new Task("Task_01", false, true, "Document", milestone2, member);

        entityManager.persist(task);

        entityManager.flush();
        entityManager.clear();

        List<Task> foundTasks = taskRepository.findAllByMilestone(milestone);
        assertEquals(0, foundTasks.size());
    }

    @Test
    public void testFindByProjectAndActive_oneProject_threeMileStone_fourRecordsFound() {
        User user = new User();
        Project project = Project
                .builder()
                .user(user)
                .name("Some project name")
                .build();

        Roadmap roadmap = Roadmap.
                builder()
                .project(project)
                .name("RM_01")
                .startDate(LocalDate.now())
                .build();

        Member member = new Member();
        Milestone milestone = new Milestone("Milestone", LocalDate.now(), LocalDate.now().plusDays(10), roadmap, new ArrayList<>());
        Milestone milestone2 = new Milestone("Milestone_02", LocalDate.now(), LocalDate.now().plusDays(10), roadmap, new ArrayList<>());
        Milestone milestone3 = new Milestone("Milestone_03", LocalDate.now(), LocalDate.now().plusDays(10), roadmap, new ArrayList<>());

        entityManager.persist(user);
        entityManager.persist(project);

        entityManager.persist(roadmap);
        entityManager.persist(member);

        entityManager.persist(milestone);
        entityManager.persist(milestone2);
        entityManager.persist(milestone3);

        List<Task> tasks = Arrays.asList(
                new Task("Task_01", false, true, "Document", milestone, member),
                new Task("Task_02", true, false, "Document", milestone, member),
                new Task("Task_03", false, true, "Document", milestone, member),
                new Task("Task_04", true, false, "Document", milestone, member),
                new Task("Task_05", false, false, "Document", milestone, member),

                new Task("Task_23", false, true, "Document", milestone2, member),
                new Task("Task_24", false, false, "Document", milestone2, member),
                new Task("Task_25", true, false, "Document", milestone2, member),

                new Task("Task_35", false, true, "Document", milestone3, member)
        );

        tasks.forEach(task -> entityManager.persist(task));

        entityManager.flush();
        entityManager.clear();

        List<Task> foundTasks = taskRepository.findAllByMilestone_Roadmap_ProjectAndActiveTrue(project);
        assertEquals(4, foundTasks.size());

        assertEquals("Task_01", foundTasks.get(0).getName());
        assertEquals("Task_03", foundTasks.get(1).getName());
        assertEquals("Task_23", foundTasks.get(2).getName());
        assertEquals("Task_35", foundTasks.get(3).getName());

        assertEquals("Milestone", foundTasks.get(0).getMilestone().getName());
        assertEquals("Milestone", foundTasks.get(1).getMilestone().getName());
        assertEquals("Milestone_02", foundTasks.get(2).getMilestone().getName());
        assertEquals("Milestone_03", foundTasks.get(3).getMilestone().getName());
    }

    @Test
    public void testFindByProjectAndActive_twoProject_fourMileStone_fourRecordsFound() {
        User user = new User();
        Project project = Project
                .builder()
                .user(user)
                .name("Some project name")
                .build();

        User user2 = new User();
        Project project2 = Project
                .builder()
                .user(user)
                .name("Some project name 2")
                .build();

        Roadmap roadmap = Roadmap.
                builder()
                .project(project)
                .name("RM_01")
                .startDate(LocalDate.now())
                .build();

        Roadmap roadmap2 = Roadmap.
                builder()
                .project(project2)
                .name("RM_01")
                .startDate(LocalDate.now())
                .build();

        Member member = new Member();
        Milestone milestone = new Milestone("Milestone", LocalDate.now(), LocalDate.now().plusDays(10), roadmap, new ArrayList<>());
        Milestone milestone2 = new Milestone("Milestone_02", LocalDate.now(), LocalDate.now().plusDays(10), roadmap, new ArrayList<>());
        Milestone milestone3 = new Milestone("Milestone_03", LocalDate.now(), LocalDate.now().plusDays(10), roadmap, new ArrayList<>());

        Milestone milestone4 = new Milestone("Milestone_03", LocalDate.now(), LocalDate.now().plusDays(10), roadmap2, new ArrayList<>());

        entityManager.persist(user);
        entityManager.persist(user2);

        entityManager.persist(project);
        entityManager.persist(project2);

        entityManager.persist(roadmap);
        entityManager.persist(roadmap2);

        entityManager.persist(member);

        entityManager.persist(milestone);
        entityManager.persist(milestone2);
        entityManager.persist(milestone3);
        entityManager.persist(milestone4);

        List<Task> tasks = Arrays.asList(
                new Task("Task_01", false, true, "Document", milestone, member),
                new Task("Task_02", true, false, "Document", milestone, member),
                new Task("Task_03", false, true, "Document", milestone, member),
                new Task("Task_04", true, false, "Document", milestone, member),
                new Task("Task_05", false, false, "Document", milestone, member),

                new Task("Task_23", false, true, "Document", milestone2, member),
                new Task("Task_24", false, false, "Document", milestone2, member),
                new Task("Task_25", true, false, "Document", milestone2, member),

                new Task("Task_35", false, true, "Document", milestone3, member),

                new Task("Task_223", false, true, "Document", milestone4, member),
                new Task("Task_224", false, true, "Document", milestone4, member),
                new Task("Task_225", true, false, "Document", milestone4, member),
                new Task("Task_235", false, true, "Document", milestone4, member)
        );

        tasks.forEach(task -> entityManager.persist(task));

        entityManager.flush();
        entityManager.clear();

        List<Task> foundTasks = taskRepository.findAllByMilestone_Roadmap_ProjectAndActiveTrue(project);
        assertEquals(4, foundTasks.size());

        assertEquals("Task_01", foundTasks.get(0).getName());
        assertEquals("Task_03", foundTasks.get(1).getName());
        assertEquals("Task_23", foundTasks.get(2).getName());
        assertEquals("Task_35", foundTasks.get(3).getName());

        assertEquals("Milestone", foundTasks.get(0).getMilestone().getName());
        assertEquals("Milestone", foundTasks.get(1).getMilestone().getName());
        assertEquals("Milestone_02", foundTasks.get(2).getMilestone().getName());
        assertEquals("Milestone_03", foundTasks.get(3).getMilestone().getName());
    }


    @Test
    public void testFindByProjectAndActive_oneProject_twoMileStone_noRecordsFound() {
        User user = new User();
        Project project = Project
                .builder()
                .user(user)
                .name("Some project name")
                .build();

        User user2 = new User();
        Project project2 = Project
                .builder()
                .user(user)
                .name("Some project name 2")
                .build();

        Roadmap roadmap = Roadmap.
                builder()
                .project(project2)
                .name("RM_01")
                .startDate(LocalDate.now())
                .build();

        Member member = new Member();
        Milestone milestone = new Milestone("Milestone", LocalDate.now(), LocalDate.now().plusDays(10), roadmap, new ArrayList<>());

        entityManager.persist(user);
        entityManager.persist(user2);

        entityManager.persist(project);
        entityManager.persist(project2);

        entityManager.persist(roadmap);
        entityManager.persist(member);

        entityManager.persist(milestone);

        Task task = new Task("Task_01", false, true, "Document", milestone, member);

        entityManager.persist(task);

        entityManager.flush();
        entityManager.clear();

        List<Task> foundTasks = taskRepository.findAllByMilestone_Roadmap_ProjectAndActiveTrue(project);
        assertEquals(0, foundTasks.size());
    }
}
