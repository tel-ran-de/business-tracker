package de.telran.businesstracker.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Milestone {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Setter
    private String name;
    @Setter
    private LocalDate startDate;
    @Setter
    private LocalDate finishDate;

    @ManyToOne
    private Roadmap roadmap;

    @ElementCollection
    private List<String> kpis = new ArrayList<>();

    public Milestone(String name, LocalDate startDate, LocalDate finishDate, Roadmap roadmap, List<String> kpis) {
        this.name = name;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.roadmap = roadmap;
        this.kpis = kpis;
    }

    public void addKpi(String kpi) {
        this.kpis.add(kpi);
    }
}
