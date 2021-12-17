package com.green.testtrixi.serviceImpl;

import com.green.testtrixi.model.CastObce;
import com.green.testtrixi.model.Obec;
import com.green.testtrixi.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

@Service
public class FileServiceImpl implements FileService {

    private final ObecServiceImpl obecService;

    private final CastObceServiceImpl castObceService;

    @Autowired
    private FileServiceImpl(ObecServiceImpl obecService, CastObceServiceImpl castObceService) {
        this.obecService = obecService;
        this.castObceService = castObceService;
    }


    @Override
    public void download(String urlStr, String fileStr) {
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

    @Override
    public String unzip(String zipFileName, String destDir) {
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

    @Override
    public void processXMLFile(String file) {
        try {
            System.out.println("Processing started");
            InputStream is = new FileInputStream(file);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(is);
            System.out.println("Scanning for all Obec elements");
            NodeList obecNodes = document.getElementsByTagName("vf:Obec");
            for (int i = 0; i < obecNodes.getLength(); i++) {
                Obec obec = new Obec();
                Element element = (Element) obecNodes.item(i);
                NodeList ids = element.getElementsByTagName("obi:Kod");
                obec.setId(Long.parseLong(ids.item(0).getTextContent()));
                NodeList names = element.getElementsByTagName("obi:Nazev");
                obec.setName(names.item(0).getTextContent());
                System.out.println("Saving Obec with name " + obec.getName());
                obecService.save(obec);
            }
            System.out.println("Scanning for all CastObce elements");
            NodeList obecCastNodes = document.getElementsByTagName("vf:CastObce");
            for (int i = 0; i < obecCastNodes.getLength(); i++) {
                CastObce castObce = new CastObce();
                Element element = (Element) obecCastNodes.item(i);
                NodeList ids = element.getElementsByTagName("coi:Kod");
                castObce.setId(Long.parseLong(ids.item(0).getTextContent()));
                NodeList names = element.getElementsByTagName("coi:Nazev");
                castObce.setName(names.item(0).getTextContent());
                NodeList obecIds = element.getElementsByTagName("obi:Kod");
                castObce.setObec(obecService.findById(Long.parseLong(obecIds.item(0).getTextContent())));
                System.out.println("Saving CastObce with name " + castObce.getName());
                castObceService.save(castObce);
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

}
