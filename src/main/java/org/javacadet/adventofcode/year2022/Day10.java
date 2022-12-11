package org.javacadet.adventofcode.year2022;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/* Cathode-Ray Tube */
public class Day10 {

    private static int partOne(List<String> lines) {
        int registerX = 1;
        int cycleCount = 0;
        int sumOfSignalStrengths = 0;

        for (String line : lines) {
            String[] parts = line.split(" ");

            String instruction = parts[0];
            switch (instruction) {
                case "noop" -> {
                    cycleCount++;
                    if ((cycleCount - 20) % 40 == 0) {
                        sumOfSignalStrengths += (cycleCount * registerX);
                    }
                }
                case "addx" -> {
                    cycleCount++;
                    if ((cycleCount - 20) % 40 == 0) {
                        sumOfSignalStrengths += (cycleCount * registerX);
                    }

                    cycleCount++;
                    if ((cycleCount - 20) % 40 == 0) {
                        sumOfSignalStrengths += (cycleCount * registerX);
                    }

                    int value = Integer.parseInt(parts[1]);
                    registerX += value;
                }
            }
        }

        return sumOfSignalStrengths;
    }

    private static char getPixel(int crt, int sprite) {
        return sprite - 1 <= crt && crt <= sprite + 1 ? '#' : '.';
    }

    private static String partTwo(List<String> lines) {
        int registerX = 1;
        int cycleCount = 0;
        StringBuilder screen = new StringBuilder();

        for (String line : lines) {
            String[] parts = line.split(" ");

            String instruction = parts[0];
            switch (instruction) {
                case "noop" -> {
                    screen.append(getPixel(registerX, cycleCount++));
                    if (cycleCount % 40 == 0) {
                        screen.append('\n');
                        cycleCount = 0;
                    }
                }
                case "addx" -> {
                    screen.append(getPixel(registerX, cycleCount++));
                    if (cycleCount % 40 == 0) {
                        screen.append('\n');
                        cycleCount = 0;
                    }

                    screen.append(getPixel(registerX, cycleCount++));
                    if (cycleCount % 40 == 0) {
                        screen.append('\n');
                        cycleCount = 0;
                    }

                    int value = Integer.parseInt(parts[1]);
                    registerX += value;
                }
            }
        }

        return screen.toString();
    }

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Path.of("src/main/resources/input-day10.txt"));
        System.out.println(partOne(lines)); // 14240
        System.out.println(partTwo(lines)); // PLULKBZH
    }
}
