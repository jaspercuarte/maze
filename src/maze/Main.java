package maze;

import java.util.Random;
import java.util.Scanner;

public class Main {
    private static final int ROWS = 15;
    private static final int COLS = 30;
    private static int[][] maze = new int[ROWS][COLS];
    private static String[][] solution = new String[ROWS][COLS];
    private static boolean[][] visited = new boolean[ROWS][COLS];
    static Scanner sc = new Scanner(System.in);

    static final String PATH_COLOR = "\033[33m";
    static final String POINT_COLOR = "\033[32m";
    static final String RESET_COLOR = "\033[0m";

    public static void main(String[] args) {
        while (true) {
            for (int i = 0; i < ROWS; i++) {
                for (int j = 0; j < COLS; j++) {
                    visited[i][j] = false;
                }
            }
            generateMaze(0.3);

            for (int x = 0; x < ROWS; x++) {
                for (int y = 0; y < COLS; y++) {
                    solution[x][y] = (maze[x][y] == 1) ? "#" : " ";
                }
            }

            if (solveMaze(0, 0)) {
                for (int i = 0; i < ROWS; i++) {
                    for (int j = 0; j < COLS; j++) {
                        if (!solution[i][j].equals("#") && !solution[i][j].equals(" ")) {
                            solution[i][j] = " ";
                        }
                        visited[i][j] = false;
                    }
                }

                System.out.println("\nMAZE PROBLEM:");
                solution[0][0] = POINT_COLOR + "0" + RESET_COLOR;
                solution[ROWS-1][COLS-1] = POINT_COLOR + "1" + RESET_COLOR;
                printSolution();

                System.out.println("\nPress ENTER to reveal solution...");
                sc.nextLine();

                solveMaze(0, 0);

                solution[0][0] = POINT_COLOR + "0" + RESET_COLOR;
                solution[ROWS-1][COLS-1] = POINT_COLOR + "1" + RESET_COLOR;
                printSolution();
                break;
            } else {
                System.out.println("Generating Solvable Maze: No path found!");
                continue;
            }
        }
    }

    private static void generateMaze(double wallChance) {
        Random rand = new Random();
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                maze[i][j] = (rand.nextDouble() < wallChance) ? 1 : 0;
            }
        }
        maze[0][0] = 0;
        maze[ROWS - 1][COLS - 1] = 0;
    }

    private static boolean solveMaze(int x, int y) {
        if (x == ROWS-1 && y == COLS-1 && maze[x][y] == 0) {
            return true;
        }

        if (!isSafe(x, y) || visited[x][y]) return false;

        solution[x][y] = PATH_COLOR+"o"+RESET_COLOR;
        visited[x][y] = true;

        if (solveMaze(x, y + 1)) {
            // solution[x][y] = PATH_COLOR+">"+RESET_COLOR;
            return true;
        }
        if (solveMaze(x + 1, y)) {
            // solution[x][y] = PATH_COLOR+"V"+RESET_COLOR;
            return true;
        }
        if (solveMaze(x, y - 1)) {
            // solution[x][y] = PATH_COLOR+"<"+RESET_COLOR;
            return true;
        }
        if (solveMaze(x - 1, y)) {
            // solution[x][y] = PATH_COLOR+"^"+RESET_COLOR;
            return true;
        }

        solution[x][y] = " ";
        visited[x][y] = false;
        return false;
    }

    private static boolean isSafe(int x, int y) {
        return x >= 0 && x < ROWS && y >= 0 && y < COLS && maze[x][y] == 0; 
    }

    private static void printSolution() {
        System.out.print("#");
        for (int i = 0; i < COLS; i++) System.out.print("#");
        System.out.println("#");
        for (int x = 0; x < ROWS; x++) {
            for (int y = 0; y < COLS; y++) {
                System.out.print((y == 0? "#": "") + solution[x][y] + (y == COLS-1? "#": ""));
            } 
            System.out.println();
        }
        System.out.print("#");
        for (int i = 0; i < COLS; i++) System.out.print("#");
        System.out.println("#");
    }
}