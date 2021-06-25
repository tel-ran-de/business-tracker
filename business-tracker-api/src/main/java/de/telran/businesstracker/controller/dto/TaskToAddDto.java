package de.telran.businesstracker.controller.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class TaskToAddDto {

    public Long id;

    public String name;

    public Boolean finished;

    public Long milestoneId;

    public Long memberId;

    public List<ResourceDto> resources;
}
