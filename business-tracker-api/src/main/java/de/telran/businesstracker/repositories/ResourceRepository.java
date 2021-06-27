package de.telran.businesstracker.repositories;

import de.telran.businesstracker.model.Resource;
import de.telran.businesstracker.model.Roadmap;
import de.telran.businesstracker.model.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourceRepository extends CrudRepository<Resource, Long> {
    List<Resource> findAllByTask_Milestone_Roadmap(Roadmap roadmap);
    List<Resource> findAllByTask(Task task);
}
