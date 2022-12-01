package org.javacadet.adventofcode.year2022;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

/* Calorie Counting */
public class Day01 {

    private static int partOne(List<String> lines) {
        int maxCalories = 0;
        int sumCalories = 0;

        for (String line : lines) {
            if (line.isEmpty() || !lines.iterator().hasNext()) { // hasNext() is needed so the last elf is counted
                if (maxCalories < sumCalories) {
                    maxCalories = sumCalories;
                }
                sumCalories = 0;
            } else {
                sumCalories += Integer.parseInt(line);
            }
        }

        return maxCalories;
    }

    private static int partTwo(List<String> lines) {
        int[] maxCalories = new int[3];
        int sumCalories = 0;

        for (String line : lines) {
            if (line.isEmpty() || !lines.iterator().hasNext()) { // hasNext() is needed so the last elf is counted
                for (int i = 0; i < maxCalories.length; i++) {
                    if (maxCalories[i] < sumCalories) {
                        for (int j = maxCalories.length - 1; j > i; j--) {
                            maxCalories[j] = maxCalories[j-1];
                        }
                        maxCalories[i] = sumCalories;
                        break;
                    }
                }
                sumCalories = 0;
            } else {
                sumCalories += Integer.parseInt(line);
            }
        }

        return Arrays.stream(maxCalories).sum();
    }

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Path.of("src/main/resources/input-day01.txt"));
        System.out.println(partOne(lines)); // 74198
        System.out.println(partTwo(lines)); // 209914
    }
}
