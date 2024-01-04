package com.devjoemar.ownwctool.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CcwcServiceTest {
    private final CcwcService ccwcService;
    private final ResourceLoader resourceLoader;

    @Autowired
    public CcwcServiceTest(CcwcService ccwcService, ResourceLoader resourceLoader) {
        this.ccwcService = ccwcService;
        this.resourceLoader = resourceLoader;
    }

    private String getFilePath(String resourcePath) throws IOException {
        final Resource resource = resourceLoader.getResource("classpath:" + resourcePath);
        return resource.getFile().getAbsolutePath();
    }

    @Test
    public void testCountBytes() throws IOException {
        final String filepath = getFilePath("testfiles/test.txt");
        final long byteCount = ccwcService.countBytes(filepath);
        final long expectedByteCount = 342_190;
        assertEquals(expectedByteCount, byteCount);
    }

    @Test
    public void testCountLines() throws IOException {
        final String filepath = getFilePath("testfiles/test.txt");
        final long byteCount = ccwcService.countLines(filepath);
        final long expectedByteCount = 7_145;
        assertEquals(expectedByteCount, byteCount);
    }

    @Test
    public void testCountWords() throws IOException {
        final String filepath = getFilePath("testfiles/test.txt");
        final long byteCount = ccwcService.countWords(filepath);
        final long expectedByteCount = 58_164;
        assertEquals(expectedByteCount, byteCount);
    }

    @Test
    public void testCountCharacters() throws IOException {
        String filepath = getFilePath("testfiles/test.txt");
        final long byteCount = ccwcService.countCharacters(filepath);
        final long expectedByteCount = 339_292;
        assertEquals(expectedByteCount, byteCount);
    }


    @Test
    public void testCountBytes_empty_file() throws IOException {
        final String filepath = getFilePath("testfiles/empty.txt");
        final long byteCount = ccwcService.countBytes(filepath);
        final long expectedByteCount = 0;
        assertEquals(expectedByteCount, byteCount);
    }

    @Test
    public void testCountLines_empty_file() throws IOException {
        final String filepath = getFilePath("testfiles/empty.txt");
        final long byteCount = ccwcService.countLines(filepath);
        final long expectedByteCount = 0;
        assertEquals(expectedByteCount, byteCount);
    }

    @Test
    public void testCountWords_empty_file() throws IOException {
        final String filepath = getFilePath("testfiles/empty.txt");
        final long byteCount = ccwcService.countWords(filepath);
        final long expectedByteCount = 0;
        assertEquals(expectedByteCount, byteCount);
    }

    @Test
    public void testCountCharacters_empty_file() throws IOException {
        String filepath = getFilePath("testfiles/empty.txt");
        final long byteCount = ccwcService.countCharacters(filepath);
        final long expectedByteCount = 0;
        assertEquals(expectedByteCount, byteCount);
    }


    @Test
    public void testCountBytes_bigfile() throws IOException {
        final String filepath = getFilePath("testfiles/bigfile.txt");
        final long byteCount = ccwcService.countBytes(filepath);
        final long expectedByteCount = 6_488_666;
        assertEquals(expectedByteCount, byteCount);
    }

    @Test
    public void testCountLines_bigfile() throws IOException {
        final String filepath = getFilePath("testfiles/bigfile.txt");
        final long byteCount = ccwcService.countLines(filepath);
        final long expectedByteCount = 128_457;
        assertEquals(expectedByteCount, byteCount);
    }

    @Test
    public void testCountWords_bigfile() throws IOException {
        final String filepath = getFilePath("testfiles/bigfile.txt");
        final long byteCount = ccwcService.countWords(filepath);
        final long expectedByteCount = 1_095_695;
        assertEquals(expectedByteCount, byteCount);
    }

    @Test
    public void testCountCharacters_bigfile() throws IOException {
        String filepath = getFilePath("testfiles/bigfile.txt");
        final long byteCount = ccwcService.countCharacters(filepath);
        final long expectedByteCount = 6_488_666;
        assertEquals(expectedByteCount, byteCount);
    }


}