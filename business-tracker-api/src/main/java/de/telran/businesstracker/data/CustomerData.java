package de.telran.businesstracker.data;

import lombok.*;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustomerData {

    @Id
    private String formId;
    private String email;

    private String lastName;
    private String linkedInUrl;
    private String phoneNumber;
    private String firstName;

    @ElementCollection
    private List<String> startupPos = new ArrayList<>();
    @ElementCollection
    private List<String> interest = new ArrayList<>();
    @ElementCollection
    private List<String> businessGoals = new ArrayList<>();
    private int engLvl;

    private String videoUrl1;
    private String videoUrl2;
}
