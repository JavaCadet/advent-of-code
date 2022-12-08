package org.javacadet.adventofcode.year2022;

import java.io.*;
import java.nio.file.*;
import java.util.*;

/* No Space Left On Device */
public class Day07 {

    private static int partOne(List<String> lines) {
        Directory root = new Directory("/");
        Directory currentDir = root;

        for (String line : lines) {
            String[] parts = line.split(" ");

            if (parts[0].equals("$")) {
                if (parts[1].equals("cd")) {
                    if (parts[2].equals("/")) {
                        currentDir = root;
                    } else if (parts[2].equals("..")) {
                        currentDir = currentDir.getParent();
                    } else {
                        currentDir = currentDir.getChild(parts[2]);
                    }
                }
            } else {
                if (parts[0].equals("dir")) {
                    currentDir.addChild(new Directory(parts[1], currentDir));
                } else {
                    int fileSize = Integer.parseInt(parts[0]);
                    currentDir.addFile(fileSize);
                }
            }
        }

        return findTotalSize(root, 100_000);
    }

    public static int findTotalSize(Directory root, int limit) {
        int totalSize = 0;

        for (Directory directory : root.getChildren().values()) {
            int size = directory.getTotalSize();
            if (size <= limit) {
                totalSize += size;
            }
            totalSize += findTotalSize(directory, limit);
        }

        return totalSize;
    }


    private static int partTwo(List<String> lines) {
        Directory root = new Directory("/");
        Directory currentDir = root;

        for (String line : lines) {
            String[] parts = line.split(" ");

            if (parts[0].equals("$")) {
                if (parts[1].equals("cd")) {
                    if (parts[2].equals("/")) {
                        currentDir = root;
                    } else if (parts[2].equals("..")) {
                        currentDir = currentDir.getParent();
                    } else {
                        currentDir = currentDir.getChild(parts[2]);
                    }
                }
            } else {
                if (parts[0].equals("dir")) {
                    currentDir.addChild(new Directory(parts[1], currentDir));
                } else {
                    int fileSize = Integer.parseInt(parts[0]);
                    currentDir.addFile(fileSize);
                }
            }
        }

        int minSize = 30_000_000 - (70_000_000 - root.getTotalSize());
        System.out.println("LIMIT: " + minSize);
        return findSmallest(root, minSize);
    }

    public static int findSmallest(Directory root, int minSize) {
        int smallest = 0;

        for (Directory directory : root.getChildren().values()) {
            int size = directory.getTotalSize();

            if (size >= minSize && (smallest == 0 || size < smallest)) {
                smallest = size;
            }

            size = findSmallest(directory, minSize);

            if (size >= minSize && (smallest == 0 || size < smallest)) {
                smallest = size;
            }
        }

        return smallest;
    }

    public static void main(String[] args) throws IOException {
        String fileName = "src/main/resources/input-day07.txt";
        List<String> lines = Files.readAllLines(Path.of(fileName));
        System.out.println(partOne(lines)); // 1749646
        System.out.println(partTwo(lines)); // 1498966
    }
}

class Directory {

    private String name;
    private int size;
    private Directory parent;
    private Map<String, Directory> children;

    public Directory(String name) {
        this.name = name;
        this.size = 0;
        this.children = new HashMap<>();
    }

    public Directory(String name, Directory parent) {
        this(name);
        this.parent = parent;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Directory getParent() {
        return this.parent;
    }

    public void setParent(Directory parent) {
        this.parent = parent;
    }

    public Map<String, Directory> getChildren() {
        return this.children;
    }

    public void setChildren(Map<String, Directory> children) {
        this.children = children;
    }

    public Directory getChild(String name) {
        return this.children.get(name);
    }

    public void addChild(Directory directory) {
        this.children.put(directory.getName(), directory);
    }

    public void addFile(int size) {
        this.size += size;
    }

    public int getTotalSize() {
        int totalSum = this.size;

        for (Directory child : children.values()) {
            totalSum += child.getTotalSize();
        }

        return totalSum;
    }
}
