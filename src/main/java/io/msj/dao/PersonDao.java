package io.msj.dao;

import io.msj.entity.Person;
import org.springframework.data.repository.CrudRepository;

public interface PersonDao extends CrudRepository<Person, Long>{
    
}
