package io.github.solomon135975;

/**
 * PlayMinesweeps class
 * the function of this class is just to intialise a Minesweeps object called game and call it in the main method
 */
public class PlayMinesweeps {

    private static Minesweeps game = new Minesweeps();

    public static void main( String[] args )
    {
        game.playMinesweeper();
    }
}
