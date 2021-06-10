package de.telran.businesstracker.repositories;

import de.telran.businesstracker.data.CustomerData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerDataRepository extends CrudRepository<CustomerData, String> {

}
