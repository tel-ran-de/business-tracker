package de.telran.businesstracker.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Setter
    private String name;
    private boolean finished;
    private boolean active;
    @Setter
    private String delivery;

    @ManyToOne
    @Setter
    private Milestone milestone;

    @OneToMany(mappedBy = "task", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Resource> resources;

    @ManyToOne
    @Setter
    private Member responsibleMember;

    public Task(String name, boolean finished, boolean active, String delivery, Milestone milestone, Member responsibleMember) {
        this.name = name;
        this.finished = finished;
        this.active = active;
        this.delivery = delivery;
        this.milestone = milestone;
        this.responsibleMember = responsibleMember;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isFinished() {
        return finished;
    }

    public boolean isActive() {
        return active;
    }
}
