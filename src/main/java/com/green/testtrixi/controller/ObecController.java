package com.green.testtrixi.controller;

import com.green.testtrixi.model.Obec;
import com.green.testtrixi.serviceImpl.ObecServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Controller
@RequestMapping("/")
public class ObecController {

    private final ObecServiceImpl obecService;

    @Autowired
    private ObecController(ObecServiceImpl obecService) {
        this.obecService = obecService;
    }

    @GetMapping("/start")
    public String rates() {
        String url = "https://www.smartform.cz/download/20210331_OB_573060_UZSZ.xml.zip";
        String fileLoc = System.getProperty("user.dir");
        String fileName = "/newFile.zip";

        download(url, fileLoc + fileName);
        String filePath = unzip(fileLoc + fileName, fileLoc);
        processXMLFile(filePath);
        return "results";
    }


    private void download(String urlStr, String fileStr) {
        try {
            System.out.println("Downloading file from URL");
            URL url = new URL(urlStr);
            BufferedInputStream bis = new BufferedInputStream(url.openStream());
            FileOutputStream fos = new FileOutputStream(fileStr);
            byte[] buffer = new byte[1024];
            int count = 0;
            while ((count = bis.read(buffer, 0, 1024)) != -1) {
                fos.write(buffer, 0, count);
            }
            fos.close();
            bis.close();
            System.out.println("Downloading complete");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String unzip(String zipFileName, String destDir) {
        String filePath = "";
        try {
            byte[] buffer = new byte[1024];
            ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFileName));
            ZipEntry zipEntry = zis.getNextEntry();
            assert zipEntry != null;
            filePath = destDir + File.separator + zipEntry.getName();
            System.out.println("Unzipping " + filePath);
            FileOutputStream fos = new FileOutputStream(filePath);
            int count = 0;
            while ((count = zis.read(buffer, 0, 1024)) != -1) {
                fos.write(buffer, 0, count);
            }
            fos.close();
            zis.closeEntry();
            zis.close();
            System.out.println("Unzipping complete");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filePath;
    }

    public void processXMLFile(String file) {
        try {
            InputStream is = new FileInputStream(file);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(is);
            NodeList obecNodes = document.getElementsByTagName("vf:Obec");
            for (int i = 0; i < obecNodes.getLength(); i++) {
                Obec obec = new Obec();
                Element element = (Element) obecNodes.item(i);
                NodeList ids = element.getElementsByTagName("obi:Kod");
                obec.setId(Long.parseLong(ids.item(0).getTextContent()));
                NodeList names = element.getElementsByTagName("obi:Nazev");
                obec.setName(names.item(0).getTextContent());
                obecService.save(obec);
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }
}
