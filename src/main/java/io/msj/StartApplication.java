package io.msj;

import io.msj.dao.ProdutoDao;
import io.msj.entity.Aluno;
import io.msj.entity.Classe;
import io.msj.repository.AlunoRepository;
import io.msj.repository.ClasseRepository;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@EntityScan(basePackages = "entity")
//@EnableJpaRepositories(basePackages = "entity")
@SpringBootApplication
public class StartApplication implements CommandLineRunner {

    // @Autowired
    //private UserDao userDao;
    //@Autowired
   // private ProdutoDao prodDAO;

    /*@Autowired
    ClasseRepository classeRepository;

    @Autowired
    AlunoRepository alunoRepository;

    /*@Autowired
    private PersonDao personDao;*/
    public static void main(String[] args) {
        SpringApplication.run(StartApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {

        /*Set<Aluno> aluno;
        Classe classe;

        classe = new Classe();
        classe.setClassName("4º série A");

        Aluno aluno1 = new Aluno();
        aluno1.setIdade(10);
        aluno1.setName("Ricardo Aguiar");
        aluno1.setClasse(classe);

        Aluno aluno2 = new Aluno();
        aluno2.setIdade(11);
        aluno2.setName("João Aguiar");
        aluno2.setClasse(classe);

        aluno = new HashSet<>();
        aluno.add(aluno1);
        aluno.add(aluno2);
        classe.setAluno(aluno);

        classeRepository.save(classe);

        alunoRepository.save(aluno1);
        alunoRepository.save(aluno2);

        /*String a = "a";
        
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
