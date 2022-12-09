package org.javacadet.adventofcode.year2022;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/* Treetop Tree House */
public class Day08 {

    private static boolean isVisibleFromTop(int[][] grid, int row, int col) {
        int current = grid[row][col];

        while(--row >= 0) {
            if (grid[row][col] >= current) {
                return false;
            }
        }

        return true;
    }

    private static boolean isVisibleFromBottom(int[][] grid, int row, int col) {
        int current = grid[row][col];

        while(++row < grid.length) {
            if (grid[row][col] >= current) {
                return false;
            }
        }

        return true;
    }

    private static boolean isVisibleFromLeft(int[][] grid, int row, int col) {
        int current = grid[row][col];

        while(--col >= 0) {
            if (grid[row][col] >= current) {
                return false;
            }
        }

        return true;
    }

    private static boolean isVisibleFromRight(int[][] grid, int row, int col) {
        int current = grid[row][col];

        while(++col < grid[row].length) {
            if (grid[row][col] >= current) {
                return false;
            }
        }

        return true;
    }

    private static boolean isVisible(int[][] grid, int row, int col) {
        return isVisibleFromTop(grid, row, col) ||
                isVisibleFromBottom(grid, row, col) ||
                isVisibleFromLeft(grid, row, col) ||
                isVisibleFromRight(grid, row, col);
    }

    private static int partOne(List<String> lines) {
        int rows = lines.size();
        int cols = lines.get(0).length();

        /* Create grid */
        int[][] grid = new int[rows][cols];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                grid[row][col] = Integer.parseInt(lines.get(row).charAt(col) + "");
            }
        }

        /* Count visible trees */
        int count = (rows - 1) * 2 + (cols - 1) * 2;
        for (int row = 1; row < rows - 1; row++) {
            for (int col = 1; col < cols - 1; col++) {
                if (isVisible(grid, row, col)) {
                    count++;
                }
            }
        }

        return count;
    }

    private static int countVisibleTreesFromTop(int[][] grid, int row, int col) {
        int current = grid[row][col];
        int count = 0;

        while(--row >= 0) {
            count++;
            if (grid[row][col] >= current) {
                break;
            }
        }

        return count;
    }

    private static int countVisibleTreesFromBottom(int[][] grid, int row, int col) {
        int current = grid[row][col];
        int count = 0;

        while(++row < grid.length) {
            count++;
            if (grid[row][col] >= current) {
                break;
            }
        }

        return count;
    }

    private static int countVisibleTreesFromLeft(int[][] grid, int row, int col) {
        int current = grid[row][col];
        int count = 0;

        while(--col >= 0) {
            count++;
            if (grid[row][col] >= current) {
                break;
            }
        }

        return count;
    }

    private static int countVisibleTreesFromRight(int[][] grid, int row, int col) {
        int current = grid[row][col];
        int count = 0;

        while(++col < grid[row].length) {
            count++;
            if (grid[row][col] >= current) {
                break;
            }
        }

        return count;
    }

    private static int calculateScenicScore(int[][] grid, int row, int col) {
        return countVisibleTreesFromTop(grid, row, col) *
                countVisibleTreesFromBottom(grid, row, col) *
                countVisibleTreesFromLeft(grid, row, col) *
                countVisibleTreesFromRight(grid, row, col);
    }

    private static int partTwo(List<String> lines) {
        int rows = lines.size();
        int cols = lines.get(0).length();

        /* Create grid */
        int[][] grid = new int[rows][cols];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                grid[row][col] = Integer.parseInt(lines.get(row).charAt(col) + "");
            }
        }

        /* Find the highest scenic score */
        int maxScenicScore = 0;
        for (int row = 1; row < rows - 1; row++) {
            for (int col = 1; col < cols - 1; col++) {
                int scenicScore = calculateScenicScore(grid, row, col);
                maxScenicScore = Math.max(scenicScore, maxScenicScore);
            }
        }

        return maxScenicScore;
    }

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Path.of("src/main/resources/input-day08.txt"));
        System.out.println(partOne(lines)); // 1805
        System.out.println(partTwo(lines)); // 444528
    }
}
