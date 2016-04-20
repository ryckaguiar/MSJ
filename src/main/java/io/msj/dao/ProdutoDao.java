package io.msj.dao;

import io.msj.entity.Produto;
import org.springframework.data.repository.CrudRepository;

public interface ProdutoDao extends CrudRepository<Produto, Long>{
    
}
