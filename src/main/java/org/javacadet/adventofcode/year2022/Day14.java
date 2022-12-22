package org.javacadet.adventofcode.year2022;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/* Regolith Reservoir */
public class Day14 {

    private static class Point {

        public static Set<Point> getPath(Point point1, Point point2) {
            Set<Point> path = new HashSet<>();

            if (point1.getX() == point2.getX()) {
                int x = point1.getX();
                for (int y = Math.min(point1.getY(), point2.getY()); y <= Math.max(point1.getY(), point2.getY()); y++) {
                    path.add(new Point(x, y));
                }
            } else {
                int y = point1.getY();
                for (int x = Math.min(point1.getX(), point2.getX()); x <= Math.max(point1.getX(), point2.getX()); x++) {
                    path.add(new Point(x, y));
                }
            }

            return path;
        }

        private int x;
        private int y;

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Point(String s) {
            String[] xy = s.split(",");
            this.x = Integer.parseInt(xy[0]);
            this.y = Integer.parseInt(xy[1]);
        }

        public boolean moveDown(Set<Point> rocks) {
            Point point = new Point(x, y+1);
            if (!rocks.contains(point)) {
                y++;
                return true;
            }
            return false;
        }

        public boolean moveDownLeft(Set<Point> rocks) {
            Point point = new Point(x-1, y+1);
            if (!rocks.contains(point)) {
                x--;
                y++;
                return true;
            }
            return false;
        }

        public boolean moveDownRight(Set<Point> rocks) {
            Point point = new Point(x+1, y+1);
            if (!rocks.contains(point)) {
                x++;
                y++;
                return true;
            }
            return false;
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) return true;
            if (!(other instanceof Point point)) return false;

            if (x != point.x) return false;
            return y == point.y;
        }

        @Override
        public int hashCode() {
            int result = x;
            result = 31 * result + y;
            return result;
        }

        @Override
        public String toString() {
            return "Point(" + x + ", " + y + ")";
        }
    }

    private static int partOne(List<String> lines) {
        Set<Point> rocks = new HashSet<>();

        for (String line : lines) {
            List<Point> path = Arrays.stream(line.split(" -> ")).map(Point::new).toList();
            for (int i = 0; i < path.size() - 1; i++) {
                rocks.addAll(Point.getPath(path.get(i), path.get(i+1)));
            }
        }

        int maxY = rocks.stream()
                .mapToInt(Point::getY)
                .max()
                .orElseThrow();

        int sandCount = 0;
        Point start = new Point(500, 0);

        while (true) {
            Point aUnitOfSand = new Point(start.getX(), start.getY());

            while (true) {
                if (!aUnitOfSand.moveDown(rocks) && !aUnitOfSand.moveDownLeft(rocks) && !aUnitOfSand.moveDownRight(rocks)) {
                    rocks.add(aUnitOfSand);
                    sandCount++;
                    break;
                }

                if (aUnitOfSand.getY() == maxY) {
                    return sandCount;
                }
            }
        }
    }

    private static int partTwo(List<String> lines) {
        Set<Point> rocks = new HashSet<>();

        for (String line : lines) {
            List<Point> path = Arrays.stream(line.split(" -> ")).map(Point::new).toList();
            for (int i = 0; i < path.size() - 1; i++) {
                rocks.addAll(Point.getPath(path.get(i), path.get(i+1)));
            }
        }

        int maxY = rocks.stream()
                .mapToInt(Point::getY)
                .max()
                .orElseThrow() + 1;

        int sandCount = 0;
        Point start = new Point(500, 0);

        while (true) {
            Point aUnitOfSand = new Point(start.getX(), start.getY());

            while (true) {
                if (aUnitOfSand.getY() == maxY || !aUnitOfSand.moveDown(rocks) && !aUnitOfSand.moveDownLeft(rocks) && !aUnitOfSand.moveDownRight(rocks)) {
                    rocks.add(aUnitOfSand);
                    sandCount++;
                    break;
                }
            }

            if (aUnitOfSand.equals(start)) {
                return sandCount;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Path.of("src/main/resources/input-day14.txt"));
        System.out.println(partOne(lines)); // 1513
        System.out.println(partTwo(lines)); // 22646
    }
}
