package io.github.solomon135975;

public class Grid {
    private int numRows;
    private int numColumns;

    private int totMines;
    private int numFlags;

    private Cell[][] grid;

    public Grid (int numRows, int numColumns, int totMines) {
        this.numRows = numRows;
        this.numColumns = numColumns; 
        this.totMines = totMines;
        this.numFlags = totMines;

        grid = new Cell[numColumns][numRows];

        for (int y = 0; y < numColumns; y++) {
            for (int x = 0; x < numRows; x++) {
                grid[y][x] = new Cell();
            }
        }
    }


}
