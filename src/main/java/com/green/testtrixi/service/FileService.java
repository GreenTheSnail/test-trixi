package com.green.testtrixi.service;

import org.springframework.stereotype.Service;

@Service
public interface FileService {

    void download(String urlStr, String fileStr);

    String unzip(String zipFileName, String destDir);

    void processXMLFile(String file);
}
