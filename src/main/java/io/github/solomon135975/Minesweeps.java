package io.github.solomon135975;

import java.util.Scanner;

public class Minesweeps {

    private boolean gameWon = false;
    private boolean gameLost = false;
    private boolean gameOngoing = true;
    private final int numRows = 9;
    private final int numCols = 9;
    private final int numMines = 10;
    private Grid grid = new Grid(numRows,numCols,numMines);
    private String rules = "Q: quit\n" + "To flag: [row][col]f | e.g.: a4f\n" + 
                            "To reveal: [row][col] | e.g.: g7\n" + "Num flags left: " + grid.getNumFlags()+ "\n";

    //to play the game
    public void playMinesweeper() {

        Scanner scanner = new Scanner(System.in);
        MineGenerator mineGen = new MineGenerator();

        System.out.println (rules);
        grid.printGrid();

        String firstMove = handleFirstMove(scanner);

        if (!gameOngoing) {
            System.out.println("thanks for playing");
            scanner.close();
            return;
        }
        
        mineGen.generateMines(grid, getRow(firstMove), getColumn(firstMove));
        executeMove(firstMove);

        while (gameOngoing) {
            System.out.println (rules);
            grid.printGrid();

            String move = readUserInput(scanner);

            if (!gameOngoing) {
                System.out.println("thanks for playing");
                scanner.close();
                continue;
            }

            executeMove(move);

            checkGameOngoing(getRow(move), getColumn(move), getAction(move));
        }
        scanner.close();
    }

    private String readUserInput(Scanner scanner) {
        String move = scanner.nextLine().toLowerCase().trim().replace(" ", "");

        if (move.equals("q")) {
            gameOngoing = false;
            gameLost = true;
            return null;
        }

        while (!checkInputValid(move)) {
            System.out.println ("try enter a correct input this time" + rules);
            grid.printGrid();
            move = scanner.nextLine();
        }
        return move;
    }

    private String handleFirstMove(Scanner scanner) {
        String firstMove = readUserInput(scanner);

        if (firstMove.equals("q")) {
            gameOngoing = false;
            gameLost = true;
            return null;
        }

        while (getAction(firstMove) == 'f') {
            System.out.println ("pls dont flag your first move :)" + rules);
            grid.printGrid();
            firstMove = scanner.nextLine();
        }
        return firstMove;
    }

    private boolean checkGameOngoing(int row, int col, char action) {
        if (hasPlayerWon()) {
            System.out.println("WIN!!!!!!!!!");
            gameOngoing = false;
            gameOver();
            return true;
        }
        else if (hasPlayerLost(row, col, action)) {
            System.out.println("Woopsies! You clicked a mine, game over :(");
            gameOngoing = false;
            gameOver();
            return true;
        }
        return false;
    }
    
    private void executeMove(String move) {
        char action = getAction(move);
        int row = getRow(move);
        int column = getColumn(move);
        grid.makeMove(row, column, action);
    }

    private boolean checkInputValid(String move) {
        if (move == null || move.isEmpty() || move.length() > 3) {
            return false;
        }
        
        char firstChar = move.charAt(0);
        if (firstChar < 'a' || firstChar > 'a' + numRows -1) {
            return false;
        }

        char lastChar = move.charAt(move.length() - 1);
        if (lastChar == 'f') {
            move = move.substring(0, move.length()-1);
        } 
        else if (lastChar < '0' || lastChar > (char)(numCols + '0' - 1)) {
            return false;
        }


        String colStr = move.substring(1);
        if (colStr.matches("\\d+")) {
            int col = Integer.parseInt(colStr);
            if (col < 0 || col > numCols -1) {
                return false;
            }
        }        
        else { return false; }

        return true;
    }

    private char getAction(String move) {
        return move.charAt(move.length() - 1) == 'f' ? 'f' : '\0';
    }

    private int getRow(String move) {
        return move.charAt(0) - 'a'; 
    }

    private int getColumn(String move) {
        String num = move.substring(1, move.length() - (getAction(move) == 'f' ? 1 : 0));
        return Integer.parseInt(num);
    }

    private void gameOver() {
        System.out.println("You flagged " + grid.numMinesFlagged() + " mines out of " + numMines);
        System.out.println("You revealed " + (grid.getNumRevealedCells() - 1) + " safe cells of " + numCols*numRows);
        grid.printRevealedGrid();
        //print revealed grid
    }

    private boolean hasPlayerWon() {
        if (grid.allSafeCellsRevealed()) {
            gameWon = true;
            return true;
        }
        return false;
    }

    private boolean hasPlayerLost(int row, int col, char action) {
        if (grid.isMineClicked(row, col)) {
            gameLost = true;
            return true;
        }
        return false;
    }
}