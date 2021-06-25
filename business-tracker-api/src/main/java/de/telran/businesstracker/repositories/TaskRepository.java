package de.telran.businesstracker.repositories;


import de.telran.businesstracker.model.Milestone;
import de.telran.businesstracker.model.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {
    List<Task> findAll();

    List<Task> findAllByMilestone(Milestone milestone);

    List<Task> findAllByActive(boolean active);
}
