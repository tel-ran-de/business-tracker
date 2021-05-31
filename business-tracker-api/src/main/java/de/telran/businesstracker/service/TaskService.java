package de.telran.businesstracker.service;

import de.telran.businesstracker.data.Member;
import de.telran.businesstracker.data.Milestone;
import de.telran.businesstracker.data.Task;
import de.telran.businesstracker.repositories.MemberRepository;
import de.telran.businesstracker.repositories.MilestoneRepository;
import de.telran.businesstracker.repositories.TaskRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {

    static final String MEMBER_DOES_NOT_EXIST = "Error! This member doesn't exist in our DB";
    static final String MILESTONE_DOES_NOT_EXIST = "Error! This milestone doesn't exist in our DB";
    static final String TASK_DOES_NOT_EXIST = "Error! This task doesn't exist in our DB";


    TaskRepository taskRepository;
    MemberRepository memberRepository;
    MilestoneRepository milestoneRepository;

    public TaskService(TaskRepository taskRepository, MemberRepository memberRepository, MilestoneRepository milestoneRepository){
        this.taskRepository = taskRepository;
        this.memberRepository=memberRepository;
        this.milestoneRepository =milestoneRepository;
    }

    public Task add(String name,boolean finished, Long milestoneId, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new EntityNotFoundException(MEMBER_DOES_NOT_EXIST));
        Milestone milestone = milestoneRepository.findById(milestoneId).orElseThrow(() -> new EntityNotFoundException(MILESTONE_DOES_NOT_EXIST));
        Task task = Task.builder().name(name).milestone(milestone).finished(finished).responsibleMember(member).build();
        taskRepository.save(task);
        return task;
    }

    public Task edit(Task task, String name,boolean finished, Long milestoneId, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new EntityNotFoundException(MEMBER_DOES_NOT_EXIST));
        Milestone milestone = milestoneRepository.findById(milestoneId).orElseThrow(() -> new EntityNotFoundException(MILESTONE_DOES_NOT_EXIST));
        task.setName(name);
        task.setFinished(finished);
        task.setResponsibleMember(member);
        task.setMilestone(milestone);
        taskRepository.save(task);
        return task;
    }
    public List<Task> getAll() {
        return new ArrayList<>(taskRepository.findAll());
    }

    public Task getById(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(TASK_DOES_NOT_EXIST));
    }

    public void removeById(Long id) {
        taskRepository.deleteById(id);
    }
}


