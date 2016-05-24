package io.msj.dao;

import io.msj.entity.Produto;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ProdutoDao extends CrudRepository<Produto, Long> {

    @Query("select U from Produto U where U.nome like %?1%")
    public List<Produto> findByName(String nome);

}
