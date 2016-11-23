package io.msj.bean;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfEFStream;
import com.lowagie.text.pdf.PdfWriter;
import io.msj.dao.ProdutoDao;
import io.msj.entity.Produto;
import io.msj.entity.User;
import io.msj.entity.UserRole;
import io.msj.repository.UserRepository;
import io.msj.repository.UserRolesRepository;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import javax.validation.Valid;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.repo.JasperDesignReportResource;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.annotation.ModelAndViewResolver;
import org.springframework.web.servlet.view.document.AbstractPdfView;
import org.springframework.web.servlet.view.jasperreports.JasperReportsPdfView;

@Controller
public class FieldValidator extends AbstractPdfView {

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

    @RequestMapping(value = "/pdf", method = RequestMethod.GET)
    public ModelAndView pdfGenerator(HttpServletRequest request,
   HttpServletResponse response) throws IOException {

        
      /*  String nameAch;
        nameAch = "testeItext" + ".pdf";
        String pathFiles = System.getProperty("user.dir") + "/src/main/resources/arquivos/" + nameAch;
        final ServletContext servletContext = request.getSession().getServletContext();
        System.out.println(servletContext);
        final File tempDirectory = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        System.out.println(tempDirectory.getAbsolutePath());
        //final String temperotyFilePath = tempDirectory.getAbsolutePath();

        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "attachment; filename=" + nameAch);

        try {
            InputStream inputStream = new FileInputStream(pathFiles);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            baos.write(inputStream);
            OutputStream outputStream = response.getOutputStream();
            baos.writeTo(outputStream);
            return new ModelAndView("pdf");
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        return new ModelAndView("pdfView");

        //ByteArrayOutputStream baos = new ByteArrayOutputStream();
        /*
        Document document = new Document();
        try {
            //PdfWriter.getInstance(document, new FileOutputStream(pathFiles));
            //document.open();
            
            document.add(new Paragraph("Teste 1"));
            
        } catch (DocumentException e) {
            e.getMessage();
        }
        //document.close();
        
       return new ModelAndView("redirect:upanddown");

        /*
        String url = System.getProperty("catalina.home") + "/reports/report2.jrxml";
        System.out.println(url);
        //String pathContext = context.System.out.println(pathContext);
        //String realPathJasper = this.getClass().getResource("report2.jrxml").getPath();
        InputStream inputJrxml = new FileInputStream("/home/ricardo/NetBeansProjects/MSJ/src/main/resources/reports/report2.jrxml");

        //JRDataSource jrDataSource = (JRDataSource)  prodDao.findAll();
        JRBeanCollectionDataSource jrDataSource = new JRBeanCollectionDataSource((Collection<?>) prodDao.findAll(), false);
       // modelMap.addAttribute("format", "pdf");
        modelMap.addAttribute("datasource", jrDataSource);
        //Map<String, Object> parameters = new HashMap<>();

        JasperReport jasperReport = JasperCompileManager
                .compileReport(inputJrxml);
        
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, jrDataSource);
        

        //JasperExportManager.exportReportToPdfFile(jasperPrint, "/reports/repor2.pdf");        
        
        JasperReportsPdfView jrpv = new JasperReportsPdfView();
        jrpv.setUrl(url);
        jrpv.setReportDataKey("datasource");      
        
        return new ModelAndView(jrpv, modelMap);

        //JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());

        //JasperViewer jv = new JasperViewer(jasperPrint);
        //jv.setVisible(true);
        //File outDir = new File("/home/ricardo/jasperoutput");
        //outDir.mkdirs();
        //JasperExportManager.exportReportToPdfFile(jasperPrint,
        //      "/home/ricardo/jasperoutput/StyledTextReport.pdf");
        //java.net.URL url = this.getClass().getResource("/reports/report2.jrxml");
        //JasperExportManager.exportReportToPdf(jasperPrint);
        //JasperReportsPdfView jrpv = new JasperReportsPdfView();
        //jrpv.setUrl("/home/ricardo/NetBeansProjects/MSJ/src/main/resources/reports/report2.jrxml");
        //jrpv.render(modelMap, request, response);
        //return new ModelAndView("", modelMap);*/
    }

    /*@Bean(name = "multiReport")
    public JasperReportsMultiFormatView jasperRMF() {
        JasperReportsMultiFormatView jrmfv = new JasperReportsMultiFormatView();
        jrmfv.setUrl("classpath:/reports/report2.xhtml");
        jrmfv.setFormatKey("format");
        jrmfv.setReportDataKey("datasource");
        return jrmfv;
    }*/
    @RequestMapping(value = "/pdf1", method = RequestMethod.GET, consumes = "application/pdf")
    @Override
    protected void buildPdfDocument(Map<String, Object> map, Document dcmnt, PdfWriter writer, HttpServletRequest hsr, HttpServletResponse hsr1) throws Exception {


        Map<String, String> revenueData = new HashMap<String, String>();
        revenueData.put("1/20/2010", "$100,000");
        revenueData.put("1/21/2010", "$200,000");
        revenueData.put("1/22/2010", "$300,000");
        revenueData.put("1/23/2010", "$400,000");
        revenueData.put("1/24/2010", "$500,000");

        Table table = new Table(2);
        table.addCell("Month");
        table.addCell("Revenue");

        for (Map.Entry<String, String> entry : revenueData.entrySet()) {
            table.addCell(entry.getKey());
            table.addCell(entry.getValue());
        }

        dcmnt.add(table);

    }
}
