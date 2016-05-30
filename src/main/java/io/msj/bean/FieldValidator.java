package io.msj.bean;

import io.msj.dao.ProdutoDao;
import io.msj.entity.Produto;
import io.msj.entity.User;
import io.msj.entity.UserRole;
import io.msj.repository.UserRepository;
import io.msj.repository.UserRolesRepository;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.spring4.view.AjaxThymeleafView;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.ResourceBundleViewResolver;
import org.springframework.web.servlet.view.jasperreports.JasperReportsMultiFormatView;
import org.springframework.web.servlet.view.jasperreports.JasperReportsPdfView;
import org.springframework.web.servlet.view.jasperreports.JasperReportsViewResolver;
import org.thymeleaf.spring4.view.AjaxThymeleafViewResolver;
import sun.security.pkcs11.wrapper.Functions;

@Controller
public class FieldValidator {

    private ProdutoDao prodDao;

    private UserRepository userRepository;

    private UserRolesRepository userRolesRepository;

    @Autowired
    public FieldValidator(ProdutoDao prodDao, UserRolesRepository userRolesRepository, UserRepository userRepository) {
        this.prodDao = prodDao;
        this.userRolesRepository = userRolesRepository;
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String Create(Model model) {
        model.addAttribute("produto", new Produto());
        return "/postForm";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    //@ResponseBody
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

    //@ModelAttribute("currentUser")
    @RequestMapping("currentUser")
    @ResponseBody
    public String currentUser() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @RequestMapping("/login")
    public String LoginUser(Model model) {
        // model.addAttribute("currentUser", SecurityContextHolder.getContext().getAuthentication().getName());
        return "/login";
    }

    /*@RequestMapping("/userInfo")
    @ResponseBody
    public Map<String, Object> LoginUserInfo(Model model) {

        Map<String, Object> currentUsers = new HashMap<>();
        currentUsers.put("currentUser", SecurityContextHolder.getContext().getAuthentication().getName());
        currentUsers.put("Hora", new SimpleDateFormat("HH:MM").format(new Date()));
        return currentUsers;
    }*/
    @RequestMapping("/modal")
    public String LoadModal(Model model) {
        model.addAttribute("produto", new Produto());
        return "/modal";
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

    @RequestMapping(value = "/createRoleUser", method = RequestMethod.GET)
    public String createRoleUser(Model model) {

        model.addAttribute("userrole", new UserRole());

        return "/cadUserRoles";
    }

    @ModelAttribute("listarUser")
    public List<User> listUser() {
        return (List<User>) userRepository.findAll();
    }

    @RequestMapping(value = "/createRoleUser", method = RequestMethod.POST)
    public String createRoleUser(@Valid UserRole userRole, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/cadUserRoles";
        }
        userRolesRepository.save(userRole);
        return "redirect:/listar";
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
    @RequestMapping("/search")
    public String search() {
        return "search";
    }

    @RequestMapping(value = "/search/{nome}", method = RequestMethod.GET)
    public ModelAndView search(@PathVariable String nome, ModelMap model) {
        model.put("search", prodDao.findByName(nome));
        return new ModelAndView("table", model);
    }
//######################PDF REPORT#########################################

    @RequestMapping(value = "/pdf", method = RequestMethod.GET, produces = "aplication/pdf")
    public ModelAndView getPdf(ModelAndView modelAndView) throws Exception {
        

        JasperReport jasperReport = JasperCompileManager
                .compileReport("/home/ricardo/NetBeansProjects/MSJ/src/main/resources/reports/report2.jrxml");

        JRBeanCollectionDataSource jrBean = new JRBeanCollectionDataSource((Collection<?>) prodDao.findAll(), false);

        Map<String, Object> parameters = new HashMap<String, Object>();

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, jrBean);

        File outDir = new File("/home/ricardo/jasperoutput");
        outDir.mkdirs();

        JasperExportManager.exportReportToPdfFile(jasperPrint,
                "/home/ricardo/jasperoutput/StyledTextReport.pdf");
       
        return new ModelAndView("reportsimplepdfjasperreport", parameters);

    }
}
