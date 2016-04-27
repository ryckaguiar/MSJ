
package io.msj.mvcconfig;

import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

public class MvcConfig extends WebMvcConfigurerAdapter{
    
    @Override
    public  void addViewControllers(ViewControllerRegistry registry){
        registry.addViewController("/listar").setViewName("home");
    }
    
}
