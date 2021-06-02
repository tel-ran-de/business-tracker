package de.telran.businesstracker.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDto {

    public Long id;

    public String name;

    public Long userId;
}
