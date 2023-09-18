package io.github.solomon135975;

import java.util.Scanner;

public class Minesweeps {

    private boolean gameWon = false;
    private boolean gameLost = false;
    private boolean gameOngoing = true;

    //to play the game
    public void playMinesweeper() {

        Scanner scanner = new Scanner(System.in);

        Grid grid = new Grid(9,9,10);

        String rules = "Q: quit\n" + "To flag: a3f\n" + "To reveal: a3\n" + "Num flags left: " + grid.getNumFlags();

        System.out.println (rules);
        grid.printGrid();
        String move = scanner.nextLine();

        move.replace(" ", "");

        while (!move.toLowerCase().trim().equals("q") && gameOngoing) {
            

        }

        scanner.close();
    }

    public boolean checkInputValid(String move) {
        if (move == null || move.isEmpty() || move.length() > 3) {
            return false;
        }
        
        char firstChar = move.charAt(0);
        if (firstChar < 'a' || firstChar > 'a' + 9) {
            return false;
        }

        char lastChar = move.charAt(move.length() - 1);
        if (lastChar != 'f' && (lastChar <'0' || lastChar > '9')) {
            return false;
        }

        

        return true;
    }

    public char getAction(String move) {
        return move.charAt(move.length() - 1) == 'f' ? 'f' : '\0';
    }

    public int getRow(String move) {
        return move.charAt(0) - 'a'; 
    }

    public int getColumn(String move) {
        String num = move.substring(1, move.length() - (getAction(move) == 'f' ? 1 : 0));
        return Integer.parseInt(num) - 1;
    }

    public void gameOver() {

    }

    public void checkWin() {
        
    }
}
