package io.msj.bean;

import io.msj.dao.ProdutoDao;
import io.msj.entity.Produto;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.annotation.ModelAndViewResolver;

@Controller
public class ProdutoBean extends WebMvcConfigurerAdapter {

    @Autowired
    private ProdutoDao prodDao;

    @RequestMapping(value = "/listar", method = RequestMethod.GET)
    public String listaProduto(Model model, Produto produto) {
        String hello = "hello i am alive";
        model.addAttribute("posts", prodDao.findAll());
        model.addAttribute("hello", hello);

        return "/listar";
    }

    @ModelAttribute("listarProduto")
    public List<Produto> listProd() {
        return (List<Produto>) prodDao.findAll();
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public ModelAndView delete(@PathVariable long id) {
        prodDao.delete(id);
        return new ModelAndView("redirect:/listar");
    }
    @RequestMapping(value = "/saveAjax", method = RequestMethod.POST)
    @ResponseBody
    public Produto produtoAjax(@Valid Produto produto){
             
        prodDao.save(produto);
        
        return produto;
    }

    /*
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newProject() {
        return "new";
    }

   
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModelAndView create(@RequestParam(name = "produto") String nome,
            @RequestParam(name = "descricao") String descricao) {
        
            prodDao.save(new Produto(nome, descricao));
            return new ModelAndView("redirect:/listar");
        
    }
     */
 /*@RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public String edit(@PathVariable long id, Model model) {
        model.addAttribute("post", prodDao.findOne(id));
        return "/edit";
    }

    /*@RequestMapping(value = "/editar", method = RequestMethod.POST)
    public ModelAndView alterar(@RequestParam("post_id") long id,
            @RequestParam("nome")String nome, @RequestParam("descricao")String descricao, @RequestParam("email") String email) {
        Produto p = prodDao.findOne(id);
        p.setNome(nome);
        p.setDescricao(descricao);
        p.setDescricao(email);
        prodDao.save(p);
        return new ModelAndView("redirect:/listar");
    }

    
    
    @RequestMapping(value = "/tsijquery", method = RequestMethod.GET)
    @ResponseBody()
    public List<Produto> tsjquery() {
        return (List<Produto>) prodDao.findAll();
    }*/    
}
