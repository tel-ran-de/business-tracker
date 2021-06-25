package de.telran.businesstracker.service;

import de.telran.businesstracker.model.Member;
import de.telran.businesstracker.model.Milestone;
import de.telran.businesstracker.model.Task;
import de.telran.businesstracker.repositories.MemberRepository;
import de.telran.businesstracker.repositories.MilestoneRepository;
import de.telran.businesstracker.repositories.ResourceRepository;
import de.telran.businesstracker.repositories.TaskRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class TaskService {

    static final String MEMBER_DOES_NOT_EXIST = "Error! This member doesn't exist in our DB";
    static final String MILESTONE_DOES_NOT_EXIST = "Error! This milestone doesn't exist in our DB";
    static final String TASK_DOES_NOT_EXIST = "Error! This task doesn't exist in our DB";


    TaskRepository taskRepository;
    MemberRepository memberRepository;
    MilestoneRepository milestoneRepository;
    ResourceRepository resourceRepository;

    public TaskService(TaskRepository taskRepository, MemberRepository memberRepository, MilestoneRepository milestoneRepository, ResourceRepository resourceRepository) {
        this.taskRepository = taskRepository;
        this.memberRepository = memberRepository;
        this.milestoneRepository = milestoneRepository;
        this.resourceRepository = resourceRepository;
    }

    public Task add(String name, boolean finished, Long milestoneId, Long memberId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException(MEMBER_DOES_NOT_EXIST));
        Milestone milestone = milestoneRepository.findById(milestoneId)
                .orElseThrow(() -> new EntityNotFoundException(MILESTONE_DOES_NOT_EXIST));
        Task task = Task.builder()
                .name(name)
                .milestone(milestone)
                .finished(finished)
                .responsibleMember(member)
                .build();
        taskRepository.save(task);

        return task;
    }

    public void edit(Long id, String name, boolean finished) {
        Task task = getById(id);
        task.setName(name);
        task.setFinished(finished);
        taskRepository.save(task);
    }

    public Task getById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(TASK_DOES_NOT_EXIST));
    }

    public void removeById(Long id) {
        taskRepository.deleteById(id);
    }

    public List<Task> getAllTasksByMileStoneId(long milestoneId) {
        Milestone milestone = milestoneRepository.findById(milestoneId)
                .orElseThrow(() -> new EntityNotFoundException(MILESTONE_DOES_NOT_EXIST));

        return taskRepository.findAllByMilestone(milestone);
    }

    public List<Task> getByActiveParam(boolean isActive) {
        return taskRepository.findAllByActive(isActive);
    }
}



