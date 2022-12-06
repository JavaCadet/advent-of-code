package org.javacadet.adventofcode.year2022;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

/* Tuning Trouble */
public class Day06 {

    private static boolean isUnique(String str) {
        Set<Character> chars = new HashSet<>();

        for (Character ch : str.toCharArray()) {
            if (!chars.add(ch)) {
                return false;
            }
        }

        return true;
    }

    private static int partOne(String line) {
        for (int i = 4; i < line.length(); i++) {
            if (isUnique(line.substring(i-4, i))) {
                return i;
            }
        }
        return -1;
    }

    private static int partTwo(String line) {
        for (int i = 14; i < line.length(); i++) {
            if (isUnique(line.substring(i-14, i))) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) throws IOException {
        Path inputFile = Path.of("src/main/resources/input-day06.txt");
        Files.lines(inputFile)
                .mapToInt(Day06::partOne)
                .forEach(System.out::println); // 1651
        Files.lines(inputFile)
                .mapToInt(Day06::partTwo)
                .forEach(System.out::println); // 3837
    }
}
