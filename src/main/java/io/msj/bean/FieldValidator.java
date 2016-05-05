package io.msj.bean;

import io.msj.dao.ProdutoDao;
import io.msj.entity.Produto;
import io.msj.entity.UserRole;
import io.msj.repository.UserRepository;
import io.msj.repository.UserRolesRepository;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class FieldValidator {

    private ProdutoDao prodDao;

    private UserRolesRepository userRolesRepository;

    @Autowired
    public FieldValidator(ProdutoDao prodDao, UserRolesRepository userRolesRepository) {
        this.prodDao = prodDao;
        this.userRolesRepository = userRolesRepository;
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String Create(Model model) {
        model.addAttribute("produto", new Produto());
        return "/postForm";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String checkFieldInfo(@Valid Produto produto, BindingResult br/*, @RequestParam(value = "id") Long id, @RequestParam(name = "nome") String nome,
            @RequestParam(name = "descricao") String descricao, @RequestParam(name = "email") String email*/) {

        if (br.hasErrors()) {
            return "/postForm";
        } else {
            /* produto = prodDao.findOne(id);
            produto.setEmail(email);
            produto.setNome(nome);
            produto.setDescricao(descricao);*/
            prodDao.save(produto);
            return "redirect:/edit/" + produto.getId();
        }

    }

    @RequestMapping("/login")
    public String LoginUser() {
        return "/login";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public String Logout() {
        return "/logout";
    }

    /*
    @RequestMapping("/logout")
    public String LogoutUser() {
        return "/login";
    }
    @RequestMapping(value = "/new/{id}", method = RequestMethod.GET)
    public String newId(@PathVariable("id") Long id, Model model) {
        model.addAttribute("produto", prodDao.findOne(id));
        return "/new";
    }
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String LoginUserPost() {
        return "/login";
    }
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("produto", prodDao.findOne(id));
        return "/postForm";
    }

    @RequestMapping(value = "/createRoleUser", method = RequestMethod.POST)
    public String createRoleUser(@Valid UserRole userRole, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/createRoleUser";
        }
        userRolesRepository.save(userRole);
        return "/listar";
    }
    /*
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveNew(@Valid Produto produto, BindingResult br, @RequestParam("id") long id, @RequestParam("nome") String nome, @RequestParam("email") String email,
            @RequestParam("descricao") String descricao) {

        if (br.hasErrors()) {
            return "/new";
        }

        produto = prodDao.findOne(id);
        produto.setEmail(email);
        produto.setNome(nome);
        produto.setDescricao(descricao);
        System.out.println(produto.getId());
        System.out.println(produto.getNome());
        System.out.println(produto.getEmail());
        System.out.println(produto.getDescricao());
        //prodDao.save(produto);
        //Produto p = prodDao.findOne(produto.getId());
        //prodDao.save(p);

        return "redirect:/listar";
    }
    /*@RequestMapping("/cadastrar")
    public String crud(){
        return "cadastrar";
    }
   
    @RequestMapping(value ="/new", method = RequestMethod.POST)
    public ModelAndView create(Produto produto, @RequestParam(name = "produto") String nome,
            @RequestParam(name = "descricao") String descricao, BindingResult br) {

        if (checkFieldInfo(produto, br) == "false") {
        
            prodDao.save(new Produto(nome, descricao));
        }
        
        return new ModelAndView("redirect:/listar");

    }
     */

}
