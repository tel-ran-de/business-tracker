package de.telran.businesstracker.service;

import de.telran.businesstracker.model.Member;
import de.telran.businesstracker.model.Project;
import de.telran.businesstracker.model.User;
import de.telran.businesstracker.repositories.MemberRepository;
import de.telran.businesstracker.repositories.ProjectRepository;
import de.telran.businesstracker.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

    @Test
    public void testAdd_success() {
        User user = new User(2L);
        Project project = new Project(3L, "Project", user);

        when(projectRepository.findById(project.getId())).thenReturn(Optional.of(project));
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        Member member = Member.builder()
                .id(1L)
                .position("Dev")
                .project(project)
                .user(user)
                .build();

        memberService.add(member.getPosition(), member.getProject().getId(), member.getUser().getId());

        verify(memberRepository, times(1)).save(any());
        verify(memberRepository, times(1)).save(argThat(savedMember -> savedMember.getPosition().equals(member.getPosition()) &&
                savedMember.getProject().getId().equals(project.getId()) &&
                savedMember.getUser().getId().equals(user.getId()))
        );
    }

    @Test
    public void testAdd_projectDoesNotExist_EntityNotFoundException() {
        User user = new User(2L);
        Project project = new Project(3L, "Project", user);

        Member member = Member.builder()
                .id(1L)
                .position("Dev")
                .project(project)
                .user(user)
                .build();

        Exception exception = assertThrows(EntityNotFoundException.class, () ->
                memberService.add(member.getPosition(), member.getProject().getId() + 100, member.getUser().getId()));

        verify(projectRepository, times(1)).findById(any());
        assertEquals("Error! This project doesn't exist in our DB", exception.getMessage());
    }

    @Test
    public void memberEdit_memberExist_fieldsChanged() {

        User user = new User(2L);
        Project project = new Project(3L, "Project", user);

        Member member = Member.builder()
                .id(1L)
                .position("Dev")
                .project(project)
                .user(user)
                .build();

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
        User user = new User(2L);
        Project project = new Project(3L, "Project", user);

        Member member = Member.builder()
                .id(1L)
                .position("Dev")
                .project(project)
                .user(user)
                .build();

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
        User user = new User(2L);
        Project project = new Project(3L, "Project", user);

        Member member = Member.builder()
                .id(1L)
                .position("Dev")
                .project(project)
                .user(user)
                .build();

        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> memberService.getById(member.getId() + 1));

        verify(memberRepository, times(1)).findById(any());
        assertEquals("Error! This member doesn't exist in our DB", exception.getMessage());

    }

    @Captor
    ArgumentCaptor<Member> taskArgumentCaptor;

    @Test
    void removeById_oneObjectDeleted() {
        User user = new User(2L);
        Project project = new Project(3L, "Project", user);

        Member member = Member.builder()
                .id(1L)
                .position("Dev")
                .project(project)
                .user(user)
                .build();

        when(projectRepository.findById(project.getId())).thenReturn(Optional.of(project));
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        memberService.add(member.getPosition(), member.getProject().getId(), member.getUser().getId());
        memberService.removeById(member.getId());

        List<Member> capturedMembers = taskArgumentCaptor.getAllValues();
        verify(memberRepository, times(1)).deleteById(member.getId());
        assertEquals(0, capturedMembers.size());
    }
}
