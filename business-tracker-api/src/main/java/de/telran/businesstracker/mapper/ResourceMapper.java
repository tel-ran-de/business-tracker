package de.telran.businesstracker.mapper;

import de.telran.businesstracker.data.Resource;
import de.telran.businesstracker.dto.ResourceDto;
import org.springframework.stereotype.Component;

@Component
public class ResourceMapper {

    public ResourceDto toDto(Resource resource) {
        return ResourceDto.builder()
                .id(resource.getId())
                .name(resource.getName())
                .hours(resource.getHours())
                .cost(resource.getCost())
                .taskId(resource.getTask().getId())
                .build();
    }
}
