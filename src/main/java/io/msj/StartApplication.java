package io.msj;

import io.msj.dao.PersonDao;
import io.msj.dao.ProdutoDao;
import io.msj.dao.UserDao;
import io.msj.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@EntityScan(basePackages = "entity")
//@EnableJpaRepositories(basePackages = "entity")
public class StartApplication implements CommandLineRunner {

    
    @Autowired
    private UserDao userDao;
    /*@Autowired
    private ProdutoDao prodDAO;

    @Autowired
    private PersonDao personDao;*/
    
    public static void main(String[] args) {
        SpringApplication.run(StartApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        String a = "a";
        
        System.out.println(userDao.findByName(a));
         /*
        //System.out.println("io.msj.StartApplication.run()");
           Calendar c = Calendar.getInstance();
        c.getTime();
       for (int i = 0; i < 10; i++) {
            prodDAO.save(new Produto("HP LJ 180" + i, "RH" + i, ""));
            personDao.save(new Person("Cliente", "Sobrenome", 20+i, "Masc", true, c));
        }*/
    }
}
