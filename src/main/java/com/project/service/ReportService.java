package com.project.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.project.model.Student;
import com.project.repository.StudentRepository;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;




@Service
public class ReportService {
    @Autowired
     private StudentRepository repo;

     
    public String exportReport(String reportFormat,HttpServletResponse response) throws JRException, IOException{
          List<Student> students=new ArrayList<Student>();
          students=repo.findAll();


          //load and compile
          String path="C:\\Users";
          File file=ResourceUtils.getFile("classpath:students.jrxml");
          JasperReport jasperReport=JasperCompileManager.compileReport(file.getAbsolutePath());
          JRBeanCollectionDataSource dataSource=new JRBeanCollectionDataSource(students);
          Map<String,Object>  parameters=new HashMap<>();
          parameters.put("createdBy", "Admin");
          JasperPrint jasperPrint=JasperFillManager.fillReport(jasperReport,parameters, dataSource);
          if(reportFormat.equalsIgnoreCase("html")){
            JasperExportManager.exportReportToHtmlFile(jasperPrint, path+"\\students.html");
          }
          if(reportFormat.equalsIgnoreCase("pdf")){
            JasperExportManager.exportReportToPdfFile(jasperPrint, path+"\\students.pdf");
          }
           if(reportFormat.equalsIgnoreCase("excel")){
          JRXlsxExporter exporter = new JRXlsxExporter(); // initialize exporter 
          exporter.setExporterInput(new SimpleExporterInput(jasperPrint)); // set compiled report as input
            // set output file via path with filename
          SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
          configuration.setOnePagePerSheet(true); // setup configuration
          configuration.setDetectCellType(true);
          exporter.setConfiguration(configuration); 
          exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));// set configuration
          response.setContentType("application/xlsx");
          response.addHeader("Content-Disposition", "inline; filename=student.xlsx;"); 
          exporter.exportReport();
           }
       return "report generated in path " +path;
    }

          
    }
    

