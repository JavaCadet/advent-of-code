package org.javacadet.adventofcode.year2022;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Predicate;

/* Hill Climbing Algorithm */
public class Day12 {

    private static class HeightMap {

        private final Map<Square, Set<Square>> connections;

        public HeightMap() {
            this.connections = new HashMap<>();
        }

        public void addSquare(Square square) {
            this.connections.putIfAbsent(square, new HashSet<>());
        }

        public void addConnection(Square square1, Square square2) {
            this.addSquare(square1);
            this.addSquare(square2);
            this.connections.get(square1).add(square2);
        }

        public Set<Square> getAllSquares() {
            return this.connections.keySet();
        }

        public Set<Square> getConnectedSquares(Square square) {
            return this.connections.get(square);
        }

        public List<Square> findAllSquares(Predicate<Square> predicate) {
            return this.getAllSquares().stream()
                    .filter(predicate)
                    .toList();
        }

        public Square findSquare(Predicate<Square> predicate) {
            return this.findAllSquares(predicate).stream()
                    .findFirst()
                    .orElseThrow();
        }

        public Square findStart() {
            return this.findSquare(Square::isStart);
        }

        public Square findDestination() {
            return this.findSquare(Square::isDestination);
        }

        public List<Square> findShortestPath() {
            Square start = this.findStart();
            Square destination = this.findDestination();
            return this.findShortestPath(start, destination);
        }

        public List<Square> findShortestPath(Square start, Square destination) {
            Map<Square, Square> parents = new HashMap<>();
            Queue<Square> queue = new LinkedList<>();

            parents.put(start, null);
            queue.add(start);

            while (!queue.isEmpty()) {
                Square current = queue.poll();

                if (current.equals(destination)) {
                    List<Square> path = new ArrayList<>();
                    for (Square square = current; !square.equals(start); square = parents.get(square)) {
                        path.add(square);
                    }
                    return path;
                }

                for (Square neighbour : this.getConnectedSquares(current)) {
                    if (parents.containsKey(neighbour)) continue;
                    parents.put(neighbour, current);
                    queue.add(neighbour);
                }
            }

            return new ArrayList<>();
        }

        @Override
        public String toString() {
            StringBuilder result = new StringBuilder();

            for (Square square : this.getAllSquares()) {
                if (square.isStart()) {
                    result.append("START: ");
                }

                if (square.isDestination()) {
                    result.append("DESTINATION: ");
                }

                result.append(square).append("  ->");
                for (Square connectedSquare : this.getConnectedSquares(square)) {
                    result.append("  ").append(connectedSquare);
                }
                result.append("\n");
            }

            return result.toString();
        }
    }

    private static class Square {

        private final int row;
        private final int col;
        private final char value;
        private final boolean start;
        private final boolean destination;

        public static Square build(int row, int col, char value) {
            return switch (value) {
                case 'S' -> new Square(row, col, 'a', true, false);
                case 'E' -> new Square(row, col, 'z', false, true);
                default  -> new Square(row, col, value);
            };
        }

        public Square(int row, int col, char value, boolean start, boolean destination) {
            this.row = row;
            this.col = col;
            this.value = value;
            this.start = start;
            this.destination = destination;
        }

        public Square(int row, int col, char value) {
            this(row, col, value, false, false);
        }

        public char getValue() {
            return this.value;
        }

        public boolean isStart() {
            return this.start;
        }

        public boolean isDestination() {
            return this.destination;
        }

        public boolean canMoveTo(Square square) {
            return square.value - this.value <= 1;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }

            if (o == null || this.getClass() != o.getClass()) {
                return false;
            }

            Square that = (Square) o;

            return this.row == that.row && this.col == that.col;
        }

        @Override
        public int hashCode() {
            return Objects.hash(row, col);
        }

        @Override
        public String toString() {
            return "[" + row + "," + col + "]" + "(" + value + ")";
        }
    }

    private static int partOne(List<String> lines) {
        HeightMap map = new HeightMap();

        int rows = lines.size();
        for (int row = 0; row < rows; row++) {
            String line = lines.get(row);
            int cols = line.length();
            for (int col = 0; col < cols; col++) {
                char ch = line.charAt(col);
                Square current = Square.build(row, col, ch   );

                if (row > 0) {
                    ch = lines.get(row - 1).charAt(col);
                    Square squareUp = Square.build(row - 1, col, ch);
                    if (current.canMoveTo(squareUp)) {
                        map.addConnection(current, squareUp);
                    }
                }

                if (row < rows - 1) {
                    ch = lines.get(row + 1).charAt(col);
                    Square squareDown = Square.build(row + 1, col, ch);
                    if (current.canMoveTo(squareDown)) {
                        map.addConnection(current, squareDown);
                    }
                }

                if (col > 0) {
                    ch = lines.get(row).charAt(col - 1);
                    Square squareLeft = Square.build(row, col - 1, ch);
                    if (current.canMoveTo(squareLeft)) {
                        map.addConnection(current, squareLeft);
                    }
                }

                if (col < cols - 1) {
                    ch = lines.get(row).charAt(col + 1);
                    Square squareLeft = Square.build(row, col + 1, ch);
                    if (current.canMoveTo(squareLeft)) {
                        map.addConnection(current, squareLeft);
                    }
                }
            }
        }

        return map.findShortestPath().size();
    }

    private static int partTwo(List<String> lines) {
        HeightMap map = new HeightMap();

        int rows = lines.size();
        for (int row = 0; row < rows; row++) {
            String line = lines.get(row);
            int cols = line.length();
            for (int col = 0; col < cols; col++) {
                char ch = line.charAt(col);
                Square current = Square.build(row, col, ch   );

                if (row > 0) {
                    ch = lines.get(row - 1).charAt(col);
                    Square squareUp = Square.build(row - 1, col, ch);
                    if (current.canMoveTo(squareUp)) {
                        map.addConnection(current, squareUp);
                    }
                }

                if (row < rows - 1) {
                    ch = lines.get(row + 1).charAt(col);
                    Square squareDown = Square.build(row + 1, col, ch);
                    if (current.canMoveTo(squareDown)) {
                        map.addConnection(current, squareDown);
                    }
                }

                if (col > 0) {
                    ch = lines.get(row).charAt(col - 1);
                    Square squareLeft = Square.build(row, col - 1, ch);
                    if (current.canMoveTo(squareLeft)) {
                        map.addConnection(current, squareLeft);
                    }
                }

                if (col < cols - 1) {
                    ch = lines.get(row).charAt(col + 1);
                    Square squareLeft = Square.build(row, col + 1, ch);
                    if (current.canMoveTo(squareLeft)) {
                        map.addConnection(current, squareLeft);
                    }
                }
            }
        }

        return map.findAllSquares(square -> square.getValue() == 'a').stream()
                .mapToInt(square -> map.findShortestPath(square, map.findDestination()).size())
                .filter(n -> n > 0)
                .min()
                .orElseThrow();
    }

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Path.of("src/main/resources/input-day12.txt"));
        System.out.println(partOne(lines)); // 394
        System.out.println(partTwo(lines)); // 388
    }
}
