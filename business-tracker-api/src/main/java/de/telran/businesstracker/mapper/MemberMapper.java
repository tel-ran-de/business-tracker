package de.telran.businesstracker.mapper;

import de.telran.businesstracker.controller.dto.MemberDto;
import de.telran.businesstracker.model.Member;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {

    public MemberDto toDto(Member member) {
        return new MemberDto(member.getId(),
                member.getProject().getId(),
                member.getUser().getId(),
                member.getImg(),
                member.getName(),
                member.getLastName(),
                member.getPosition());
    }
}
