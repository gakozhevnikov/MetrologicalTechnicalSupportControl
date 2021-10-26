package com.kga.metrologicaltechnicalsupportcontrol.util;


import com.kga.metrologicaltechnicalsupportcontrol.model.FileInfo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;


class FileManagerTest {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private static MultipartFile multipartFile;

    private static FileManager manager;

    private static FileInfo file;

    @BeforeAll
    public static void prepareTestData() throws IOException {
        file = FileInfo.builder()
                .id(9L)
                .name("mockFile.txt")
                .key("mockFile.txt")
                .size(38975L)
                .uploadDate(LocalDate.now())
                .build();
        multipartFile = new MockMultipartFile("mockFile", "mockFile.txt", "txt",
                new FileInputStream("src/test/resources/mockFile.txt"));
        manager = new FileManager();
    }
    @Test
    public void uploadTest() throws IOException {
        ReflectionTestUtils.setField(manager, "DIRECTORY_PATH", "src/test/resources/storage/");

        manager.upload(multipartFile.getBytes(), "mockFile.txt");

        Path checkFile = Paths.get("src/test/resources/storage/mockFile.txt");
        assertThat(Files.exists(checkFile), is(true));
        assertThat(Files.isRegularFile(checkFile), is(true));
        assertThat(Files.size(checkFile), is(equalTo(multipartFile.getSize())));
        Files.delete(checkFile);
    }

    @Test
    public void downloadTest() throws IOException {
        ReflectionTestUtils.setField(manager, "DIRECTORY_PATH", "src/test/resources/");

        Resource resource = manager.download(file.getKey());

        assertThat(resource.isFile(), is(true));
        assertThat(resource.getFilename(),is(equalTo(file.getName())));
        assertThat(resource.exists(), is(true));
    }

    @Test
    public void deleteTest() throws IOException {
        Path checkFile = Paths.get("src/test/resources/storage/mockFile.txt");
        Files.createFile(checkFile);
        assertThat(Files.exists(checkFile), is(true));
        assertThat(Files.isRegularFile(checkFile), is(true));
        ReflectionTestUtils.setField(manager, "DIRECTORY_PATH", "src/test/resources/storage/");
        manager.delete(file.getKey());
        assertThat(Files.notExists(checkFile), is(true));
    }

    @Test
    public void getWorkPlanFileIsExist() {
        log.info("getWorkPlanFileIsExist the path of file: {}", FileManager.getWorkPlanFile());
        log.info("getWorkPlanFileIsExist file is exist: {}", Objects.requireNonNull(FileManager.getWorkPlanFile()).exists());
        assertThat(FileManager.getWorkPlanFile().exists(), is(true));
    }



}