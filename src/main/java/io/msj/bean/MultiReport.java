package io.msj.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.jasperreports.JasperReportsMultiFormatView;

@Configuration
public class MultiReport {

    @Bean
    public JasperReportsMultiFormatView jasperRMF() {
        JasperReportsMultiFormatView jrmfv = new JasperReportsMultiFormatView();
        jrmfv.setUrl("classpath:/reports/report2.jrxml");
        jrmfv.setFormatKey("format");
        jrmfv.setReportDataKey("datasource");
        return jrmfv;
    }
}
