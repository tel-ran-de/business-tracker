package de.telran.businesstracker.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "task")

public class Task{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "finished")
    private Boolean finished;

    @ManyToOne
    private Milestone milestone;

    @ManyToOne
    private Member responsibleMember;

    public Task(String name, boolean finished, Milestone milestone, Member responsibleMember) {
        this.name = name;
        this.finished = finished;
        this.responsibleMember = responsibleMember;
        this.milestone = milestone;
    }
}
