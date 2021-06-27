package de.telran.businesstracker.service;

import de.telran.businesstracker.model.*;
import de.telran.businesstracker.repositories.MemberRepository;
import de.telran.businesstracker.repositories.MilestoneRepository;
import de.telran.businesstracker.repositories.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    MemberRepository memberRepository;

    @Mock
    MilestoneRepository milestoneRepository;

    @Mock
    TaskRepository taskRepository;

    @InjectMocks
    TaskService taskService;

    private Task task;
    private Milestone milestone;
    private Member member;

    @BeforeEach
    public void beforeEachTest() {
        User user = new User();
        Project project = new Project();
        Roadmap roadmap = new Roadmap();
        member = new Member(1L, "img-url", "Ivan", "Petrov", "Boss", project, user);
        milestone = new Milestone(3L, "Milestone", LocalDate.now(), LocalDate.now().plusDays(10), roadmap, new ArrayList<>());
        task = new Task(2L, "Task", false, false, "Document", milestone, new ArrayList<>(), member);
    }

    @Test
    public void testAdd_success() {
        when(memberRepository.findById(member.getId()))
                .thenReturn(Optional.of(member));
        when(milestoneRepository.findById(milestone.getId())).thenReturn(Optional.of(milestone));

        taskService.add(task.getName(), task.isFinished(), task.isActive(), task.getDelivery(), task.getMilestone().getId(), task.getResponsibleMember().getId());

        verify(taskRepository, times(1)).save(argThat(savedTask -> savedTask.getName().equals(task.getName()) &&
                !savedTask.isActive() && savedTask.getMilestone().getId().equals(milestone.getId()) &&
                savedTask.getResponsibleMember().getId().equals(member.getId()))
        );
    }

    @Test
    public void testAdd_memberDoesNotExist_EntityNotFoundException() {
        Exception exception = assertThrows(EntityNotFoundException.class, () ->
                taskService.add(task.getName(), task.isFinished(), task.isActive(), task.getDelivery(), task.getMilestone().getId(), task.getResponsibleMember().getId())
        );

        verify(memberRepository, times(1)).findById(any());
        assertEquals("Error! This member doesn't exist in our DB", exception.getMessage());
    }

    @Test
    public void testEdit_taskExist_fieldsNameChanged() {
        String name = "newTask";

        when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));
        taskService.edit(task.getId(), name, task.isFinished(), task.isActive(), task.getDelivery());

        verify(taskRepository, times(1)).save(argThat(
                savedTask -> savedTask.getName().equals(name) &&
                        savedTask.getId().equals(task.getId()) &&
                        savedTask.isFinished() == task.isFinished() &&
                        savedTask.isActive() == task.isActive() &&
                        savedTask.getDelivery().equals(task.getDelivery()) &&
                        savedTask.getMilestone().getId().equals(milestone.getId()) &&
                        savedTask.getResponsibleMember().getId().equals(member.getId()))
        );
    }

    @Test
    public void testEdit_taskExist_fieldsActiveChanged() {
        when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));
        taskService.edit(task.getId(), task.getName(), task.isFinished(), true, task.getDelivery());

        verify(taskRepository, times(1)).save(argThat(
                savedTask -> savedTask.getName().equals(task.getName()) &&
                        savedTask.getId().equals(task.getId()) &&
                        savedTask.isFinished() == task.isFinished() &&
                        savedTask.isActive() == true &&
                        savedTask.getDelivery().equals(task.getDelivery()) &&
                        savedTask.getMilestone().getId().equals(milestone.getId()) &&
                        savedTask.getResponsibleMember().getId().equals(member.getId()))
        );
    }

    @Test
    public void testEdit_taskExist_fieldsFinishedChanged() {
        when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));
        taskService.edit(task.getId(), task.getName(), true, task.isActive(), task.getDelivery());

        verify(taskRepository, times(1)).save(argThat(
                savedTask -> savedTask.getName().equals(task.getName()) &&
                        savedTask.getId().equals(task.getId()) &&
                        savedTask.isFinished() == true &&
                        savedTask.isActive() == task.isActive() &&
                        savedTask.getDelivery().equals(task.getDelivery()) &&
                        savedTask.getMilestone().getId().equals(milestone.getId()) &&
                        savedTask.getResponsibleMember().getId().equals(member.getId()))
        );
    }

    @Test
    public void testEdit_taskExist_fieldsDeliveryChanged() {
        String newDelivery = "SomeNewDelivery";

        when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));
        taskService.edit(task.getId(), task.getName(), task.isFinished(), task.isActive(), newDelivery);

        verify(taskRepository, times(1)).save(argThat(
                savedTask -> savedTask.getName().equals(task.getName()) &&
                        savedTask.getId().equals(task.getId()) &&
                        savedTask.isFinished() == task.isFinished() &&
                        savedTask.isActive() == task.isActive() &&
                        savedTask.getDelivery().equals(newDelivery) &&
                        savedTask.getMilestone().getId().equals(milestone.getId()) &&
                        savedTask.getResponsibleMember().getId().equals(member.getId()))
        );
    }

    @Test
    void testGetById_objectExist() {
        when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));
        Task task1 = taskService.getById(task.getId());

        assertEquals(task1.getName(), task.getName());
        assertEquals(task1.isActive(), task.isActive());
        assertEquals(task1.isFinished(), task.isFinished());
        assertEquals(task1.getDelivery(), task.getDelivery());
        assertEquals(task1.getMilestone(), task.getMilestone());
        assertEquals(task1.getResponsibleMember(), task.getResponsibleMember());

        verify(taskRepository, times(1)).findById(argThat(
                id -> id.equals(this.task.getId())));
    }

    @Test
    void testGetById_objectNotExist() {
        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> taskService.getById(task.getId() + 1));

        verify(taskRepository, times(1)).findById(any());
        assertEquals("Error! This task doesn't exist in our DB", exception.getMessage());
    }

    @Captor
    ArgumentCaptor<Task> taskArgumentCaptor;

    @Test
    void removeById_oneObjectDeleted() {

        when(memberRepository.findById(member.getId())).thenReturn(Optional.of(member));
        when(milestoneRepository.findById(milestone.getId())).thenReturn(Optional.of(milestone));

        taskService.add(task.getName(), task.isFinished(), task.isActive(), task.getDelivery(), task.getMilestone().getId(), task.getResponsibleMember().getId());
        taskService.removeById(task.getId());

        List<Task> capturedTasks = taskArgumentCaptor.getAllValues();
        verify(taskRepository, times(1)).deleteById(task.getId());
        assertEquals(0, capturedTasks.size());
    }
}
