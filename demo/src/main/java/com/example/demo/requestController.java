package com.example.demo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.InputSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@RequestMapping("/api/request")
@CrossOrigin(origins = "http://localhost:8081")
public class requestController {  

    /*@PostMapping(consumes = {MediaType.APPLICATION_XML_VALUE}, produces = { MediaType.APPLICATION_XML_VALUE}})
    public UserRest cerateUser(@RequestBody UserDetailsModelRequest UserDetails){
        UserRest returnValue = new UserRest();

        return returnValue;
    }*/
    @GetMapping("/result")
    public String verif() throws ParseException{
        readXml result = new readXml();	
        return result.verificationTemporelles();
    }
    
    @PostMapping(path="/newXml",consumes = MediaType.ALL_VALUE)
	public ResponseEntity<Object> getDetails(@RequestBody newXml xml) {
        
                ResponseEntity<Object> response = null;
    
                
                try {
    
                    response = ResponseEntity.status(HttpStatus.OK).body(xml);
                    Document doc = convertStringToDocument(xml.getFile()); 
                    System.out.println(xml.getFile());
                    XMLOutputter xmlOutputter = new XMLOutputter();
                   try(FileOutputStream fileOutputStream=new FileOutputStream("test2.xml")){
                    xmlOutputter.output(doc, fileOutputStream);
                   }
    
                } catch (Exception e) {
        
                    response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        
                }
        
                return response;
        
            }
            private static Document convertStringToDocument(String xmlStr) {
               SAXBuilder sax = new SAXBuilder();
               sax.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
               sax.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA,"");  
                try  
                {  
                    Document doc = sax.build(new StringReader(xmlStr)); 
                    return doc;
                } catch (Exception e) {  
                    e.printStackTrace();  
                } 
                return null;
            }
}
