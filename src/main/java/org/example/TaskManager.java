package org.example;


import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TaskManager {

    static final String FILE_NAME = "tasks.csv";
    static String[][] tasks;
    static final String[] OPTIONS = {"[a]dd", "[d]elete", "[l]ist", "[q]uit"};

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String description = null;
        String dueDate = null;
        String priority = null;
        String delete = null;


        loadFromFileToTable("tasks.csv");

        listOptions(OPTIONS);

        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();

            switch (input) {
                case "quit":
                case "q":
                    quitApp();
                    break;
                case "add":
                case "a":
                    addTask(description, dueDate, priority);
                    break;
                case "delete":
                case "d":
                    deleteTask(delete);
                    break;
                case "list":
                case "l":

                    printTab(tasks);
                    break;
                default:
                    System.out.println("Please select a correct option.");
            }

            listOptions(OPTIONS);
        }

    }


    public static String[][] loadFromFileToTable(String filename) {
        Path taskFile = Paths.get(filename);

        if (!Files.exists(taskFile)) {
            System.out.println("Given file does not exist!");
        }
        tasks = null;

        try {

            int i = 0;
            List<String> taskStrings = Files.readAllLines(taskFile);

            // [][] = [poziom][pion]
            // [][] =[pojedynczy element między przecinkami - kolumna][każdy z elementów między przecinkami po kolei - split by coma]
            tasks = new String[taskStrings.size()][taskStrings.get(0).split(",").length];

            for (i = 0; i < taskStrings.size(); i++) {
                String[] split = taskStrings.get(i).split(",");
                for (int j = 0; j < split.length; j++) {
                    tasks[i][j] = split[j];

                }

            }
        } catch (IOException e) {
            e.getMessage();
        }


        return tasks;
    }

    public static void printTab(String[][] taskTab) {

        System.out.println("Task list:" + "\n");

        for (int i = 0; i < taskTab.length; i++) {
            System.out.print(i + " : ");
            for (int j = 0; j < taskTab[i].length; j++) {
                System.out.print(taskTab[i][j] + " ");
            }
            System.out.println("");
        }
    }

    public static void saveToFile(String fileName, String[][] tabFinished) {
        Path file = Paths.get(fileName);

        String[] lines = new String[tasks.length];
        for (int i = 0; i < tabFinished.length; i++) {
            lines[i] = String.join(",", tabFinished[i]);
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
        tasks[tasks.length - 1] = new String[3];
        tasks[tasks.length - 1][0] = description;
        tasks[tasks.length - 1][1] = dueDate;
        tasks[tasks.length - 1][2] = priority;

    }

    public static void deleteTask(String delete) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please provide number of task to delete.");
        delete = scanner.nextLine();


        int index = 0;

        if (NumberUtils.isParsable(delete)) {
            index = Integer.parseInt(delete);

        } else {
            System.out.println("Please provide a number");
           delete = scanner.nextLine();
        }
        if (index < 0) {
            System.out.println("Please provide a number greater or equal 0");
            scanner.nextLine();

        }

        if (index >= 0) {

            try {
                if (index < tasks.length) {
                    tasks = ArrayUtils.remove(tasks, index);
                    System.out.println("Task deleted successfully");
                }


            } catch (ArrayIndexOutOfBoundsException ex) {
                System.out.println("Task with the number provided does not exist");
            }

        }

    }

    public static void listOptions(String[] options) {
        System.out.println("\n"+"Please select an option: ");

        for (String option : options) {
            System.out.println(option);
        }

    }

    public static void quitApp() {

        saveToFile(FILE_NAME, tasks);
        System.out.println("Thank you for using the application! Good bye!");
        System.exit(0);

    }

}