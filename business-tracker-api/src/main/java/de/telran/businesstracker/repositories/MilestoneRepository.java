package de.telran.businesstracker.repositories;

import de.telran.businesstracker.model.Milestone;
import de.telran.businesstracker.model.Project;
import de.telran.businesstracker.model.Roadmap;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface MilestoneRepository extends CrudRepository<Milestone, Long> {
    List<Milestone> findAll();

    List<Milestone> findAllByRoadmapProject(Project project);
    List<Milestone> findAllByRoadmap(Roadmap roadmap);
}
