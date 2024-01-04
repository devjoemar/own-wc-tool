package com.devjoemar.ownwctool;

import com.devjoemar.ownwctool.service.CcwcService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class WcToolCommandArgsTest {

    @Mock
    private CcwcService ccwcService;

    private WcToolApplication wcToolApplication;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        wcToolApplication = new WcToolApplication(ccwcService);
    }

    @Test
    public void testNoArguments() throws IOException {
        String response = wcToolApplication.processArgs();
        assertTrue(response.contains("Please input valid arguments"));
    }

    @Test
    public void testByteCountCommand() throws IOException {
        String filename = "test.txt";
        long expectedCount = 123L;
        when(ccwcService.countBytes(filename)).thenReturn(expectedCount);

        String response = wcToolApplication.processArgs("-c", filename);

        assertEquals(expectedCount + " " + filename, response);
        verify(ccwcService).countBytes(filename);
    }

    @Test
    public void testLineCountCommand() throws IOException {
        String filename = "test.txt";
        long expectedCount = 456L;
        when(ccwcService.countLines(filename)).thenReturn(expectedCount);

        String response = wcToolApplication.processArgs("-l", filename);

        assertEquals(expectedCount + " " + filename, response);
        verify(ccwcService).countLines(filename);
    }

    @Test
    public void testWordCountCommand() throws IOException {
        String filename = "test.txt";
        long expectedCount = 789L;
        when(ccwcService.countWords(filename)).thenReturn(expectedCount);

        String response = wcToolApplication.processArgs("-w", filename);

        assertEquals(expectedCount + " " + filename, response);
        verify(ccwcService).countWords(filename);
    }

    @Test
    public void testCharacterCountCommand() throws IOException {
        String filename = "test.txt";
        long expectedCount = 1011L;
        when(ccwcService.countCharacters(filename)).thenReturn(expectedCount);

        String response = wcToolApplication.processArgs("-m", filename);

        assertEquals(expectedCount + " " + filename, response);
        verify(ccwcService).countCharacters(filename);
    }

    @Test
    public void testDefaultCase() throws IOException {
        String filename = "test.txt";
        long[] expectedCounts = new long[]{1, 2, 3, 4};
        when(ccwcService.countAllWithStructuredConcurrency(filename)).thenReturn(expectedCounts);

        String response = wcToolApplication.processArgs(filename);

        assertTrue(response.contains(filename));
        verify(ccwcService).countAllWithStructuredConcurrency(filename);
    }

    @Test
    public void testInvalidCommand() throws IOException {
        String response = wcToolApplication.processArgs("-x", "test.txt");
        assertEquals("Command option not found", response);
    }
}
