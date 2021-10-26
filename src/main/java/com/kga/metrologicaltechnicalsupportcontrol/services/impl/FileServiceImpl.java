package com.kga.metrologicaltechnicalsupportcontrol.services.impl;



import com.kga.metrologicaltechnicalsupportcontrol.repository.interfaces.FileDAO;
import com.kga.metrologicaltechnicalsupportcontrol.model.FileInfo;
import com.kga.metrologicaltechnicalsupportcontrol.services.interfaces.FileService;
import com.kga.metrologicaltechnicalsupportcontrol.util.FileManager;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.core.io.Resource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

/*https://javarush.ru/groups/posts/2476-sokhranenie-faylov-v-prilozhenie-i-dannihkh-o-nikh-na-bd?post=full#discussion*/
//@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileDAO fileDAO;
    private final FileManager fileManager;

    @Transactional(rollbackFor = {IOException.class})
    @Override
    public FileInfo upload(MultipartFile resource) throws IOException {
        String key = generateKey(resource.getName());
        FileInfo createdFile = FileInfo.builder()
                .name(resource.getOriginalFilename())
                .key(key)
                .size(resource.getSize())
                .build();
        createdFile = fileDAO.create(createdFile);
        fileManager.upload(resource.getBytes(), key);

        return createdFile;
    }

    private String generateKey(String name) {
        return DigestUtils.md5Hex(name + LocalDateTime.now().toString());
    }

    @Override
    public Resource download(String key) throws IOException {
        return fileManager.download(key);
    }

    @Transactional(readOnly = true)
    @Override
    public FileInfo findById(Long fileId) {
        return fileDAO.findById(fileId);
    }

    @Transactional(rollbackFor = {IOException.class})
    @Override
    public void delete(Long fileId) throws IOException {
        FileInfo file = fileDAO.findById(fileId);
        fileDAO.delete(fileId);
        fileManager.delete(file.getKey());
    }
}
