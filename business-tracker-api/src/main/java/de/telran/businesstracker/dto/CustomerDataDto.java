package de.telran.businesstracker.dto;

import lombok.*;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

@Builder
//@Getter
//@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDataDto {

    public String email;
    public String formId;
    //ask form page_1
    public String lastName;
    public String linkedInUrl;
    public String phoneNumber;
    public String firstName;
    //ask form page_2
    public List<String> startupPos;
    public List<String> interest;
    public List<String> businessGoals;
    public int engLvl;
    //TODO video
    public String videoUrl1;
    public String videoUrl2;
}
