package io.github.solomon135975;

import java.util.Scanner;

/**
 * Minesweeps class
 * this class handles the game loop and related methods to the overall game state
 */
public class Minesweeps {

    private boolean gameWon = false;
    private boolean gameLost = false;
    private boolean gameOngoing = true;
    private final int numRows;
    private final int numCols;
    private final int numMines;
    private Grid grid;

    public Minesweeps (Difficulty difficulty) {
        this.numRows = difficulty.numRows;
        this.numCols = difficulty.numCols;
        this.numMines = difficulty.numMines; 
        this.grid = new Grid (numRows, numCols, numMines);
    }

    /**
     * manages the game loop
     * handles game state (gameOnGoing, gameWon, gameLost)
     */
    public void playMinesweeper() {

        String rules = "Q: quit\n" + "To flag: [row][col]f | e.g.: a4f\n" + 
                            "To reveal: [row][col] | e.g.: g7\n" + "Num flags left: ";

        Scanner scanner = new Scanner(System.in);
        MineGenerator mineGen = new MineGenerator();

        System.out.println (rules + grid.getNumFlags());
        grid.printGrid();

        String firstMove = handleFirstMove(scanner, rules);

        if (!gameOngoing) {
            System.out.println("thanks for playing");
            scanner.close();
            return;
        }
        
        mineGen.generateMines(grid, getRow(firstMove), getColumn(firstMove));
        executeMove(firstMove);

        while (gameOngoing) {
            System.out.println (rules + grid.getNumFlags());
            grid.printGrid();

            String move = readUserInput(scanner, rules);

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

    /**
     * takes the user's input while it isnt valid
     * @param scanner
     * @param rules
     * @return
     */
    private String readUserInput(Scanner scanner, String rules) {
        String move = scanner.nextLine().toLowerCase().trim().replace(" ", "");

        if (move.equals("q")) {
            gameOngoing = false;
            gameLost = true;
            return null;
        }

        while (!checkInputValid(move)) {
            System.out.println ("try enter a correct input this time" + rules + grid.getNumFlags());
            grid.printGrid();
            move = scanner.nextLine();
        }
        return move;
    }

    /**
     * handles the first move and validates it
     * it can't be a flag move because the mine generation is based on the first cell chosen
     * @param scanner
     * @param rules
     * @return
     */
    private String handleFirstMove(Scanner scanner, String rules) {
        String firstMove = readUserInput(scanner, rules);

        if (firstMove.equals("q")) {
            gameOngoing = false;
            gameLost = true;
            return null;
        }

        while (getAction(firstMove) == 'f') {
            System.out.println ("pls dont flag your first move :)" + rules + grid.getNumFlags());
            grid.printGrid();
            firstMove = scanner.nextLine();
        }
        return firstMove;
    }

    /**
     * this checks whether the game is still playable - i.e. has not been lost or won
     * @param row
     * @param col
     * @param action
     * @return
     */
    private boolean checkGameOngoing(int row, int col, char action) {
        if (hasPlayerWon()) {
            System.out.println("\nWIN!!!!!!!!!");
            gameOngoing = false;
            gameOver();
            return true;
        }
        else if (hasPlayerLost(row, col, action)) {
            System.out.println("\nWoopsies! You clicked a mine, game over :(");
            gameOngoing = false;
            gameOver();
            return true;
        }
        return false;
    }
    
    /**
     * executes a move by splitting the move into the action, row and column
     * @param move
     */
    private void executeMove(String move) {
        char action = getAction(move);
        int row = getRow(move);
        int column = getColumn(move);
        grid.makeMove(row, column, action);
    }

    /**
     * this checks if the input is valid
     * user can't pass an empty string, or a string longer than what's expected (3 but this will be adjusted for larger grids) or less than what's expected (min 2)
     * @param move
     * @return
     */
    private boolean checkInputValid(String move) {
        if (move == null || move.isEmpty()) {
            return false;
        }

        int maxLength = Integer.toString(numRows).length() + Integer.toString(numCols).length() + 1;
        //checks if move is out of bounds
        if (move.length() > maxLength || move.length() < 2) {
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

        String colStr = move.substring(1);
        if (colStr.matches("\\d+")) {  //checks if last char of the move is a positive number
            int col = Integer.parseInt(colStr);
            if (col < 0 || col > numCols -1) {
                return false;
            }
        }        
        else { return false; }

        return true;
    }

    /**
     * returns the action of the move
     * if f then return f for flag or null if it isn't f 
     * @param move
     * @return
     */
    private char getAction(String move) {
        return move.charAt(move.length() - 1) == 'f' ? 'f' : '\0';
    }

    /**
     * this gets the row of the move made 
     * each row is labelled by a letter so needs to subtract the value of 'a' to get the row number
     * @param move
     * @return
     */
    private int getRow(String move) {
        return move.charAt(0) - 'a'; 
    }

    /**
     * returns the column of the move
     * @param move
     * @return
     */
    private int getColumn(String move) {
        // String num = move.substring(1, move.length() - (getAction(move) == 'f' ? 1 : 0));
        // return Integer.parseInt(num);
        if (getAction(move) == 'f') {
            String num = move.substring(1, move.length()-1);
            return Integer.parseInt(num);
        }
        else {
            String num = move.substring(1);
            return Integer.parseInt(num);
        }
    }

    /**
     * if the game is over, it returns these statistics from the game and prints the revealed grid
     */
    private void gameOver() {
        System.out.println("You flagged " + grid.numMinesFlagged() + " mines out of " + numMines);
        System.out.println("You revealed " + (grid.getNumRevealedCells() - 1) + " safe cells of " + numCols*numRows);
        grid.printRevealedGrid();
    }

    /**
     * this checks if the player has won
     * to win, player needs to have revealed all the safe cells
     * @return
     */
    private boolean hasPlayerWon() {
        if (grid.allSafeCellsRevealed()) {
            gameWon = true;
            return true;
        }
        return false;
    }

    /**
     * checks if the player has lost
     * to lose, the player needs to have clicked a mine
     * @param row
     * @param col
     * @param action
     * @return
     */
    private boolean hasPlayerLost(int row, int col, char action) {
        if (grid.isMineClicked(row, col)) {
            gameLost = true;
            return true;
        }
        return false;
    }
}