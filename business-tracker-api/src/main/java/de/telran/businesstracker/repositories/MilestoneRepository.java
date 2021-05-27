package de.telran.businesstracker.repositories;

import de.telran.businesstracker.data.Milestone;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MilestoneRepository extends CrudRepository<Milestone, Long> {}
