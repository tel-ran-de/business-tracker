package de.telran.businesstracker.mapper;

import de.telran.businesstracker.controller.dto.MemberDto;
import de.telran.businesstracker.model.Member;
import de.telran.businesstracker.model.Project;
import de.telran.businesstracker.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class MemberMapperTest {
    @InjectMocks
    MemberMapper memberMapper;

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
    public void mapMemberToTaskDto() {
        MemberDto memberDto = memberMapper.toDto(member);

        assertEquals(member.getId(), memberDto.id);
        assertEquals(member.getLastName(), memberDto.lastName);
        assertEquals(member.getName(), memberDto.name);
        assertEquals(member.getPosition(), memberDto.position);
        assertEquals(member.getImg(), memberDto.img);
        assertEquals(member.getProject().getId(), memberDto.projectId);
        assertEquals(member.getUser().getId(), memberDto.userId);
        assertEquals(7, MemberDto.class.getFields().length);
    }
}
