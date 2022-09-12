package com.example.demo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class newXml {
    private String file;
//getter
public String getFile(){
    return file;
}

//setter
public void setFile(String newFile){
    this.file=newFile;
}
//toString()
}
