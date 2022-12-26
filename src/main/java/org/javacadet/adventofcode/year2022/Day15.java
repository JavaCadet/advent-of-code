package org.javacadet.adventofcode.year2022;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/* Beacon Exclusion Zone */
public class Day15 {

    private static class Point {

        public static int calcDistance(Point p1, Point p2) {
            return Math.abs(p1.getX() - p2.getX()) + Math.abs(p1.getY() - p2.getY());
        }

        private final int x;
        private final int y;

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

        @Override
        public boolean equals(Object other) {
            if (this == other) return true;
            if (!(other instanceof Point point)) return false;
            if (x != point.getX()) return false;
            return getY() == point.getY();
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public String toString() {
            return getClass().getSimpleName() + " (" + x + ", " + y + ")";
        }
    }

    private static class Sensor extends Point {

        private final int reachableDistance;

        public int getReachableDistance() {
            return reachableDistance;
        }

        public Sensor(int x, int y, Beacon beacon) {
            super(x, y);
            this.reachableDistance = Point.calcDistance(this, beacon);
        }

        public boolean isReachable(Point point) {
            return Point.calcDistance(this, point) <= reachableDistance;
        }
    }

    private static class Beacon extends Point {
        public Beacon(int x, int y) {
            super(x, y);
        }
    }

    private static void printMap(Set<Sensor> sensors, Set<Beacon> beacons) {
        int minX = sensors.stream()
                .mapToInt(sensor -> sensor.getX() - sensor.getReachableDistance()).min().orElseThrow();
        int maxX = sensors.stream()
                .mapToInt(sensor -> sensor.getX() + sensor.getReachableDistance()).max().orElseThrow();
        int minY = sensors.stream()
                .mapToInt(sensor -> sensor.getY() - sensor.getReachableDistance()).min().orElseThrow();
        int maxY = sensors.stream()
                .mapToInt(sensor -> sensor.getY() + sensor.getReachableDistance()).max().orElseThrow();

        for (int y = minY; y <= maxY; y++) {
            for (int x = minX; x <= maxX; x++) {
                Point point = new Point(x, y);
                if (sensors.contains(point)) {
                    System.out.print("S");
                } else if (beacons.contains(point)) {
                    System.out.print("B");
                } else {
                    if (canHoldBeacon(point, sensors)) {
                        System.out.print(".");
                    } else {
                        System.out.print("#");
                    }
                }
            }
            System.out.println();
        }
    }

    private static boolean canHoldBeacon(Point point, Set<Sensor> sensors) {
        for (Sensor sensor : sensors) {
            if (sensor.isReachable(point)) {
                return false;
            }
        }
        return true;
    }

    private static int partOne(List<String> lines) {
        Set<Sensor> sensors = new HashSet<>();
        Set<Beacon> beacons = new HashSet<>();

        for (String line : lines) {
            int[] position = Arrays.stream(line.replaceAll("[^-?0-9]+", " ").trim().split(" "))
                    .mapToInt(Integer::parseInt).toArray();

            Beacon beacon = new Beacon(position[2], position[3]);
            Sensor sensor = new Sensor(position[0], position[1], beacon);

            sensors.add(sensor);
            beacons.add(beacon);
        }

        //printMap(sensors, beacons);

        int minX = sensors.stream()
                .mapToInt(sensor -> sensor.getX() - sensor.getReachableDistance()).min().orElseThrow();
        int maxX = sensors.stream()
                .mapToInt(sensor -> sensor.getX() + sensor.getReachableDistance()).max().orElseThrow();
        int minY = sensors.stream()
                .mapToInt(sensor -> sensor.getY() - sensor.getReachableDistance()).min().orElseThrow();
        int maxY = sensors.stream()
                .mapToInt(sensor -> sensor.getY() + sensor.getReachableDistance()).max().orElseThrow();

        int result = 0;
        int y = 2000000;
        //int y = 10;

        for (int x = minX; x <= maxX; x++) {
            Point point = new Point(x, y);
            if (!sensors.contains(point) &&
                !beacons.contains(point) &&
                !canHoldBeacon(point, sensors)) {
                result++;
            }
        }

        return result;
    }

    private static boolean isInLimits(Point point, int N) {
        return point.getX() >= 0 && point.getX() <= N && point.getY() >= 0 && point.getY() <= N;
    }

    private static long partTwo(List<String> lines) {
        Set<Sensor> sensors = new HashSet<>();

        for (String line : lines) {
            int[] position = Arrays.stream(line.replaceAll("[^-?0-9]+", " ").trim().split(" "))
                    .mapToInt(Integer::parseInt).toArray();

            Beacon beacon = new Beacon(position[2], position[3]);
            Sensor sensor = new Sensor(position[0], position[1], beacon);

            sensors.add(sensor);
        }

        int N = 4_000_000;

        Point edgePoint = null;

        out: for (Sensor sensor : sensors) {
            for (int i = 0; i <= sensor.getReachableDistance() + 1; i++) {
                int j = sensor.getReachableDistance() + 1 - i;

                edgePoint = new Point(sensor.getX() + i, sensor.getY() + j);
                if (isInLimits(edgePoint, N) && canHoldBeacon(edgePoint, sensors)) {
                    break out;
                }

                edgePoint = new Point(sensor.getX() - i, sensor.getY() - j);
                if (isInLimits(edgePoint, N) && canHoldBeacon(edgePoint, sensors)) {
                    break out;
                }

                edgePoint = new Point(sensor.getX() + i, sensor.getY() - j);
                if (isInLimits(edgePoint, N) && canHoldBeacon(edgePoint, sensors)) {
                    break out;
                }

                edgePoint = new Point(sensor.getX() - i, sensor.getY() + j);
                if (isInLimits(edgePoint, N) && canHoldBeacon(edgePoint, sensors)) {
                    break out;
                }
            }
        }

        if (edgePoint == null) {
            throw new IllegalStateException("Should never happen!");
        }

        return (long) edgePoint.getX() * N + edgePoint.getY();

    }

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Path.of("src/main/resources/input-day15.txt"));
        System.out.println(partOne(lines)); // 5688618
        System.out.println(partTwo(lines)); // 12625383204261
    }
}
