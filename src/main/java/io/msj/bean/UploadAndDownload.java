package io.msj.bean;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class UploadAndDownload {

    @RequestMapping(value = "/upanddown")
    public String getLoadFile(Model model) throws Exception {

        String pathFiles = System.getProperty("user.dir") + "/src/main/resources/arquivos/";
        //System.out.println(pathFiles);
        File files = new File(pathFiles);
        model.addAttribute("files",
                Arrays.stream(files.listFiles())
                .sorted(Comparator.comparingLong(f -> -1 * f.lastModified()))
                .map(f -> f.getName())
                .collect(Collectors.toList())
        );

        return "upanddown";

    }

    @RequestMapping(value = "/upanddown", method = RequestMethod.POST)
    public String upLoadFile(@RequestParam("name") String name, @RequestParam("file") MultipartFile file) throws Exception {
        String pathFiles = System.getProperty("user.dir") + "/src/main/resources/arquivos/";

        try {
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(pathFiles + name)));

            FileCopyUtils.copy(file.getInputStream(), stream);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:upanddown";

    }

}
