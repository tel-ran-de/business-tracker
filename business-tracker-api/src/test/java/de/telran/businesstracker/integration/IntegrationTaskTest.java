package de.telran.businesstracker.integration;

import de.telran.businesstracker.model.*;
import de.telran.businesstracker.repositories.*;
import de.telran.businesstracker.service.TaskService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class IntegrationTaskTest {

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
        projectRepository.save(project);

        Roadmap roadmap = Roadmap.builder().build();
        roadmapRepository.save(roadmap);

        Member member = Member.builder().build();
        memberRepository.save(member);

        Milestone milestone = new Milestone();
        milestoneRepository.save(milestone);


        Task task = taskService.add("Task1", false, milestone.getId(), member.getId());

        Assertions.assertEquals("Task1", task.getName());
        Assertions.assertEquals(false, task.getFinished());
        Assertions.assertEquals(milestone.getId(), task.getMilestone().getId());
        Assertions.assertEquals(member.getId(), task.getResponsibleMember().getId());

        taskService.edit(task.getId(), "Task2", true);
        Task editedTask = taskService.getById(task.getId());
        Assertions.assertEquals("Task2", editedTask.getName());
        Assertions.assertEquals(true, editedTask.getFinished());

        Task task1 = taskService.add("Task1", false, milestone.getId(), member.getId());
        Assertions.assertEquals("Task2", taskService.getById(task.getId()).getName());

        List<Task> expected = taskService.getAll();
        Assertions.assertEquals(expected.size(), 2);
        Assertions.assertEquals(expected.get(0).getName(), "Task2");

        taskService.removeById(task1.getId());
        taskService.removeById(task.getId());

        List<Task> expected1 = taskService.getAll();
        Assertions.assertEquals(expected1.size(), 0);
    }
}
