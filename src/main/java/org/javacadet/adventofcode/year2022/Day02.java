package org.javacadet.adventofcode.year2022;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/* Rock Paper Scissors */
public class Day02 {

    private static int partOne(List<String> lines) {
        int totalScore = 0;

        for (String line : lines) {
            String[] cols = line.split(" ");

            totalScore += switch (cols[0]) {
                case "A" -> switch (cols[1]) {
                    case "X" -> 3;
                    case "Y" -> 6;
                    case "Z" -> 0;
                    default -> throw new IllegalArgumentException("cols[1] can be either X, Y, or Z");
                };
                case "B" -> switch (cols[1]) {
                    case "X" -> 0;
                    case "Y" -> 3;
                    case "Z" -> 6;
                    default -> throw new IllegalArgumentException("cols[1] can be either X, Y, or Z");
                };
                case "C" -> switch (cols[1]) {
                    case "X" -> 6;
                    case "Y" -> 0;
                    case "Z" -> 3;
                    default -> throw new IllegalArgumentException("cols[1] can be either X, Y, or Z");
                };
                default -> throw new IllegalArgumentException("cols[0] can be either A, B, or C");
            };

            totalScore += switch (cols[1]) {
                case "X" -> 1;
                case "Y" -> 2;
                case "Z" -> 3;
                default -> throw new IllegalArgumentException("cols[1] can be either X, Y, or Z");
            };
        }

        return totalScore;
    }

    private static int partTwo(List<String> lines) {
        int totalScore = 0;

        for (String line : lines) {
            String[] cols = line.split(" ");

            totalScore += switch (cols[0]) {
                case "A" -> switch (cols[1]) {
                    case "X" -> 3;
                    case "Y" -> 1;
                    case "Z" -> 2;
                    default -> throw new IllegalArgumentException("cols[1] can be either X, Y, or Z");
                };
                case "B" -> switch (cols[1]) {
                    case "X" -> 1;
                    case "Y" -> 2;
                    case "Z" -> 3;
                    default -> throw new IllegalArgumentException("cols[1] can be either X, Y, or Z");
                };
                case "C" -> switch (cols[1]) {
                    case "X" -> 2;
                    case "Y" -> 3;
                    case "Z" -> 1;
                    default -> throw new IllegalArgumentException("cols[1] can be either X, Y, or Z");
                };
                default -> throw new IllegalArgumentException("cols[0] can be either A, B, or C");
            };

            totalScore += switch (cols[1]) {
                case "X" -> 0;
                case "Y" -> 3;
                case "Z" -> 6;
                default -> throw new IllegalArgumentException("cols[1] can be either X, Y, or Z");
            };
        }

        return totalScore;
    }

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Path.of("src/main/resources/input-day02.txt"));
        System.out.println(partOne(lines)); // 11603
        System.out.println(partTwo(lines)); // 12725
    }
}
