package de.telran.businesstracker.service;

import de.telran.businesstracker.data.Member;
import de.telran.businesstracker.data.Milestone;
import de.telran.businesstracker.data.Project;
import de.telran.businesstracker.data.Roadmap;
import de.telran.businesstracker.data.Task;
import de.telran.businesstracker.data.User;
import de.telran.businesstracker.repositories.MemberRepository;
import de.telran.businesstracker.repositories.MilestoneRepository;
import de.telran.businesstracker.repositories.TaskRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class TaskServiceTest {

    @Mock
    MemberRepository memberRepository;

    @Mock
    MilestoneRepository milestoneRepository;

    @Mock
    TaskRepository taskRepository;

    @InjectMocks
    TaskService taskService;

    @Test
    public void testAdd_success() {
        User user = new User();
        Project project = new Project();
        Roadmap roadmap = new Roadmap();
        Member member = new Member(1L, "Boss", project, user);
        Milestone milestone = new Milestone(3L, "Milestone", LocalDate.now(),
                LocalDate.now().plusDays(10), roadmap);

        when(memberRepository.findById(member.getId())).thenReturn(Optional.of(member));
        when(milestoneRepository.findById(milestone.getId())).thenReturn(Optional.of(milestone));

        Task task = Task.builder()
                .id(5L)
                .name("Task")
                .finished(false)
                .milestone(milestone)
                .responsibleMember(member)
                .build();
        taskService.add(task.getName(), task.getFinished(), task.getMilestone().getId(), task.getResponsibleMember().getId());

        verify(taskRepository, times(1)).save(any());
        verify(taskRepository, times(1)).save(argThat(savedTask -> savedTask.getName().equals(task.getName()) &&
                savedTask.getFinished().equals(false) && savedTask.getMilestone().getId().equals(milestone.getId()) &&
                savedTask.getResponsibleMember().getId().equals(member.getId()))
        );
    }

    @Test
    public void testAdd_memberDoesNotExist_EntityNotFoundException() {
        User user = new User();
        Project project = new Project();
        Roadmap roadmap = new Roadmap();
        Member member = new Member(1L, "Boss", project, user);
        Milestone milestone = new Milestone(3L, "Milestone", LocalDate.now(),
                LocalDate.now().plusDays(10), roadmap);

        Task task = Task.builder()
                .id(5L)
                .name("Task")
                .finished(false)
                .milestone(milestone)
                .responsibleMember(member)
                .build();

        Exception exception = assertThrows(EntityNotFoundException.class, () ->
                taskService.add(task.getName(), task.getFinished(), task.getMilestone().getId(), task.getResponsibleMember().getId() + 1));

        verify(memberRepository, times(1)).findById(any());
        assertEquals("Error! This member doesn't exist in our DB", exception.getMessage());
    }

    @Test
    public void testEdit_taskExist_fieldsChanged() {

        User user = new User();
        Project project = new Project();
        Roadmap roadmap = new Roadmap();
        Member oldMember = new Member(1L, "Boss", project, user);
        Milestone oldMilestone = new Milestone(3L, "Milestone", LocalDate.now(),
                LocalDate.now().plusDays(10), roadmap);

        Task oldTask = Task.builder()
                .id(5L)
                .name("Task")
                .finished(false)
                .milestone(oldMilestone)
                .responsibleMember(oldMember)
                .build();

        Member newMember = new Member(7L, "Dev", project, user);
        Milestone newMilestone = new Milestone(12L, "newMilestone", LocalDate.now(),
                LocalDate.now().plusDays(24), roadmap);

        String name = "newTask";
        when(memberRepository.findById(newMember.getId())).thenReturn(Optional.of(newMember));
        when(milestoneRepository.findById(newMilestone.getId())).thenReturn(Optional.of(newMilestone));

        when(taskRepository.findById(oldTask.getId())).thenReturn(Optional.of(oldTask));
        taskService.edit(oldTask.getId(), name, true, newMilestone.getId(), newMember.getId());

        verify(taskRepository, times(1)).save(any());
        verify(taskRepository, times(1)).save(argThat(savedTask -> savedTask.getName().equals(name) &&
                savedTask.getFinished().equals(true) && savedTask.getMilestone().getId().equals(newMilestone.getId()) &&
                savedTask.getResponsibleMember().getId().equals(newMember.getId()))
        );
    }

    @Test
    void getAll_twoObjects() {
        User user = new User();
        Project project = new Project();
        Roadmap roadmap = new Roadmap();
        Member member = new Member(1L, "Boss", project, user);
        Milestone milestone = new Milestone(3L, "Milestone", LocalDate.now(),
                LocalDate.now().plusDays(10), roadmap);

        Task task1 = Task.builder()
                .id(5L)
                .name("Task")
                .finished(false)
                .milestone(milestone)
                .responsibleMember(member)
                .build();

        Task task2 = Task.builder()
                .id(6L)
                .name("Task2")
                .finished(false)
                .milestone(milestone)
                .responsibleMember(member)
                .build();

        List<Task> tasks = new ArrayList<>();

        tasks.add(task1);
        tasks.add(task2);

        when(taskRepository.findAll()).thenReturn(tasks);

        assertEquals(task1.getName(), taskService.getAll().get(0).getName());
        assertEquals(task1.getFinished(), taskService.getAll().get(0).getFinished());
        assertEquals(task1.getMilestone(), taskService.getAll().get(0).getMilestone());
        assertEquals(task1.getResponsibleMember(), taskService.getAll().get(0).getResponsibleMember());

        assertEquals(task2.getName(), taskService.getAll().get(1).getName());
        assertEquals(task2.getFinished(), taskService.getAll().get(1).getFinished());
        assertEquals(task2.getMilestone(), taskService.getAll().get(1).getMilestone());
        assertEquals(task2.getResponsibleMember(), taskService.getAll().get(1).getResponsibleMember());
    }

    @Test
    void testGetById_objectExist() {
        User user = new User();
        Project project = new Project();
        Roadmap roadmap = new Roadmap();
        Member member = new Member(1L, "Boss", project, user);
        Milestone milestone = new Milestone(3L, "Milestone", LocalDate.now(),
                LocalDate.now().plusDays(10), roadmap);

        Task task = Task.builder()
                .id(5L)
                .name("Task")
                .finished(false)
                .milestone(milestone)
                .responsibleMember(member)
                .build();

        when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));
        Task task1 = taskService.getById(task.getId());

        assertEquals(task1.getName(), task.getName());
        assertEquals(task1.getFinished(), task.getFinished());
        assertEquals(task1.getMilestone(), task.getMilestone());
        assertEquals(task1.getResponsibleMember(), task.getResponsibleMember());

        verify(taskRepository, times(1)).findById(argThat(
                id -> id.equals(task.getId())));
    }

    @Test
    void testGetById_objectNotExist() {
        User user = new User();
        Project project = new Project();
        Roadmap roadmap = new Roadmap();
        Member member = new Member(1L, "Boss", project, user);
        Milestone milestone = new Milestone(3L, "Milestone", LocalDate.now(),
                LocalDate.now().plusDays(10), roadmap);

        Task task = Task.builder()
                .id(5L)
                .name("Task")
                .finished(false)
                .milestone(milestone)
                .responsibleMember(member)
                .build();

        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> taskService.getById(task.getId() + 1));

        verify(taskRepository, times(1)).findById(any());
        assertEquals("Error! This task doesn't exist in our DB", exception.getMessage());

    }

    @Captor
    ArgumentCaptor<Task> taskArgumentCaptor;

    @Test
    void removeById_oneObjectDeleted() {
        User user = new User();
        Project project = new Project();
        Roadmap roadmap = new Roadmap();
        Member member = new Member(1L, "Boss", project, user);
        Milestone milestone = new Milestone(3L, "Milestone", LocalDate.now(),
                LocalDate.now().plusDays(10), roadmap);

        Task task = Task.builder()
                .id(5L)
                .name("Task")
                .finished(false)
                .milestone(milestone)
                .responsibleMember(member)
                .build();
        when(memberRepository.findById(member.getId())).thenReturn(Optional.of(member));
        when(milestoneRepository.findById(milestone.getId())).thenReturn(Optional.of(milestone));

        taskService.add(task.getName(), task.getFinished(), task.getMilestone().getId(), task.getResponsibleMember().getId());
        taskService.removeById(task.getId());

        List<Task> capturedTasks = taskArgumentCaptor.getAllValues();
        verify(taskRepository, times(1)).deleteById(task.getId());
        assertEquals(0, capturedTasks.size());
    }
}