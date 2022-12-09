package org.javacadet.adventofcode.year2022;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/* Rope Bridge */
public class Day09 {

    private enum Side {
        U (0, -1),
        D (0, 1),
        L (-1, 0),
        R (1, 0);

        private final int x;
        private final int y;

        Side(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private static class Position {

        private final int x;
        private final int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Position newPosition(Side side) {
            return new Position(this.x + side.x, this.y + side.y);
        }

        public Position newPosition(Position previous) {
            int dx = previous.x - this.x;
            int dy = previous.y - this.y;

            if (dx ==  0 && dy == -2) return this.newPosition(Side.U);
            if (dx ==  0 && dy ==  2) return this.newPosition(Side.D);
            if (dx == -2 && dy ==  0) return this.newPosition(Side.L);
            if (dx ==  2 && dy ==  0) return this.newPosition(Side.R);

            if (dx == -1 && dy == -2) return this.newPosition(Side.U).newPosition(Side.L);
            if (dx == -2 && dy == -1) return this.newPosition(Side.U).newPosition(Side.L);
            if (dx ==  1 && dy == -2) return this.newPosition(Side.U).newPosition(Side.R);
            if (dx ==  2 && dy == -1) return this.newPosition(Side.U).newPosition(Side.R);

            if (dx == -1 && dy ==  2) return this.newPosition(Side.D).newPosition(Side.L);
            if (dx == -2 && dy ==  1) return this.newPosition(Side.D).newPosition(Side.L);
            if (dx ==  1 && dy ==  2) return this.newPosition(Side.D).newPosition(Side.R);
            if (dx ==  2 && dy ==  1) return this.newPosition(Side.D).newPosition(Side.R);

            // 2nd part
            if (dx == -2 && dy == -2) return this.newPosition(Side.U).newPosition(Side.L);
            if (dx ==  2 && dy == -2) return this.newPosition(Side.U).newPosition(Side.R);
            if (dx == -2 && dy ==  2) return this.newPosition(Side.D).newPosition(Side.L);
            if (dx ==  2 && dy ==  2) return this.newPosition(Side.D).newPosition(Side.R);

            return this;
        }

        @Override
        public boolean equals(Object that) {
            if (this == that) return true;
            if (that == null || getClass() != that.getClass()) return false;
            Position position = (Position) that;
            return this.x == position.x && this.y == position.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.x, this.y);
        }

        @Override
        public String toString() {
            return "{ " + this.x + ", " + this.y + " }";
        }
    }

    private static int partOne(List<String> lines) {
        Set<Position> visitedByTail = new HashSet<>();
        Position head = new Position(0, 0);
        Position tail = new Position(0, 0);

        visitedByTail.add(tail);

        for (String line : lines) {
            String[] parts = line.split(" ");
            Side side = Side.valueOf(parts[0]);
            int stepCount = Integer.parseInt(parts[1]);

            for (int i = 0; i < stepCount; i++) {
                head = head.newPosition(side);
                tail = tail.newPosition(head);
                visitedByTail.add(tail);
            }
        }

        return visitedByTail.size();
    }

    private static int partTwo(List<String> lines) {
        Set<Position> visitedByTail = new HashSet<>();
        Position head = new Position(0, 0);
        Position[] tail = new Position[9];
        Arrays.fill(tail, new Position(0, 0));

        visitedByTail.add(tail[8]);

        for (String line : lines) {
            String[] parts = line.split(" ");
            Side side = Side.valueOf(parts[0]);
            int stepCount = Integer.parseInt(parts[1]);

            for (int i = 0; i < stepCount; i++) {
                head = head.newPosition(side);
                for(int j = 0; j < 9; j++) {
                    tail[j] = tail[j].newPosition(j == 0 ? head : tail[j-1]);
                }

                visitedByTail.add(tail[8]);
            }
        }

        return visitedByTail.size();
    }

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Path.of("src/main/resources/input-day09.txt"));
        System.out.println(partOne(lines)); // 6256
        System.out.println(partTwo(lines)); // 2665
    }
}
