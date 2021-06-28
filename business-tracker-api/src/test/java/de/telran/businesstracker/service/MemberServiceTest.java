package de.telran.businesstracker.service;

import de.telran.businesstracker.model.Member;
import de.telran.businesstracker.model.Project;
import de.telran.businesstracker.model.User;
import de.telran.businesstracker.repositories.MemberRepository;
import de.telran.businesstracker.repositories.ProjectRepository;
import de.telran.businesstracker.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    MemberRepository memberRepository;

    @Mock
    ProjectRepository projectRepository;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    MemberService memberService;

    private Project project;
    private User user;
    private Member member;

    @BeforeEach
    public void beforeEachTest() {
        user = new User(1L);
        project = new Project(1L, "Some project", user);
        member = new Member(1L, "img-url", "Ivan", "Petrov", "Boss", project, user);
    }

    @Test
    public void testAdd_success() {
        when(projectRepository.findById(project.getId())).thenReturn(Optional.of(project));
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        memberService.add(member.getPosition(), member.getProject().getId(), member.getUser().getId());

        verify(memberRepository, times(1)).save(any());
        verify(memberRepository, times(1)).save(argThat(savedMember ->
                savedMember.getPosition().equals(member.getPosition()) &&
                        savedMember.getProject().getId().equals(project.getId()) &&
                        savedMember.getUser().getId().equals(user.getId()))
        );
    }

    @Test
    public void testAdd_projectDoesNotExist_EntityNotFoundException() {
        Exception exception = assertThrows(EntityNotFoundException.class, () ->
                memberService.add(member.getPosition(), member.getProject().getId() + 100, member.getUser().getId()));

        verify(projectRepository, times(1)).findById(any());
        assertEquals("Error! This project doesn't exist in our DB", exception.getMessage());
    }

    @Test
    public void memberEdit_memberExist_fieldsChanged() {
        String newPosition = "Senior";

        when(memberRepository.findById(member.getId())).thenReturn(Optional.of(member));
        memberService.edit(member.getId(), newPosition);

        verify(memberRepository, times(1)).save(any());
        verify(memberRepository, times(1)).save(argThat(savedMember -> savedMember.getPosition().equals(newPosition) &&
                savedMember.getProject().getId().equals(project.getId()) &&
                savedMember.getUser().getId().equals(user.getId()))
        );
    }

    @Test
    void testGetById_objectExist() {
        when(memberRepository.findById(member.getId())).thenReturn(Optional.of(member));
        Member expectedMember = memberService.getById(member.getId());

        assertEquals(expectedMember.getPosition(), member.getPosition());
        assertEquals(expectedMember.getProject(), member.getProject());
        assertEquals(expectedMember.getUser(), member.getUser());

        verify(memberRepository, times(1)).findById(argThat(
                id -> id.equals(member.getId())));
    }

    @Test
    void testGetById_objectNotExist() {
        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> memberService.getById(member.getId() + 1));

        verify(memberRepository, times(1)).findById(any());
        assertEquals("Error! This member doesn't exist in our DB", exception.getMessage());

    }

    @Test
    void testGetMembersByProjectId_fourUsersFound() {
        List<Member> members = Arrays.asList(
                member,
                new Member(2L, "img", "Vasja", "Pupkin", "CTO", project, user),
                new Member(3L, "img", "Max", "Schulz", "Dev", project, user)
        );
        when(projectRepository.findById(project.getId())).thenReturn(Optional.of(project));
        when(memberRepository.findAllByProject(project)).thenReturn(members);

        List<Member> membersResult = memberService.getAllByProjectId(project.getId());

        verify(projectRepository, times(1)).findById(project.getId());
        verify(memberRepository, times(1)).findAllByProject(project);

        assertEquals(members.size(), membersResult.size());
        assertTrue(membersResult.contains(member));

        assertEquals(2L, membersResult.get(1).getId());
        assertEquals("Vasja", membersResult.get(1).getName());
        assertEquals("Pupkin", membersResult.get(1).getLastName());
        assertEquals("CTO", membersResult.get(1).getPosition());

        assertEquals(3L, membersResult.get(2).getId());
        assertEquals("Max", membersResult.get(2).getName());
        assertEquals("Schulz", membersResult.get(2).getLastName());
        assertEquals("Dev", membersResult.get(2).getPosition());
    }

    @Captor
    ArgumentCaptor<Member> taskArgumentCaptor;

    @Test
    void removeById_oneObjectDeleted() {
        when(projectRepository.findById(project.getId())).thenReturn(Optional.of(project));
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        memberService.add(member.getPosition(), member.getProject().getId(), member.getUser().getId());
        memberService.removeById(member.getId());

        List<Member> capturedMembers = taskArgumentCaptor.getAllValues();
        verify(memberRepository, times(1)).deleteById(member.getId());
        assertEquals(0, capturedMembers.size());
    }
}
