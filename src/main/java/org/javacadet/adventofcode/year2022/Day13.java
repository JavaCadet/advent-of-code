package org.javacadet.adventofcode.year2022;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/* Distress Signal */
public class Day13 {

    private static List<Object> parseLine(String line) {
        Deque<List<Object>> stack = new LinkedList<>();
        List<Object> packet = new LinkedList<>();
        StringBuilder str = new StringBuilder();

        for (char ch : line.toCharArray()) {
            switch (ch) {
                case '[' -> {
                    stack.push(new LinkedList<>());
                }
                case ']' -> {
                    if (!str.isEmpty()) {
                        stack.getFirst().add(Integer.parseInt(str.toString()));
                        str.setLength(0);
                    }
                    packet = stack.pop();
                    if (!stack.isEmpty()) {
                        stack.getFirst().add(packet);
                    }
                }
                case ',' -> {
                    if (!str.isEmpty()) {
                        stack.getFirst().add(Integer.parseInt(str.toString()));
                        str.setLength(0);
                    }
                }
                default -> {
                    str.append(ch);
                }
            }
        }

        return packet;
    }

    private static int compare(List<Object> left, List<Object> right, int i) {
        if (i >= left.size() && i >= right.size()) return 0;
        if (i >= left.size()) return -1;
        if (i >= right.size()) return 1;

        Object leftItem = left.get(i);
        Object rightItem = right.get(i);

        if (leftItem instanceof Integer leftInteger && rightItem instanceof Integer rightInteger) {
            int result = leftInteger.compareTo(rightInteger);
            return result != 0 ? result : compare(left, right, i + 1);
        }

        List<Object> leftList = leftItem instanceof List<?> ? (List<Object>) leftItem : List.of(leftItem);
        List<Object> rightList = rightItem instanceof List<?> ? (List<Object>) rightItem : List.of(rightItem);

        int result = compare(leftList, rightList, 0);
        return result != 0 ? result : compare(left, right, i + 1);
    }

    private static int partOne(List<String> lines) {
        int sumOfPairIndices = 0;
        int pairIndex = 0;

        for (int i = 0; i < lines.size(); i += 3) {
            pairIndex++;

            List<Object> left = parseLine(lines.get(i));
            List<Object> right = parseLine(lines.get(i+1));

            if (compare(left, right, 0) < 0) {
                sumOfPairIndices += pairIndex;
            }
        }

        return sumOfPairIndices;
    }

    private static int partTwo(List<String> lines) {
        List<List<Object>> packets = new ArrayList<>(lines.stream()
                .filter(s -> !s.isEmpty())
                .map(Day13::parseLine)
                .toList());
        List<List<Object>> dividers = List.of(parseLine("[[2]]"), parseLine("[[6]]"));

        packets.addAll(dividers);

        List<List<Object>> sortedPackets = packets.stream().sorted((left, right) -> compare(left, right, 0)).toList();
        return dividers.stream()
                .map(divider -> sortedPackets.indexOf(divider) + 1)
                .reduce((a, b) -> a * b)
                .orElseThrow();
    }

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Path.of("src/main/resources/input-day13.txt"));
        System.out.println(partOne(lines)); // 5588
        System.out.println(partTwo(lines)); // 23958
    }
}
