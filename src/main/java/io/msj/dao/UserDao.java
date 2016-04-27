
package io.msj.dao;

import io.msj.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserDao extends CrudRepository<User, Long>{
    
    //@Query("SELECT user_name, password  FROM `user` WHERE user_name=?")
    public List<String> userName(String userName);
    
}
