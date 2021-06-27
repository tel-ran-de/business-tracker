package de.telran.businesstracker.controller.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {

    public Long id;

    public Long projectId;
    public Long userId;

    public String img;
    public String name;
    public String lastName;
    public String position;
}
