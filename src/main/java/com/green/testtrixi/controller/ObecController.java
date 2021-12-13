package com.green.testtrixi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Controller
@RequestMapping("/")
public class ObecController {

    @GetMapping("/start")
    public String rates() {
        String url = "https://www.smartform.cz/download/20210331_OB_573060_UZSZ.xml.zip";
        String fileLoc = System.getProperty("user.dir");
        String fileName = "/newFile.zip";

        try{
            Download(url, fileLoc+ fileName);
            String filePath = Unzip(fileLoc+ fileName, fileLoc);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "results";
    }


    private void Download(String urlStr, String fileStr) throws IOException {
        System.out.println("Downloading file from URL");
        URL url = new URL(urlStr);
        BufferedInputStream bis = new BufferedInputStream(url.openStream());
        FileOutputStream fos = new FileOutputStream(fileStr);
        byte[] buffer = new byte[1024];
        int count = 0;
        while((count = bis.read(buffer, 0, 1024)) != -1){
            fos.write(buffer, 0, count);
        }
        fos.close();
        bis.close();
        System.out.println("Downloading complete");
    }

    private String Unzip(String zipFileName, String destDir) throws IOException {
        byte[] buffer = new byte[1024];
        ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFileName));
        ZipEntry zipEntry = zis.getNextEntry();
        assert zipEntry != null;
        String filePath = destDir + File.separator + zipEntry.getName();
        System.out.println("Unzipping " + filePath);
        FileOutputStream fos = new FileOutputStream(filePath);
        int count = 0;
        while((count = zis.read(buffer, 0, 1024)) != -1) {
            fos.write(buffer, 0, count);
        }
        fos.close();
        zis.closeEntry();
        zis.close();
        System.out.println("Unzipping complete");
        return filePath;
    }
}
