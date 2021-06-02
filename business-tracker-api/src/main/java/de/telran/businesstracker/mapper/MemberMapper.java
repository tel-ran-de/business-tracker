package de.telran.businesstracker.mapper;

import de.telran.businesstracker.data.Member;
import de.telran.businesstracker.dto.MemberDto;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {

    public MemberDto toDto(Member member) {
        return MemberDto.builder()
                .id(member.getId())
                .position(member.getPosition())
                .projectId(member.getProject().getId())
                .userId(member.getUser().getId())
                .build();
    }
}
