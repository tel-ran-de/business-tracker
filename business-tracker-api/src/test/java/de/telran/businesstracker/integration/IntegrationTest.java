package de.telran.businesstracker.integration;

import de.telran.businesstracker.model.*;
import de.telran.businesstracker.repositories.*;
import de.telran.businesstracker.service.TaskService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class IntegrationTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MilestoneRepository milestoneRepository;
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    RoadmapRepository roadmapRepository;
    @Autowired
    TaskService taskService;

    @Test
    public void integrationTestTask() {
        User user = new User();
        userRepository.save(user);
        Project project = Project.builder().build();
        Roadmap roadmap = Roadmap.builder().build();
        projectRepository.save(project);
        roadmapRepository.save(roadmap);
        // Member member = new Member(1L, "Boss", project, user);
        Member member = Member.builder().build();
        memberRepository.save(member);
        Milestone milestone = new Milestone();
        // Milestone milestone = new Milestone(2L, "Milestone", LocalDate.now(), LocalDate.now().plusDays(3), roadmap);
        milestoneRepository.save(milestone);
        Task task = taskService.add("Task1", false, false, "Documnet", milestone.getId(), member.getId());

        Assertions.assertEquals("Task1", task.getName());
        Assertions.assertEquals(false, task.isFinished());
        Assertions.assertEquals(milestone.getId(), task.getMilestone().getId());
        Assertions.assertEquals(member.getId(), task.getResponsibleMember().getId());

        taskService.edit(task.getId(), "Task2", true, false, "Document");
        Task editedTask = taskService.getById(task.getId());
        Assertions.assertEquals("Task2", editedTask.getName());
        Assertions.assertEquals(true, editedTask.isFinished());

        Task task1 = taskService.add("Task1", false, false, "Document", milestone.getId(), member.getId());
        Assertions.assertEquals("Task2", taskService.getById(task.getId()).getName());

        taskService.removeById(task1.getId());
        taskService.removeById(task.getId());

        milestoneRepository.delete(milestone);
        memberRepository.delete(member);
        roadmapRepository.delete(roadmap);
        projectRepository.delete(project);
        userRepository.delete(user);

    }
}
