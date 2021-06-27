package de.telran.businesstracker.controller.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class TaskToAddDto {

    public String name;
    public boolean finished;
    public boolean active;
    public long mileStoneId;
    public long memberId;
    public String delivery;

    public List<ResourceDto> resources;
}
