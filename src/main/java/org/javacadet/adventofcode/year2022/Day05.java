package org.javacadet.adventofcode.year2022;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

/* Supply Stacks */
public class Day05 {

    private static String partOne(List<String> lines) {
        /* Find number of stacks */
        int i;
        for (i = 0; i < lines.size(); i++) {
            if (lines.get(i).startsWith(" 1 ")) {
                break;
            }
        }

        /* Fill stacks */
        int stackCount = lines.get(i).split(" {3}").length;
        LinkedList<String>[] stacks = new LinkedList[stackCount];
        for (int j = i - 1; j >= 0; j--) {
            String line = lines.get(j)
                    .replaceAll(" {4}", "[0] ")
                    .replaceAll("[^0A-Z]", "");

            for (int k = 0; k < line.length(); k++) {
                if (stacks[k] == null) {
                    stacks[k] = new LinkedList<>();
                }

                if (!Character.isDigit(line.charAt(k))) {
                    stacks[k].push(line.charAt(k) + "");
                }
            }
        }

        /* Do moves */
        for (i += 2; i < lines.size(); i++) {
            int[] moves = Arrays.stream(lines.get(i)
                    .replace("move ", "")
                    .replace("from ", "")
                    .replace("to ", "")
                    .split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray();

            int crates = moves[0];
            int stackFrom = moves[1] - 1;
            int stackTo = moves[2] - 1;

            for (int crate = 0; crate < crates; crate++) {
                stacks[stackTo].push(stacks[stackFrom].pop());
            }
        }

        /* Get result */
        return Arrays.stream(stacks).map(LinkedList::pop).collect(Collectors.joining());
    }

    private static String partTwo(List<String> lines) {
        /* Find number of stacks */
        int i;
        for (i = 0; i < lines.size(); i++) {
            if (lines.get(i).startsWith(" 1 ")) {
                break;
            }
        }

        /* Fill stacks */
        int stackCount = lines.get(i).split(" {3}").length;
        LinkedList<String>[] stacks = new LinkedList[stackCount];
        for (int j = i - 1; j >= 0; j--) {
            String line = lines.get(j)
                    .replaceAll(" {4}", "[0] ")
                    .replaceAll("[^0A-Z]", "");

            for (int k = 0; k < line.length(); k++) {
                if (stacks[k] == null) {
                    stacks[k] = new LinkedList<>();
                }

                if (!Character.isDigit(line.charAt(k))) {
                    stacks[k].push(line.charAt(k) + "");
                }
            }
        }

        /* Do moves */
        for (i += 2; i < lines.size(); i++) {
            int[] moves = Arrays.stream(lines.get(i)
                            .replace("move ", "")
                            .replace("from ", "")
                            .replace("to ", "")
                            .split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray();

            int crates = moves[0];
            int stackFrom = moves[1] - 1;
            int stackTo = moves[2] - 1;

            LinkedList<String> tempStack = new LinkedList<>();
            for (int crate = 0; crate < crates; crate++) {
                tempStack.push(stacks[stackFrom].pop());
            }

            for (int crate = 0; crate < crates; crate++) {
                stacks[stackTo].push(tempStack.pop());
            }
        }

        /* Get result */
        return Arrays.stream(stacks).map(LinkedList::pop).collect(Collectors.joining());
    }

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Path.of("src/main/resources/input-day05.txt"));
        System.out.println(partOne(lines)); // FCVRLMVQP
        System.out.println(partTwo(lines)); // RWLWGJGFD
    }
}
