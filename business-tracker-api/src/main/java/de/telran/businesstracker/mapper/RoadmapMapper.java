package de.telran.businesstracker.mapper;

import de.telran.businesstracker.data.Roadmap;
import de.telran.businesstracker.dto.RoadmapDto;
import org.springframework.stereotype.Component;

@Component
public class RoadmapMapper {

    public RoadmapDto toDto(Roadmap roadmap) {
        return RoadmapDto.builder()
                .id(roadmap.getId())
                .name(roadmap.getName())
                .startDate(roadmap.getStartDate())
                .projectId(roadmap.getProject().getId())
                .build();
    }
}
