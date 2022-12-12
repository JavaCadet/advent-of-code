package org.javacadet.adventofcode.year2022;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/* Monkey in the Middle */
public class Day11 {

    private static class Monkey {

        private final int id;
        private final Deque<Long> items;
        private final String[] operation;
        private final long divisor;
        private final int targetIfTrue;
        private final int targetIfFalse;
        private long inspectedItemsCount;

        public Monkey(List<String> lines) {
            int lineIndex = 0;

            this.id = Integer.parseInt(lines.get(lineIndex).replaceAll("[^0-9]", ""));
            this.items = new LinkedList<>(
                    Arrays.stream(lines.get(lineIndex+1).replaceAll("[^0-9,]", "").split(","))
                             .map(Long::parseLong).toList());
            this.operation = lines.get(lineIndex+2).replaceFirst("  Operation: new = old ", "").split(" ");
            this.divisor = Long.parseLong(lines.get(lineIndex+3).replaceAll("[^0-9]", ""));
            this.targetIfTrue = Integer.parseInt(lines.get(lineIndex+4).replaceAll("[^0-9]", ""));
            this.targetIfFalse = Integer.parseInt(lines.get(lineIndex+5).replaceAll("[^0-9]", ""));
        }

        public int getId() {
            return id;
        }

        public Deque<Long> getItems() {
            return items;
        }

        public String getOperation() {
            return operation[0];
        }

        public String getOperand() {
            return operation[1];
        }

        public long getDivisor() {
            return divisor;
        }

        public int getTargetIfTrue() {
            return targetIfTrue;
        }

        public int getTargetIfFalse() {
            return targetIfFalse;
        }

        public long getInspectedItemsCount() {
            return inspectedItemsCount;
        }

        public void incrementInspectedItemsCount() {
            inspectedItemsCount++;
        }
    }

    private static long partOne(List<String> lines) {
        List<Monkey> monkeys = new ArrayList<>();
        for (int i = 0; i < lines.size(); i += 7) {
            monkeys.add(new Monkey(lines.subList(i, i+6)));
        }

        for (int round = 0; round < 20; round++) {
            for (Monkey monkey : monkeys) {
                Deque<Long> items = monkey.getItems();
                while (!items.isEmpty()) {
                    long item = items.remove();
                    long operand = monkey.getOperand().equals("old") ? item : Long.parseLong(monkey.getOperand());

                    item = switch (monkey.getOperation()) {
                        case "+" -> item + operand;
                        case "*" -> item * operand;
                        default -> throw new RuntimeException("Should not happen ever!");
                    } / 3;

                    monkey.incrementInspectedItemsCount();

                    if (item % monkey.getDivisor() == 0) {
                        monkeys.get(monkey.getTargetIfTrue()).getItems().add(item);
                    } else {
                        monkeys.get(monkey.getTargetIfFalse()).getItems().add(item);
                    }
                }
            }
        }

        return monkeys.stream()
                .mapToLong(Monkey::getInspectedItemsCount)
                .boxed()
                .sorted(Collections.reverseOrder())
                .limit(2)
                .reduce(1L, (x, y) -> x * y);
    }

    private static long partTwo(List<String> lines) {
        List<Monkey> monkeys = new ArrayList<>();
        for (int i = 0; i < lines.size(); i += 7) {
            monkeys.add(new Monkey(lines.subList(i, i+6)));
        }

        // NOTE: This was the hardest part -- math -- still don't get how it works
        long modulo = monkeys.stream()
                .mapToLong(Monkey::getDivisor)
                .reduce(1L, (a, b) -> a * b);

        for (int round = 0; round < 10_000; round++) {
            for (Monkey monkey : monkeys) {
                Deque<Long> items = monkey.getItems();
                while (!items.isEmpty()) {
                    long item = items.remove();
                    long operand = monkey.getOperand().equals("old") ? item : Long.parseLong(monkey.getOperand());

                    item = switch (monkey.getOperation()) {
                        case "+" -> item + operand;
                        case "*" -> item * operand;
                        default -> throw new RuntimeException("Should not happen ever!");
                    } % modulo;

                    monkey.incrementInspectedItemsCount();

                    if (item % monkey.getDivisor() == 0) {
                        monkeys.get(monkey.getTargetIfTrue()).getItems().add(item);
                    } else {
                        monkeys.get(monkey.getTargetIfFalse()).getItems().add(item);
                    }
                }
            }
        }

        return monkeys.stream()
                .mapToLong(Monkey::getInspectedItemsCount)
                .boxed()
                .sorted(Collections.reverseOrder())
                .limit(2)
                .reduce(1L, (x, y) -> x * y);
    }

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Path.of("src/main/resources/input-day11.txt"));
        System.out.println(partOne(lines)); // 54054
        System.out.println(partTwo(lines)); // 14314925001
    }
}
