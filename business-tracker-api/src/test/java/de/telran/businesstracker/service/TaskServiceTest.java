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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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
                savedTask.getFinished().equals(false) && savedTask.getMilestone().getId() == milestone.getId() &&
                savedTask.getResponsibleMember().getId() == member.getId())
        );
    }

    @Test
    public void testEdit_taskExist_AllFieldsChanged() {

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
        Boolean finished = true;
        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(newMember));
        when(milestoneRepository.findById(anyLong())).thenReturn(Optional.of(newMilestone));

        taskService.edit(oldTask, name, finished, newMilestone.getId(), newMember.getId());

        verify(taskRepository, times(1)).save(any());
        verify(taskRepository, times(1)).save(argThat(savedTask -> savedTask.getName().equals(name) &&
                savedTask.getFinished().equals(true) && savedTask.getMilestone().getId() == newMilestone.getId() &&
                savedTask.getResponsibleMember().getId() == newMember.getId())
        );
    }

    @Test
    void getAll() {
    }

    @Test
    void getById() {
    }

    @Test
    void removeById() {
    }
}