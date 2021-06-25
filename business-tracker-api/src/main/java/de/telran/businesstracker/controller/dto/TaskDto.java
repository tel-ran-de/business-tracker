package de.telran.businesstracker.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {

    public Long id;

    public String name;

    public boolean finished;
    public boolean acitve;

    public Long milestoneId;

    public Long memberId;

    public String delivery;
}
