package io.github.solomon135975;

/**
 * difficult enum to manage the difficulty levels
 * 
 * 9x9 with 10 mines if easy
 * 16x16 with 40 mines if medium
 * 25x36 with 99 mines if hard
 */
public enum Difficulty {
    EASY(9,9,10),
    MEDIUM(16,16,40),
    HARD(25,36,99);

    public final int numRows;
    public final int numCols;
    public int numMines;

    Difficulty(int rows, int cols, int mines) {
        this.numRows = rows;
        this.numCols = cols;
        this.numMines = mines;
    }
}
