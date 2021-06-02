package de.telran.businesstracker.mapper;

import de.telran.businesstracker.data.Milestone;
import de.telran.businesstracker.dto.MilestoneDto;
import org.springframework.stereotype.Component;

@Component
public class MilestoneMapper {

    public MilestoneDto toDto(Milestone milestone) {
        return MilestoneDto.builder()
                .id(milestone.getId())
                .name(milestone.getName())
                .startDate(milestone.getStartDate())
                .finishDate(milestone.getFinishDate())
                .roadmapId(milestone.getRoadmap().getId())
                .build();
    }
}
