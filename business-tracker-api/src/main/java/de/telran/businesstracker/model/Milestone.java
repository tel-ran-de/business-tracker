package de.telran.businesstracker.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Milestone {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    private String name;

    private LocalDate startDate;

    private LocalDate finishDate;

    @ManyToOne
    private Roadmap roadmap;

    @ElementCollection
    private Set<String> kpis = new HashSet<>();

    public void addKpi(String kpi) {
        this.kpis.add(kpi);
    }

    public void removeKpi(String kpi) {
        this.kpis.remove(kpi);
    }
}
