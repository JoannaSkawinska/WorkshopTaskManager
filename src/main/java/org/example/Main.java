package org.example;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    static String[][] tasks;

    public static void main(String[] args) {

        String description = null;
        String dueDate = null;
        String priority = null;

      tasks = loadFromFileToTable("tasks.csv");
        printTab(tasks);
       addTask(description, dueDate, priority);
        saveToFile("tasks.csv",tasks);

    }

    public static void showOptions(String[] tab) {

    }

    public static String[][] loadFromFileToTable(String filename) {
        Path taskFile = Paths.get(filename);

        if (!Files.exists(taskFile)) {
            System.out.println("Given file does not exist!");
        }

        String[][] taskTable = null;

        try {

            int i = 0;
            List<String> taskStrings = Files.readAllLines(taskFile);

            // [][] = [poziom][pion]
            // [][] =[pojedynczy element między przecinkami - kolumna][każdy z elementów między przecinkami po kolei - split by coma]

            taskTable = new String[taskStrings.size()][taskStrings.get(0).split(",").length];

            for (i = 0; i < taskStrings.size(); i++) {
                String[] split = taskStrings.get(i).split(",");
                for (int j = 0; j < split.length; j++) {
                    taskTable[i][j] = split[j];

                }

            }
        } catch (IOException e) {
            e.getMessage();
        }


        return taskTable;
    }

    public static void printTab(String[][] taskTab) {
        for (int i = 0; i < taskTab.length; i++) {
            System.out.print(i + " : ");
            for (int j = 0; j < taskTab[i].length; j++) {
                System.out.print(taskTab[i][j] + " ");
            }
            System.out.println("");
        }
    }

    public static void saveToFile(String fileName, String[][]tabFinished) {
        Path file = Paths.get(fileName);

        String[] lines = new String[tasks.length];
        for (int i = 0; i < tabFinished.length; i++) {
            lines[i] = String.join(",",tabFinished[i]);
        }

        try {
            Files.write(file, Arrays.asList(lines));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public static void addTask(String description, String dueDate, String priority) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please provide description for new task:");
       description = scanner.nextLine();
        System.out.println("Please add due date for new task:");
        dueDate = scanner.nextLine();
        System.out.println("Is your task important? true/false");
       priority = scanner.nextLine();
        tasks = Arrays.copyOf(tasks, tasks.length + 1);
        tasks[tasks.length-1] = new String[3];
        tasks[tasks.length-1][0] = description;
        tasks[tasks.length-1][1] = dueDate;
        tasks[tasks.length-1][2] = priority;

    }

    public static void deleteTask(String[] args) {

    }

    public static void listTasks(String[] args) {

    }

    public static void quitApp(String[] args) {

    }

}