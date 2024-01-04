package com.devjoemar.ownwctool;

import java.util.Map;
import java.util.HashMap;

public enum CommandOption {
    BYTE_COUNT("-c", "Count the number of bytes in a file"),
    LINE_COUNT("-l", "Count the number of lines in a file"),
    WORD_COUNT("-w", "Count the number of words in a file"),
    CHARACTER_COUNT("-m", "Count the number of characters in a file");

    private final String option;
    private final String description;

    // Map for reverse lookup
    private static final Map<String, CommandOption> lookup = new HashMap<>();

    static {
        for (CommandOption option : CommandOption.values()) {
            lookup.put(option.getOption(), option);
        }
    }

    CommandOption(String option, String description) {
        this.option = option;
        this.description = description;
    }

    public String getOption() {
        return option;
    }

    public String getDescription() {
        return description;
    }

    public static CommandOption fromString(String text) {
        return lookup.get(text.toLowerCase());
    }
}
