
package io.msj.dao;

import io.msj.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserDao extends CrudRepository<User, Long>{
    
    @Query("SELECT u FROM User u WHERE u.userName=?1")
    public List<String> findByName(String userName);
    
}
