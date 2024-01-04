package com.devjoemar.ownwctool.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import java.util.concurrent.StructuredTaskScope;

import java.util.stream.Stream;

import org.springframework.stereotype.Service;

@Service
public class CcwcService {

    public long countBytes(String filename) throws IOException {
        final Path path = Paths.get(filename);
        return Files.size(path);
    }

    public long countLines(String filename) throws IOException {
        final Path path = Paths.get(filename);
        try (Stream<String> lines = Files.lines(path)) {
            return lines.count();
        }
    }

    public long countWords(String filename) throws IOException {
        final Path path = Paths.get(filename);
        try (Stream<String> lines = Files.lines(path)) {
            return lines
                    .map(this::splitLineIntoWords) // Split each line into words
                    .flatMap(Arrays::stream) // Create a stream of words
                    .filter(word -> !word.isEmpty()) // Filter out any empty strings
                    .count(); // Count the words
        }
    }

    private String[] splitLineIntoWords(String line) {
        // Trimming leading and trailing spaces from the line
        // Splitting the line by one or more whitespace characters
        return line.trim().split("\\s+");
    }

    public long countCharacters(String filename) throws IOException {
        final Path path = Paths.get(filename);
        long characterCount = 0;
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            int ch;
            while ((ch = reader.read()) != -1) {
                characterCount++;
            }
        }
        return characterCount;
    }

    public long[] countAll(String filename) throws IOException {
        long byteCount = countBytes(filename);
        long lineCount = countLines(filename);
        long wordCount = countWords(filename);
        long charCount = countCharacters(filename);
        return new long[]{lineCount, wordCount, byteCount, charCount};
    }

    public long[] countAllWithStructuredConcurrency(final String filename) {
        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            StructuredTaskScope.Subtask<Long> byteCount = scope.fork(() -> countBytes(filename));
            StructuredTaskScope.Subtask<Long> lineCount = scope.fork(() -> countLines(filename));
            StructuredTaskScope.Subtask<Long> wordCount = scope.fork(() -> countWords(filename));
            StructuredTaskScope.Subtask<Long> charCount = scope.fork(() -> countCharacters(filename));

            // Wait for all tasks to complete
            scope.join();
            scope.throwIfFailed();
            return new long[]{
                    lineCount.get(),
                    wordCount.get(),
                    byteCount.get(),
                    charCount.get()
            };
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Error in concurrent counting", e);
        }
    }
}
