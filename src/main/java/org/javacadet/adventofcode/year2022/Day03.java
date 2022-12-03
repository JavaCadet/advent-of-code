package org.javacadet.adventofcode.year2022;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/* Rucksack Reorganization */
public class Day03 {

    private static int partOne(List<String> lines) {
        int sum = 0;

        for (String line : lines) {
            int half = line.length() / 2;
            String firstHalf = line.substring(0, half);
            String secondHalf = line.substring(half);

            for (int i = 0; i < half; i++) {
                char ch = firstHalf.charAt(i);
                if (secondHalf.contains(Character.toString(ch))) {
                    int priority = ch - (Character.isUpperCase(ch) ? 'A' - 26 : 'a') + 1;
                    sum += priority;
                    break;
                }
            }
        }

        return sum;
    }

    private static int partTwo(List<String> lines) {
        int sum = 0;

        for (int i = 0; i < lines.size(); i++) {
            String firstGroup = lines.get(i++);
            String secondGroup = lines.get(i++);
            String thirdGroup = lines.get(i);

            for (int j = 0; j < firstGroup.length(); j++) {
                char ch = firstGroup.charAt(j);
                if (secondGroup.contains(Character.toString(ch)) && thirdGroup.contains(Character.toString(ch))) {
                    int priority = ch - (Character.isUpperCase(ch) ? 'A' - 26 : 'a') + 1;
                    sum += priority;
                    break;
                }
            }
        }

        return sum;
    }

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Path.of("src/main/resources/input-day03.txt"));
        System.out.println(partOne(lines)); // 7831
        System.out.println(partTwo(lines)); // 2683
    }
}
