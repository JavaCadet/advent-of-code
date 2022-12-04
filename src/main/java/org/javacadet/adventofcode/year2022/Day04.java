package org.javacadet.adventofcode.year2022;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

/* Camp Cleanup */
public class Day04 {

    private static int partOne(List<String> lines) {
        int count = 0;

        for (String line : lines) {
            String[] elfs = line.split(",");
            int[] elf1 = Arrays.stream(elfs[0].split("-")).mapToInt(Integer::parseInt).toArray();
            int[] elf2 = Arrays.stream(elfs[1].split("-")).mapToInt(Integer::parseInt).toArray();
            int len1 = elf1[1] - elf1[0];
            int len2 = elf2[1] - elf2[0];

            if (len1 < len2) {
                if (elf1[0] >= elf2[0] && elf1[1] <= elf2[1]) {
                    count++;
                }
            } else if (len2 < len1) {
                if (elf2[0] >= elf1[0] && elf2[1] <= elf1[1]) {
                    count++;
                }
            } else {
                if (elf1[0] == elf2[0] && elf1[1] == elf2[1]) {
                    count++;
                }
            }
        }

        return count;
    }

    private static int partTwo(List<String> lines) {
        int count = 0;

        for (String line : lines) {
            String[] elfs = line.split(",");
            int[] elf1 = Arrays.stream(elfs[0].split("-")).mapToInt(Integer::parseInt).toArray();
            int[] elf2 = Arrays.stream(elfs[1].split("-")).mapToInt(Integer::parseInt).toArray();

            if ((elf1[0] >= elf2[0] && elf1[0] <= elf2[1]) ||
                (elf1[1] >= elf2[0] && elf1[1] <= elf2[1]) ||
                (elf2[0] >= elf1[0] && elf2[0] <= elf1[1]) ||
                (elf2[1] >= elf1[0] && elf2[1] <= elf1[1])) {
                count++;
            }
        }

        return count;
    }

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Path.of("src/main/resources/input-day04.txt"));
        System.out.println(partOne(lines)); // 518
        System.out.println(partTwo(lines)); // 909
    }
}
