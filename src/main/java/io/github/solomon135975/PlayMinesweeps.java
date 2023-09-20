package io.github.solomon135975;

import java.util.Scanner;

/**
 * PlayMinesweeps class
 * the function of this class is just to intialise a Minesweeps object called game and call it in the main method
 */
public class PlayMinesweeps {
    public static void main( String[] args ) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select the difficulty: 1. Easy, 2. Medium, 3. Hard (choose the num)");
        int choice = scanner.nextInt();
        while (choice != 1 && choice != 2 && choice != 3) {
            System.out.println("Choose the number correlating to the difficulty: 1. Easy, 2. Medium, 3. Hard");
            choice = scanner.nextInt();
        }

        Difficulty difficulty;
        switch (choice) {
            case 2: 
                difficulty = Difficulty.MEDIUM;
                break;
            case 3:
                difficulty = Difficulty.HARD;
                break;
            default:
                difficulty = Difficulty.EASY;
                break;
        }

        Minesweeps game = new Minesweeps(difficulty);
        game.playMinesweeper();
    }
}
