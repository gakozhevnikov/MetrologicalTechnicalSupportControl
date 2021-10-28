package com.kga.metrologicaltechnicalsupportcontrol.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class FileManager {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private static String DIRECTORY_PATH ="src/main/resources/storage";//refactor - автоматическое заполнение в зависимости от папки ресурсов
    private static String PATH_WORK_PLAN_FILE="src/main/resources/storage/graph2021.xls";

    public void upload(byte[] resource, String keyName) throws IOException {
        Path path = Paths.get(DIRECTORY_PATH, keyName);
        Path file = Files.createFile(path);
        FileOutputStream stream = null;
        try {
            stream = new FileOutputStream(file.toString());
            stream.write(resource);
        } finally {
            stream.close();
        }
    }

    public Resource download(String key) throws IOException {
        Path path = Paths.get(DIRECTORY_PATH + key);
        Resource resource = new UrlResource(path.toUri());
        if (resource.exists() || resource.isReadable()) {
            return resource;
        } else {
            throw new IOException();
        }
    }

    public void delete(String key) throws IOException {
        Path path = Paths.get(DIRECTORY_PATH + key);
        Files.delete(path);
    }

    public static File getWorkPlanFile(){
        File file = new File(PATH_WORK_PLAN_FILE);
        if(file.exists()){
            return file;
        }
        return null;
    }

}
