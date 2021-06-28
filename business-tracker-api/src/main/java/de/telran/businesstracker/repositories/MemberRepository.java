package de.telran.businesstracker.repositories;


import de.telran.businesstracker.model.Member;
import de.telran.businesstracker.model.Project;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends CrudRepository<Member, Long> {
    List<Member> findAllByProject(Project project);
}
