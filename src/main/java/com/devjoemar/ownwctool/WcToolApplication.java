package com.devjoemar.ownwctool;

import com.devjoemar.ownwctool.service.CcwcService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class WcToolApplication implements CommandLineRunner {

	private final CcwcService ccwcService;

	public WcToolApplication(CcwcService ccwcService) {
		this.ccwcService = ccwcService;
	}

	public static void main(String[] args) {
		SpringApplication.run(WcToolApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(processArgs(args));
	}

	public String processArgs(String... args) throws IOException {
		if (args.length == 0) {
			return getUsageMessage();
		} else if (args.length > 1) {
			return performCommand(args);
		} else {
			var filename = args[0];

			final long startTime = System.nanoTime();

			long[] counts = ccwcService.countAllWithStructuredConcurrency(filename);

			final long endTime = System.nanoTime();
			long durationInMillis = (endTime - startTime) / 1_000_000;

			System.out.println("Execution time: " + durationInMillis + " ms");
			return String.format("%d %d %d %s", counts[0], counts[1], counts[2], filename);
		}
	}

	private String performCommand(String... args) throws IOException {
		var commandOption = CommandOption.fromString(args[0]);
		if (commandOption == null) {
			return "Command option not found";
		}

		var filename = args[1];
		return switch (commandOption) {
			case BYTE_COUNT -> ccwcService.countBytes(filename) + " " + filename;
			case LINE_COUNT -> ccwcService.countLines(filename) + " " + filename;
			case WORD_COUNT -> ccwcService.countWords(filename) + " " + filename;
			case CHARACTER_COUNT -> ccwcService.countCharacters(filename) + " " + filename;
			default -> "No file path specified";
		};
	}

	private String getUsageMessage() {
		var sb = new StringBuilder();
		sb.append("Please input valid arguments. Example: {command} \"yourFileNameWithPath\"")
			.append("\nHere are the valid command:")
			.append("\nCommand | Description");

		for (CommandOption option : CommandOption.values()) {
			sb.append("\n")
			  .append(option.getOption()).append("\t\t| ").append(option.getDescription());
		}
		return sb.toString();
	}

}